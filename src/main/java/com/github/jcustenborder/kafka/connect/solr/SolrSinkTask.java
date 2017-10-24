/**
 * Copyright © 2016 Jeremy Custenborder (jcustenborder@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jcustenborder.kafka.connect.solr;


import com.github.jcustenborder.kafka.connect.utils.VersionUtil;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.connect.errors.RetriableException;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class SolrSinkTask<CONFIG extends SolrSinkConnectorConfig> extends SinkTask {
  private static final Logger log = LoggerFactory.getLogger(SolrSinkTask.class);
  protected CONFIG config;

  protected abstract CONFIG config(Map<String, String> settings);

  SolrInputDocumentConverter converter = new SolrInputDocumentConverter();

  @Override
  public void start(Map<String, String> settings) {
    log.info("Starting");
    this.config = config(settings);
    log.info("Creating Solr client.");
  }

  @Override
  public void put(Collection<SinkRecord> records) {
    Map<String, Operations> operationsLookup = new HashMap<>();

    for (SinkRecord record : records) {
      final String id = null != record.key() ? record.key().toString() : null;
      final Operations operations = operationsLookup.computeIfAbsent(record.topic(), s -> new Operations(config));
      if (null == record.value() && null != id) {
        log.trace("put() - record.value() is null. Deleting '{}'", id);
        operations.delete().deleteById(id);
      } else {
        SolrInputDocument document = converter.convert(record.value());
        operations.update().add(document);
      }
    }

    try {
      for (final Map.Entry<String, Operations> kvp : operationsLookup.entrySet()) {
        final String topic = kvp.getKey();
        final Operations operations = kvp.getValue();

        for (UpdateRequest updateRequest : operations.operations()) {
          log.trace("put() - {}", updateRequest);
          process(topic, updateRequest);
        }
      }
    } catch (SolrServerException | IOException ex) {
      throw new RetriableException(ex);
    }
  }

  protected abstract void process(String topic, UpdateRequest updateRequest) throws IOException, SolrServerException;


  @Override
  public void flush(Map<TopicPartition, OffsetAndMetadata> map) {

  }

  @Override
  public String version() {
    return VersionUtil.version(this.getClass());
  }
}

/**
 * Copyright © 2016 Jeremy Custenborder (jcustenborder@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jcustenborder.kafka.connect.solr;


import com.github.jcustenborder.kafka.connect.utils.VersionUtil;
import org.apache.kafka.connect.errors.RetriableException;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class SolrSinkTask<CONFIG extends SolrSinkConnectorConfig, BUILDER extends SolrInputDocumentBuilder<CONFIG>> extends SinkTask {
  private static final Logger log = LoggerFactory.getLogger(SolrSinkTask.class);
  protected CONFIG config;
  protected SolrClient client;
  protected BUILDER documentBuilder;

  protected abstract CONFIG config(Map<String, String> settings);

  protected abstract BUILDER documentBuilder();

  protected abstract SolrClient client();

  @Override
  public void start(Map<String, String> settings) {
    log.info("Starting");
    this.config = config(settings);
    this.documentBuilder = documentBuilder();
    log.info("Creating Solr client.");
    this.client = client();
    log.info("Created Solr client {}.", this.client);
  }

  @Override
  public void put(Collection<SinkRecord> records) {

    List<UpdateRequest> requests = this.documentBuilder.build(records);

    for (UpdateRequest updateRequest : requests) {
      if (updateRequest.getDocuments() != null && !updateRequest.getDocuments().isEmpty()) {
        try {
          updateRequest.setCommitWithin(this.config.commitWithin);
          log.trace("put() - Sending {} documents to solr.", updateRequest.getDocuments().size());
          UpdateResponse response = updateRequest.process(this.client);
          if (null != response && log.isTraceEnabled()) {
            log.trace("put() - ElapsedTime = {} QTime = {}", response.getElapsedTime(), response.getQTime());
          }
        } catch (SolrServerException e) {
          throw new RetriableException("Exception thrown while processing request", e);
        } catch (IOException e) {
          throw new RetriableException("Exception thrown while processing request", e);
        }
      } else {
        log.trace("put() - no documents to post.");
      }
    }
  }

  @Override
  public void stop() {
    try {
      if (null != this.client) {
        this.client.close();
      }
    } catch (IOException e) {
      log.error("Exception thrown while closing client.", e);
    }
  }

  @Override
  public String version() {
    return VersionUtil.version(this.getClass());
  }
}

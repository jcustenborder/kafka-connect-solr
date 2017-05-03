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

import com.google.common.base.Preconditions;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;

abstract class SolrInputDocumentBuilder<CONFIG extends SolrSinkConnectorConfig> {
  private static final Logger log = LoggerFactory.getLogger(SolrInputDocumentBuilder.class);

  protected final CONFIG config;

  SolrInputDocumentBuilder(CONFIG config) {
    this.config = config;
  }

  protected SolrInputDocument build(SinkRecord record) {
    Preconditions.checkNotNull(record, "record cannot be null.");
    SolrInputDocument document = new SolrInputDocument();

    if (record.value() instanceof Map) {
      if (log.isTraceEnabled()) {
        log.trace("build() - Processing {}:{}:{} as Map",
            record.topic(),
            record.kafkaPartition(),
            record.kafkaOffset()
        );
      }
      Map<String, Object> map = (Map) record.value();
      for (String key : map.keySet()) {
        Object value = map.get(key);
        log.trace("build() - Setting {} to {}.", key, value);
        document.addField(key.toString(), value);
      }
    } else if (record.value() instanceof Struct) {
      if (log.isTraceEnabled()) {
        log.trace("build() - Processing {}:{}:{} as Struct",
            record.topic(),
            record.kafkaPartition(),
            record.kafkaOffset()
        );
      }
      Struct struct = (Struct) record.value();
      List<Field> fields = struct.schema().fields();
      for (Field field : fields) {
        Object value = struct.get(field);
        log.trace("build() - Setting {} to {}.", field.name(), value);
        document.addField(field.name(), value);
      }
    } else {
      String message = String.format(
          "%s:%s:%s has an unsupported type for a value. Only Struct or Map are supported.",
          record.topic(), record.kafkaPartition(), record.kafkaOffset()
      );
      throw new UnsupportedOperationException(message);
    }
    return document;
  }

  protected UpdateRequest newUpdateRequest() {
    UpdateRequest updateRequest = new UpdateRequest();
    if (this.config.useBasicAuthentication) {
      log.trace("put() - Configuring UpdateRequest to use basic authentication. Username = '{}'", this.config.username);
      updateRequest.setBasicAuthCredentials(this.config.username, this.config.password);
    }
    return updateRequest;
  }

  public abstract List<UpdateRequest> build(Collection<SinkRecord> records);
}

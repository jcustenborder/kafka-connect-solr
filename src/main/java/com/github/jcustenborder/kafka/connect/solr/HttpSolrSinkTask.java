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


import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class HttpSolrSinkTask extends SolrSinkTask<HttpSolrSinkConnectorConfig, HttpSolrInputDocumentBuilder> {
  private static final Logger log = LoggerFactory.getLogger(HttpSolrSinkTask.class);

  @Override
  protected HttpSolrSinkConnectorConfig config(Map settings) {
    return new HttpSolrSinkConnectorConfig(settings);
  }

  @Override
  protected HttpSolrInputDocumentBuilder documentBuilder() {
    return new HttpSolrInputDocumentBuilder(this.config);
  }

  @Override
  protected SolrClient client() {
    ConcurrentUpdateSolrClient.Builder builder = new ConcurrentUpdateSolrClient.Builder(this.config.solrUrl)
        .withQueueSize(this.config.queueSize)
        .withThreadCount(this.config.threadCount);
    return builder.build();
  }

  @Override
  public void flush(Map<TopicPartition, OffsetAndMetadata> map) {

  }
}

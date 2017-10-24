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


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpSolrSinkTask extends SolrSinkTask<HttpSolrSinkConnectorConfig> {
  private static final Logger log = LoggerFactory.getLogger(HttpSolrSinkTask.class);

  @Override
  protected HttpSolrSinkConnectorConfig config(Map settings) {
    return new HttpSolrSinkConnectorConfig(settings);
  }


  Map<String, ConcurrentUpdateSolrClient> clients = new HashMap<>();

  SolrClient client(String topic) {
    return this.clients.computeIfAbsent(topic, s -> {
      ConcurrentUpdateSolrClient.Builder builder = new ConcurrentUpdateSolrClient.Builder(this.config.solrUrl)
          .withQueueSize(this.config.queueSize)
          .withThreadCount(this.config.threadCount);
      ConcurrentUpdateSolrClient client = builder.build();
      return client;
    });
  }


  @Override
  protected void process(String topic, UpdateRequest updateRequest) throws IOException, SolrServerException {
    SolrClient client = client(topic);
    UpdateResponse response = updateRequest.process(client);
    log.trace("process() - qtime = {} elapsedTime = {}", response.getQTime(), response.getElapsedTime());
  }

  @Override
  public void stop() {
    for (ConcurrentUpdateSolrClient client : this.clients.values()) {
      try {
        client.shutdownNow();
        client.blockUntilFinished();
        client.close();
      } catch (Exception e) {
        log.error("Exception thrown while closing client.", e);
      }
    }
  }
}

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


import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CloudSolrSinkTask extends SolrSinkTask<CloudSolrSinkConnectorConfig> {
  private static final Logger log = LoggerFactory.getLogger(CloudSolrSinkTask.class);

  @Override
  protected CloudSolrSinkConnectorConfig config(Map settings) {
    return new CloudSolrSinkConnectorConfig(settings);
  }

  CloudSolrClient client;

  @Override
  public void start(Map<String, String> settings) {
    super.start(settings);
    CloudSolrClient.Builder builder = new CloudSolrClient.Builder();
    builder.withZkHost(this.config.zookeeperHosts);
    builder.withZkChroot(this.config.zookeeperChroot);
    builder.withConnectionTimeout(this.config.solrConnectTimeoutMs);
    builder.withSocketTimeout(this.config.solrSocketTimeoutMs);
    this.client = builder.build();
    this.client.setZkConnectTimeout(this.config.zookeeperConnectTimeoutMs);
    this.client.setZkClientTimeout(this.config.zookeeperClientTimeoutMs);
    this.client.setRetryExpiryTime((int) TimeUnit.SECONDS.convert(this.config.zookeeperRetryExpiryTimeMs, TimeUnit.MILLISECONDS));
  }

  @Override
  protected void process(String topic, UpdateRequest updateRequest) throws IOException, SolrServerException {
    final String collection = collection(topic);
    updateRequest.setParam("collection", collection);
    UpdateResponse response = updateRequest.process(client);
    log.trace("process() - qtime = {} elapsedTime = {}", response.getQTime(), response.getElapsedTime());
  }

  private String collection(String topic) {
    return topic;
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
}

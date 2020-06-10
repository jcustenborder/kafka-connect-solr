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

import com.github.jcustenborder.kafka.connect.utils.config.ConfigKeyBuilder;
import org.apache.kafka.common.config.ConfigDef;

import java.util.List;
import java.util.Map;

class CloudSolrSinkConnectorConfig extends SolrSinkConnectorConfig {

  public static final String ZOOKEEPER_HOSTS_CONFIG = "solr.zookeeper.hosts";
  public static final String ZOOKEEPER_CHROOT_CONFIG = "solr.zookeeper.chroot";
  public static final String ZOOKEEPER_CONNECT_TIMEOUT_CONFIG = "solr.zookeeper.connect.timeout.ms";
  public static final String ZOOKEEPER_CLIENT_TIMEOUT_CONFIG = "solr.zookeeper.client.timeout.ms";
  public static final String ZOOKEEPER_RETRY_EXPIRY_TIME_CONFIG = "solr.zookeeper.retry.expiry.time.ms";
  public static final String SOLR_CONNECT_TIMEOUT_CONFIG = "solr.connect.timeout.ms";
  public static final String SOLR_SOCKET_TIMEOUT_CONFIG = "solr.socket.timeout.ms";


  private static final String ZOOKEEPER_HOSTS_DOC = "Zookeeper hosts that are used to store solr configuration.";
  private static final String ZOOKEEPER_CHROOT_DOC = "Chroot within solr for the zookeeper configuration.";
  private static final String ZOOKEEPER_CONNECT_TIMEOUT_DOC = "Set the connect timeout to the zookeeper ensemble in ms.";
  private static final String ZOOKEEPER_CLIENT_TIMEOUT_DOC = "Set the timeout to the zookeeper ensemble in ms.";
  private static final String ZOOKEEPER_RETRY_EXPIRY_TIME_DOC = "This is the time to wait to refetch the " +
      "state after getting the same state version from ZK in ms.";
  private static final String SOLR_CONNECT_TIMEOUT_DOC = "Set the connect timeout to the solr in ms.";
  private static final String SOLR_SOCKET_TIMEOUT_DOC = "Set the solr read timeout on all sockets in ms.";

  public final List<String> zookeeperHosts;
  public final String zookeeperChroot;
  public final int zookeeperConnectTimeoutMs;
  public final int zookeeperClientTimeoutMs;
  public final int zookeeperRetryExpiryTimeMs;
  public final int solrConnectTimeoutMs;
  public final int solrSocketTimeoutMs;

  protected CloudSolrSinkConnectorConfig(Map<String, String> props) {
    super(config(), props);
    this.zookeeperHosts = this.getList(ZOOKEEPER_HOSTS_CONFIG);
    this.zookeeperChroot = this.getString(ZOOKEEPER_CHROOT_CONFIG);
    this.zookeeperConnectTimeoutMs = getInt(ZOOKEEPER_CONNECT_TIMEOUT_CONFIG);
    this.zookeeperClientTimeoutMs = getInt(ZOOKEEPER_CLIENT_TIMEOUT_CONFIG);
    this.zookeeperRetryExpiryTimeMs = getInt(ZOOKEEPER_RETRY_EXPIRY_TIME_CONFIG);
    this.solrConnectTimeoutMs = getInt(SOLR_CONNECT_TIMEOUT_CONFIG);
    this.solrSocketTimeoutMs = getInt(SOLR_SOCKET_TIMEOUT_CONFIG);
  }


  public static ConfigDef config() {
    return SolrSinkConnectorConfig.config()
        .define(
            ConfigKeyBuilder.of(ZOOKEEPER_HOSTS_CONFIG, ConfigDef.Type.LIST)
            .importance(ConfigDef.Importance.HIGH)
            .documentation(ZOOKEEPER_HOSTS_DOC)
            .group(CONNECTION_GROUP)
            .build()
        ).define(
            ConfigKeyBuilder.of(ZOOKEEPER_CHROOT_CONFIG, ConfigDef.Type.STRING)
                .importance(ConfigDef.Importance.HIGH)
                .documentation(ZOOKEEPER_CHROOT_DOC)
                .group(CONNECTION_GROUP)
                .defaultValue(null)
                .build()
        ).define(
            ConfigKeyBuilder.of(ZOOKEEPER_CONNECT_TIMEOUT_CONFIG, ConfigDef.Type.INT)
                .importance(ConfigDef.Importance.LOW)
                .documentation(ZOOKEEPER_CONNECT_TIMEOUT_DOC)
                .group(CONNECTION_GROUP)
                .defaultValue(15000)
                .build()
        ).define(
            ConfigKeyBuilder.of(ZOOKEEPER_CLIENT_TIMEOUT_CONFIG, ConfigDef.Type.INT)
                .importance(ConfigDef.Importance.LOW)
                .documentation(ZOOKEEPER_CLIENT_TIMEOUT_DOC)
                .group(CONNECTION_GROUP)
                .defaultValue(45000)
                .build()
        ).define(
            ConfigKeyBuilder.of(ZOOKEEPER_RETRY_EXPIRY_TIME_CONFIG, ConfigDef.Type.INT)
                .importance(ConfigDef.Importance.LOW)
                .documentation(ZOOKEEPER_RETRY_EXPIRY_TIME_DOC)
                .group(CONNECTION_GROUP)
                .defaultValue(3000)
                .build()
        ).define(
             ConfigKeyBuilder.of(SOLR_CONNECT_TIMEOUT_CONFIG, ConfigDef.Type.INT)
                 .importance(ConfigDef.Importance.LOW)
                 .documentation(SOLR_CONNECT_TIMEOUT_DOC)
                 .group(CONNECTION_GROUP)
                 .defaultValue(15000)
                 .build()
        ).define(
             ConfigKeyBuilder.of(SOLR_SOCKET_TIMEOUT_CONFIG, ConfigDef.Type.INT)
                 .importance(ConfigDef.Importance.LOW)
                 .documentation(SOLR_SOCKET_TIMEOUT_DOC)
                 .group(CONNECTION_GROUP)
                 .defaultValue(120000)
                 .build()
        );
  }
}

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
  public static final String COLLECTION_NAME_CONFIG = "solr.collection.name";
  private static final String ZOOKEEPER_HOSTS_DOC = "Zookeeper hosts that are used to store solr configuration.";
  private static final String ZOOKEEPER_CHROOT_DOC = "Chroot within solr for the zookeeper configuration.";

  public final List<String> zookeeperHosts;
  public final String zookeeperChroot;

  protected CloudSolrSinkConnectorConfig(Map<String, String> props) {
    super(config(), props);
    this.zookeeperHosts = this.getList(ZOOKEEPER_HOSTS_CONFIG);
    this.zookeeperChroot = this.getString(ZOOKEEPER_CHROOT_CONFIG);
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
        );
  }
}

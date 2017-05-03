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

import org.apache.kafka.common.config.ConfigDef;

import java.util.List;
import java.util.Map;

class CloudSolrSinkConnectorConfig extends SolrSinkConnectorConfig {

  public static final String ZOOKEEPER_HOSTS_CONFIG = "solr.zookeeper.hosts";
  public static final String ZOOKEEPER_CHROOT_CONFIG = "solr.zookeeper.chroot";
  public static final String COLLECTION_NAME_CONFIG = "solr.collection.name";
  private static final String ZOOKEEPER_HOSTS_DOC = "Zookeeper hosts that are used to store solr configuration.";
  private static final String ZOOKEEPER_CHROOT_DOC = "Chroot within solr for the zookeeper configuration.";
  private static final String COLLECTION_NAME_DOC = "Name of the solr collection to write to.";

  public final List<String> zookeeperHosts;
  public final String zookeeperChroot;
  public final String collectionName;

  protected CloudSolrSinkConnectorConfig(Map<String, String> props) {
    super(config(), props);
    this.zookeeperHosts = this.getList(ZOOKEEPER_HOSTS_CONFIG);
    this.zookeeperChroot = this.getString(ZOOKEEPER_CHROOT_CONFIG);
    this.collectionName = this.getString(COLLECTION_NAME_CONFIG);
  }


  public static ConfigDef config() {
    return SolrSinkConnectorConfig.config()
        .define(ZOOKEEPER_HOSTS_CONFIG, ConfigDef.Type.LIST, ConfigDef.Importance.HIGH, ZOOKEEPER_HOSTS_DOC)
        .define(ZOOKEEPER_CHROOT_CONFIG, ConfigDef.Type.STRING, null, ConfigDef.Importance.HIGH, ZOOKEEPER_CHROOT_DOC)
        .define(COLLECTION_NAME_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, COLLECTION_NAME_DOC);
  }
}

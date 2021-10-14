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
import com.google.common.base.Strings;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

class SolrSinkConnectorConfig extends AbstractConfig {
  public static final String SOLR_COMMIT_WITHIN_CONFIG = "solr.commit.within";
  public static final String SOLR_USERNAME_CONFIG = "solr.username";
  public static final String SOLR_PASSWORD_CONFIG = "solr.password";
  public static final String SOLR_DELETE_DOCUMENTS_CONFIG = "solr.delete.documents.enabled";
  public static final String SOLR_CONNECT_TIMEOUT_CONFIG = "solr.connect.timeout.ms";
  public static final String SOLR_SOCKET_TIMEOUT_CONFIG = "solr.socket.timeout.ms";

  static final String SOLR_USERNAME_DOC = "The username to use for basic authentication.";
  static final String SOLR_PASSWORD_DOC = "The password to use for basic authentication.";
  static final String SOLR_COMMIT_WITHIN_DOC = "Configures Solr UpdaterRequest for a commit within " +
      "the requested number of milliseconds. -1 disables the commit within setting and relies on " +
      "the standard Solr commit setting.";
  static final String SOLR_DELETE_DOCUMENTS_DOC = "Flag to determine if the connector should delete documents. General " +
      "practice in Kafka is to treat a record that contains a key with a null value as a delete.";
  static final String SOLR_CONNECT_TIMEOUT_DOC = "Set the connect timeout to the solr in ms.";
  static final String SOLR_SOCKET_TIMEOUT_DOC = "Set the solr read timeout on all sockets in ms.";

  /* solr auto create collection */
  static final String SOLR_COLLECTION_AUTO_CREATE = "solr.collection.auto.create";
  static final String SOLR_COLLECTION_AUTO_NUMSHARDS = "solr.collection.auto.numShards";
  static final String SOLR_COLLECTION_AUTO_REPLICATIONFACTOR = "solr.collection.auto.replicationFactor";
  static final String SOLR_COLLECTION_AUTO_MAXSHARDSPERNODE = "solr.collection.auto.maxShardsPerNode";

  static final String SOLR_COLLECTION_AUTO_CREATE_DOC = "Whether to create collection automatically before writing to Solr.";
  static final String SOLR_COLLECTION_AUTO_NUMSHARDS_DOC = "Solr Collection numShards config.";
  static final String SOLR_COLLECTION_AUTO_REPLICATIONFACTOR_DOC = "Solr Collection replicationFactor config.";
  static final String SOLR_COLLECTION_AUTO_MAXSHARDSPERNODE_DOC = "Solr Collection maxShardsPerNode config.";

  public final String username;
  public final String password;
  public final boolean useBasicAuthentication;
  public final int commitWithin;
  public final boolean deleteDocuments;
  public final int solrConnectTimeoutMs;
  public final int solrSocketTimeoutMs;
  public final boolean collectionAutoCreate;
  public final int collectionNumShards;
  public final int collectionReplicationFactor;
  public final int collectionMaxShardsPerNode;


  protected SolrSinkConnectorConfig(ConfigDef configDef, Map<String, String> props) {
    super(configDef, props);
    this.commitWithin = this.getInt(SOLR_COMMIT_WITHIN_CONFIG);
    this.username = this.getString(SOLR_USERNAME_CONFIG);
    this.password = this.getPassword(SOLR_PASSWORD_CONFIG).value();
    this.useBasicAuthentication = !Strings.isNullOrEmpty(this.username);
    this.deleteDocuments = this.getBoolean(SOLR_DELETE_DOCUMENTS_CONFIG);
    this.solrConnectTimeoutMs = this.getInt(SOLR_CONNECT_TIMEOUT_CONFIG);
    this.solrSocketTimeoutMs = this.getInt(SOLR_SOCKET_TIMEOUT_CONFIG);
    this.collectionAutoCreate = this.getBoolean(SOLR_COLLECTION_AUTO_CREATE);
    this.collectionNumShards = this.getInt(SOLR_COLLECTION_AUTO_NUMSHARDS);
    this.collectionReplicationFactor = this.getInt(SOLR_COLLECTION_AUTO_REPLICATIONFACTOR);
    this.collectionMaxShardsPerNode = this.getInt(SOLR_COLLECTION_AUTO_MAXSHARDSPERNODE);
  }

  public static final String AUTHENTICATION_GROUP = "Authentication";
  public static final String INDEXING_GROUP = "Indexing";
  public static final String CONNECTION_GROUP = "Connection";

  public static ConfigDef config() {
    return new ConfigDef()
        .define(
            ConfigKeyBuilder.of(SOLR_USERNAME_CONFIG, ConfigDef.Type.STRING)
                .defaultValue("")
                .importance(ConfigDef.Importance.HIGH)
                .documentation(SOLR_USERNAME_DOC)
                .group(AUTHENTICATION_GROUP)
                .build()
        )
        .define(
            ConfigKeyBuilder.of(SOLR_PASSWORD_CONFIG, ConfigDef.Type.PASSWORD)
                .defaultValue("")
                .importance(ConfigDef.Importance.HIGH)
                .documentation(SOLR_PASSWORD_DOC)
                .group(AUTHENTICATION_GROUP)
                .build()
        )
        .define(
            ConfigKeyBuilder.of(SOLR_COMMIT_WITHIN_CONFIG, ConfigDef.Type.INT)
                .defaultValue(-1)
                .importance(ConfigDef.Importance.LOW)
                .documentation(SOLR_COMMIT_WITHIN_DOC)
                .group(INDEXING_GROUP)
                .build()
        )
        .define(
            ConfigKeyBuilder.of(SOLR_DELETE_DOCUMENTS_CONFIG, ConfigDef.Type.BOOLEAN)
                .defaultValue(true)
                .importance(ConfigDef.Importance.MEDIUM)
                .documentation(SOLR_DELETE_DOCUMENTS_DOC)
                .group(INDEXING_GROUP)
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
        ).define(
                ConfigKeyBuilder.of(SOLR_COLLECTION_AUTO_CREATE, ConfigDef.Type.BOOLEAN)
                        .importance(ConfigDef.Importance.LOW)
                        .documentation(SOLR_COLLECTION_AUTO_CREATE_DOC)
                        .group(CONNECTION_GROUP)
                        .defaultValue(false)
                        .build()
        ).define(
                ConfigKeyBuilder.of(SOLR_COLLECTION_AUTO_NUMSHARDS, ConfigDef.Type.INT)
                        .importance(ConfigDef.Importance.LOW)
                        .documentation(SOLR_COLLECTION_AUTO_NUMSHARDS_DOC)
                        .group(CONNECTION_GROUP)
                        .defaultValue(1)
                        .build()
        ).define(
                ConfigKeyBuilder.of(SOLR_COLLECTION_AUTO_REPLICATIONFACTOR, ConfigDef.Type.INT)
                        .importance(ConfigDef.Importance.LOW)
                        .documentation(SOLR_COLLECTION_AUTO_REPLICATIONFACTOR_DOC)
                        .group(CONNECTION_GROUP)
                        .defaultValue(1)
                        .build()
        ).define(
                ConfigKeyBuilder.of(SOLR_COLLECTION_AUTO_MAXSHARDSPERNODE, ConfigDef.Type.INT)
                        .importance(ConfigDef.Importance.LOW)
                        .documentation(SOLR_COLLECTION_AUTO_MAXSHARDSPERNODE_DOC)
                        .group(CONNECTION_GROUP)
                        .defaultValue(1)
                        .build()
    );
  }
}

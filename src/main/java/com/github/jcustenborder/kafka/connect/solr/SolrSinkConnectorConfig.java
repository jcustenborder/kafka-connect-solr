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

  static final String SOLR_USERNAME_DOC = "The username to use for basic authentication.";
  static final String SOLR_PASSWORD_DOC = "The password to use for basic authentication.";
  static final String SOLR_COMMIT_WITHIN_DOC = "Configures Solr UpdaterRequest for a commit within " +
      "the requested number of milliseconds. -1 disables the commit within setting and relies on " +
      "the standard Solr commit setting.";
  static final String SOLR_DELETE_DOCUMENTS_DOC = "Flag to determine if the connector should delete documents. General " +
      "practice in Kafka is to treat a record that contains a key with a null value as a delete.";

  public final String username;
  public final String password;
  public final boolean useBasicAuthentication;
  public final int commitWithin;
  public final boolean deleteDocuments;


  protected SolrSinkConnectorConfig(ConfigDef configDef, Map<String, String> props) {
    super(configDef, props);
    this.commitWithin = this.getInt(SOLR_COMMIT_WITHIN_CONFIG);
    this.username = this.getString(SOLR_USERNAME_CONFIG);
    this.password = this.getPassword(SOLR_PASSWORD_CONFIG).value();
    this.useBasicAuthentication = !Strings.isNullOrEmpty(this.username);
    this.deleteDocuments = this.getBoolean(SOLR_DELETE_DOCUMENTS_CONFIG);
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
        );
  }
}

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
  static final String SOLR_COMMIT_WITHIN_DOC = "Configures Solr UpdaterRequest for a commit within the requested number of milliseconds .";
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

  public static ConfigDef config() {
    return new ConfigDef()
        .define(SOLR_COMMIT_WITHIN_CONFIG, ConfigDef.Type.INT, -1, ConfigDef.Importance.LOW, SOLR_COMMIT_WITHIN_DOC)
        .define(SOLR_USERNAME_CONFIG, ConfigDef.Type.STRING, "", ConfigDef.Importance.HIGH, SOLR_USERNAME_DOC)
        .define(SOLR_PASSWORD_CONFIG, ConfigDef.Type.PASSWORD, "", ConfigDef.Importance.HIGH, SOLR_PASSWORD_DOC)
        .define(SOLR_DELETE_DOCUMENTS_CONFIG, ConfigDef.Type.BOOLEAN, true, ConfigDef.Importance.MEDIUM, SOLR_DELETE_DOCUMENTS_DOC);
  }
}

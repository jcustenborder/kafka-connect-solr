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

import java.util.Map;

class HttpSolrSinkConnectorConfig extends SolrSinkConnectorConfig {
  public static final String CORE_NAME_CONFIG = "solr.core.name";
  public static final String SOLR_URL_CONFIG = "solr.url";
  private static final String CORE_NAME_DOC = "Name of the solr core to write to.";
  private static final String SOLR_URL_DOC = "Url to connect to solr with.";


  public final String solrUrl;
  public final String coreName;


  public HttpSolrSinkConnectorConfig(Map<String, String> props) {
    super(config(), props);
    this.solrUrl = this.getString(SOLR_URL_CONFIG);
    this.coreName = this.getString(CORE_NAME_CONFIG);

  }

  public static ConfigDef config() {
    return SolrSinkConnectorConfig.config()
        .define(CORE_NAME_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, CORE_NAME_DOC)
        .define(SOLR_URL_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, SOLR_URL_DOC);
  }
}

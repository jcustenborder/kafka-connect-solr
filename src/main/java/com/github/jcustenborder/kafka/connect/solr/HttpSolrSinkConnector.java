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

import com.github.jcustenborder.kafka.connect.utils.config.Description;
import com.github.jcustenborder.kafka.connect.utils.config.DocumentationTip;
import com.github.jcustenborder.kafka.connect.utils.config.Title;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;

@Title("Standard Solr")
@Description("This connector is used to connect to write directly to a Solr core.")
@DocumentationTip("The target collection for this connector is selected by the topic name. " +
    "`Transformations <https://kafka.apache.org/documentation/#connect_transforms>`_ like the " +
    "RegexRouter transformation can be used to change the topic name before it is sent to Solr.")
public class HttpSolrSinkConnector extends SolrSinkConnector {
  @Override
  public Class<? extends Task> taskClass() {
    return HttpSolrSinkTask.class;
  }

  @Override
  public ConfigDef config() {
    return HttpSolrSinkConnectorConfig.config();
  }
}

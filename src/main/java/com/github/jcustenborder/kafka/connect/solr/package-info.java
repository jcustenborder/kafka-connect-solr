/**
 * Copyright © 2017 Jeremy Custenborder (jcustenborder@gmail.com)
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
@Introduction(
    "The SOLR connector is a high speed mechanism for writing data to `Apache Solr " +
        "<http://lucene.apache.org/solr/>`_.")
@Title("Apache Solr")
@DocumentationTip("If you are seeing error messages such as `Invalid version " +
    "(expected 2, but 60) or the data in not in 'javabin' format` compare the version of " +
    "the Solr Server against the version of solrj the connector is compiled with. This error " +
    "message is most likely due to a version mismatch between the server and solrj. To address this " +
    "try replacing the solr-solrj-*.jar packaged with the connector with the version that " +
    "matches the Solr server you are connecting to.")
@PluginOwner("jcustenborder")
@PluginName("kafka-connect-solr")
package com.github.jcustenborder.kafka.connect.solr;

import com.github.jcustenborder.kafka.connect.utils.config.DocumentationTip;
import com.github.jcustenborder.kafka.connect.utils.config.Introduction;
import com.github.jcustenborder.kafka.connect.utils.config.PluginName;
import com.github.jcustenborder.kafka.connect.utils.config.PluginOwner;
import com.github.jcustenborder.kafka.connect.utils.config.Title;
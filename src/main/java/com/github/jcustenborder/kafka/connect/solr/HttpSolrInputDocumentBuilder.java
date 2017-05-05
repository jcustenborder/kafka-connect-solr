/**
 * Copyright © 2016 Jeremy Custenborder (jcustenborder@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jcustenborder.kafka.connect.solr;

import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class HttpSolrInputDocumentBuilder extends SolrInputDocumentBuilder<HttpSolrSinkConnectorConfig> {
  HttpSolrInputDocumentBuilder(HttpSolrSinkConnectorConfig config) {
    super(config);
  }

  @Override
  public List<UpdateRequest> build(Collection<SinkRecord> records) {
    UpdateRequest request = newUpdateRequest();
    
    for (SinkRecord record : records) {
      SolrInputDocument document = build(record);
      request.add(document);
    }

    return Arrays.asList(request);
  }


}

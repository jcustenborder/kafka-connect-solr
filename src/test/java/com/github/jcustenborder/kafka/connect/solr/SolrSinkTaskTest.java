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

import org.apache.solr.client.solrj.SolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrSinkTaskTest {
  private static final Logger log = LoggerFactory.getLogger(SolrSinkTaskTest.class);
  SolrSinkTask task;
  SolrClient client;

//  @BeforeEach
//  public void before() throws IOException, SolrServerException {
//    this.task = mock(SolrSinkTask.class, Mockito.CALLS_REAL_METHODS);
//    this.client = mock(SolrClient.class);
//    when(this.task.client()).thenReturn(this.client);
//    when(this.task.config(anyMap())).thenAnswer(invocationOnMock -> {
//      Map<String, String> settings = invocationOnMock.getArgument(0);
//      return new SolrSinkConnectorConfig(SolrSinkConnectorConfig.config(), settings);
//    });
//
//    Map<String, String> settings = ImmutableMap.of();
//    this.task.start(settings);
//    when(this.client.request(any(UpdateRequest.class), any())).thenAnswer(new Answer<Object>() {
//      @Override
//      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
//        log.trace("request");
//        NamedList<Object> result = new NamedList<>();
//        NamedList<Object> responseHeaders = new NamedList<>();
//        responseHeaders.add("status", 200);
//        responseHeaders.add("QTime", 123);
//        result.add("responseHeader", responseHeaders);
//        return result;
//      }
//    });
//  }
//
//  @Test
//  public void test() {
//    List<SinkRecord> records = Records.records();
//    this.task.put(records);
//
//
//  }

}

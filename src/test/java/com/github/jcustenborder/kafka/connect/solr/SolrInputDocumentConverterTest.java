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

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.apache.solr.common.SolrInputDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SolrInputDocumentConverterTest {

  SolrInputDocumentConverter structConverter;


  @BeforeEach
  public void setup() {
    this.structConverter = new SolrInputDocumentConverter();
  }

  @Test
  public void array() {
    Schema schema = SchemaBuilder.struct()
        .field("array", SchemaBuilder.array(Schema.STRING_SCHEMA))
        .build();


  }

  @Test
  public void innerStruct() {
    Schema innerSchema = SchemaBuilder.struct()
        .optional()
        .name("com.github.jcustenborder.kafka.connect.solr.Address")
        .field("address", Schema.OPTIONAL_STRING_SCHEMA)
        .field("city", Schema.OPTIONAL_STRING_SCHEMA)
        .field("state", Schema.OPTIONAL_STRING_SCHEMA)
        .field("zip", Schema.OPTIONAL_STRING_SCHEMA)
        .build();

    Schema outerSchema = SchemaBuilder.struct()
        .name("com.github.jcustenborder.kafka.connect.solr.Outer")
        .field("firstName", Schema.OPTIONAL_STRING_SCHEMA)
        .field("lastName", Schema.OPTIONAL_STRING_SCHEMA)
        .field("address", innerSchema)
        .build();

    Struct innerStruct = new Struct(innerSchema)
        .put("address", "123 Main St")
        .put("city", "Beverly Hills")
        .put("state", "CA")
        .put("zip", "90210");

    Struct outerStruct = new Struct(outerSchema)
        .put("firstName", "Example")
        .put("lastName", "User")
        .put("address", innerStruct);

    final SolrInputDocument addressDocument = new SolrInputDocument();
    addressDocument.addField("address", "123 Main St");
    addressDocument.addField("city", "Beverly Hills");
    addressDocument.addField("state", "CA");
    addressDocument.addField("zip", "90210");

    final SolrInputDocument expected = new SolrInputDocument();
    expected.addField("firstName", "Example");
    expected.addField("lastName", "User");
    expected.addField("address", addressDocument);

    SolrInputDocument actual = this.structConverter.convert(outerStruct);
    SolrInputDocumentAssertions.assertSolrInputDocument(expected, actual);
  }

}

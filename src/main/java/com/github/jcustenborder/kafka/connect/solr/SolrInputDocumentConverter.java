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

import com.github.jcustenborder.kafka.connect.utils.data.AbstractConverter;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.solr.common.SolrInputDocument;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SolrInputDocumentConverter extends AbstractConverter<SolrInputDocument> {
  @Override
  protected SolrInputDocument newValue() {
    return new SolrInputDocument();
  }

  @Override
  protected void setStringField(SolrInputDocument result, String fieldName, String value) {
    result.setField(fieldName, value);
  }

  @Override
  protected void setFloat32Field(SolrInputDocument result, String fieldName, Float value) {
    result.setField(fieldName, value);
  }

  @Override
  protected void setFloat64Field(SolrInputDocument result, String fieldName, Double value) {
    result.setField(fieldName, value);
  }

  @Override
  protected void setTimestampField(SolrInputDocument result, String fieldName, Date value) {
    result.setField(fieldName, value);
  }

  @Override
  protected void setDateField(SolrInputDocument result, String fieldName, Date value) {
    result.setField(fieldName, value);
  }

  @Override
  protected void setTimeField(SolrInputDocument result, String fieldName, Date value) {
    result.setField(fieldName, value);
  }

  @Override
  protected void setInt8Field(SolrInputDocument result, String fieldName, Byte value) {
    result.setField(fieldName, value);
  }

  @Override
  protected void setInt16Field(SolrInputDocument result, String fieldName, Short value) {
    result.setField(fieldName, value);
  }

  @Override
  protected void setInt32Field(SolrInputDocument result, String fieldName, Integer value) {
    result.setField(fieldName, value);
  }

  @Override
  protected void setInt64Field(SolrInputDocument result, String fieldName, Long value) {
    result.setField(fieldName, value);
  }

  @Override
  protected void setBytesField(SolrInputDocument result, String fieldName, byte[] value) {
    result.setField(fieldName, value);
  }

  @Override
  protected void setDecimalField(SolrInputDocument result, String fieldName, BigDecimal value) {
    result.setField(fieldName, value);
  }

  @Override
  protected void setBooleanField(SolrInputDocument result, String fieldName, Boolean value) {
    result.setField(fieldName, value);
  }

  @Override
  protected void setStructField(SolrInputDocument result, String fieldName, Struct value) {
    SolrInputDocument childDocument = convert(value);
    result.setField(fieldName, childDocument);
  }

  @Override
  protected void setArray(SolrInputDocument result, String fieldName, Schema schema, List value) {
    result.setField(fieldName, value);
  }

  @Override
  protected void setMap(SolrInputDocument result, String fieldName, Schema schema, Map value) {
    SolrInputDocument childDocument = convert(value);
    result.setField(fieldName, childDocument);
  }

  @Override
  protected void setNullField(SolrInputDocument result, String fieldName) {

  }
}

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

import com.google.common.base.Equivalence;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;

public class SolrInputFieldEquivalence extends Equivalence<SolrInputField> {

  @Override
  protected boolean doEquivalent(SolrInputField o1, SolrInputField o2) {
    if (o1.getValue() instanceof SolrInputDocument) {
      if (!(o2.getValue() instanceof SolrInputDocument)) {
        return false;
      }
      final MapDifference<String, SolrInputField> difference = Maps.difference(
          (SolrInputDocument) o1.getValue(),
          (SolrInputDocument) o2.getValue(),
          this
      );
      if (!difference.areEqual()) {
        return false;
      }
    } else {
      if (o1.getValue() != o2.getValue()) {
        return false;
      }
    }
    return true;
  }

  @Override
  protected int doHash(SolrInputField o1) {
    return o1.hashCode();
  }
}

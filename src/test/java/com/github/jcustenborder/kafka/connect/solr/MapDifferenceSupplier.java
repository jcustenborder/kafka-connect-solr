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

import com.google.common.collect.MapDifference;
import org.apache.solr.common.SolrInputField;

import java.util.Map;
import java.util.function.Supplier;

public class MapDifferenceSupplier implements Supplier<String> {
  final MapDifference<String, SolrInputField> difference;

  MapDifferenceSupplier(MapDifference<String, SolrInputField> difference) {
    this.difference = difference;
  }

  @Override
  public String get() {
    StringBuilder builder = new StringBuilder();
    if (!difference.entriesDiffering().isEmpty()) {
      builder.append("Differing:\n");
      for (Map.Entry<String, MapDifference.ValueDifference<SolrInputField>> diff : difference.entriesDiffering().entrySet()) {
        builder.append("  ");
        builder.append(diff.getKey());
        builder.append('\n');
        builder.append("  left  : ");
        builder.append(diff.getValue().leftValue());
        builder.append('\n');
        builder.append("  right : ");
        builder.append(diff.getValue().rightValue());
        builder.append('\n');
      }
    }

    return builder.toString();
  }
}
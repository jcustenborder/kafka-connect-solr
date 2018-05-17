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

import org.apache.solr.client.solrj.request.UpdateRequest;

import java.util.ArrayList;
import java.util.List;

class Operations {
  final SolrSinkConnectorConfig config;

  List<UpdateRequest> operations = new ArrayList<>(1000);
  boolean lastOperationIsDelete;
  UpdateRequest lastUpdate;
  UpdateRequest lastDelete;

  Operations(SolrSinkConnectorConfig config) {
    this.config = config;
  }

  UpdateRequest addOperation(boolean delete) {
    UpdateRequest result;
    if (delete) {
      result = (this.lastDelete = new UpdateRequest());
    } else {
      result = (this.lastUpdate = new UpdateRequest());
    }
    this.operations.add(result);
    this.lastOperationIsDelete = delete;
    if (this.config.commitWithin > 0) {
      result.setCommitWithin(this.config.commitWithin);
    }
    if (this.config.useBasicAuthentication) {
      result.setBasicAuthCredentials(
          this.config.username,
          this.config.password
      );
    }
    return result;
  }

  public UpdateRequest update() {
    UpdateRequest result;

    if (null == this.lastUpdate || this.lastOperationIsDelete) {
      result = addOperation(false);
    } else {
      result = this.lastUpdate;
    }

    return result;
  }

  public UpdateRequest delete() {
    UpdateRequest result;

    if (null == this.lastDelete || !this.lastOperationIsDelete) {
      result = addOperation(true);
    } else {
      result = this.lastDelete;
    }

    return result;
  }

  public List<UpdateRequest> operations() {
    return this.operations;
  }

}

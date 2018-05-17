# Introduction

The SOLR connector is a high speed mechanism for writing data to [Apache Solr](http://lucene.apache.org/solr/).

### Tip

If you are seeing error messages such as `Invalid version (expected 2, but 60) or the data in not in 'javabin' format` compare the version of the Solr Server against the version of solrj the connector is compiled with. This error message is most likely due to a version mismatch between the server and solrj. To address this try replacing the solr-solrj-*.jar packaged with the connector with the version that matches the Solr server you are connecting to.

# Sink Connectors


## Cloud Solr

This connector is used to connect to [SolrCloud](https://cwiki.apache.org/confluence/display/solr/SolrCloud) using the Zookeeper based configuration.

### Tip

The target collection for this connector is selected by the topic name. [Transformations](https://kafka.apache.org/documentation/#connect_transforms) like the RegexRouter transformation can be used to change the topic name before it is sent to Solr.


### Configuration

#### Authentication


##### `solr.password`

The password to use for basic authentication.

*Importance:* High

*Type:* Password

*Default Value:* [hidden]



##### `solr.username`

The username to use for basic authentication.

*Importance:* High

*Type:* String


#### Connection


##### `solr.zookeeper.hosts`

Zookeeper hosts that are used to store solr configuration.

*Importance:* High

*Type:* List



##### `solr.zookeeper.chroot`

Chroot within solr for the zookeeper configuration.

*Importance:* High

*Type:* String


#### Indexing


##### `solr.delete.documents.enabled`

Flag to determine if the connector should delete documents. General practice in Kafka is to treat a record that contains a key with a null value as a delete.

*Importance:* Medium

*Type:* Boolean

*Default Value:* true



##### `solr.commit.within`

Configures Solr UpdaterRequest for a commit within the requested number of milliseconds. -1 disables the commit within setting and relies on the standard Solr commit setting.

*Importance:* Low

*Type:* Int

*Default Value:* -1





#### Examples


##### Standalone Example

This configuration is used typically along with [standalone mode](http://docs.confluent.io/current/connect/concepts.html#standalone-workers).

```properties
name=CloudSolrSinkConnector1
connector.class=com.github.jcustenborder.kafka.connect.solr.CloudSolrSinkConnector
tasks.max=1
topics=< Required Configuration >
solr.zookeeper.hosts=< Required Configuration >
```

##### Distributed Example

This configuration is used typically along with [distributed mode](http://docs.confluent.io/current/connect/concepts.html#distributed-workers).
Write the following json to `connector.json`, configure all of the required values, and use the command below to
post the configuration to one the distributed connect worker(s).

```json
{
  "config" : {
    "name" : "CloudSolrSinkConnector1",
    "connector.class" : "com.github.jcustenborder.kafka.connect.solr.CloudSolrSinkConnector",
    "tasks.max" : "1",
    "topics" : "< Required Configuration >",
    "solr.zookeeper.hosts" : "< Required Configuration >"
  }
}
```

Use curl to post the configuration to one of the Kafka Connect Workers. Change `http://localhost:8083/` the the endpoint of
one of your Kafka Connect worker(s).

Create a new instance.
```bash
curl -s -X POST -H 'Content-Type: application/json' --data @connector.json http://localhost:8083/connectors
```

Update an existing instance.
```bash
curl -s -X PUT -H 'Content-Type: application/json' --data @connector.json http://localhost:8083/connectors/TestSinkConnector1/config
```



## Standard Solr

This connector is used to connect to write directly to a Solr core.

### Tip

The target collection for this connector is selected by the topic name. [Transformations](https://kafka.apache.org/documentation/#connect_transforms) like the RegexRouter transformation can be used to change the topic name before it is sent to Solr.


### Configuration

#### Authentication


##### `solr.password`

The password to use for basic authentication.

*Importance:* High

*Type:* Password

*Default Value:* [hidden]



##### `solr.username`

The username to use for basic authentication.

*Importance:* High

*Type:* String


#### Connection


##### `solr.url`

Url to connect to solr with.

*Importance:* High

*Type:* String


#### Indexing


##### `solr.queue.size`

The number of documents to batch together before sending to Solr. See [ConcurrentUpdateSolrClient.Builder.withQueueSize(int)](https://lucene.apache.org/solr/6_3_0/solr-solrj/org/apache/solr/client/solrj/impl/ConcurrentUpdateSolrClient.Builder.html#withQueueSize-int-)

*Importance:* High

*Type:* Int

*Default Value:* 100



##### `solr.delete.documents.enabled`

Flag to determine if the connector should delete documents. General practice in Kafka is to treat a record that contains a key with a null value as a delete.

*Importance:* Medium

*Type:* Boolean

*Default Value:* true



##### `solr.thread.count`

The number of threads used to empty ConcurrentUpdateSolrClients queue. See [ConcurrentUpdateSolrClient.Builder.withThreadCount(int)](https://lucene.apache.org/solr/6_3_0/solr-solrj/org/apache/solr/client/solrj/impl/ConcurrentUpdateSolrClient.Builder.html#withThreadCount-int-)

*Importance:* Medium

*Type:* Int

*Default Value:* 1



##### `solr.commit.within`

Configures Solr UpdaterRequest for a commit within the requested number of milliseconds. -1 disables the commit within setting and relies on the standard Solr commit setting.

*Importance:* Low

*Type:* Int

*Default Value:* -1





#### Examples


##### Standard

This example will connect to a Solr Cloud cluster without authentication.


Select one of the following configuration methods based on how you have deployed Kafka Connect.
Distributed Mode will the the JSON / REST examples. Standalone mode will use the properties based
example.


###### Distributed Mode Json

```json
{
  "name" : "httpSolrSinkConnector1",
  "config" : {
    "connector.class" : "com.github.jcustenborder.kafka.connect.solr.HttpSolrSinkConnector",
    "tasks.max" : "1",
    "topics" : "topic1,topic2,topic3",
    "solr.url" : "http://solr.example.com:8993/"
  }
}
```

###### Standalone Mode Properties

```properties
connector.class=com.github.jcustenborder.kafka.connect.solr.HttpSolrSinkConnector
tasks.max=1
topics=topic1,topic2,topic3
solr.url=http://solr.example.com:8993/

```



##### Basic Authentication

This example will connect to a Solr Cloud cluster using basic authentication.


Select one of the following configuration methods based on how you have deployed Kafka Connect.
Distributed Mode will the the JSON / REST examples. Standalone mode will use the properties based
example.


###### Distributed Mode Json

```json
{
  "name" : "httpSolrSinkConnector1",
  "config" : {
    "connector.class" : "com.github.jcustenborder.kafka.connect.solr.HttpSolrSinkConnector",
    "tasks.max" : "1",
    "topics" : "topic1,topic2,topic3",
    "solr.url" : "http://solr.example.com:8993/",
    "solr.username" : "freddy",
    "solr.password" : "password12345"
  }
}
```

###### Standalone Mode Properties

```properties
connector.class=com.github.jcustenborder.kafka.connect.solr.HttpSolrSinkConnector
tasks.max=1
topics=topic1,topic2,topic3
solr.url=http://solr.example.com:8993/
solr.username=freddy
solr.password=password12345

```






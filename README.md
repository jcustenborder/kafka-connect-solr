# Source Connectors

## CloudSolrSinkConnector

This connector is used to connect to `SolrCloud <https://cwiki.apache.org/confluence/display/solr/SolrCloud>`_ using the Zookeeper based configuration.

### Configuration

| Name                          | Type     | Importance | Default Value | Validator | Documentation                                                                                                                                                |
| ----------------------------- | -------- | ---------- | ------------- | --------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------|
| solr.collection.name          | String   | High       |               |           | Name of the solr collection to write to.                                                                                                                     |
| solr.zookeeper.hosts          | List     | High       |               |           | Zookeeper hosts that are used to store solr configuration.                                                                                                   |
| solr.password                 | Password | High       | [hidden]      |           | The password to use for basic authentication.                                                                                                                |
| solr.username                 | String   | High       |               |           | The username to use for basic authentication.                                                                                                                |
| solr.zookeeper.chroot         | String   | High       |               |           | Chroot within solr for the zookeeper configuration.                                                                                                          |
| solr.delete.documents.enabled | Boolean  | Medium     | true          |           | Flag to determine if the connector should delete documents. General practice in Kafka is to treat a record that contains a key with a null value as a delete.|
| solr.commit.within            | Int      | Low        | -1            |           | Configures Solr UpdaterRequest for a commit within the requested number of milliseconds .                                                                    |


#### Standalone Example

```properties
name=connector1
tasks.max=1
connector.class=com.github.jcustenborder.kafka.connect.solr.CloudSolrSinkConnector
# The following values must be configured.
solr.collection.name=
solr.zookeeper.hosts=
```

#### Distributed Example

```json
{
    "name": "connector1",
    "config": {
        "connector.class": "com.github.jcustenborder.kafka.connect.solr.CloudSolrSinkConnector",
        "solr.collection.name":"",
        "solr.zookeeper.hosts":"",
    }
}
```

## HttpSolrSinkConnector

This connector is used to connect to write directly to a Solr core.

### Configuration

| Name                          | Type     | Importance | Default Value | Validator          | Documentation                                                                                                                                                                                                                                                                         |
| ----------------------------- | -------- | ---------- | ------------- | ------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| solr.url                      | String   | High       |               |                    | Url to connect to solr with.                                                                                                                                                                                                                                                          |
| solr.password                 | Password | High       | [hidden]      |                    | The password to use for basic authentication.                                                                                                                                                                                                                                         |
| solr.username                 | String   | High       |               |                    | The username to use for basic authentication.                                                                                                                                                                                                                                         |
| solr.delete.documents.enabled | Boolean  | Medium     | true          |                    | Flag to determine if the connector should delete documents. General practice in Kafka is to treat a record that contains a key with a null value as a delete.                                                                                                                         |
| solr.queue.size               | Int      | Medium     | 100           | [1,...,2147483647] | The number of documents to batch together before sending to Solr. See `ConcurrentUpdateSolrClient.Builder.withQueueSize(int) <https://lucene.apache.org/solr/6_3_0/solr-solrj/org/apache/solr/client/solrj/impl/ConcurrentUpdateSolrClient.Builder.html#withQueueSize-int->`_         |
| solr.thread.count             | Int      | Medium     | 1             | [1,...,100]        | The number of threads used to empty ConcurrentUpdateSolrClients queue. See `ConcurrentUpdateSolrClient.Builder.withThreadCount(int) <https://lucene.apache.org/solr/6_3_0/solr-solrj/org/apache/solr/client/solrj/impl/ConcurrentUpdateSolrClient.Builder.html#withThreadCount-int->`_|
| solr.commit.within            | Int      | Low        | -1            |                    | Configures Solr UpdaterRequest for a commit within the requested number of milliseconds .                                                                                                                                                                                             |


#### Standalone Example

```properties
name=connector1
tasks.max=1
connector.class=com.github.jcustenborder.kafka.connect.solr.HttpSolrSinkConnector
# The following values must be configured.
solr.url=
```

#### Distributed Example

```json
    {
        "name": "connector1",
        "config": {
        "connector.class": "com.github.jcustenborder.kafka.connect.solr.HttpSolrSinkConnector",
            "solr.url":"",
    }
}
```
# Running in development

```bash
docker-compose exec solr-cloud solr zk upconfig -d /config/solrcloud -n twitter -z zookeeper:2181
docker-compose exec solr-cloud solr create_collection -n twitter -c twitter
docker-compose exec solr-standalone solr create_core -c twitter -d /config/solrcloud
```

There is also a docker-compose script with configuration for zookeeper, solr cloud, and solr standalone. This can be used with `docker-compose up`

```bash
mvn clean package
export CLASSPATH="$(find `pwd`/target/kafka-connect-solr-1.0.0-SNAPSHOT-package/share/java/kafka-connect-solr -type f | tr '\n' ':')"
$CONFLUENT_HOME/bin/connect-standalone $CONFLUENT_HOME/etc/schema-registry/connect-avro-standalone.properties config/httpsolr.properties
```



 


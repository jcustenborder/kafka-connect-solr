# Configuration

## CloudSolrSinkConnector

This connector is used to connect to `SolrCloud <https://cwiki.apache.org/confluence/display/solr/SolrCloud>`_ using the Zookeeper based configuration.

```properties
name=connector1
tasks.max=1
connector.class=com.github.jcustenborder.kafka.connect.solr.CloudSolrSinkConnector

# Set these required values
solr.zookeeper.hosts=
solr.collection.name=
```

| Name                          | Description                                                                                                                                                   | Type     | Default  | Valid Values | Importance |
|-------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|----------|----------|--------------|------------|
| solr.collection.name          | Name of the solr collection to write to.                                                                                                                      | string   |          |              | high       |
| solr.zookeeper.hosts          | Zookeeper hosts that are used to store solr configuration.                                                                                                    | list     |          |              | high       |
| solr.password                 | The password to use for basic authentication.                                                                                                                 | password | [hidden] |              | high       |
| solr.username                 | The username to use for basic authentication.                                                                                                                 | string   | ""       |              | high       |
| solr.zookeeper.chroot         | Chroot within solr for the zookeeper configuration.                                                                                                           | string   | null     |              | high       |
| solr.delete.documents.enabled | Flag to determine if the connector should delete documents. General practice in Kafka is to treat a record that contains a key with a null value as a delete. | boolean  | true     |              | medium     |
| solr.commit.within            | Configures Solr UpdaterRequest for a commit within the requested number of milliseconds .                                                                     | int      | -1       |              | low        |

## HttpSolrSinkConnector

This connector is used to connect to write directly to a Solr core.

```properties
name=connector1
tasks.max=1
connector.class=com.github.jcustenborder.kafka.connect.solr.HttpSolrSinkConnector

# Set these required values
solr.url=
```

| Name                          | Description                                                                                                                                                                                                                                                                            | Type     | Default  | Valid Values       | Importance |
|-------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------|----------|--------------------|------------|
| solr.url                      | Url to connect to solr with.                                                                                                                                                                                                                                                           | string   |          |                    | high       |
| solr.password                 | The password to use for basic authentication.                                                                                                                                                                                                                                          | password | [hidden] |                    | high       |
| solr.username                 | The username to use for basic authentication.                                                                                                                                                                                                                                          | string   | ""       |                    | high       |
| solr.delete.documents.enabled | Flag to determine if the connector should delete documents. General practice in Kafka is to treat a record that contains a key with a null value as a delete.                                                                                                                          | boolean  | true     |                    | medium     |
| solr.queue.size               | The number of documents to batch together before sending to Solr. See `ConcurrentUpdateSolrClient.Builder.withQueueSize(int) <https://lucene.apache.org/solr/6_3_0/solr-solrj/org/apache/solr/client/solrj/impl/ConcurrentUpdateSolrClient.Builder.html#withQueueSize-int->`_          | int      | 100      | [1,...,2147483647] | medium     |
| solr.thread.count             | The number of threads used to empty ConcurrentUpdateSolrClients queue. See `ConcurrentUpdateSolrClient.Builder.withThreadCount(int) <https://lucene.apache.org/solr/6_3_0/solr-solrj/org/apache/solr/client/solrj/impl/ConcurrentUpdateSolrClient.Builder.html#withThreadCount-int->`_ | int      | 1        | [1,...,100]        | medium     |
| solr.commit.within            | Configures Solr UpdaterRequest for a commit within the requested number of milliseconds .                                                                                                                                                                                              | int      | -1       |                    | low        |

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



 


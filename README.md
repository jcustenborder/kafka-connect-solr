# Configuration

## CloudSolrSinkConnector

This connector is used to connect to [SolrCloud](https://cwiki.apache.org/confluence/display/solr/SolrCloud) using the Zookeeper based configuration.

```properties
name=connector1
tasks.max=1
connector.class=com.github.jcustenborder.kafka.connect.solr.CloudSolrSinkConnector

# Set these required values
solr.collection.name=
solr.zookeeper.hosts=
```

| Name                       | Description                                                                                                          | Type     | Default  | Valid Values | Importance |
|----------------------------|----------------------------------------------------------------------------------------------------------------------|----------|----------|--------------|------------|
| solr.collection.name       | Name of the solr collection to write to.                                                                             | string   |          |              | high       |
| solr.zookeeper.hosts       | Zookeeper hosts that are used to store solr configuration.                                                           | list     |          |              | high       |
| solr.password              | The password to use for basic authentication.                                                                        | password | [hidden] |              | high       |
| solr.username              | The username to use for basic authentication.                                                                        | string   | ""       |              | high       |
| solr.zookeeper.chroot      | Chroot within solr for the zookeeper configuration.                                                                  | string   | null     |              | high       |
| solr.commit.within         | Configures Solr UpdaterRequest for a commit within the requested number of milliseconds .                            | int      | -1       |              | low        |
| solr.ignore.unknown.fields | Flag to determine if the connector should raise an exception when it encountered a field it doesn't have configured. | boolean  | false    |              | low        |

## HttpSolrSinkConnector

This connector is used to connect to write directly to a Solr core.

```properties
name=connector1
tasks.max=1
connector.class=com.github.jcustenborder.kafka.connect.solr.HttpSolrSinkConnector

# Set these required values
solr.url=
solr.core.name=
```

| Name                       | Description                                                                                                          | Type     | Default  | Valid Values | Importance |
|----------------------------|----------------------------------------------------------------------------------------------------------------------|----------|----------|--------------|------------|
| solr.core.name             | Name of the solr core to write to.                                                                                   | string   |          |              | high       |
| solr.url                   | Url to connect to solr with.                                                                                         | string   |          |              | high       |
| solr.password              | The password to use for basic authentication.                                                                        | password | [hidden] |              | high       |
| solr.username              | The username to use for basic authentication.                                                                        | string   | ""       |              | high       |
| solr.commit.within         | Configures Solr UpdaterRequest for a commit within the requested number of milliseconds .                            | int      | -1       |              | low        |
| solr.ignore.unknown.fields | Flag to determine if the connector should raise an exception when it encountered a field it doesn't have configured. | boolean  | false    |              | low        |

# Running in development

```bash
docker-compose exec solr-cloud solr zk upconfig -d /config/solrcloud -n twitter -z zookeeper:2181
docker-compose exec solr-cloud solr create_collection -n twitter -c twitter
docker-compose exec solr-standalone solr create_core -c twitter -d /config/solrcloud
```

There is also a docker-compose script with configuration for zookeeper, solr cloud, and solr standalone. This can be used with `docker-compose up`

```
mvn clean package
export CLASSPATH="$(find `pwd`/target/kafka-connect-solr-1.0.0-SNAPSHOT-package/share/java/kafka-connect-solr -type f | tr '\n' ':')"
$CONFLUENT_HOME/bin/connect-standalone $CONFLUENT_HOME/etc/schema-registry/connect-avro-standalone.properties config/httpsolr.properties
```



 


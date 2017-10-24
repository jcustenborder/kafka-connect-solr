Configuration
=============

CloudSolrSinkConnector
----------------------

This connector is used to connect to
``SolrCloud <https://cwiki.apache.org/confluence/display/solr/SolrCloud>``\ \_
using the Zookeeper based configuration.

.. code:: properties

    name=connector1
    tasks.max=1
    connector.class=com.github.jcustenborder.kafka.connect.solr.CloudSolrSinkConnector

    # Set these required values
    solr.zookeeper.hosts=
    solr.collection.name=

+-----------+-------------------------------------------------+-----+-----+------+-----+
| Name      | Description                                     | Typ | Def | Vali | Imp |
|           |                                                 | e   | aul | d    | ort |
|           |                                                 |     | t   | Valu | anc |
|           |                                                 |     |     | es   | e   |
+===========+=================================================+=====+=====+======+=====+
| solr.coll | Name of the solr collection to write to.        | str |     |      | hig |
| ection.na |                                                 | ing |     |      | h   |
| me        |                                                 |     |     |      |     |
+-----------+-------------------------------------------------+-----+-----+------+-----+
| solr.zook | Zookeeper hosts that are used to store solr     | lis |     |      | hig |
| eeper.hos | configuration.                                  | t   |     |      | h   |
| ts        |                                                 |     |     |      |     |
+-----------+-------------------------------------------------+-----+-----+------+-----+
| solr.pass | The password to use for basic authentication.   | pas | [hi |      | hig |
| word      |                                                 | swo | dde |      | h   |
|           |                                                 | rd  | n]  |      |     |
+-----------+-------------------------------------------------+-----+-----+------+-----+
| solr.user | The username to use for basic authentication.   | str | “”  |      | hig |
| name      |                                                 | ing |     |      | h   |
+-----------+-------------------------------------------------+-----+-----+------+-----+
| solr.zook | Chroot within solr for the zookeeper            | str | nul |      | hig |
| eeper.chr | configuration.                                  | ing | l   |      | h   |
| oot       |                                                 |     |     |      |     |
+-----------+-------------------------------------------------+-----+-----+------+-----+
| solr.dele | Flag to determine if the connector should       | boo | tru |      | med |
| te.docume | delete documents. General practice in Kafka is  | lea | e   |      | ium |
| nts.enabl | to treat a record that contains a key with a    | n   |     |      |     |
| ed        | null value as a delete.                         |     |     |      |     |
+-----------+-------------------------------------------------+-----+-----+------+-----+
| solr.comm | Configures Solr UpdaterRequest for a commit     | int | -1  |      | low |
| it.within | within the requested number of milliseconds .   |     |     |      |     |
+-----------+-------------------------------------------------+-----+-----+------+-----+

HttpSolrSinkConnector
---------------------

This connector is used to connect to write directly to a Solr core.

.. code:: properties

    name=connector1
    tasks.max=1
    connector.class=com.github.jcustenborder.kafka.connect.solr.HttpSolrSinkConnector

    # Set these required values
    solr.url=

+--------+--------------------------------------------------------+----+----+------+----+
| Name   | Description                                            | Ty | De | Vali | Im |
|        |                                                        | pe | fa | d    | po |
|        |                                                        |    | ul | Valu | rt |
|        |                                                        |    | t  | es   | an |
|        |                                                        |    |    |      | ce |
+========+========================================================+====+====+======+====+
| solr.u | Url to connect to solr with.                           | st |    |      | hi |
| rl     |                                                        | ri |    |      | gh |
|        |                                                        | ng |    |      |    |
+--------+--------------------------------------------------------+----+----+------+----+
| solr.p | The password to use for basic authentication.          | pa | [h |      | hi |
| asswor |                                                        | ss | id |      | gh |
| d      |                                                        | wo | de |      |    |
|        |                                                        | rd | n] |      |    |
+--------+--------------------------------------------------------+----+----+------+----+
| solr.u | The username to use for basic authentication.          | st | “” |      | hi |
| sernam |                                                        | ri |    |      | gh |
| e      |                                                        | ng |    |      |    |
+--------+--------------------------------------------------------+----+----+------+----+
| solr.d | Flag to determine if the connector should delete       | bo | tr |      | me |
| elete. | documents. General practice in Kafka is to treat a     | ol | ue |      | di |
| docume | record that contains a key with a null value as a      | ea |    |      | um |
| nts.en | delete.                                                | n  |    |      |    |
| abled  |                                                        |    |    |      |    |
+--------+--------------------------------------------------------+----+----+------+----+
| solr.q | The number of documents to batch together before       | in | 10 | [1,… | me |
| ueue.s | sending to Solr. See                                   | t  | 0  | ,214 | di |
| ize    | ``ConcurrentUpdateSolrClient.Builder.withQueueSize(int |    |    | 7483 | um |
|        | ) <https://lucene.apache.org/solr/6_3_0/solr-solrj/org |    |    | 647] |    |
|        | /apache/solr/client/solrj/impl/ConcurrentUpdateSolrCli |    |    |      |    |
|        | ent.Builder.html#withQueueSize-int->``\ \_             |    |    |      |    |
+--------+--------------------------------------------------------+----+----+------+----+
| solr.t | The number of threads used to empty                    | in | 1  | [1,… | me |
| hread. | ConcurrentUpdateSolrClients queue. See                 | t  |    | ,100 | di |
| count  | ``ConcurrentUpdateSolrClient.Builder.withThreadCount(i |    |    | ]    | um |
|        | nt) <https://lucene.apache.org/solr/6_3_0/solr-solrj/o |    |    |      |    |
|        | rg/apache/solr/client/solrj/impl/ConcurrentUpdateSolrC |    |    |      |    |
|        | lient.Builder.html#withThreadCount-int->``\ \_         |    |    |      |    |
+--------+--------------------------------------------------------+----+----+------+----+
| solr.c | Configures Solr UpdaterRequest for a commit within the | in | -1 |      | lo |
| ommit. | requested number of milliseconds .                     | t  |    |      | w  |
| within |                                                        |    |    |      |    |
+--------+--------------------------------------------------------+----+----+------+----+
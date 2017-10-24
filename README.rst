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

.. csv-table:: Configuration
    :header: "Name", "Type", "Importance", "Default Value", "Validator", "Documentation"
    :widths: auto

    "solr.collection.name","String","High","java.lang.Object@549778bc","","Name of the solr collection to write to."
    "solr.zookeeper.hosts","List","High","java.lang.Object@549778bc","","Zookeeper hosts that are used to store solr configuration."
    "solr.password","Password","High","[hidden]","","The password to use for basic authentication."
    "solr.username","String","High","","","The username to use for basic authentication."
    "solr.zookeeper.chroot","String","High","","","Chroot within solr for the zookeeper configuration."
    "solr.delete.documents.enabled","Boolean","Medium","true","","Flag to determine if the connector should delete documents. General practice in Kafka is to treat a record that contains a key with a null value as a delete."
    "solr.commit.within","Int","Low","-1","","Configures Solr UpdaterRequest for a commit within the requested number of milliseconds ."

HttpSolrSinkConnector
---------------------

This connector is used to connect to write directly to a Solr core.

.. code:: properties

    name=connector1
    tasks.max=1
    connector.class=com.github.jcustenborder.kafka.connect.solr.HttpSolrSinkConnector

    # Set these required values
    solr.url=

.. csv-table:: Configuration
    :header: "Name", "Type", "Importance", "Default Value", "Validator", "Documentation"
    :widths: auto

    "solr.url","String","High","java.lang.Object@549778bc","","Url to connect to solr with."
    "solr.password","Password","High","[hidden]","","The password to use for basic authentication."
    "solr.username","String","High","","","The username to use for basic authentication."
    "solr.delete.documents.enabled","Boolean","Medium","true","","Flag to determine if the connector should delete documents. General practice in Kafka is to treat a record that contains a key with a null value as a delete."
    "solr.queue.size","Int","Medium","100","[1,...,2147483647]","The number of documents to batch together before sending to Solr. See `ConcurrentUpdateSolrClient.Builder.withQueueSize(int) <https://lucene.apache.org/solr/6_3_0/solr-solrj/org/apache/solr/client/solrj/impl/ConcurrentUpdateSolrClient.Builder.html#withQueueSize-int->`_"
    "solr.thread.count","Int","Medium","1","[1,...,100]","The number of threads used to empty ConcurrentUpdateSolrClients queue. See `ConcurrentUpdateSolrClient.Builder.withThreadCount(int) <https://lucene.apache.org/solr/6_3_0/solr-solrj/org/apache/solr/client/solrj/impl/ConcurrentUpdateSolrClient.Builder.html#withThreadCount-int->`_"
    "solr.commit.within","Int","Low","-1","","Configures Solr UpdaterRequest for a commit within the requested number of milliseconds ."
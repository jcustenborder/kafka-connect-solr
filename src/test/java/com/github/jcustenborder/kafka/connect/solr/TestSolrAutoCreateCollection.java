package com.github.jcustenborder.kafka.connect.solr;

import org.apache.solr.client.solrj.embedded.JettyConfig;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.cloud.MiniSolrCloudCluster;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.jcustenborder.kafka.connect.solr.CloudSolrSinkConnectorConfig.ZOOKEEPER_HOSTS_CONFIG;
import static com.github.jcustenborder.kafka.connect.solr.SolrSinkConnectorConfig.SOLR_COLLECTION_AUTO_CREATE;

public class TestSolrAutoCreateCollection {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void test() throws Exception {
        final File newFolder = temporaryFolder.newFolder("MiniSolrCloudClusterTest");
        final Path baseDir = newFolder.toPath();

        final int numServers = 1;
        final int jettyPort = 0;
        final JettyConfig jettyConfig = JettyConfig.builder().setPort(jettyPort).build();

        // create a MiniSolrCloudCluster instance
        final MiniSolrCloudCluster miniSolrCloudCluster = new MiniSolrCloudCluster(numServers, baseDir, jettyConfig);
        CloudSolrClient cloudSolrClient = miniSolrCloudCluster.getSolrClient();

        // Upload Solr configuration directory to ZooKeeper
        String solrZKConfigDir = "src/test/solr/solrcloud/conf";
        File configDir = new File(solrZKConfigDir);
        miniSolrCloudCluster.uploadConfigSet(configDir.toPath(), "TestSolrZKConfigName");

        CloudSolrSinkTask cloudSolrSinkTask = new CloudSolrSinkTask();
        cloudSolrSinkTask.client = cloudSolrClient;

        Map<String, String> collectionConfig = new HashMap<>();
        collectionConfig.put(ZOOKEEPER_HOSTS_CONFIG, miniSolrCloudCluster.getZkServer().getZkAddress());
        collectionConfig.put(SOLR_COLLECTION_AUTO_CREATE, "true");
        cloudSolrSinkTask.config = new CloudSolrSinkConnectorConfig(collectionConfig);

        // test auto create collection
        cloudSolrSinkTask.checkSolrCollection(cloudSolrClient, "test_topic");

        List<String> collections = CollectionAdminRequest.listCollections(cloudSolrClient);
        Assert.assertEquals(collections.size(), 1);

        // shutdown MiniSolrCloudCluster
        miniSolrCloudCluster.shutdown();
    }

}

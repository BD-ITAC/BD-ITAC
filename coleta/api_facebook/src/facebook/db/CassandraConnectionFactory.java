/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebook.db;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 *
 * @author Fellipe Rey
 */
public class CassandraConnectionFactory {

    private Cluster cluster;
    private Session session;
    
    
    
    // Creates a connection with CassandraDB
    public void getConnection() {
        // Build a Cluster with the data of the BD-ITAC server
        cluster = Cluster.builder().addContactPoint("localhost").withCredentials("bditac", "bditac2016").withPort(5000).build();
        // Connect with credentials informed
        session = cluster.connect();        
    }
    
    // Close the Cassandra DB Connection 
    public void close(){
        // Closes the Cluster Connection
        cluster.close();
    }
    
    // Returns the Session
    public Session getSession(){
        return this.session;
    }
    
}

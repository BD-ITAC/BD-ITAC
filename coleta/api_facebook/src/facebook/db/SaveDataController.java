/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebook.db;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import facebook.classes.Comments;
import facebook.classes.Pages;
import facebook.classes.Posts;
import java.util.ArrayList;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;

/**
 *
 * @author Fellipe Rey
 */
public class SaveDataController {
    
    
    // Save all posts and comments collected on Cassandra
    public void saveOnCassandra(ArrayList<Pages> fbPages) {
        // Creates a Connection with Cassandra
        CassandraConnectionFactory cassandraConnection = new CassandraConnectionFactory();
        // Estabilishes a Cassandra Connection
        cassandraConnection.getConnection();
        // Receives the next Id to storage on bditac.facebook Cassandra's table
        long id = getNextId(cassandraConnection);
        // Create an instance of Insert to add data to table
        Insert insertNewQuery = null;
        // Verifies if there are pages on fbPages
        if (!fbPages.isEmpty()){
            for (Pages page : fbPages) {
                // Verifies if there are posts on fbPages.getPosts()
                if (!page.getPosts().isEmpty()){
                    for (Posts post : page.getPosts()) {
                        // Verifies if there are comments on fbPages.getPosts().getComments()
                        if (!post.getComments().isEmpty()){
                            for (Comments comment : post.getComments()) {
                                // Insert all values from a Comment
                                insertNewQuery = QueryBuilder
                                .insertInto("bditac", "facebook")
                                .value("fcb_id", id++)
                                .value("fcb_author_name", comment.getName())
                                .value("fcb_datahora", new java.sql.Timestamp(comment.getPostDate().getTime()))
                                .value("fcb_idcomment", Long.parseLong(comment.getId()))
                                .value("fcb_pagename", page.getPageName())
                                .value("fcb_message", comment.getMessage())
                                .value("fcb_tipo", "Comment");
                                // Insert a new comment on bditac.facebook
                                cassandraConnection.getSession().execute(insertNewQuery.toString());
                            }
                        }
                        // Insert all values from a post
                        insertNewQuery = QueryBuilder
                            .insertInto("bditac", "facebook")
                            .value("fcb_id", id++)
                            .value("fcb_author_name", page.getPageName())
                            .value("fcb_datahora", new java.sql.Timestamp(post.getDate().getTime()))
                            .value("fcb_pagename", page.getPageName())
                            .value("fcb_message", post.getMessage())
                            .value("fcb_tipo", "Post");
                            // Insert a new post on bditac.facebook
                            cassandraConnection.getSession().execute(insertNewQuery.toString());
                    }
                }
            }
        }
        // Closes the Cassandra Connection
        cassandraConnection.close();
    }
    
    
    
    // Get the next fcb_id on Cassandra Table (fcb_id is not auto increment!)
    public long getNextId(CassandraConnectionFactory c){
        // Select the higher value from fcb_id
        ResultSet results = c.getSession().execute("SELECT max(fcb_id) as max FROM bditac.facebook;");
        for (Row row : results) {
            // return the higher value from fcb_id + 1 (The next ID to be stored)
            return (row.getVarint("max") != null) ? row.getVarint("max").longValue() + 1 : 0;
        }
        return 0;
    }

}

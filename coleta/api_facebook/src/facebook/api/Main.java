/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebook.api;

import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import facebook.db.CassandraConnectionFactory;
import facebook.controller.Controller;
import facebook4j.FacebookException;
import facebook4j.internal.org.json.JSONException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;



/**
 *
 * @author Fellipe Rey
 */
public class Main {
    
    public static void main(String[] args) throws FacebookException, JSONException, IOException, ClassNotFoundException, SQLException, Exception {
        // Initialize Controller with the selected keyword
        Controller fbController = new Controller();
        // Create a new Thread passing the Controller as parameter
        Thread t = new Thread(fbController);
        // Process all Pages and collect the posts and comments
        t.start();
    }
    
}

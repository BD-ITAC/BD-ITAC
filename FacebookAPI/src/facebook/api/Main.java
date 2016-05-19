/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebook.api;

import facebook.controller.Controller;
import facebook4j.FacebookException;
import facebook4j.internal.org.json.JSONException;
import java.io.IOException;



/**
 *
 * @author Fellipe Rey
 */
public class Main {
    
    public static void main(String[] args) throws FacebookException, JSONException, IOException {
        // Initialize Controller with the selected keyword
        Controller fbController = new Controller("frio");
        // Process all Pages and collect the posts and comments
        fbController.processPagesPosts();
    }
    
}

package facebook.tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import facebook.controller.Controller;
import facebook4j.FacebookException;
import facebook4j.internal.org.json.JSONException;
import java.io.IOException;
import junit.framework.TestCase;

/**
 *
 * @author Fellipe Rey
 */
public class FacebookAPITest extends TestCase{
    
    public FacebookAPITest() {
    }
    
    public void testProcessPagesPosts() throws FacebookException, JSONException, IOException{
        // If not null, so we can collect some data with keyword "chuva"
        assertNotSame(new Controller("chuva").processPagesPosts(), null);
        // We expected not collect any data with keyword "qpeiabe"
        assertEquals(new Controller("qpeiabe").getNumPosts(), 0);
    }
    
}

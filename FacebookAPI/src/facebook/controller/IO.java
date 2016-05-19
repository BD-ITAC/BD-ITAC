/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebook.controller;

import facebook.classes.Comments;
import facebook.classes.Pages;
import facebook.classes.Posts;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Fellipe Rey
 */
public class IO {
    
    public IO(){
        
    }
    
    public JSONObject generateJSON(ArrayList<Pages> fbPages) throws JSONException{
        JSONObject finalJSON = new JSONObject();
        JSONArray fJSONArray = new JSONArray();
        for (Pages page : fbPages) {
            JSONObject jsonPage = new JSONObject();
            JSONArray jsonPageArray = new JSONArray();
            jsonPage.put("Page Name", page.getPageName());
            if (page.getPosts() != null)
                for (Posts post : page.getPosts()) {
                    JSONObject jsonPost = new JSONObject();
                    JSONArray jsonPostArray = new JSONArray();
                    jsonPost.put("Message", post.getMessage());
                    jsonPost.put("Date", post.getDate());
                    for (Comments comment : post.getComments()) {
                        JSONObject jsonComment = new JSONObject();
                        jsonComment.put("Name", comment.getName());
                        jsonComment.put("ID", comment.getId());
                        jsonComment.put("Message", comment.getMessage());
                        jsonComment.put("Date", comment.getPostDate());
                        jsonPostArray.put(jsonComment);
                    }
                    jsonPost.put("Comments", jsonPostArray);
                    jsonPageArray.put(jsonPost);
                }
            jsonPage.put("Posts", jsonPageArray);
            fJSONArray.put(jsonPage);
        }
        finalJSON.put("Pages", fJSONArray);
        return finalJSON;
    }
    
    public void gravarJSON(ArrayList<Pages> fbPages) throws JSONException, IOException{
        JSONObject toSave = generateJSON(fbPages);
        FileWriter fw = new FileWriter("saida.txt");  
        BufferedWriter bw = new BufferedWriter(fw);  
        bw.write(toSave.toString());  
        bw.flush();  
        bw.close();
    }
    
}

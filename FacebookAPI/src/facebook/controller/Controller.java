/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebook.controller;

import facebook.classes.Comments;
import facebook.classes.Pages;
import facebook.classes.Posts;
import facebook4j.Comment;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.internal.org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Fellipe Rey
 */
public class Controller {
    
    // Keyword defined by Team Scrum #01
    private String keyword;
    // Creating a new ArrayList of Pages (Each page have a maximum number of 100 posts)
    ArrayList<Pages> fbPages;
    
    public Controller(String keyword) {
        this.keyword = keyword;
        fbPages = new ArrayList<>();
    }
    
    
    /*
     * Connection with Facebook
     */
    public Facebook connectToFacebook(){
        // Create a new connection between Java and Facebook, using AppId and AppSecret
        ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
              .setOAuthAppId("1597781263867452")
              .setOAuthAppSecret("71a1654d922698f93daf30bd6cb47fcf")
              .setOAuthAccessToken("1597781263867452|71a1654d922698f93daf30bd6cb47fcf")
              .setOAuthPermissions("message,created_time,name,comments,place");
        // Return the Facebook instance
        return new FacebookFactory(cb.build()).getInstance();
    }
    
    
    /*
     * Method to collect all posts and comments from defined pages
     */
    public ArrayList<Pages> processPagesPosts() throws FacebookException, JSONException, IOException{
        // Connect with Facebook
        Facebook facebook = connectToFacebook();
        // List of all analyzed pages
        String[] pageList = {"Climatempo.Meteorologia", "DesastresBrasil", "Jornal-O-Vale",
                 "folhadesp", "camarasjc", "g1", "UOLNoticias", "estadao",
                 "jornaldaband", "radiocbn"};
        // Add all pages in the array
        for (String page : pageList){
            fbPages.add(new Pages(page));
        }
        // For each page, get all posts
        for (Pages page : fbPages) {
            // We can get only 100 posts
            ResponseList<Post> feeds = facebook.getFeed(page.getPageName(), new Reading().limit(100));
            // Creating a new ArrayList of Posts
            ArrayList<Posts> fbPosts = new ArrayList<>();
            // Number of collected posts
            int numPosts = 0;
            // For all posts
            for (int i = 0; i < feeds.size(); i++) {
                // Receives the current post
                Post post = feeds.get(i);
                // Receives the Text of the post
                String message = post.getMessage();
                // Verifies if message is not null (Videos and Photos cannot have messages) AND verifies if the keyword are in the message
                if (message != null && message.toLowerCase().contains(keyword.toLowerCase())){
                    // Increments numPosts
                    numPosts++;
                    // Receive all comments in this post
                    ResponseList<Comment> comments = facebook.getPostComments(post.getId(), new Reading().limit(100));
                    // Receives the date of the publication
                    String date = post.getCreatedTime().toString();
                    // Receives the ID of this post
                    String id = post.getId();
                    // Creating a new ArrayList to store all Comments
                    ArrayList<Comments> postComments = new ArrayList<>();
                    // For all comments in this post
                    for (int j = 0 ; j < comments.size(); j++){
                        // Add this post if a keyword is present
                        postComments.add(new Comments(comments.get(j).getFrom().getName(), comments.get(j).getFrom().getId(), comments.get(j).getCreatedTime(), comments.get(j).getMessage()));
                    }
                    // Add all comments to this post
                    fbPosts.add(new Posts(message, date, postComments));
                }
            }
            // Add all posts to this page
            if (numPosts > 0) page.setPosts(fbPosts);
        }
        // Save the result in JSON in a .txt file
        new IO().gravarJSON(fbPages);
        // Return the Collection with Pages and their respectives posts and comments
        return fbPages;
    }
    
    
    /*
     * Method which verifies the total number of posts in all pages
     */
    public int getNumPosts() throws FacebookException, JSONException, IOException{
        // Verifies if the method processPagePosts was performed
        if (fbPages.isEmpty())
            processPagesPosts();
        // Initializing numPosts
        int numPosts = 0;
        // Increments numPosts for each post detected
        numPosts = fbPages.stream().map((page) -> (page.getPosts() != null) ? page.getPosts().size() : 0).reduce(numPosts, Integer::sum);
        // Return the number of posts
        return numPosts;
    }
    
}

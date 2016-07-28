/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebook.controller;

import facebook.db.SaveDataController;
import facebook.db.MySQLConnectionFactory;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fellipe Rey
 */
public class Controller implements Runnable{
    
    // Keyword defined by Team Scrum #01
    private ArrayList<String> keywords;
    // Creating a new ArrayList of Pages (Each page have a maximum number of 100 posts)
    private ArrayList<Pages> fbPages;
    
    // Default Constructor
    public Controller() {
        fbPages = new ArrayList<>();
        keywords = new ArrayList<>();
    }
    
    // Constructor to run JUnir
    public Controller(String keyword) {
        this();
        this.keywords.add(keyword);
    }
    
    // Get all Keywords related to a crisis
    public void getKeywords() throws Exception{
        Connection conn = new MySQLConnectionFactory().getConnection();
        String sql = "SELECT p.pal_palavra " +
                     "FROM palavra p INNER JOIN crise_identificacao ci on p.pal_id = ci.pal_id "
                + "INNER JOIN crise_tipo ct on ct.crt_id = ci.crt_id "
                + "INNER JOIN crise c on c.crt_id = ct.crt_id "
                + "WHERE cri_ativa=1";
        PreparedStatement comando = conn.prepareStatement(sql);
        ResultSet resultado = comando.executeQuery();
        while (resultado.next()) {
            keywords.add(resultado.getString("p.pal_palavra"));
        }
        resultado.close();
        comando.close();
        sql = "SELECT au.apu_usuario, au.apu_ultimoid, au.apu_id "
                + "FROM crise c INNER JOIN crise_api ca on c.cri_id = ca.cri_id "
                + "INNER JOIN api a ON ca.api_id = a.api_id "
                + "INNER JOIN api_usuario au ON a.api_id=au.api_id "
                + "WHERE a.api_id = 2 AND c.cri_ativa=1;";
        PreparedStatement comando1 = conn.prepareStatement(sql);
        ResultSet resultado1 = comando1.executeQuery();
        while (resultado1.next()) {
            fbPages.add(new Pages(resultado1.getString("au.apu_usuario"), resultado1.getLong("au.apu_ultimoid"), resultado1.getLong("au.apu_id")));
        }
        resultado1.close();
        comando1.close();
        conn.close();
    }
    
    // Update in MySQL the higher post id (the last one pubished)
    public void updatePostId(long post_id, long api_id) throws Exception{
        Connection conn = new MySQLConnectionFactory().getConnection();
        String sql = "UPDATE api_usuario SET apu_ultimoid=" + post_id+" WHERE apu_id="+api_id+";";
        PreparedStatement comando = conn.prepareStatement(sql);
        comando.executeUpdate();
        comando.close();
        conn.close();
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
     * Method which verifies the total number of posts in all pages
     */
    public int getNumPosts() throws FacebookException, JSONException, IOException, Exception{
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
    
    //Convert Date in format "Sun Jun 05 21:18:29 BRT 2016" to "2016-06-05 21:18:29"
    public Date convertToDate(String date){
        // Slip String Date by space
        String[] splitDate = date.split(" ");
        // Create an array with all Months
        String[] meses = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        // Change the Month name by its number value
        for (int position = 0; position < meses.length; position++) {
            // Verifies if the selected Month equals to an array position
            if (splitDate[1].equals(meses[position]))
                // Changing the name for its number (plus 1, array position starts in 0 and month starts in 1)
                splitDate[1] = String.valueOf(position+1);
        }
        // Slit the Hour by ":" to add to constructor
        String[] splitHour = splitDate[3].split(":");
        // Uses this obsolete constructor to create a new date
        return new Date(Integer.parseInt(splitDate[5]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[2]), Integer.parseInt(splitHour[0]), Integer.parseInt(splitHour[1]), Integer.parseInt(splitHour[2]));
    }
    
    /*
     * Method to collect all posts and comments from defined pages
     */
    public ArrayList<Pages> processPagesPosts() throws FacebookException, JSONException, IOException, Exception{
        // Connect with Facebook
        Facebook facebook = connectToFacebook();
        // Get the Keyword
        getKeywords();
        // For each page, get all posts
        for (Pages page : fbPages) {
            // We can get only 100 posts
            ResponseList<Post> feeds = facebook.getFeed(page.getPageName(), new Reading().limit(100));
            // Creating a new ArrayList of Posts
            ArrayList<Posts> fbPosts = new ArrayList<>();
            // Number of collected posts
            int numPosts = 0;
            // Bigger post ID
            long post_id = 0;
            // For all posts
            for (int i = 0; i < feeds.size(); i++) {
                // Receives the current post
                Post post = feeds.get(i);
                // Receives the actual fcb_id
                long actual_id = Long.parseLong(post.getId().split("_")[1]);
                // Verifies if exists a new post to this page. Ignores older posts
                if (actual_id < page.getLastPostID()){
                    break;
                } else {
                    // post_id receives the higher post_id (the last post published)
                    if (actual_id > post_id)
                        post_id = actual_id;
                }
                // Receives the Text of the post
                String message = post.getMessage();
                // Verifies if message is not null (Videos and Photos cannot have messages) AND verifies if the keyword are in the message
                if (message != null && containKeywors(message)){
                    // Increments numPosts
                    numPosts++;
                    // Receive all comments in this post
                    ResponseList<Comment> comments = facebook.getPostComments(post.getId(), new Reading().limit(25));
                    // Receives the date of the publication
                    Date date = convertToDate(post.getCreatedTime().toString());
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
                    fbPosts.add(new Posts(id, message, date, postComments));
                }
            }
            // If there is a new post in this page
            if (page.getLastPostID() < post_id) {
                // Set the higher post to Page
                page.setLastPostID(post_id);
                // Saves the higher post on MySQL
                updatePostId(post_id, page.getBdID());
            }
            // Add all posts to this page
            if (numPosts > 0) page.setPosts(fbPosts);
        }
        // Save the result in JSON in a .txt file
        new SaveDataController().saveOnCassandra(fbPages);
        // Return the Collection with Pages and their respectives posts and comments
        return fbPages;
    }
    
    // TEST ONLY!
    public boolean containKeywors(String message) {
        for (String keyword : keywords) {
            if (message.toLowerCase().contains(keyword.toLowerCase()))
                return true;
        }
        return false;
    }
    
    @Override
    public void run(){
        try {
            processPagesPosts();
        } catch (JSONException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

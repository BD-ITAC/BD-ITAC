/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebook.classes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fellipe Rey
 */
public class Pages {
    
    private String pageName;
    private long bdID;
    private long  lastPostID;
    private List<Posts> posts;
    
    public Pages(String pageName, long lastPostID, long bdID){
        this.pageName = pageName;
        this.lastPostID = lastPostID;
        this.bdID = bdID;
        this.posts = new ArrayList<>();
    }
    
    public Pages(String pageName, long lastPostID, long bdID, List<Posts> posts){
        this(pageName, lastPostID, bdID);
        this.posts = posts;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public List<Posts> getPosts() {
        return posts;
    }

    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }
    
    public void addPosts(Posts post){
        this.posts.add(post);
    }

    public long getLastPostID() {
        return lastPostID;
    }

    public void setLastPostID(long lastPostID) {
        this.lastPostID = lastPostID;
    }

    public long getBdID() {
        return bdID;
    }

    public void setBdID(long bdID) {
        this.bdID = bdID;
    }
  
}

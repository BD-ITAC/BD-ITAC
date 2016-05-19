/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebook.classes;

import java.util.List;

/**
 *
 * @author Fellipe Rey
 */
public class Pages {
    
    private String pageName;
    private List<Posts> posts;
    
    public Pages(String pageName){
        this.pageName = pageName;
    }
    
    public Pages(String pageName, List<Posts> posts){
        this(pageName);
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
    
}

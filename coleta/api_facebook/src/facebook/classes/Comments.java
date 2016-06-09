/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebook.classes;

import java.util.Date;

/**
 *
 * @author Fellipe Rey
 */
public class Comments {
    
    private String name;
    private String id;
    private Date postDate;
    private String message;
    
    public Comments(String name, String id, Date postDate, String message){
        this.name = name;
        this.id = id;
        this.postDate = postDate;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}

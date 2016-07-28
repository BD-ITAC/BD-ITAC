/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebook.classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Fellipe Rey
 */
public class Posts {
    
    private String id;
    private String message;
    private Date date;
    private List<Comments> comments;
    
    public Posts (String id, String message, Date date, ArrayList<Comments> comments) {
        this.id = id;
        this.message = message;
        this.date = date;
        this.comments = comments;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}

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
public class Posts {
    
    private String message;
    private String date;
    private List<Comments> comments;
    
    public Posts (String message, String date, ArrayList<Comments> comments) {
        this.message = message;
        this.date = date;
        this.comments = comments;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

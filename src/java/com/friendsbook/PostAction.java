/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsbook;

import com.friendsbook.DAO.UserPostDAO;
import com.friendsbook.pojo.UserPost;
import java.time.LocalDateTime;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Home
 */
@Named(value = "postBean")
@RequestScoped
public class PostAction {

    /**
     * Creates a new instance of PostAction
     */
    public PostAction() {
    }
    
    private String postDescription;
    private String comment;

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String createPost(String userId){
        
        UserPost post = new UserPost();
        post.setType(UserPost.POST);
        post.setUserId(userId);
        post.setTimeStamp(LocalDateTime.now());
        post.setDescription(this.postDescription);
        if(UserPostDAO.createPostDAO(post)){
            this.setPostDescription(null);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Post created successfully!"));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Oops! something went wrong, please try again."));
        }
        
        return "home.xhtml";
    }
    
    public void createComment(String userId, UserPost post){
        
    }
    
    
}

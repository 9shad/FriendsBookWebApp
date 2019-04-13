/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsbook;

import com.friendsbook.DAO.LoginDAO;
import com.friendsbook.DAO.UserPostDAO;
import com.friendsbook.pojo.UserFriend;
import com.friendsbook.pojo.UserPost;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Home
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginAction implements Serializable {

    /**
     * Creates a new instance of LoginAction
     */
    public LoginAction() {
    }
    
    private String userId;   
    private String password;
    private UserFriend user;
    private List<UserPost> postsFromFriends;

    public UserFriend getUser() {
        return user;
    }
    
    public String login(){
        this.user = LoginDAO.checkUserCredentials(userId, password);
        if(user == null){
            FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("Invalid Credentials, Please try again!"));
            return "index.xhtml";
        }else{
            if(postsFromFriends == null || postsFromFriends.isEmpty()){
                postsFromFriends = UserPostDAO.getNewPosts(user.getUserId());
            }
        }
        return "home.xhtml";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserPost> getPostsFromFriends() {
        return postsFromFriends;
    }

    public void setPostsFromFriends(List<UserPost> postsFromFriends) {
        this.postsFromFriends = postsFromFriends;
    }
    
    
}

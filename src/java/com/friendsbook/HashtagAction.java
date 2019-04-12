/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsbook;

import com.friendsbook.DAO.HashTagDAO;
import com.friendsbook.DAO.UserPostDAO;
import com.friendsbook.pojo.UserPost;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Home
 */
@Named(value = "hashtagBean")
@SessionScoped
public class HashtagAction implements Serializable{

    /**
     * Creates a new instance of HashtagAction
     */
    public HashtagAction() {
    }
    
    private String userInputHashtag;
    private List<UserPost> posts;
    private List<String> trendingHashTag;

    public List<UserPost> getPosts() {
        return posts;
    }

    public void setPosts(List<UserPost> posts) {
        this.posts = posts;
    }

    public String getUserInputHashtag() {
        return userInputHashtag;
    }

    public List<String> getTrendingHashTag() {
        return trendingHashTag;
    }

    public void setTrendingHashTag(List<String> trendingHashTag) {
        this.trendingHashTag = trendingHashTag;
    }
    
    public void setUserInputHashtag(String userInputHashtag) {
        this.userInputHashtag = userInputHashtag;
    }
    
    public void getTrendingHashTags(){
        if(this.trendingHashTag == null || this.trendingHashTag.isEmpty()){
            this.trendingHashTag = HashTagDAO.getMostTrendingHashtagDAO();
        }
    }
    
    public String showHashtagPostsByFriends(String userId){
        if(this.userInputHashtag!=null && userInputHashtag.charAt(0)!='#'){
            userInputHashtag = "#" + userInputHashtag;
        }
        if(userId != null && this.userInputHashtag!=null){
            this.posts = UserPostDAO.getPostsContainingHashtag(userId, this.userInputHashtag);
            if(this.posts != null && !this.posts.isEmpty()){
                //this.setUserInputHashtag(null);
                return "hashtagPosts.xhtml";
            }
                
            else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Oops! No posts from your friends has hashtag "+this.userInputHashtag));
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Oops! Please try again."));
        }
        return "";
    }
    public String showHashtagPostsByFriends(String userId, String hashtag){
        this.userInputHashtag = hashtag;
        return this.showHashtagPostsByFriends(userId);
    }
    public void attributeListener(ActionEvent event) {
      this.userInputHashtag = (String)event.getComponent().getAttributes().get("hashtag");	
      //String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hidden1").setHidden1(value);
      //<input type="hidden" name="hidden1" value="this is hidden2" />
   }
}

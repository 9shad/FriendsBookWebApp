/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsbook;

import com.friendsbook.DAO.FriendDAO;
import com.friendsbook.pojo.UserFriend;
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
@Named(value = "friendsBean")
@SessionScoped
public class FriendsAction implements Serializable {

    /**
     * Creates a new instance of FriendsAction
     */
    public FriendsAction() {
    }
        
    private List<String> friendList;
    private UserFriend friendProfile;

    public List<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<String> friendList) {
        this.friendList = friendList;
    }
    
    public void generateFriendsList(String userId){
        if(friendList == null || friendList.isEmpty()){
            friendList = FriendDAO.getFriendList(userId);
            
        }
    }

    public UserFriend getFriendProfile() {
        return friendProfile;
    }

    public void setFriendProfile(UserFriend friendProfile) {
        this.friendProfile = friendProfile;
    }
    
    public String generateFriendProfile(String userId){
        friendProfile = null;
        friendProfile = FriendDAO.getFriendProfile(userId);
        if(friendProfile != null){
            return "friendList.xhtml";
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Oops! cannot load profile information for "+userId));
            return "friendList.xhtml";
        }
    }

    public String viewAllFriends(){
        friendProfile = null;
        return "friendList.xhtml";
    }

    
}

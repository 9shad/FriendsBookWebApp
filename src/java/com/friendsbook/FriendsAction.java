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
import java.util.ArrayList;
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
    private List<UserFriend> friendProfile;
    private List<List<String>> friends;

    public List<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<String> friendList) {
        this.friendList = friendList;
    }

    public List<List<String>> getFriends() {
        return friends;
    }

    public void setFriends(List<List<String>> friends) {
        this.friends = friends;
    }
    
    public void generateFriendsList(String userId){
        if(friendList == null || friendList.isEmpty()){
            friendList = FriendDAO.getFriendList(userId);
        }
        List<String> sortedList = new ArrayList<>();
        sortedList.addAll(friendList);
        List<List<String>> allFriends = new ArrayList<>();
        List<String> list = null;
        for(int i = 0;i<sortedList.size();i++){
           if(i%3 == 0){
               if(list!=null)
                   allFriends.add(list);
               list = new ArrayList<>(3);
               list.add(sortedList.get(i));
           } else{
               if(list!=null){
                   list.add(sortedList.get(i));
               }
           }
           if(i+1 == sortedList.size()){
               allFriends.add(list);
           }
        }
        friends = allFriends;
    }

    public List<UserFriend> getFriendProfile() {
        return friendProfile;
    }

    public void setFriendProfile(ArrayList<UserFriend> friendProfile) {
        this.friendProfile = friendProfile;
    }
    
    public String generateFriendProfile(String userId){
        if(friendProfile == null){
            friendProfile = new ArrayList<>();
        }else{
            friendProfile.clear();
        }
        friendProfile.add(FriendDAO.getFriendProfile(userId));
        if(friendProfile != null && !friendList.isEmpty()){
            return "friendList.xhtml";
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Oops! cannot load profile information for "+userId));
            return "friendList.xhtml";
        }
    }

    public String viewAllFriends(){
        if(friendProfile != null)
            friendProfile.clear();
        return "friendList.xhtml";
    }

    
}

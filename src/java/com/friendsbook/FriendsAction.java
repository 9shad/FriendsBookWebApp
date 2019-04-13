/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsbook;

import com.friendsbook.DAO.FriendDAO;
import com.friendsbook.DAO.RegisterUserDAO;
import com.friendsbook.pojo.UserFriend;
import com.friendsbook.pojo.UserFriendRequest;
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
    private String toUserId;
    
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

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
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
    
    /**
     * Known bug: handling duplicate friend request
     * possible fix: if i update friend request based upon fromuserID and touserID then 
     * all duplicate friend request gets updated
     * @param fromUserId 
     */
    public void sendFriendRequest(String fromUserId){
        UserFriendRequest obj = new UserFriendRequest();
        obj.setFromUserId(fromUserId);
        obj.setToUserId(this.toUserId);
        if(RegisterUserDAO.checkUserId(toUserId)){
            if(friendList != null && friendList.contains(toUserId)){ 
                FacesContext.getCurrentInstance().addMessage("",new FacesMessage(toUserId+ " is already your friend!"));
                toUserId = null;
                return;
            }//else if list is null then we need to hit database to check both users are already friends
            
            //this function sends friend request even for already friends
            //and also sends duplicate friend request if it is not accepted
            //need to write another DB call to counter this behaviour
            if(FriendDAO.sendFriendRequestDAO(obj)){
                this.toUserId=null;
                FacesContext.getCurrentInstance().addMessage("",new FacesMessage("Friend Request sent successfully!"));
            }else{
                FacesContext.getCurrentInstance().addMessage("",new FacesMessage("Oops! something went wrong, Please try again!!"));    
            }
        }else{
            FacesContext.getCurrentInstance().addMessage("",new FacesMessage("Oops!, Invalid User Id!"));    
        }
        
    }
    
}

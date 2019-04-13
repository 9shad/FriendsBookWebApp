/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsbook;

import com.friendsbook.DAO.NotificationDAO;
import com.friendsbook.pojo.FriendNotification;
import com.friendsbook.pojo.MessageNotification;
import com.friendsbook.pojo.UserFriendRequest;
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
@Named(value = "notificationBean")
@SessionScoped
public class NotificationAction implements Serializable {

    /**
     * Creates a new instance of NotificationAction
     */
    public NotificationAction() {
    }
    
    private List<FriendNotification> friendNotifications;
    private List<MessageNotification> messageNotifications;

    public List<FriendNotification> getFriendNotifications() {
        return friendNotifications;
    }

    public void setFriendNotifications(List<FriendNotification> friendNotifications) {
        this.friendNotifications = friendNotifications;
    }

    public List<MessageNotification> getMessageNotifications() {
        return messageNotifications;
    }

    public void setMessageNotifications(List<MessageNotification> messageNotifications) {
        this.messageNotifications = messageNotifications;
    }
    
    public int getCount(String userId){
        int count = 0;
        if(this.friendNotifications == null || this.messageNotifications == null){
            fetchNotification(userId);
        }
        if(this.friendNotifications != null){
            count += this.friendNotifications.size();
        }
        if(this.messageNotifications != null){
            count += this.getMessageNotifications().size();
        }
        return count;
    }
    //this call from front end is omitted since getCount will automatically call this once user logs in
    //in case if data is not fetched properly then replace nav menu with below line
    //<h:commandLink class ="nav-item nav-link ml-sm-2 active" action="#{notificationBean.fetchNotification(loginBean.user.userId)}"><i class="far fa-bell"></i> Notification [#{notificationBean.getCount(loginBean.user.userId)}]</h:commandLink>
    public String fetchNotification(String userId){
        if(messageNotifications == null || messageNotifications.isEmpty())
            messageNotifications = NotificationDAO.getUnProsessedMsgNotification(userId);
        if(friendNotifications == null || friendNotifications.isEmpty())
            friendNotifications = NotificationDAO.getUnProsessedFrndNotification(userId);
        return "notification.xhtml";
    }
    
    public void processRequest(FriendNotification notification, String status, List<String> friendList){
        if(processFriendRequest(notification,status)){
            if(status.equals(UserFriendRequest.ACCEPTED)){
                FacesContext.getCurrentInstance().addMessage("", new FacesMessage("We added "+notification.getFriendRequests().getFromUserId()+" to your friend list."));
                if(friendList != null){
                    friendList.add(0, notification.getFriendRequests().getFromUserId());
                }
            }                
            else
                FacesContext.getCurrentInstance().addMessage("", new FacesMessage("We rejected "+notification.getFriendRequests().getFromUserId()+" friend request."));
            this.friendNotifications.remove(notification);
        }else{
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Oops! something went wrong, please try again!"));
        }
    }
    
    private boolean processFriendRequest(FriendNotification notification, String status) {      
            if(NotificationDAO.processFriendRequest(notification.getId(), status)) {
                    return NotificationDAO.processNotification(notification.getId());
            }
            return false;
    }
    
    public String processRequest(MessageNotification notification, String status){
        if(status.equals("hide")){
            if(NotificationDAO.processNotification(notification.getId())){
                this.messageNotifications.remove(notification);
                FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Message notification processed successfully!"));
            }else{
                FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Oops! something went wrong, please try again!"));
            }
        }        
        return "";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsbook;

import com.friendsbook.DAO.UserMessageDAO;
import com.friendsbook.pojo.UserMessage;
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
@Named(value = "messageBean")
@SessionScoped
public class MessageAction implements Serializable {

    /**
     * Creates a new instance of MessageAction
     */
    public MessageAction() {
    }
    
    private String message;
    List<UserMessage> historyMessages;
    private String toUserId;
    private String htmlOutput;
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserMessage> getHistoryMessages() {
        return historyMessages;
    }

    public void setHistoryMessages(List<UserMessage> historyMessages) {
        this.historyMessages = historyMessages;
    }
    
    public void sendMessage(String fromUserId){
        UserMessage userMessage = new UserMessage();
        userMessage.setFromUser(fromUserId);
        userMessage.setToUser(this.toUserId);
        userMessage.setMsgDescription(this.message);
        if(UserMessageDAO.sendMessage(userMessage)) {
            historyMessages.add(userMessage);
            //append message to  html output
        }else {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Oops! something went wrong, please try again."));
        }
        this.message = "";
    }

    public String getHtmlOutput() {
        return htmlOutput;
    }
    
    public void loadHistoryMessage(String fromUserId, String toUserId) {
        this.toUserId = toUserId;
        if(historyMessages!=null || !historyMessages.isEmpty()){
            historyMessages.clear();
        }
        historyMessages = UserMessageDAO.getHistoryMessages(fromUserId, toUserId);
        generateHtmlOutput();
    }
    
    public void generateHtmlOutput(){
        if(historyMessages!=null){
            htmlOutput = "";
            StringBuilder sb = new StringBuilder();
            for(UserMessage message : historyMessages){
                
            }
        }
    }
    
    public String loadMessage(String fromUserId, String toUserId){
        this.loadHistoryMessage(fromUserId, toUserId);
        return "messages.xhtml";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsbook;

import com.friendsbook.DAO.NotificationDAO;
import com.friendsbook.DAO.UserMessageDAO;
import com.friendsbook.pojo.MessageNotification;
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
    private String htmlOutput = "<div class=\"p-2 mb-1 bg-secondary text-white\" style=\"width:100%;border-radius: 25px\">Select a user to start conversation!</div>";
    
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
        if(toUserId == null || toUserId.isEmpty())
            return;
        UserMessage userMessage = new UserMessage();
        userMessage.setFromUser(fromUserId);
        userMessage.setToUser(this.toUserId);
        userMessage.setMsgDescription(this.message);
        if(UserMessageDAO.sendMessage(userMessage)) {
            historyMessages.add(userMessage);
            //append message to  html output
            StringBuffer sb = new StringBuffer();
            sb.append("<div class=\"p-2 mb-2 bg-secondary text-white ml-auto\" style=\"width:40%;border-radius: 25px\">");
            sb.append(userMessage.getMsgDescription());
            sb.append("<br/><footer class=\"blockquote-footer text-white\" style=\"margin-left:48%\">");
            sb.append(userMessage.getTimeStamp());
            sb.append("</footer>");
            sb.append("</div>");
            if(historyMessages.size()>1)
                htmlOutput += sb.toString();
            else
                htmlOutput = sb.toString();
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
        if(historyMessages!=null && !historyMessages.isEmpty()){
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
                if(toUserId.equals(message.getToUser())){ 
                    //indicates loggedin user has received this message to toUser
                    sb.append("<div class=\"p-2 mb-2 bg-secondary text-white ml-auto\" style=\"width:40%;border-radius: 25px\">");
                    sb.append(message.getMsgDescription());
                    sb.append("<br/><footer class=\"blockquote-footer text-white\" style=\"margin-left:50%\">");
                    sb.append(message.getTimeStamp());
                    sb.append("</footer>");
                    sb.append("</div>");
                }else{
                    sb.append("<div class=\"p-2 mb-2 bg-info text-white mr-auto\" style=\"width:40%;border-radius: 25px\">");
                    sb.append(message.getMsgDescription());
                    sb.append("<br/><footer class=\"blockquote-footer text-white\" style=\"margin-left:50%\">");
                    sb.append(message.getTimeStamp());
                    sb.append("</footer>");
                    sb.append("</div>");
                }
            }
            if(sb.length() > 0)
                htmlOutput = sb.toString();
            else
                htmlOutput = "<div class=\"p-1 mb-1 bg-secondary text-white\" style=\"width:100%;border-radius: 25px\"> Send a message to start a new conversation!</div>";
        }
    }
    
    public String loadMessage(String fromUserId, String toUserId,MessageNotification notification, List<MessageNotification> messageNotifications){
        if(NotificationDAO.processNotification(notification.getId())){
            messageNotifications.remove(notification);
            this.loadHistoryMessage(fromUserId, toUserId);
            return "messages.xhtml";
        }else{
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Oops! something went wrong, please try again!"));
            return "";
        }
    }
    
}

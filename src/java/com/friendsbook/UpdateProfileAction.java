/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsbook;

import com.friendsbook.DAO.UpdateProfileDAO;
import com.friendsbook.pojo.User;
import com.friendsbook.pojo.UserFriend;
import java.io.Serializable;
import java.time.LocalDate;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Home
 */
@Named(value = "updateProfileBean")
@SessionScoped
public class UpdateProfileAction implements Serializable{

    /**
     * Creates a new instance of UpdateProfileAction
     */
    public UpdateProfileAction() {
    }
    
    private UserFriend userInfo;
    private int month;
    private String day;
    private String year;
    

    public UserFriend getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserFriend userInfo) {
        this.userInfo = userInfo;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    
    public String loadProfile(User user){
        userInfo = new UserFriend();
        userInfo.setName(user.getName());
        userInfo.setEmail(user.getEmail());
        userInfo.setSchool(user.getSchool());
        userInfo.setGender(user.getGender());
        userInfo.setUserId(user.getUserId());
        this.day = user.getBirthdayDate().getDayOfMonth()+"";
        this.month = user.getBirthdayDate().getMonthValue();
        this.year = user.getBirthdayDate().getYear()+"";
        return "userProfile.xhtml";
    }
    
    public void updateProfile(User user){
        StringBuilder description = new StringBuilder();
        
        if(!user.getName().equals(userInfo.getName())){
            description.append("Updated name to: "+userInfo.getName()+". <br/>");
        }
        
        if(!user.getGender().equals(userInfo.getGender())){
            description.append("Updated gender to : "+userInfo.getGender()+". <br/>");
        }
        
        if(!user.getSchool().equals(userInfo.getSchool())){
            description.append("Updated school to: " + user.getSchool()+". <br/>");
        }
        
        //YYYY-MM-DD
        userInfo.setBirthdayDate(LocalDate.of(Integer.parseInt(year), month, Integer.parseInt(day)));
        if(!user.getBirthdayDate().isEqual(userInfo.getBirthdayDate())){
            description.append("Updated birthdate to: " + userInfo.getBirthdayDate()+". <br/>");
        }
				
        if(!user.getEmail().equals(userInfo.getEmail())){
            description.append("Updated email to: " + userInfo.getEmail()+". <br/>");
        }
        
        if(description.length() != 0){
           if(UpdateProfileDAO.updateUserProfileDAO(user, description.toString())){
               FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Profile Updated Successfully!"));
               user.setName(userInfo.getName());
               user.setEmail(userInfo.getEmail());
               user.setGender(userInfo.getGender());
               user.setSchool(userInfo.getSchool());
               user.setBirthdayDate(userInfo.getBirthdayDate());
           }else{
               FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Oops! unable to updated profile!"));
           } 
        }else{
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Please change profile information before clicking update!"));
        }
        
    }
    
}

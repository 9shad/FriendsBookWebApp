/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsbook;

import com.friendsbook.DAO.RegisterUserDAO;
import com.friendsbook.pojo.User;
import com.friendsbook.util.EncryptPassword;
import java.time.LocalDate;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Home
 */
@Named(value = "registerBean")
@RequestScoped
public class RegisterAction {

    /**
     * Creates a new instance of RegisterAction
     */
    public RegisterAction() {
    }
    
    private User user;
    private String firstName;
    private String lastName;
    private String userId;
    private String password;
    private String school;
    private String gender;
    private int month;
    private String day;
    private String year;
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String registerUser(){
        this.user = new User();
        user.setName(this.firstName +" "+this.lastName);
        user.setPassword(EncryptPassword.cryptWithMD5(this.password));
        user.setGender(this.gender);
        user.setUserId(this.userId);
        user.setEmail(this.email);
        user.setSchool(this.school);
        //YYYY-MM-dd
        try{
            user.setBirthdayDate(LocalDate.of(Integer.parseInt(this.year), this.month, Integer.parseInt(this.day)));
        }catch(Exception e){
            FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("Invalid Date!"));
        }
        String result = this.validateUserId(user.getUserId());
        if( result != null){
            FacesContext.getCurrentInstance().addMessage("error", new FacesMessage(result));
            return "index.xhtml";
        }
        result = this.checkPasswordString(user.getUserId(), this.password);
    
        if(result != null){
            FacesContext.getCurrentInstance().addMessage("error", new FacesMessage(result));
            return "index.xhtml";
        }
        
        if(RegisterUserDAO.addUserToDB(user)){
            this.setEmail(null);
            this.setFirstName(null);
            this.setLastName(null);
            this.setPassword(null);
            this.setSchool(null);
            this.setUserId(null);
            this.setGender(null);
            this.setYear(null);
            this.setMonth(0);
            this.setDay(null);
            FacesContext.getCurrentInstance().addMessage("success", new FacesMessage("Regrestration Successful!"));
        }else{
            FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("Oops!! something went wrong, Please try again!"));
        }
        
        return "index.xhtml";
    }
    
    private String validateUserId(String userId){
        if(!this.checkUserIdString(userId)){
                return "User ID must contain one letter, one number and one character from {#,?,!,*}";
        }else if(RegisterUserDAO.checkUserId(userId)){
                return "User ID Already Exists, please try again!";
        }
        return null;
    }

    private boolean checkUserIdString(String input) {
       // String specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";
            String specialChars = "#?!*";
        char currentCharacter;
        boolean numberPresent = false;
        boolean characterPresent = false;
        boolean specialCharacterPresent = false;

        for (int i = 0; i < input.length(); i++) {
            currentCharacter = input.charAt(i);
            if (Character.isDigit(currentCharacter)) {
                numberPresent = true;
            } else if (Character.isAlphabetic(currentCharacter)) {
                characterPresent = true;
            } else if (specialChars.contains(String.valueOf(currentCharacter))) {
                specialCharacterPresent = true;
            }
        }
        return
          numberPresent && characterPresent && specialCharacterPresent;
    }

    private String checkPasswordString(String userId,String pass){
            if(userId.equals(pass) || pass.contains(userId)){
                    return "Password cannot contain User Id information";
            }
            return null;
    }
    
    
}

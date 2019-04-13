/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.friendsbook;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Home
 */
@Named(value = "logoutBean")
@RequestScoped
public class LogoutAction {

    /**
     * Creates a new instance of LogoutAction
     */
    public LogoutAction() {
    }
    
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        //FacesContext.getCurrentInstance().getExternalContext().redirect("/login.xhtml");
        return "index.xhtml?faces-redirect=true";
    }
    
}

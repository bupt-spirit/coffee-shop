/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop.web;

import coffeeshop.ejb.UserManager;
import coffeeshop.ejb.UserManagerException;
import coffeeshop.web.util.MessageBundle;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

@Named
@SessionScoped
public class ChangePasswordController implements Serializable {

    @Inject
    private UserManager userManager;

    @Inject
    private UserInfoController userInfoController;

    @Inject
    private MessageBundle messageBundle;

    String oldPassword, newPassword, reenteredNewPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getReenteredPassword() {
        return reenteredNewPassword;
    }

    public void setReenteredPassword(String reenteredPassword) {
        this.reenteredNewPassword = reenteredPassword;
    }

    public void changePassword() throws UserManagerException {

        if (userInfoController.getCurrentUser().) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    messageBundle.getString("")));
        } else if (newPassword == reenteredNewPassword) {
            userManager.changePassword(userInfoController.getCurrentUser().getUsername(), newPassword);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    messageBundle.getString("Ui.Message.ChangePasswordSuccessTitle")));
        }

    }

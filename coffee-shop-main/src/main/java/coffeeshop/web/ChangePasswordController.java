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
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named
@RequestScoped
public class ChangePasswordController implements Serializable {

    @Inject
    private UserManager userManager;

    @Inject
    private UserInfoController userInfoController;

    @Inject
    private MessageBundle bundle;

    private String oldPassword, newPassword, reenteredNewPassword;
    
    private UIComponent oldPasswordComponent, reenteredPasswordComponent;

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

    public String getReenteredNewPassword() {
        return reenteredNewPassword;
    }

    public void setReenteredNewPassword(String reenteredPassword) {
        this.reenteredNewPassword = reenteredPassword;
    }

    public UIComponent getOldPasswordComponent() {
        return oldPasswordComponent;
    }

    public void setOldPasswordComponent(UIComponent oldPasswordComponent) {
        this.oldPasswordComponent = oldPasswordComponent;
    }

    public UIComponent getReenteredPasswordComponent() {
        return reenteredPasswordComponent;
    }

    public void setReenteredPasswordComponent(UIComponent reenteredPasswordComponent) {
        this.reenteredPasswordComponent = reenteredPasswordComponent;
    }

    public void changePassword() throws UserManagerException {
        if (newPassword.equals(reenteredNewPassword)) {
            try {
                userManager.changePassword(userInfoController.getCurrentUser().getUsername(), oldPassword, newPassword);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        bundle.getString("Ui.Message.ChangePasswordSuccessTitle"),
                        null
                ));

            } catch (UserManagerException ex) {
                FacesContext.getCurrentInstance().addMessage(oldPasswordComponent.getClientId(),
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                bundle.getString("Ui.Message.ChangePassword.OldPasswordWrong"),
                                null
                        ));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(reenteredPasswordComponent.getClientId(),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            bundle.getString("Ui.Message.ChangePassword.Mismatch"),
                            null
                    ));
        }
        oldPassword = newPassword = reenteredNewPassword = null;
    }

}

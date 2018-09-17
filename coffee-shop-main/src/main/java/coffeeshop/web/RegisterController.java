package coffeeshop.web;

import coffeeshop.ejb.UserManager;
import coffeeshop.web.util.MessageBundle;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class RegisterController {

    @Inject
    private UserManager userManager;

    @Inject
    private MessageBundle messageBundle;

    private String username, password, nickname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String register() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (userManager.isUserExisting(username)) {
            facesContext.addMessage("username",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            messageBundle.getString("Ui.Message.UsernameAlreadyExists"),
                            null
                    ));
            return null;
        }
        userManager.addCustomer(username, password, nickname);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                messageBundle.getString("Ui.Message.RegisterSuccessTitle"),
                messageBundle.getString("Ui.Message.RegisterSuccessDetail")
        ));
        return "/login";
    }

}

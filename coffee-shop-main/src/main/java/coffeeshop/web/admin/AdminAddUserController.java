package coffeeshop.web.admin;

import coffeeshop.ejb.UserManager;
import coffeeshop.web.util.MessageBundle;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class AdminAddUserController implements Serializable {

    private static final Logger LOG = Logger.getLogger(AdminAddUserController.class.getName());

    private static final long serialVersionUID = 1L;

    @EJB
    private UserManager userManager;

    @Inject
    private MessageBundle bundle;

    private String username;
    private String password;
    private String role;
    private String nickname;
    private Collection<String> roles;
    
    @PostConstruct
    void init() {
        this.roles = userManager.getRoles();
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void addUser() {
        if (userManager.isUserExisting(username)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    bundle.getString("Ui.Admin.Message.UserAlreadyExistsTitle"),
                    bundle.getFormated("Ui.Admin.Message.UserAlreadyExistsDetail", username)
            ));
        } else {
            userManager.addUser(username, password, role);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    bundle.getString("Ui.Admin.Message.AddUserSuccessTitle"),
                    bundle.getFormated("Ui.Admin.Message.AddUserSuccessDetail", username, role)
            ));
        }
    }
}

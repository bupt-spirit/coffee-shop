package coffeeshop.web;

import coffeeshop.ejb.CustomerInfoManager;
import coffeeshop.ejb.CustomerInfoManagerException;
import coffeeshop.ejb.UserManager;
import coffeeshop.ejb.UserManagerException;
import coffeeshop.entity.UserInfo;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.util.List;

@Named
@RequestScoped
public class UserInfoController {

    @EJB
    private UserManager userManager;

    @Inject
    private SecurityContext securityContext;

    public boolean isLoggedIn() {
        return securityContext.getCallerPrincipal() != null;
    }

    public UserInfo getCurrentUser() throws UserManagerException {
        if (isLoggedIn()) {
            return userManager.getUuser(securityContext.getCallerPrincipal().getName());
        } else {
            throw new ValidatorException(new FacesMessage("Not logged in but getCurrentUser called"));
        }
    }
}

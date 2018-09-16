package coffeeshop.web;

import coffeeshop.ejb.CustomerInfoManager;
import coffeeshop.ejb.CustomerInfoManagerException;
import coffeeshop.ejb.UserManager;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;

@Named
@RequestScoped
public class UserNavController {

    @EJB
    private UserManager userManager;
    
    @EJB
    private CustomerInfoManager customerInfoManager;

    @Inject
    private SecurityContext securityContext;

    public String getRole() {
        List<String> roles = userManager.getRoles();
        for (String role : roles) {
            if (securityContext.isCallerInRole(role)) {
                return role;
            }
        }
        return null;
    }

    public boolean isLoggedIn() {
        return securityContext.getCallerPrincipal() != null;
    }
    
    public String getNickname() throws ValidatorException {
        String username = getUsername();
        try {
            return customerInfoManager.getNickname(username);
        } catch (CustomerInfoManagerException ex) {
            throw new ValidatorException(new FacesMessage("Not a customer but getNickname called"));
        }
    }
    
    public String getUsername() throws ValidatorException {
        if (isLoggedIn()) {
            return securityContext.getCallerPrincipal().getName();
        } else {
            throw new ValidatorException(new FacesMessage("Not logged in but getUsername called"));
        }
    }
}

package coffeeshop.web.admin;

import coffeeshop.ejb.UserManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class InitController {

    private static final Logger LOG = Logger.getLogger(InitController.class.getName());

    private static final String DEFAULT_ADMIN_USERNAME = "admin";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin";
    
    @EJB
    private UserManager userManager;

    public void checkAndAddDefaultAdminUser() {
        if (!userManager.isUserExisting(DEFAULT_ADMIN_USERNAME)) {
            LOG.log(Level.INFO, "Default admin user does not exists");
            userManager.addAdmin(DEFAULT_ADMIN_USERNAME, DEFAULT_ADMIN_PASSWORD);
            LOG.log(Level.INFO, "New admin user added, username: {0}, password: {1}", new String[]{
                DEFAULT_ADMIN_USERNAME,
                DEFAULT_ADMIN_PASSWORD
            });
        }
    }
}

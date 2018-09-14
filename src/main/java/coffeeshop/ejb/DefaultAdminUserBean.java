package coffeeshop.ejb;

import coffeeshop.ejb.UserManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class DefaultAdminUserBean {

    private static final Logger LOG = Logger.getLogger(DefaultAdminUserBean.class.getName());

    private static final String DEFAULT_ADMIN_USERNAME = "admin";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin";

    @Inject
    private UserManager userManager;

    public void checkAndAddDefaultAdminUser() {
        if (!userManager.isUserExisting(DEFAULT_ADMIN_USERNAME)) {
            LOG.log(Level.INFO, "Default admin user does not exists");
            userManager.addUser(DEFAULT_ADMIN_USERNAME, DEFAULT_ADMIN_PASSWORD, "admin");
            LOG.log(Level.INFO, "New admin user added, username: {0}, password: {1}", new String[]{
                DEFAULT_ADMIN_USERNAME,
                DEFAULT_ADMIN_PASSWORD
            });
        }
    }
}

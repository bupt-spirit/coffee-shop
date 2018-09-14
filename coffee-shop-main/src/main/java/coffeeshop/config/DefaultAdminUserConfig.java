package coffeeshop.config;

import coffeeshop.ejb.UserManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton // Must use javax.ejb.Singleton
public class DefaultAdminUserConfig {

    private static final Logger LOG = Logger.getLogger(DefaultAdminUserConfig.class.getName());

    private static final String DEFAULT_ADMIN_USERNAME = "admin";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin";

    @Inject
    private UserManager userManager;

    @PostConstruct
    public void checkAndAddDefaultAdminUser() {
        LOG.log(Level.INFO, "Start check and add default admin user");
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

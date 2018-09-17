package coffeeshop.ejb;

import coffeeshop.config.ApplicationConfig;
import coffeeshop.entity.Customer;
import coffeeshop.entity.Staff;
import coffeeshop.entity.Store;
import coffeeshop.entity.UserInfo;
import coffeeshop.facade.StoreFacade;
import coffeeshop.facade.UserInfoFacade;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UserManagerBean implements UserManager {

    private static final Logger LOG = Logger.getLogger(UserManagerBean.class.getName());
    private static final List<String> ROLES = new ArrayList<>();

    static {
        ROLES.add("admin");
        ROLES.add("customer");
        ROLES.add("staff");
    }

    @EJB
    private UserInfoFacade userInfoFacade;
    @EJB
    private StoreFacade storeFacade;
    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private Pbkdf2PasswordHash passwordHash;
    @Inject
    private ApplicationConfig applicationConfig;

    @Override
    public List<String> getRoles() {
        return ROLES;
    }

    @PostConstruct
    private void init() {
        passwordHash.initialize(applicationConfig.getHashAlgorithmParameters());
    }

    @Override
    public void changePassword(String username, String password) throws UserManagerException {
        UserInfo userInfo = userInfoFacade.findByUsername(username);
        if (userInfo == null) {
            throw new UserManagerException("No such user");
        } else {
            userInfo.setPassword(passwordHash.generate(password.toCharArray()));
            userInfoFacade.edit(userInfo);
        }
    }

    @Override
    public boolean isUserExisting(String username) {
        return userInfoFacade.findByUsername(username) != null;
    }

    @Override
    public boolean verifyPassword(String username, String password) throws UserManagerException {
        UserInfo userInfo = userInfoFacade.findByUsername(username);
        if (userInfo == null) {
            throw new UserManagerException("No such user");
        } else {
            return passwordHash.verify(password.toCharArray(), userInfo.getPassword());
        }
    }

    @Override
    public void addCustomer(String username, String password, String nickname) {
        if (isUserExisting(username)) {
            throw new EJBException("User already exists");
        } else {
            UserInfo newUser = new UserInfo(null, username, new Date(),
                    passwordHash.generate(password.toCharArray()), "customer");
            Customer customer = new Customer(null, nickname);
            customer.setUserInfo(newUser);
            newUser.setCustomer(customer);
            userInfoFacade.create(newUser);
        }
    }

    @Override
    public void addAdmin(String username, String password) {
        if (isUserExisting(username)) {
            throw new EJBException("User already exists");
        } else {
            UserInfo newUser = new UserInfo(null, username, new Date(),
                    passwordHash.generate(password.toCharArray()), "admin");
            userInfoFacade.create(newUser);
        }
    }

    @Override
    public void addStaff(String username, String password, Store store) {
        if (isUserExisting(username)) {
            throw new EJBException("User already exists");
        } else {
            UserInfo newUser = new UserInfo(null, username, new Date(),
                    passwordHash.generate(password.toCharArray()), "customer");
            Staff staff = new Staff();
            staff.setUserInfo(newUser);
            newUser.setStaff(staff);
            store.getStaffList().add(staff);
            userInfoFacade.create(newUser);
            storeFacade.edit(store);
        }
    }

    @Override
    public UserInfo getUuser(String username) throws UserManagerException {
        UserInfo userInfo = userInfoFacade.findByUsername(username);
        if (userInfo == null) {
            throw new UserManagerException("No such user");
        } else {
            return userInfo;
        }
    }
}

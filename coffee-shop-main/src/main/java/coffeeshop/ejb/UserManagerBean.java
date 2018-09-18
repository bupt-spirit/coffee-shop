package coffeeshop.ejb;

import coffeeshop.config.ApplicationConfig;
import coffeeshop.entity.Customer;
import coffeeshop.entity.Staff;
import coffeeshop.entity.Store;
import coffeeshop.entity.UserInfo;
import coffeeshop.facade.CustomerFacade;
import coffeeshop.facade.StaffFacade;
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
    private StaffFacade staffFacade;
    @EJB
    private CustomerFacade customerFacade;
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
    public UserInfo addCustomer(String username, String password, String nickname) throws UserManagerException {
        if (isUserExisting(username)) {
            throw new UserManagerException("User already exists");
        } else {
            UserInfo newUser = new UserInfo(null, username, new Date(),
                    passwordHash.generate(password.toCharArray()), "customer");
            Customer customer = new Customer(null, nickname);
            customer.setUserInfo(newUser);
            newUser.setCustomer(customer);
            userInfoFacade.create(newUser);
            customerFacade.create(customer);
            return newUser;
        }
    }

    @Override
    public UserInfo addAdmin(String username, String password) throws UserManagerException {
        if (isUserExisting(username)) {
            throw new UserManagerException("User already exists");
        } else {
            UserInfo newUser = new UserInfo(null, username, new Date(),
                    passwordHash.generate(password.toCharArray()), "admin");
            userInfoFacade.create(newUser);
            return newUser;
        }
    }

    @Override
    public UserInfo addStaff(String username, String password, Store store) throws UserManagerException {
        if (isUserExisting(username)) {
            throw new UserManagerException("User already exists");
        } else if (store == null) {
            throw new UserManagerException("Store is null");
        } else {
            UserInfo newUser = new UserInfo(null, username, new Date(),
                    passwordHash.generate(password.toCharArray()), "staff");
            Staff staff = new Staff();
            staff.setUserInfo(newUser);
            staff.setStoreId(store);
            newUser.setStaff(staff);
            store.getStaffList().add(staff);
            userInfoFacade.create(newUser);
            staffFacade.create(staff);
            storeFacade.edit(store);
            return newUser;
        }
    }

    @Override
    public UserInfo getUser(String username) throws UserManagerException {
        UserInfo userInfo = userInfoFacade.findByUsername(username);
        if (userInfo == null) {
            throw new UserManagerException("No such user");
        } else {
            return userInfo;
        }
    }

    @Override
    public List<Staff> getStaffs() {
        return staffFacade.findAll();
    }

    @Override
    public void removeStaff(Staff staff) {
        UserInfo userInfo = staff.getUserInfo();
        userInfo.setStaff(null);
        staffFacade.remove(staff);
        userInfoFacade.remove(userInfo);
    }

    @Override
    public UserInfo getUserById(int id) throws UserManagerException {
        UserInfo user = userInfoFacade.find(id);
        if (user == null) {
            throw new UserManagerException("User id not exists");
        }
        return user;
    }
}

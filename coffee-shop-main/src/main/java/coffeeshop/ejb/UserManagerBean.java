package coffeeshop.ejb;

import coffeeshop.config.ApplicationConfig;
import coffeeshop.entity.Customer;
import coffeeshop.entity.Staff;
import coffeeshop.entity.Store;
import coffeeshop.entity.UserInfo;
import coffeeshop.util.ConstraintViolationTester;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Stateless
public class UserManagerBean implements UserManager {

    private static final Logger LOG = Logger.getLogger(UserManagerBean.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    private ApplicationConfig applicationConfig;

    private static final Collection<String> ROLES = new ArrayList<>();

    static {
        ROLES.add("admin");
        ROLES.add("customer");
        ROLES.add("staff");
    }

    @Override
    public Collection<String> getRoles() {
        return ROLES;
    }

    @PostConstruct
    private void init() {
        passwordHash.initialize(applicationConfig.getHashAlgorithmParameters());
    }

    @Override
    public void changePassword(String username, String password) {
        // TODO: add changePassword method
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isUserExisting(String username) {
        try {
            em.createNamedQuery("UserInfo.findByUsername", UserInfo.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public String getUserRole(String username) throws NoResultException {
        String role = em.createNamedQuery("UserInfo.findByUsername", UserInfo.class)
                .setParameter("username", username)
                .getSingleResult().getRole();
        return role;
    }

    @Override
    public void verifyPassword(String username, String password) {
        // TODO: add verifyPassword method
        throw new UnsupportedOperationException("Not supported yet.");
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
            em.persist(newUser);
        }
    }

    @Override
    public void addAdmin(String username, String password) {
        if (isUserExisting(username)) {
            throw new EJBException("User already exists");
        } else {
            UserInfo newUser = new UserInfo(null, username, new Date(),
                    passwordHash.generate(password.toCharArray()), "admin");
            em.persist(newUser);
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
            em.persist(newUser);
            em.persist(staff);
            em.merge(store);
        }
    }
}

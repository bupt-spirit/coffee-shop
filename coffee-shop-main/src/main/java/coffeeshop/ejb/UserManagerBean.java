package coffeeshop.ejb;

import coffeeshop.entity.UserInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

@Stateless
public class UserManagerBean implements UserManager {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

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
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        passwordHash.initialize(parameters);
    }

    @Override
    public void addUser(String username, String password, String role) {
        if (isUserExisting(username)) {
            throw new EJBException("User already exists");
        } else {
            if (!getRoles().contains(role)) {
                throw new EJBException("Invalid role " + role);
            }
            UserInfo newUser = new UserInfo(0, username, new Date(),
                    passwordHash.generate(password.toCharArray()), role);
            em.persist(newUser);
        }
    }

    @Override
    public void changePassword(String username, String password) {
        // TODO: add changePassword method
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isUserExisting(String username) {
        try {
            em.createQuery("SELECT u FROM UserInfo u WHERE u.username = :username", UserInfo.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public String getUserRole(String username) throws NoResultException {
        String role = em.createQuery("SELECT u.role FROM UserInfo u WHERE u.username = :username", String.class)
                .setParameter("username", username)
                .getSingleResult();
        return role;
    }
}

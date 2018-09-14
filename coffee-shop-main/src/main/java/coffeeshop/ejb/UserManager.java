package coffeeshop.ejb;

import java.util.Collection;

public interface UserManager {

    void addUser(String username, String password, String role);

    String getUserRole(String username);

    void changePassword(String username, String password);
    
    void verifyPassword(String username, String password);

    boolean isUserExisting(String username);

    Collection<String> getRoles();
}

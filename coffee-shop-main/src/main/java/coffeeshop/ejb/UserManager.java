package coffeeshop.ejb;

import coffeeshop.entity.Store;
import java.util.Collection;
import java.util.List;

public interface UserManager {

    void addCustomer(String username, String password, String nickname);

    void addAdmin(String username, String password);

    void addStaff(String username, String password, Store store);

    String getUserRole(String username) throws UserManagerException;

    void changePassword(String username, String password) throws UserManagerException;

    boolean verifyPassword(String username, String password) throws UserManagerException;

    boolean isUserExisting(String username);

    List<String> getRoles();
}

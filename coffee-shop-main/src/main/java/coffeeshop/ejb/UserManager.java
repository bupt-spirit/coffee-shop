package coffeeshop.ejb;

import coffeeshop.entity.Store;
import coffeeshop.entity.UserInfo;
import java.util.List;

public interface UserManager {

    void addCustomer(String username, String password, String nickname);

    void addAdmin(String username, String password);

    void addStaff(String username, String password, Store store);

    UserInfo getUser(String username) throws UserManagerException;

    void changePassword(String username, String password) throws UserManagerException;

    boolean verifyPassword(String username, String password) throws UserManagerException;

    boolean isUserExisting(String username);

    List<String> getRoles();
}

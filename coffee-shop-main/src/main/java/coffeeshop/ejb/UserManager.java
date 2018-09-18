package coffeeshop.ejb;

import coffeeshop.entity.Store;
import coffeeshop.entity.UserInfo;
import java.util.List;

public interface UserManager {

    UserInfo addCustomer(String username, String password, String nickname);

    UserInfo addAdmin(String username, String password);

    UserInfo addStaff(String username, String password, Store store);

    UserInfo getUser(String username) throws UserManagerException;

    void changePassword(String username, String password) throws UserManagerException;

    boolean verifyPassword(String username, String password) throws UserManagerException;

    boolean isUserExisting(String username);

    List<String> getRoles();
}

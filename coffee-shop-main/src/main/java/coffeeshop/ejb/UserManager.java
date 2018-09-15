package coffeeshop.ejb;

import coffeeshop.entity.Store;
import java.util.Collection;

public interface UserManager {

    void addAdmin(String username, String password);

    void addCustomer(String username, String password, String nickname);

    void addStaff(String username, String password, Store store);

    String getUserRole(String username);

    void changePassword(String username, String password);

    void verifyPassword(String username, String password);

    boolean isUserExisting(String username);

    Collection<String> getRoles();
}

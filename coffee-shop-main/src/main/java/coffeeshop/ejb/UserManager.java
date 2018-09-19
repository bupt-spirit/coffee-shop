package coffeeshop.ejb;

import coffeeshop.entity.Staff;
import coffeeshop.entity.Store;
import coffeeshop.entity.UserInfo;
import java.util.List;

public interface UserManager {

    UserInfo addCustomer(String username, String password, String nickname) throws UserManagerException;

    UserInfo addAdmin(String username, String password) throws UserManagerException;

    UserInfo addStaff(String username, String password, Store store) throws UserManagerException;

    UserInfo getUser(String username) throws UserManagerException;
    
    UserInfo getUserById(int id) throws UserManagerException;

    void changePassword(String username, String oldPassword, String newPassword) throws UserManagerException;

    boolean verifyPassword(String username, String password) throws UserManagerException;

    boolean isUserExisting(String username);

    List<String> getRoles();
    
    List<Staff> getStaffs();
    
    void removeStaff(Staff staff);
}

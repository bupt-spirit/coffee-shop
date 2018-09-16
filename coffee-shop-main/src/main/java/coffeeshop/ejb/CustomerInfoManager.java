package coffeeshop.ejb;

public interface CustomerInfoManager {
    
    boolean isCustomer(String username);
    
    String getNickname(String username) throws CustomerInfoManagerException;
}

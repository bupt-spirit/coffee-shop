package coffeeshop.ejb;

public interface CustomerInfoManager {
    
    boolean isCustomer(String username) throws CustomerInfoManagerException;
    
    String getNickname(String username) throws CustomerInfoManagerException;
}

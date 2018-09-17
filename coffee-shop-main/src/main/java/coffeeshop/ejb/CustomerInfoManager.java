package coffeeshop.ejb;

import coffeeshop.entity.Customer;

public interface CustomerInfoManager {

    boolean isCustomer(String username);

    String getNickname(String username) throws CustomerInfoManagerException;
    
    void addAddress(Customer customer,String country,String province,String city,String district,String detail,String receiver,String receiverPhone);
}

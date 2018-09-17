package coffeeshop.ejb;

import coffeeshop.entity.Address;
import coffeeshop.entity.Customer;

public interface CustomerInfoManager {

    boolean isCustomer(String username);
    
    void addAddress(Customer customer,String country,String province,String city,String district,String detail,String receiver,String receiverPhone);

    void removeAddress(Address address,Customer customer) throws CustomerInfoManagerException;
    
    Address getAddressById(int id) throws CustomerInfoManagerException;
}

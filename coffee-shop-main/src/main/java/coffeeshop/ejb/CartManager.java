package coffeeshop.ejb;

import coffeeshop.entity.Address;
import coffeeshop.entity.Store;
import javax.persistence.criteria.Order;

// Stateful cart ejb interface which stores user's cart info
public interface CartManager {
    
    Order check(Address address, Store store);
}

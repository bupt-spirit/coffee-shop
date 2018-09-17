package coffeeshop.ejb;

import coffeeshop.entity.Store;
import java.util.List;

public interface StoreManager {
    
    void addStore(String country, String province, String district, String city, String detail);
    
    List<Store> getStores();
    
    Store getStoreById(int id) throws StoreManagerException;
}

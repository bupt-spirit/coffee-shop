package coffeeshop.ejb;

import coffeeshop.entity.Store;
import java.util.List;

public interface StoreManager {

    Store addStore(Store store);
    
    List<Store> getStores();
    
    Store getStoreById(int id) throws StoreManagerException;
    
    void removeStore(Store selectedStore) throws StoreManagerException;
}

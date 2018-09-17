package coffeeshop.ejb;

import coffeeshop.entity.Store;
import java.util.List;

public interface StoreManager {
    
    public void addStore(String country, String province, String district, String city, String detail);
    
    public List<Store> getStores();
}

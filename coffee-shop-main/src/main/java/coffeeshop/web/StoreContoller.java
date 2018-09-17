package coffeeshop.web;

import coffeeshop.ejb.StoreManager;
import coffeeshop.entity.Store;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class StoreContoller {
    
    @EJB
    private StoreManager storeManager;
    
    public List<Store> getStores() {
        return storeManager.getStores();
    }
}

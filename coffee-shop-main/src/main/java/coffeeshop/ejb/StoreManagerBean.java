package coffeeshop.ejb;

import coffeeshop.entity.Store;
import coffeeshop.facade.StoreFacade;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class StoreManagerBean implements StoreManager {

    
    @EJB
    private StoreFacade storeFacade;
    
    @Override
    public void addStore(String country, String province, String district, String city, String detail){
        Store newStore = new Store(null, country, province, city, district, detail, (short)1);
        storeFacade.create(newStore);
    }
    
    @Override
    public List<Store> getStores(){
        return storeFacade.findAll();
    }

}

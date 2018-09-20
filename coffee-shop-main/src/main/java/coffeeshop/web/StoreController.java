package coffeeshop.web;

import coffeeshop.ejb.StoreManager;
import coffeeshop.ejb.StoreManagerException;
import coffeeshop.entity.Store;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class StoreController {

    private static final Logger LOG = Logger.getLogger(StoreController.class.getName());
    
    @EJB
    private StoreManager storeManager;
    
    private Store selectedStore;

    public Store getSelectedStore() {
        return selectedStore;
    }

    public void setSelectedStore(Store selectedStore) {
        this.selectedStore = selectedStore;
    }
     
    public List<Store> getStores() {
        List<Store> selectedStoreList = new ArrayList<Store>();
        for(Store store : storeManager.getStores()){
            if (store.getIsAvailable() == (short)1){
                selectedStoreList.add(store);
            }
        }
        LOG.log(Level.INFO, "Get all store: {0}", storeManager.getStores());
        return selectedStoreList;
    }
    
    public void setStoreUnavailable(){
        try {
            storeManager.removeStore(selectedStore);
        } catch (StoreManagerException ex) {
            Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

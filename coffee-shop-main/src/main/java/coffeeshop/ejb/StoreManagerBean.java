package coffeeshop.ejb;

import coffeeshop.entity.OrderInfo;
import coffeeshop.entity.Staff;
import coffeeshop.entity.Store;
import coffeeshop.facade.StoreFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class StoreManagerBean implements StoreManager {

    @EJB
    private StoreFacade storeFacade;

    @EJB
    private UserManager userManager;
    
    @EJB
    private OrderManager orderManager;

    @Override
    public Store addStore(String country, String province, String city, String district, String detail) {
        Store newStore = new Store(null, country, province, city, district, detail, (short) 1);
        newStore.setOrderInfoList(new ArrayList<>());
        newStore.setStaffList(new ArrayList<>());
        storeFacade.create(newStore);
        return newStore;
    }

    @Override
    public List<Store> getStores() {
        return storeFacade.findAll();
    }

    @Override
    public Store getStoreById(int id) throws StoreManagerException {
        Store store = storeFacade.find(id);
        if (store == null) {
            throw new StoreManagerException("Store with id " + id + " not found");
        }
        return store;
    }

    @Override
    public void removeStore(Store selectedStore) throws StoreManagerException {
        if (storeCanBeRemoved(selectedStore)) {
            for (Staff staff : selectedStore.getStaffList()) {
                userManager.removeStaff(staff);
            }
            selectedStore.setIsAvailable((short) 0);
        } else {
            //TO DO
        }
    }
    
    private boolean storeCanBeRemoved(Store selectedStore) {
        if (orderManager.getStoreUnfinishedOrder(selectedStore).isEmpty()){
            return true;
        } else{
            return false;
        }
    }
}

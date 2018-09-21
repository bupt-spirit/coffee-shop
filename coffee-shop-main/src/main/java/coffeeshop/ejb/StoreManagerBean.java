package coffeeshop.ejb;

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
    public Store addStore(Store store) {
        store.setOrderInfoList(new ArrayList<>());
        store.setStaffList(new ArrayList<>());
        store.setIsAvailable((short) 1);
        storeFacade.create(store);
        return store;
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
        if (orderManager.getStoreUnfinishedOrder(selectedStore).isEmpty()) {
            List<Staff> staffs = selectedStore.getStaffList();
            selectedStore.setStaffList(null);
            selectedStore.setIsAvailable((short) 0);
            for (Staff staff : staffs) {
                userManager.removeStaff(staff);
            }
            storeFacade.edit(selectedStore);
        } else {
            throw new StoreManagerException("Unfinish order exist");
        }
    }

}

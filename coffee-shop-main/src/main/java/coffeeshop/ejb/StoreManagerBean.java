package coffeeshop.ejb;

import coffeeshop.entity.Store;
import coffeeshop.facade.StoreFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class StoreManagerBean implements StoreManager {

    @EJB
    private StoreFacade storeFacade;

    @Override
    public void addStore(String country, String province, String district, String city, String detail) {
        Store newStore = new Store(null, country, province, city, district, detail, (short) 1);
        newStore.setOrderInfoList(new ArrayList<>());
        newStore.setStaffList(new ArrayList<>());
        storeFacade.create(newStore);
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
}

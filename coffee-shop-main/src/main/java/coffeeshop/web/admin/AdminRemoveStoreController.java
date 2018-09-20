package coffeeshop.web.admin;

import coffeeshop.ejb.StoreManager;
import coffeeshop.ejb.StoreManagerException;
import coffeeshop.entity.Store;
import coffeeshop.web.util.MessageBundle;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AdminRemoveStoreController {

    @EJB
    private StoreManager storeManager;

    @Inject
    private MessageBundle bundle;

    private Store selectedStore;

    public Store getSelectedStore() {
        return selectedStore;
    }

    public void setSelectedStore(Store selectedStore) {
        this.selectedStore = selectedStore;
    }

    public void setStoreUnavailable() {
        try {
            storeManager.removeStore(selectedStore);
        } catch (StoreManagerException ex) {
            FacesContext.getCurrentInstance().addMessage("remove-store-form:remove-store",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            bundle.getString("Ui.Message.StoreHaveUnFinishedOrders"), null
                    ));
        }
    }
}

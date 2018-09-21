package coffeeshop.web.admin;

import coffeeshop.ejb.StoreManager;
import coffeeshop.entity.Store;
import coffeeshop.web.util.MessageBundle;
import javax.inject.Named;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named
@RequestScoped
public class AdminAddStoreController {

    private static final Logger LOG = Logger.getLogger(AdminAddUserController.class.getName());

    @EJB
    private StoreManager storeManager;

    @Inject
    private MessageBundle bundle;

    private Store store;

    @PostConstruct
    private void init() {
        store = new Store();
        store.setIsAvailable((short) 1);
    }

    public Store getStore() {
        return store;
    }

    public void addStore() {
        storeManager.addStore(store);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                bundle.getFormatted("Ui.Admin.Message.AddStoreSuccess")
        ));
        init();
    }
}

package coffeeshop.web.converter;

import coffeeshop.ejb.CustomerInfoManager;
import coffeeshop.ejb.CustomerInfoManagerException;
import coffeeshop.ejb.StoreManager;
import coffeeshop.ejb.StoreManagerException;
import coffeeshop.entity.Address;
import coffeeshop.entity.Store;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;

@Named
public class StoreConverter implements Converter {

    private static final Logger LOG = Logger.getLogger(StoreConverter.class.getName());

    @EJB
    private StoreManager storeManager;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            int id = Integer.parseInt(value);
            Store store = storeManager.getStoreById(id);
            return store;
        } catch (StoreManagerException ex) {
            LOG.log(Level.SEVERE, "Not a valid store: {0}", ex);
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Conversion Error", "Not a valid store."));
        } catch (NumberFormatException ex) {
            LOG.log(Level.SEVERE, "Not a valid id: {0}", ex);
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Conversion Error", "Not a valid id."));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Store store = (Store) value;
        if (store == null) {
            return null;
        }
        return String.valueOf(store.getId());
    }

}

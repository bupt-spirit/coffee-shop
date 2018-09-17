package coffeeshop.web.converter;

import coffeeshop.ejb.CustomerInfoManager;
import coffeeshop.ejb.CustomerInfoManagerException;
import coffeeshop.entity.Address;
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
public class AddressConverter implements Converter {

    private static final Logger LOG = Logger.getLogger(AddressConverter.class.getName());

    @EJB
    private CustomerInfoManager customerInfoManager;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            int id = Integer.parseInt(value);
            Address address = customerInfoManager.getAddressById(id);
            return address;
        } catch (CustomerInfoManagerException ex) {
            LOG.log(Level.SEVERE, "Not a valid address: {0}", ex);
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Conversion Error", "Not a valid address."));
        } catch (NumberFormatException ex) {
            LOG.log(Level.SEVERE, "Not a valid id: {0}", ex);
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Conversion Error", "Not a valid id."));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Address address = (Address) value;
        if (address == null) {
            return null;
        }
        return String.valueOf(address.getId());
    }

}

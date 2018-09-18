package coffeeshop.web.converter;

import coffeeshop.ejb.OrderManager;
import coffeeshop.ejb.OrderManagerException;
import coffeeshop.entity.OrderInfo;
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
public class OrderConverter implements Converter {

    private static final Logger LOG = Logger.getLogger(OrderConverter.class.getName());
    @EJB
    OrderManager orderManager;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        try {
            int id = Integer.parseInt(value);
            OrderInfo orderInfo = orderManager.getOrderById(id);
            return orderInfo;
        } catch (OrderManagerException ex) {
            Logger.getLogger(OrderConverter.class.getName()).log(Level.SEVERE, null, ex);
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Conversion Error", "Not a valid order."));
        } catch (NumberFormatException ex) {
            LOG.log(Level.SEVERE, "Not a valid id: {0}", ex);
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Conversion Error", "Not a valid id."));
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        OrderInfo orderInfo = (OrderInfo) value;
        if (orderInfo == null) {
            return null;
        }
        return String.valueOf(orderInfo.getId());
    }

}

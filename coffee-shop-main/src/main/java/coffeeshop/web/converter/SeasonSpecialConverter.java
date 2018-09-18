package coffeeshop.web.converter;

import coffeeshop.ejb.ProductManager;
import coffeeshop.ejb.ProductManagerException;
import coffeeshop.entity.Product;
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
public class SeasonSpecialConverter implements Converter{
    
    private static final Logger LOG = Logger.getLogger(SeasonSpecialConverter.class.getName());
    
    @EJB
    private ProductManager productManager;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        try {
        int id = Integer.parseInt(value);
        Product product = productManager.getProductById(id);
        return product;
        } catch (ProductManagerException ex) {
            LOG.log(Level.SEVERE, "Not a valid product: {0}", ex);
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Conversion Error", "Not a valid product."));
        } catch (NumberFormatException ex) {
            LOG.log(Level.SEVERE, "Not a valid id: {0}", ex);
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Conversion Error", "Not a valid id."));
        }
        
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Product seasonSpecial = (Product) value;
        if (seasonSpecial == null)
        {
            return null;
        }
        return String.valueOf(seasonSpecial.getId());
        
    }
    
}

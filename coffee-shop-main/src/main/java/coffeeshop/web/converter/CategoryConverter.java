package coffeeshop.web.converter;

import coffeeshop.ejb.ProductManager;
import coffeeshop.ejb.ProductManagerException;
import coffeeshop.entity.Category;
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
public class CategoryConverter implements Converter {

    private static final Logger LOG = Logger.getLogger(CategoryConverter.class.getName());

    @EJB
    private ProductManager productManager;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            int id = Integer.parseInt(value);
            Category category = productManager.getCategoryById(id);
            return category;
        } catch (ProductManagerException ex) {
            LOG.log(Level.SEVERE, "Not a valid category: {0}", ex);
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Conversion Error", "Not a valid category."));
        } catch (NumberFormatException ex) {
            LOG.log(Level.SEVERE, "Not a valid id: {0}", ex);
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Conversion Error", "Not a valid id."));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Category category = (Category) value;
        if (category == null) {
            return null;
        }
        return String.valueOf(category.getId());
    }

}

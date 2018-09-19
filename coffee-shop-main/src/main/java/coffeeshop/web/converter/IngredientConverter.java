package coffeeshop.web.converter;

import coffeeshop.ejb.ProductManager;
import coffeeshop.ejb.ProductManagerException;
import coffeeshop.entity.Ingredient;
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
public class IngredientConverter implements Converter {

    private static final Logger LOG = Logger.getLogger(IngredientConverter.class.getName());

    @EJB
    private ProductManager productManager;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            int id = Integer.parseInt(value);
            Ingredient ingredient = productManager.getIngredientById(id);
            return ingredient;
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
        Ingredient ingredient = (Ingredient) value;
        if (ingredient == null) {
            return null;
        }
        return String.valueOf(ingredient.getId());

    }

}

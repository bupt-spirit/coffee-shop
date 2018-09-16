package coffeeshop.web;

import coffeeshop.ejb.CartManager;
import coffeeshop.ejb.CartManagerException;
import coffeeshop.ejb.ProductManager;
import coffeeshop.ejb.ProductManagerException;
import coffeeshop.entity.Category;
import coffeeshop.entity.Ingredient;
import coffeeshop.entity.Product;
import coffeeshop.facade.IngredientFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class ProductController implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());

    @EJB
    private ProductManager productManager;
    
    @Inject
    private CartController cartController;

    // For find ingredinet by view returned id
    @EJB
    private IngredientFacade ingredientFacade;

    private String selectedCatagory;
    private Product selectedProduct;
    private Set<Ingredient> selectedIngredients;
    private short itemQuality;

    @PostConstruct
    private void init() {
        selectedIngredients = new HashSet<>();
    }

    public String getSelectedCatagory() {
        return selectedCatagory;
    }

    public void setSelectedCatagory(String selectedCatagory) {
        this.selectedCatagory = selectedCatagory;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public List<Category> getCategories() {
        return productManager.getCategories();
    }

    public List<Product> getCategoryProducts(String categoryName) throws ProductManagerException {
        return productManager.getCategoryProducts(categoryName);
    }

    public List<Product> getSelectedCategoryProducts() throws ProductManagerException {
        return getCategoryProducts(selectedCatagory);
    }

    public short getItemQuality() {
        return itemQuality;
    }

    public void setItemQuality(short itemQuality) {
        this.itemQuality = itemQuality;
    }

    public void increaseItemQuality() {
        this.itemQuality += 1;
    }

    public void decreaseItemQuality() {
        this.itemQuality -= 1;
    }

    public void ingredientChanged(ValueChangeEvent event) {
        String oldValue = (String) event.getOldValue();
        if (oldValue != null) {
            int id = Integer.parseInt(oldValue);
            Ingredient ingredient = ingredientFacade.find(id);
            selectedIngredients.remove(ingredient);
        }
        String newValue = (String) event.getNewValue();
        if (newValue != null) {
            int id = Integer.parseInt(newValue);
            Ingredient ingredient = ingredientFacade.find(id);
            selectedIngredients.add(ingredient);
        }
    }
    
    public void addToCart() throws CartManagerException {
        List ingredientsList = new ArrayList<>();
        ingredientsList.addAll(selectedIngredients);
        cartController.getCartManager().add(selectedProduct, ingredientsList, itemQuality);
        LOG.log(Level.INFO, "Add suborder to cart: {0} {1} {2}",
                new Object[]{ selectedProduct, ingredientsList, itemQuality });
    }
}

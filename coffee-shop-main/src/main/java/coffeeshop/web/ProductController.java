package coffeeshop.web;

import coffeeshop.ejb.CartManagerException;
import coffeeshop.ejb.ProductManager;
import coffeeshop.ejb.ProductManagerException;
import coffeeshop.entity.Category;
import coffeeshop.entity.Ingredient;
import coffeeshop.entity.Product;
import coffeeshop.facade.IngredientFacade;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class ProductController implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());

    @EJB
    private ProductManager productManager;

    @Inject
    private CartController cartController;

    // For find ingredient by view returned id
    @EJB
    private IngredientFacade ingredientFacade;

    private String selectedCategory;
    private Product selectedProduct;
    private Set<Ingredient> selectedIngredients;
    private short itemQuality;

    @PostConstruct
    private void init() {
        selectedIngredients = new HashSet<>();
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
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

    private List<Product> getCategoryProducts(String categoryName) throws ProductManagerException {
        return productManager.getCategoryProducts(categoryName);
    }

    public List<Product> getSelectedCategoryProducts() throws ProductManagerException {
        return getCategoryProducts(selectedCategory);
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
        List<Ingredient> ingredientsList = new ArrayList<>(selectedIngredients);
        cartController.getCartManager().add(selectedProduct, ingredientsList, itemQuality);
        LOG.log(Level.INFO, "Add suborder to cart: {0} {1} {2}",
                new Object[]{selectedProduct, ingredientsList, itemQuality});
    }
}

package coffeeshop.web;

import coffeeshop.ejb.CartManagerException;
import coffeeshop.ejb.ProductManager;
import coffeeshop.ejb.ProductManagerException;
import coffeeshop.entity.Category;
import coffeeshop.entity.Ingredient;
import coffeeshop.entity.Product;
import coffeeshop.facade.IngredientFacade;
import coffeeshop.web.util.MessageBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named
@SessionScoped
public class ProductController implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());

    @EJB
    private ProductManager productManager;

    @Inject
    private CartController cartController;

    @Inject
    private MessageBundle bundle;

    // For find ingredient by view returned id
    @EJB
    private IngredientFacade ingredientFacade;

    private String selectedCategory;
    private Product selectedProduct;
    private Set<Ingredient> selectedIngredients;
    private short itemQuantity;
    private boolean sorted = false;

    @PostConstruct
    private void init() {
        selectedIngredients = new HashSet<>();
    }

    public String getSelectedCategory() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        if (viewId.equals("/index.xhtml")) {
            this.selectedCategory = null;
        }
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
        // Important, clear product selections
        this.selectedIngredients.clear();
        this.itemQuantity = 1;
    }

    public List<Category> getCategories() {
        return productManager.getCategories();
    }

    private List<Product> getCategoryProducts(String categoryName) throws ProductManagerException {
        return productManager.getCategoryProducts(categoryName);
    }

    public List<Product> getSelectedCategoryProducts() throws ProductManagerException {
        List<Product> originalList = getCategoryProducts(selectedCategory);
        if(sorted)
            Collections.sort(originalList, new costComparator());
        return originalList;
    }
    
    static class costComparator implements Comparator{
        @Override
        public int compare(Object object1,Object object2){
            Product p1 = (Product)object1;
            Product p2 = (Product)object2;
            return p1.getCost().compareTo(p2.getCost());
        }
    }
    
    public void changeSortedStatus(){
        sorted = !sorted;
    }
    
    public String getButtonName(){
        if(sorted)
            return bundle.getString("Ui.Button.OriginalOrder");
        else
            return bundle.getString("Ui.Button.SortOrder");
    }
    

    public short getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(short itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public void increaseItemQuantity() {
        this.itemQuantity += 1;
    }

    public void decreaseItemQuantity() {
        this.itemQuantity -= 1;
    }

    public Set<Ingredient> getSelectedIngredients() {
        return selectedIngredients;
    }

    public void setSelectedIngredients(Set<Ingredient> selectedIngredients) {
        this.selectedIngredients = selectedIngredients;
    }

    public void ingredientChanged(ValueChangeEvent event) {
        Ingredient oldValue = (Ingredient) event.getOldValue();
        if (oldValue != null) {
            selectedIngredients.remove(oldValue);
        }
        Ingredient newValue = (Ingredient) event.getNewValue();
        if (newValue != null) {
            selectedIngredients.add(newValue);
        }
    }

    public BigDecimal getSuborderAmount() {
        BigDecimal cost = selectedProduct.getCost();
        for (Ingredient ingredient : selectedIngredients) {
            cost = cost.add(ingredient.getCost());
        }
        LOG.log(Level.INFO, "Current cost: {0}", cost);
        return cost;
    }

    public BigDecimal getIngredientsAmount() {
        BigDecimal cost = BigDecimal.ZERO;
        for (Ingredient ingredient : selectedIngredients) {
            cost = cost.add(ingredient.getCost());
        }
        LOG.log(Level.INFO, "Current cost: {0}", cost);
        return cost;
    }

    public void addToCart() throws CartManagerException {
        cartController.getCartManager().add(selectedProduct, new ArrayList(selectedIngredients), itemQuantity);
        LOG.log(Level.INFO, "Add suborder to cart: {0} {1} {2}",
                new Object[]{selectedProduct, selectedIngredients, itemQuantity});
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                bundle.getString("Ui.Message.AddedToCart"),
                null
        ));
    }

}

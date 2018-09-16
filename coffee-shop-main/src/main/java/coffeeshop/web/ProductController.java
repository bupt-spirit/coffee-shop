package coffeeshop.web;

import coffeeshop.ejb.ProductManager;
import coffeeshop.ejb.ProductManagerException;
import coffeeshop.entity.Category;
import coffeeshop.entity.Ingredient;
import coffeeshop.entity.IngredientCategory;
import coffeeshop.entity.Product;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class ProductController implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    ProductManager productManager;

    private String selectedCatagory;
    private Product selectedProduct;

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
}

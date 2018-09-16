package coffeeshop.web;

import coffeeshop.ejb.ProductManager;
import coffeeshop.ejb.ProductManagerException;
import coffeeshop.entity.Category;
import coffeeshop.entity.Ingredient;
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
    ProductManager productManagerBean;

    private List<Category> categories;
    private List<Product> products;
    private List<Ingredient> ingredients;

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
        this.categories = productManagerBean.getCategories();
        return categories;
    }

    public List<Product> getCategoryProducts(String categoryName) throws ProductManagerException {
        this.products = productManagerBean.getCategoryProducts(categoryName);
        return products;
    }

    public List<Product> getSelectedCategoryProducts() throws ProductManagerException {
        return getCategoryProducts(selectedCatagory);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop.web;

import coffeeshop.ejb.ProductManager;
import coffeeshop.ejb.ProductManagerException;
import coffeeshop.entity.Category;
import coffeeshop.entity.Ingredient;
import coffeeshop.entity.Product;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

@Named
@RequestScoped
public class ProductController {

    @EJB
    ProductManager productManagerBean;

    private List<Category> categories;
    private List<Product> products;
    private List<Ingredient> ingredients;

    private String selectedCatagory;

    public String getSelectedCatagory() {
        return selectedCatagory;
    }

    public void setSelectedCatagory(String selectedCatagory) {
        this.selectedCatagory = selectedCatagory;
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

package coffeeshop.ejb;

import coffeeshop.entity.Category;
import coffeeshop.entity.Image;
import coffeeshop.entity.Ingredient;
import coffeeshop.entity.IngredientCategory;
import coffeeshop.entity.Nutrition;
import coffeeshop.entity.Product;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;

public interface ProductManager {

    List<Category> getCategories();

    Category getCategoryById(int id) throws ProductManagerException;
    
    Ingredient getIngredientById(int id) throws ProductManagerException;

    List<Product> getCategoryProducts(String categoryName) throws ProductManagerException;

    List<Ingredient> getIngredientsByCategory(String ingredientCategoryName) throws ProductManagerException;

    List<IngredientCategory> getIngredientCategoryByProduct(String productName) throws ProductManagerException;

    List<Product> getAllProduct();

    List<IngredientCategory> getIngredientCategories();

    Product getProductById(int id) throws ProductManagerException;

    Product createProduct(Product newProduct,boolean addNutrition,Nutrition newNutrition,Image newImage) throws IOException, URISyntaxException;

    void removeProduct(Product selectedProduct) throws ProductManagerException,SeasonSpecialManagerException;
    
    Product editProduct(Product selectedProduct,String name, String description, BigDecimal price, Category category,
            boolean addNutrition, int calories, int fat, int carbon, int fiber, int protein, int sodium,
            byte[] bytes, String contentType, List<IngredientCategory> ingredientCategoies) throws ProductManagerException;
}

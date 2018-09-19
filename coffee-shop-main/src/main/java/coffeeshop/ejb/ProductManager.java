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

    List<Product> getCategoryProducts(String categoryName) throws ProductManagerException;

    List<Ingredient> getIngredientsByCategory(String ingredientCategoryName) throws ProductManagerException;

    List<IngredientCategory> getIngredientCategoryByProduct(String productName) throws ProductManagerException;

    List<Product> getAllProduct();

    List<IngredientCategory> getIngredientCategories();

    Product getProductById(int id) throws ProductManagerException;

    Product createProduct(String name, String description, BigDecimal price, Category category,
            byte[] bytes, String imageName, List<IngredientCategory> ingredientCategoies) throws IOException, URISyntaxException;

    Product createProduct(String name, String description, BigDecimal price, Category category,
            int calories, int fat, int carbon, int fiber, int protein, int sodium,
            byte[] bytes, String imageName, List<IngredientCategory> ingredientCategoies) throws IOException, URISyntaxException;

}

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

    List<Product> getCategoryProducts(String categoryName) throws ProductManagerException;

    List<Ingredient> getIngredientsByCategory(String ingredientCategoryName) throws ProductManagerException;

    List<IngredientCategory> getIngredientCategoryByProduct(String productName) throws ProductManagerException;

    List<Product> getAllProduct();

    Product getProductById(int id) throws ProductManagerException;

    Product createProduct(String name, String description, BigDecimal price, Category category, Nutrition nutrition,
            byte[] bytes, String imageName) throws IOException, URISyntaxException;

    Nutrition createNutrition(int calories, int fat, int carton, int fiber, int protein, int sodium);

    Image createImage(String imageSuffix, byte[] bytes) throws IOException, URISyntaxException;
}

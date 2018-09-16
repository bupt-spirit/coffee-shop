package coffeeshop.ejb;

import coffeeshop.entity.Category;
import coffeeshop.entity.Ingredient;
import coffeeshop.entity.IngredientCategory;
import coffeeshop.entity.Product;
import java.util.List;

public interface ProductManager {

    List<Category> getCategories();

    List<Product> getCategoryProducts(String categoryName) throws ProductManagerException;

    List<Ingredient> getIngredientsByCategory(String ingredientCategoryName) throws ProductManagerException;

    List<IngredientCategory> getIngredientCategoryByProduct(String productName) throws ProductManagerException;
}

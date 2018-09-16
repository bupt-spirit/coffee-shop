package coffeeshop.ejb;

import coffeeshop.entity.Category;
import coffeeshop.entity.Ingredient;
import coffeeshop.entity.IngredientCategory;
import coffeeshop.entity.Product;
import java.util.List;

public interface ProductManager {

    public List<Category> getCategories();

    public List<Product> getCategoryProducts(String categoryName) throws ProductManagerException;
    
    public List<Ingredient> getIngredientsByCategory(String ingredientCategoryName);
    
    public List<IngredientCategory> getIngredientCategoryByProduct(String productName);
}

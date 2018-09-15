package coffeeshop.ejb;

import coffeeshop.entity.Category;
import coffeeshop.entity.Ingredient;
import coffeeshop.entity.IngredientCategory;
import coffeeshop.entity.Product;
import coffeeshop.facade.CategoryFacade;
import coffeeshop.facade.ProductFacade;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ProductManagerBean implements ProductManager {

    private static final Logger LOG = Logger.getLogger(ProductManagerBean.class.getName());

    @EJB
    private CategoryFacade categoryFacade;

    @EJB
    private ProductFacade productFacade;

    @Override
    public List<Category> getCategories() {
        return categoryFacade.findAll();
    }

    @Override
    public List<Product> getCategoryProducts(String categoryName) throws ProductManagerException {
        Category category = categoryFacade.findByName(categoryName);
        if (category == null) {
            throw new ProductManagerException("no such category");
        }
        return category.getProductList();
    }

    @Override
    public List<Ingredient> getIngredientsByCategory(String ingredientCategoryName) {
       
    }


    @Override
    public List<IngredientCategory> getIngredientCategoryByProduct(String productName) {
        return productFacade.findByName(productName).getIngredientCategoryList();
    }
}

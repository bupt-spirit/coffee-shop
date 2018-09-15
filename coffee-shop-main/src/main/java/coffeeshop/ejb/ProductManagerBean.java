package coffeeshop.ejb;

import coffeeshop.entity.Category;
import coffeeshop.entity.Ingredient;
import coffeeshop.entity.IngredientCategory;
import coffeeshop.entity.Product;
import coffeeshop.facade.CategoryFacade;
import coffeeshop.facade.IngredientCategoryFacade;
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
    
    @EJB
    private IngredientCategoryFacade ingredientCategoryFacade;

    @Override
    public List<Category> getCategories() {
        return categoryFacade.findAll();
    }

    @Override
    public List<Product> getCategoryProducts(String categoryName) throws ProductManagerException {
        Category category = categoryFacade.findByName(categoryName);
        if (category == null) {
            throw new ProductManagerException("no such product category [" + categoryName + "]");
        }
        return category.getProductList();
    }

    @Override
    public List<Ingredient> getIngredientsByCategory(String categoryName) throws ProductManagerException {
       IngredientCategory category = ingredientCategoryFacade.findByName(categoryName);
       if (category == null) {
           throw new ProductManagerException("no such ingredient category [" + categoryName + "]");
       }
       return category.getIngredientList();
    }


    @Override
    public List<IngredientCategory> getIngredientCategoryByProduct(String productName) throws ProductManagerException {
        Product product = productFacade.findByName(productName);
        if (product == null) {
            throw new ProductManagerException("no such product [" + productName + "]");
        }
        return product.getIngredientCategoryList();
    }
}

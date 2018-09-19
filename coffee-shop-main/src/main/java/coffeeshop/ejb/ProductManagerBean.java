package coffeeshop.ejb;

import coffeeshop.entity.Category;
import coffeeshop.entity.Image;
import coffeeshop.entity.Ingredient;
import coffeeshop.entity.IngredientCategory;
import coffeeshop.entity.Nutrition;
import coffeeshop.entity.Product;
import coffeeshop.facade.CategoryFacade;
import coffeeshop.facade.ImageFacade;
import coffeeshop.facade.IngredientCategoryFacade;
import coffeeshop.facade.NutritionFacade;
import coffeeshop.facade.ProductFacade;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class ProductManagerBean implements ProductManager {

    private static final Logger LOG = Logger.getLogger(ProductManagerBean.class.getName());

    @EJB
    private CategoryFacade categoryFacade;

    @EJB
    private ProductFacade productFacade;

    @EJB
    private IngredientCategoryFacade ingredientCategoryFacade;

    @EJB
    private NutritionFacade nutritionFacade;

    @EJB
    private ImageFacade imageFacade;

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
    public Category getCategoryById(int id) throws ProductManagerException {
        Category category = categoryFacade.find(id);
        if (category == null) {
            throw new ProductManagerException("no such category [" + id + "]");
        }
        return category;
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

    @Override
    public List<Product> getAllProduct() {
        return productFacade.findAll();
    }

    @Override
    public Product getProductById(int id) throws ProductManagerException {
        Product product = productFacade.find(id);
        if (product == null) {
            throw new ProductManagerException("no such product id=" + id);
        }
        return product;
    }

    @Override
    public List<IngredientCategory> getIngredientCategories() {
        return ingredientCategoryFacade.findAll();
    }

    @Override
    public Product createProduct(String name, String description, BigDecimal price, Category category,
            int calories, int fat, int carbon, int fiber, int protein, int sodium,
            byte[] bytes, String imageName, List<IngredientCategory> ingredientCategoies) throws IOException, URISyntaxException {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setCost(price);
        product.setCategoryId(category);
        LOG.log(Level.INFO, "set category {0}", category);

        product.setNutritionId(createNutrition(calories, fat, carbon, fiber, protein, sodium));
        product.setLastUpdate(new Date());
        Image image = createImage(imageName, bytes);
        product.setImageUuid(image);
        image.setProduct(product);
        product.setIngredientCategoryList(new ArrayList<>());
        category.getProductList().add(product);
        productFacade.create(product);
        imageFacade.edit(image);
        categoryFacade.edit(category);
        LOG.log(Level.INFO, "start enable productingredientcategory");
        enableProductIngredient(product,ingredientCategoies);
        LOG.log(Level.INFO,"finish enable");
        return product;
    }

    @Override
    public Product createProduct(String name, String description, BigDecimal price, Category category,
            byte[] bytes, String imageName, List<IngredientCategory> ingredientCategoies) throws IOException, URISyntaxException {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setCost(price);
        product.setCategoryId(category);
        product.setNutritionId(null);
        product.setLastUpdate(new Date());
        Image image = createImage(imageName, bytes);
        product.setImageUuid(image);
        image.setProduct(product);
        product.setIngredientCategoryList(new ArrayList<>());
        category.getProductList().add(product);
        productFacade.create(product);
        imageFacade.edit(image);
        categoryFacade.edit(category);
        LOG.log(Level.INFO, "start enable productingredientcategory");
        enableProductIngredient(product,ingredientCategoies);
        LOG.log(Level.INFO,"finish enable");
        return product;
    }

    private Nutrition createNutrition(int calories, int fat, int carbon, int fiber, int protein, int sodium) {
        Nutrition nutrition = new Nutrition();
        nutrition.setCalories(calories);
        nutrition.setFat(fat);
        nutrition.setCarbon(carbon);
        nutrition.setProtein(protein);
        nutrition.setFiber(fiber);
        nutrition.setSodium(sodium);
        nutritionFacade.create(nutrition);
        return nutrition;
    }

    private Image createImage(String imageSuffix, byte[] bytes) throws IOException, URISyntaxException {
        Image image = new Image();
        image.setContent(bytes);
        image.setMediaType("image/" + imageSuffix);
        String uuid = null;
        do {
            uuid = UUID.randomUUID().toString();
        } while (imageFacade.find(uuid) != null);
        image.setUuid(uuid);
        imageFacade.create(image);
        return image;
    }

    private void enableProductIngredient(Product product, List<IngredientCategory> ingredientCategoies) {
        product.getIngredientCategoryList().addAll(ingredientCategoies);
        for (IngredientCategory category : ingredientCategoies) {
            category.getProductList().add(product);
        }
        for (IngredientCategory category : ingredientCategoies) {
            ingredientCategoryFacade.edit(category);
        }
        productFacade.edit(product);
    }
}

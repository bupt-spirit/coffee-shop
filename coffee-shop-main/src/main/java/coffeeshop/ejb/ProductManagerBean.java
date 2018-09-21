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
import coffeeshop.facade.IngredientFacade;
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

    @EJB
    private IngredientFacade ingredientFacade;

    @EJB
    SeasonSpecialManager seasonSpecialManager;

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
        return productFacade.findIsAvailable();
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
    public Product createProduct(Product newProduct, boolean addNutrition, Nutrition newNutrition, Image newImage) throws IOException, URISyntaxException {
        this.createProduct(newProduct, newImage);
        if (addNutrition) {
            nutritionFacade.create(newNutrition);
            newProduct.setNutritionId(newNutrition);
            productFacade.edit(newProduct);
        }
        return newProduct;
    }

    private Product createProduct(Product newProduct, Image newImage) throws IOException, URISyntaxException {
        newProduct.setNutritionId(null);
        newProduct.setLastUpdate(new Date());
        newProduct.setIsAvailable((short) 1);

        String uuid = null;
        do {
            uuid = UUID.randomUUID().toString();
        } while (imageFacade.find(uuid) != null);
        newImage.setUuid(uuid);

        imageFacade.create(newImage);

        newProduct.setImageUuid(newImage);
        newImage.setProduct(newProduct);

        newProduct.getCategoryId().getProductList().add(newProduct);
        productFacade.create(newProduct);
        imageFacade.edit(newImage);
        categoryFacade.edit(newProduct.getCategoryId());
        LOG.log(Level.INFO, "start enable productingredientcategory");
        enableProductIngredient(newProduct, newProduct.getIngredientCategoryList());
        LOG.log(Level.INFO, "finish enable");
        return newProduct;
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

    private Image createImage(String contentType, byte[] bytes) throws IOException, URISyntaxException {
        Image image = new Image();
        image.setContent(bytes);
        image.setMediaType(contentType);
        String uuid = null;
        do {
            uuid = UUID.randomUUID().toString();
        } while (imageFacade.find(uuid) != null);
        image.setUuid(uuid);
        imageFacade.create(image);
        return image;
    }

    private void enableProductIngredient(Product product, List<IngredientCategory> ingredientCategoies) {
        for (IngredientCategory category : ingredientCategoies) {
            category.getProductList().add(product);
        }
        for (IngredientCategory category : ingredientCategoies) {
            ingredientCategoryFacade.edit(category);
        }
        productFacade.edit(product);
    }

    @Override
    public void removeProduct(Product selectedProduct) throws ProductManagerException, SeasonSpecialManagerException {
        selectedProduct.setIsAvailable((short) 0);
        Category category = selectedProduct.getCategoryId();
        List<Product> products = category.getProductList();
        for (int i = 0; i < products.size(); ++i) {
            if (products.get(i) == selectedProduct || products.get(i).getId().equals(selectedProduct.getId())) {
                LOG.log(Level.INFO, "Remove product from category product list");
                products.remove(i);
                categoryFacade.edit(category);
                break;
            }
        }
        selectedProduct.setCategoryId(null);
        if (selectedProduct.getSeasonSpecial() != null) {
            seasonSpecialManager.removeSeasonSpecial(selectedProduct);
        }
        productFacade.edit(selectedProduct);
    }

    @Override
    public Product editProduct(Product selectedProduct, String name, String description, BigDecimal price, Category category,
            boolean addNutrition, int calories, int fat, int carbon, int fiber, int protein, int sodium,
            byte[] bytes, String contentType, List<IngredientCategory> ingredientCategoies) throws ProductManagerException {
        if (selectedProduct.getCategoryId() != category) {
            List<Product> products = selectedProduct.getCategoryId().getProductList();
            for (int i = 0; i < products.size(); ++i) {
                if (products.get(i) == selectedProduct || products.get(i).getId().equals(selectedProduct.getId())) {
                    LOG.log(Level.INFO, "Remove product from category product list");
                    products.remove(i);
                    category.getProductList().add(selectedProduct);
                    categoryFacade.edit(selectedProduct.getCategoryId());
                    categoryFacade.edit(category);
                    break;
                }
            }
            selectedProduct.setCategoryId(category);
        }
        selectedProduct.setName(name);
        selectedProduct.setDescription(description);
        selectedProduct.setCost(price);
        if (addNutrition == true && selectedProduct.getNutritionId() != null) {
            Nutrition nutrition = selectedProduct.getNutritionId();
            nutrition.setCalories(calories);
            nutrition.setFat(fat);
            nutrition.setCarbon(carbon);
            nutrition.setFiber(fiber);
            nutrition.setProtein(protein);
            nutrition.setSodium(sodium);
            nutritionFacade.edit(nutrition);
        } else if (addNutrition == false && selectedProduct.getNutritionId() == null) {
            selectedProduct.setNutritionId(createNutrition(calories, fat, carbon, fiber, protein, sodium));
        }
        selectedProduct.getImageUuid().setMediaType(contentType);
        selectedProduct.getImageUuid().setContent(bytes);

        for (IngredientCategory ingredientCategory : selectedProduct.getIngredientCategoryList()) {
            for (int i = 0; i < ingredientCategory.getProductList().size(); i++) {
                if (ingredientCategory.getProductList().get(i) == selectedProduct
                        || ingredientCategory.getProductList().get(i).getId().equals(selectedProduct.getId())) {
                    ingredientCategory.getProductList().remove(i);
                    ingredientCategoryFacade.edit(ingredientCategory);
                    break;
                }
            }
        }

        enableProductIngredient(selectedProduct, ingredientCategoies);

        imageFacade.edit(selectedProduct.getImageUuid());
        productFacade.edit(selectedProduct);
        
                
        return selectedProduct;
    }

    @Override
    public Ingredient getIngredientById(int id) throws ProductManagerException {
        Ingredient ingredient = ingredientFacade.find(id);
        if (ingredient == null) {
            throw new ProductManagerException("No ingredient having id " + id);
        }
        return ingredient;
    }
}

package coffeeshop.web.admin;

import coffeeshop.ejb.UserManager;
import coffeeshop.entity.Category;
import coffeeshop.entity.Ingredient;
import coffeeshop.entity.IngredientCategory;
import coffeeshop.entity.Nutrition;
import coffeeshop.entity.Product;
import coffeeshop.facade.CategoryFacade;
import coffeeshop.facade.IngredientCategoryFacade;
import coffeeshop.facade.IngredientFacade;
import coffeeshop.facade.NutritionFacade;
import coffeeshop.facade.ProductFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class InitController {

    private static final Logger LOG = Logger.getLogger(InitController.class.getName());

    private static final String DEFAULT_ADMIN_USERNAME = "admin";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin";

    @EJB
    private UserManager userManager;

    public void checkAndAddDefaultAdminUser() {
        if (!userManager.isUserExisting(DEFAULT_ADMIN_USERNAME)) {
            LOG.log(Level.INFO, "Default admin user does not exists");
            userManager.addAdmin(DEFAULT_ADMIN_USERNAME, DEFAULT_ADMIN_PASSWORD);
            LOG.log(Level.INFO, "New admin user added, username: {0}, password: {1}", new String[]{
                DEFAULT_ADMIN_USERNAME,
                DEFAULT_ADMIN_PASSWORD
            });
        }
    }

    @EJB
    private CategoryFacade categoryFacade;

    @EJB
    private ProductFacade productFacade;

    @EJB
    private NutritionFacade nutritionFacade;

    @EJB
    private IngredientCategoryFacade ingredientCategoryFacade;

    @EJB
    private IngredientFacade ingredientFacade;
    
    public void insertDemoData() {
        Category categoryDrinks = createCategory("Drinks");
        Category categoryFood = createCategory("Food");
        Category categoryCoffeeBean = createCategory("Coffee Bean");
        Category categoryCup = createCategory("Cup");

        IngredientCategory ingredientCategorySize = createIngredientCategory("Size");
        IngredientCategory ingredientCategorySweetness = createIngredientCategory("Sweetness");
        IngredientCategory ingredientCategoryIce = createIngredientCategory("Ice");
        IngredientCategory ingredientCategoryMilk = createIngredientCategory("Milk");
        IngredientCategory ingredientCategoryCream = createIngredientCategory("Cream");
        IngredientCategory ingredientCategoryEspresso = createIngredientCategory("Espresso");
        IngredientCategory ingredientCategorySyrup = createIngredientCategory("Syrup");
        IngredientCategory ingredientCategoryCocoaNibs = createIngredientCategory("Cocoa Nibs");

        Ingredient ingredientSizeMedium = createIngredient(
                "MEDIUM", "355ml", new BigDecimal(0), ingredientCategorySize);
        Ingredient ingredientSizeGrande = createIngredient(
                "GRANDE", "473ml", new BigDecimal(3), ingredientCategorySize);
        Ingredient ingredientSizeVenti = createIngredient(
                "VENTI", "592ml", new BigDecimal(3), ingredientCategorySize);

        Ingredient ingredientSweetnessSugarFree = createIngredient(
                "Sugar-free", "0 spoon", BigDecimal.ZERO, ingredientCategorySweetness);
        Ingredient ingredientSweetnessSemiSweet = createIngredient(
                "Semi-sweet", "1 spoon", BigDecimal.ZERO, ingredientCategorySweetness);
        Ingredient ingredientSweetnessFullSweet = createIngredient(
                "Full-sweet", "2 spoon", BigDecimal.ZERO, ingredientCategorySweetness);

        Ingredient ingredientIceNoIce = createIngredient(
                "No ice", "cold", BigDecimal.ZERO, ingredientCategoryIce);
        Ingredient ingredientIceLessIce = createIngredient(
                "Less ice", "cold", BigDecimal.ZERO, ingredientCategoryIce);
        Ingredient ingredientIceOnTheRocks = createIngredient(
                "On the rocks", "very cold", BigDecimal.ZERO, ingredientCategoryIce);

        Ingredient ingredientMilkFull = createIngredient(
                "Full milk", null, new BigDecimal(2), ingredientCategoryMilk);
        Ingredient ingredientMilkSkimmed = createIngredient(
                "Skimmed milk", null, new BigDecimal(2), ingredientCategoryMilk);

        Ingredient ingredientCreamAdd = createIngredient(
                "Add cream", null, new BigDecimal(2), ingredientCategoryCream);

        Ingredient ingredientEspressoUse = createIngredient(
                "Espresso coffee", null, new BigDecimal(4), ingredientCategoryEspresso);

        Ingredient ingredientSyrupCaramel = createIngredient(
                "Caramel syrup", null, new BigDecimal(3), ingredientCategorySyrup);
        Ingredient ingredientSyrupVanilla = createIngredient(
                "Vanilla syrup", null, new BigDecimal(3), ingredientCategorySyrup);

        Ingredient ingredientCocoaNibsAdd = createIngredient(
                "Add cocoa nibs", null, new BigDecimal(4), ingredientCategoryCocoaNibs);

        // 1
        Product productColdFoamCascaraColdBrew = createProduct("Cold Foam Cascara Cold Brew",
                "Sweetened cold foam is flavored with our Cascara syrup (for subtle notes of dark brown sugar and luscious maple) atop our bold, smooth Starbucks Cold Brew, and finished with just a hint of vanilla syrup.",
                new BigDecimal(36), categoryDrinks, createNutrition(60, 0, 13, 0, 1, 25));
        enableProductIngredient(productColdFoamCascaraColdBrew,
                ingredientCategorySize, ingredientCategorySweetness, ingredientCategoryIce, ingredientCategoryMilk,
                ingredientCategoryEspresso, ingredientCategoryCocoaNibs);

        // 2
        Product productColdFoamCascaraNitro = createProduct("Cold Foam Cascara Nitro",
                "Our velvety-smooth Nitro Cold Brew served cold, straight from the tap, is capped with Cascara flavored Cold Foam for subtle notes of dark brown sugar. Our most craveable cup of Nitro Cold Brew yet.",
                new BigDecimal(37), categoryDrinks, createNutrition(60, 0, 12, 0, 1, 20));
        enableProductIngredient(productColdFoamCascaraNitro,
                ingredientCategorySize, ingredientCategorySweetness, ingredientCategoryIce, ingredientCategoryMilk,
                ingredientCategoryCream, ingredientCategoryEspresso, ingredientCategoryCocoaNibs);

        // 3
        Product productIcedCoffee = createProduct("Iced Coffee",
                "Freshly brewed Starbucks Iced Coffee Blend served chilled and lightly sweetened over ice",
                new BigDecimal(30), categoryDrinks, createNutrition(0, 0, 0, 0, 0, 0));
        enableProductIngredient(productIcedCoffee,
                ingredientCategorySize, ingredientCategorySweetness, ingredientCategoryIce, ingredientCategoryCream,
                ingredientCategoryEspresso, ingredientCategorySyrup, ingredientCategoryCocoaNibs);

        // 4
        Product productIcedCoffeewithMilk = createProduct("Iced Coffee with Milk",
                "Freshly brewed Starbucks Iced Coffee Blend with milk - served chilled and lightly sweetened over ice.",
                new BigDecimal(35), categoryDrinks, null);
        enableProductIngredient(productIcedCoffeewithMilk,
                ingredientCategorySize, ingredientCategorySweetness, ingredientCategoryIce, ingredientCategoryMilk,
                ingredientCategoryCream, ingredientCategoryEspresso, ingredientCategorySyrup, ingredientCategoryCocoaNibs);

        // 5
        Product productNitroColdBrew = createProduct("Nitro Cold Brew",
                "Our small-batch cold brew - slow-steeped for a super smooth taste—gets even better. We're infusing it with nitrogen for a naturally sweet flavor and cascading, velvety crema. Perfection is served.",
                new BigDecimal(36), categoryDrinks, createNutrition(5, 0, 0, 0, 0, 10));
        enableProductIngredient(productNitroColdBrew,
                ingredientCategorySweetness, ingredientCategoryIce, ingredientCategoryCream, ingredientCategorySyrup);

        //6
        Product productNitroColdBrewwithSweetCream = createProduct("Nitro Cold Brew with Sweet Cream",
                "Served cold, straight from the tap, our Nitro Cold Brew is topped with a float of house-made vanilla sweet cream. The result is a subtly-sweet cascade of velvety coffee that is more sippable than ever.",
                new BigDecimal(38), categoryDrinks, createNutrition(70, 5, 5, 0, 1, 15));
        enableProductIngredient(productNitroColdBrewwithSweetCream,
                ingredientCategorySize, ingredientCategorySweetness, ingredientCategoryIce, ingredientCategoryMilk,
                ingredientCategoryEspresso, ingredientCategorySyrup, ingredientCategoryCocoaNibs);

        // 7
        Product productColdBrewCoffee = createProduct("Cold Brew Coffee",
                "Our custom blend of beans are grown to steep long and cold for a super-smooth flavor. Starbucks Cold brew is handcrafted in small batches daily, slow-steeped in cool water for 20 hours, without touching heat.",
                new BigDecimal(30), categoryDrinks, createNutrition(5, 0, 0, 0, 0, 10));
        enableProductIngredient(productColdBrewCoffee,
                ingredientCategorySweetness, ingredientCategoryIce, ingredientCategoryCream,
                ingredientCategoryEspresso, ingredientCategorySyrup, ingredientCategoryCocoaNibs);

        // 8
        Product productColdBrewCoffeewithMilk = createProduct("Cold Brew Coffee with Milk",
                "Our custom blend of beans are grown to steep long and cold for a super-smooth flavor. Starbucks Cold brew is handcrafted in small batches daily, slow-steeped in cool water for 20 hours, without touching heat and finished wiht a splash of milk.",
                new BigDecimal(33), categoryDrinks, createNutrition(0, 0, 0, 0, 0, 10));
        enableProductIngredient(productColdBrewCoffeewithMilk,
                ingredientCategorySweetness, ingredientCategoryIce, ingredientCategoryMilk, ingredientCategoryCream,
                ingredientCategoryEspresso, ingredientCategorySyrup, ingredientCategoryCocoaNibs);

        // 9
        Product productVanillaSweetCreamColdBrew = createProduct("Vanilla Sweet Cream Cold Brew",
                "Just before serving, our slow-steeped custom blend Starbucks? Cold Brew Coffee is topped with a delicate float of house-made vanilla sweet cream that cascades throughout the cup.",
                new BigDecimal(35), categoryDrinks, createNutrition(100, 6, 12, 0, 1, 20));
        enableProductIngredient(productVanillaSweetCreamColdBrew,
                ingredientCategorySweetness, ingredientCategoryIce, ingredientCategoryMilk,
                ingredientCategoryEspresso, ingredientCategorySyrup, ingredientCategoryCocoaNibs);
        // 10
        Product productHotChocolate = createProduct("Hot Chocolate",
                "Steamed milk with vanilla- and mocha-flavored syrups. Topped with sweetened whipped cream and chocolate-flavored drizzle.",
                new BigDecimal(32), categoryDrinks, createNutrition(250, 7, 37, 3, 11, 125));
        enableProductIngredient(productHotChocolate,
                ingredientCategorySize, ingredientCategorySweetness, ingredientCategoryMilk, ingredientCategoryCream,
                ingredientCategoryEspresso, ingredientCategorySyrup, ingredientCategoryCocoaNibs);

        // 11
        Product productSaltedCaramelHotChocolate = createProduct("Salted Caramel Hot Chocolate",
                "Steamed milk and mocha sauce are combined with toffee nut and vanilla syrups, then topped with sweetened whipped cream, caramel sauce and a blend of turbinado sugar and sea salt. The sweet is never as sweet without the salt.",
                new BigDecimal(38), categoryDrinks, createNutrition(300, 7, 51, 3, 10, 220));
        enableProductIngredient(productSaltedCaramelHotChocolate,
                ingredientCategorySize, ingredientCategoryMilk, ingredientCategoryCream, ingredientCategoryEspresso,
                ingredientCategoryCocoaNibs);

        // 12
        Product productSteamedAppleJuice = createProduct("Steamed Apple Juice",
                "Freshly steamed 100% pressed apple juice (not from concentrate).",
                new BigDecimal(33), categoryDrinks, null);
        enableProductIngredient(productSteamedAppleJuice,
                ingredientCategorySize, ingredientCategorySweetness, ingredientCategoryIce);

        // 13
        Product productMangoDragonfruitLemonade = createProduct("Mango Dragonfruit Lemonade",
                "This tropical-inspired pick-me-up is crafted with a clever combination of vibrant lemonade, sweet mango and refreshing dragonfruit flavors, then handshaken with ice and a scoop of real diced dragon fruit.",
                new BigDecimal(32), categoryDrinks, createNutrition(110, 0, 26, 0, 0, 10));
        enableProductIngredient(productMangoDragonfruitLemonade,
                ingredientCategorySize, ingredientCategorySweetness, ingredientCategoryIce);

        // 14
        Product productMangoDragonfruit = createProduct("Mango Dragonfruit",
                "This tropical-inspired pick-me-up is crafted with a refreshing combination of sweet mango and dragonfruit flavors, handshaken with ice and a scoop of real diced dragon fruit.",
                new BigDecimal(32), categoryDrinks, createNutrition(70, 0, 17, 0, 0, 10));
        enableProductIngredient(productMangoDragonfruit,
                ingredientCategorySize, ingredientCategorySweetness, ingredientCategoryIce);

        // 15
        Product productStrawberryAcaiLemonade = createProduct("Strawberry Acai Lemonade",
                "Sweet strawberry flavors, passion fruit and acai notes are balanced with the delightful zing of lemonade. Caffeinated with Green Coffee Extract and served over ice, this is the pick-me-up your afternoon is calling for.",
                new BigDecimal(32), categoryDrinks, createNutrition(110, 0, 27, 1, 0, 10));
        enableProductIngredient(productStrawberryAcaiLemonade,
                ingredientCategorySize, ingredientCategorySweetness, ingredientCategoryIce);

        Product productChongaBagel = createProduct("Chonga Bagel",
                "A bagel topped with Cheddar cheese, poppy seeds, sesame seeds, onion and garlic.",
                new BigDecimal(18), categoryFood, createNutrition(300, 5, 50, 3, 12, 530));
        Product productGrainRoll = createProduct("Grain Roll",
                "A wholesome multigrain roll with raisins.",
                new BigDecimal(16), categoryFood, createNutrition(380, 6, 70, 7, 10, 480));
        Product productAlmondCroissant = createProduct("Almond Croissant",
                "Our rich, almond flan is enveloped in a flaky, buttery croissant, then topped with sliced almonds. It's the perfect combination of sweet and savory.",
                new BigDecimal(15), categoryFood, createNutrition(410, 22, 45, 3, 10, 390));
        Product productAppleCiderDoughnut = createProduct("Apple Cider Doughnut",
                "Our secret ingredient to this doughnut's bright flavor? Apple cider. This pastry is then slathered with sweet vanilla icing and a dusting of turbinado sanding sugar for a lovely crunch.",
                new BigDecimal(18), categoryFood, createNutrition(400, 17, 59, 1, 4, 320));
        Product productBananaNutBread = createProduct("Banana Nut Bread",
                "Bananas, walnuts and pecans in moist, nutty, classic banana bread.",
                new BigDecimal(15), categoryFood, createNutrition(420, 22, 52, 2, 6, 320));
        Product productBlueberryMuffin = createProduct("Blueberry Muffin",
                "This delicious muffin is dotted throughout with sweet, juicy blueberries and a hint of lemon. We dust the top with granulated sugar for a delightfully crunchy texture.",
                new BigDecimal(16), categoryFood, createNutrition(360, 15, 52, 1, 5, 270));
        Product productBlueberryScone = createProduct("Blueberry Scone",
                "A traditional scone with blueberries, buttermilk and lemon.",
                new BigDecimal(20), categoryFood, createNutrition(380, 17, 54, 2, 6, 350));
        Product productButterCroissant = createProduct("Butter Croissant",
                "This classic Croissant is made with 100 percent butter to create a golden, crunchy top and soft, flakey layers inside. The perfect match for a cup of Pike Place Roast.",
                new BigDecimal(14), categoryFood, createNutrition(260, 15, 27, 1, 5, 320));

        Product productFallBlend = createProduct("Fall Blend",
                "All the flavors of fall are blended for a cup of something worth savoring.",
                new BigDecimal(188), categoryCoffeeBean, null);
        Product productPumpkinSpice = createProduct("Pumpkin Spice",
                "Add a splash of cream and a bit of sugar to evoke deliciously familiar fall flavors inspired by our Pumpkin Spice Latte.",
                new BigDecimal(198), categoryCoffeeBean, null);
        Product productBrightSkyBlend = createProduct("Bright Sky Blend",
                "A gentle and well-rounded Starbucks Blonde Roast coffee with hints of nut and nice acidity.",
                new BigDecimal(188), categoryCoffeeBean, null);
        Product productVerandaBlend = createProduct("Veranda Blend",
                "Subtle with delicate nuances of soft cocoa and lightly toasted nuts.",
                new BigDecimal(208), categoryCoffeeBean, null);
        Product productOrganicYukonBlend = createProduct("Organic Yukon Blend",
                "Bold, lively, and adventurous coffee with deep and hearty taste to make an all-around great cup of coffee.",
                new BigDecimal(198), categoryCoffeeBean, null);

        Product productClassicDoubleWallGlassMug = createProduct("Classic Double Wall Glass Mug",
                "Classic Double Wall Glass Mug",
                new BigDecimal(88), categoryCup, null);
        Product productClassicSirenTumbler = createProduct("Classic Siren Tumbler",
                "Classic Siren Tumbler",
                new BigDecimal(88), categoryCup, null);
        Product productStarrySkyStainlessSteelColdCup = createProduct("Starry Sky Stainless Steel Cold Cup",
                "Starry Sky Stainless Steel Cold Cup",
                new BigDecimal(78), categoryCup, null);
        Product productHarvestWheatStainlessSteelTumbler = createProduct("Harvest Wheat Stainless Steel Tumbler",
                "Harvest Wheat Stainless Steel Tumbler",
                new BigDecimal(78), categoryCup, null);
        Product productCopperStainlessSteelColdCup = createProduct("Copper Stainless Steel Cold Cup",
                "Copper Stainless Steel Cold Cup",
                new BigDecimal(98), categoryCup, null);

    }

    private Product createProduct(String name, String description,
            BigDecimal price, Category category, Nutrition nutrition) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setCost(price);
        product.setCategoryId(category);
        product.setNutritionId(nutrition);
        product.setLastUpdate(new Date());
        product.setIngredientCategoryList(new ArrayList<>());
        category.getProductList().add(product);
        productFacade.create(product);
        categoryFacade.edit(category);
        return product;
    }

    private Nutrition createNutrition(int calories, int fat, int carton, int fiber, int protein, int sodium) {
        Nutrition nutrition = new Nutrition();
        nutrition.setCalories(calories);
        nutrition.setFat(fat);
        nutrition.setCarbon(carton);
        nutrition.setFiber(fiber);
        nutrition.setSodium(sodium);
        nutritionFacade.create(nutrition);
        return nutrition;
    }

    private Category createCategory(String name) {
        Category category = new Category(null, name);
        category.setProductList(new ArrayList<>());
        categoryFacade.create(category);
        return category;
    }

    private IngredientCategory createIngredientCategory(String name) {
        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName(name);
        ingredientCategory.setIngredientList(new ArrayList<>());
        ingredientCategory.setProductList(new ArrayList<>());
        ingredientCategoryFacade.create(ingredientCategory);
        return ingredientCategory;
    }

    private Ingredient createIngredient(String name, String description,
            BigDecimal cost, IngredientCategory ingredientCategory) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        ingredient.setDescription(description);
        ingredient.setCost(cost);
        ingredient.setIngredientCategoryId(ingredientCategory);
        ingredientCategory.getIngredientList().add(ingredient);
        ingredientFacade.create(ingredient);
        ingredientCategoryFacade.edit(ingredientCategory);
        return ingredient;
    }

    private void enableProductIngredient(Product product, IngredientCategory... categories) {
        product.getIngredientCategoryList().addAll(Arrays.asList(categories));
        for (IngredientCategory category : categories) {
            category.getProductList().add(product);
        }
        for (IngredientCategory category : categories) {
            ingredientCategoryFacade.edit(category);
        }
        productFacade.edit(product);
    }
}

package coffeeshop.web.admin;

import coffeeshop.ejb.ProductManager;
import coffeeshop.ejb.ProductManagerException;
import coffeeshop.entity.Category;
import coffeeshop.entity.IngredientCategory;
import coffeeshop.entity.Product;
import coffeeshop.web.util.MessageBundle;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@SessionScoped
public class AdminEditProductController implements Serializable {

    private static final Logger LOG = Logger.getLogger(AdminEditProductController.class.getName());

    @Inject
    private MessageBundle bundle;

    @EJB
    ProductManager productManager;

    private String name;
    private String description;
    private Category category;
    private BigDecimal cost;

    private UploadedFile image;
    private byte[] bytes;
    private String selectedFile;
    private String imageContentType;

    private boolean addNutrition;
    private int calories;
    private int fat;
    private int carbon;
    private int fiber;
    private int protein;
    private int sodium;

    private boolean addIngredientCategory;
    private List<IngredientCategory> selectedIngredientCategories;

    private UIComponent imageUploadComponent;

    private static String getContentType(String fileName) {
        if (fileName == null) {
            return null;
        }
        int index = fileName.lastIndexOf('.');
        if (index == -1 || index == fileName.length() - 1) {
            return null;
        }
        switch (fileName.substring(index + 1)) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            default:
                return null;
        }
    }

    public String getSelectedFile() {
        return this.selectedFile;
    }

    public UploadedFile getImage() {
        return image;
    }

    public void setImage(UploadedFile image) {
        this.image = image;
        LOG.log(Level.INFO, "image name:{0}", image.getFileName());
    }

    public void uploadHandler() throws IOException {
        if (image != null) {
            this.selectedFile = image.getFileName();
            this.imageContentType = getContentType(this.selectedFile);
            if (this.imageContentType == null) {
                FacesContext.getCurrentInstance().addMessage(imageUploadComponent.getClientId(),
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                bundle.getString("Ui.Message.InvalidImage"),
                                null
                        ));
                this.image = null;
                return;
            }
            InputStream is = image.getInputstream();
            this.bytes = IOUtils.toByteArray(is);
            FacesMessage message = new FacesMessage(
                    bundle.getFormatted("Ui.Message.UploadSuccess", image.getFileName()));
            FacesContext.getCurrentInstance().addMessage(imageUploadComponent.getClientId(), message);
        }
    }

    public StreamedContent getSelectedFileContent() {
        LOG.log(Level.INFO, "File: {0}", selectedFile);
        if (bytes != null) {
            InputStream is = new ByteArrayInputStream(bytes);
            LOG.log(Level.INFO, "bytes to inputstream successfully");
            return new DefaultStreamedContent(is, imageContentType, selectedFile);
        }
        return null;
    }

    public boolean isAddNutrition() {
        return addNutrition;
    }

    public void setAddNutrition(boolean addNutrition) {
        this.addNutrition = addNutrition;
        LOG.log(Level.INFO, "current isAddNutrition {0}", addNutrition);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public ProductManager getProductManager() {
        return productManager;
    }

    public void setProductManager(ProductManager productManager) {
        this.productManager = productManager;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getCarbon() {
        return carbon;
    }

    public void setCarbon(int carbon) {
        this.carbon = carbon;
    }

    public int getFiber() {
        return fiber;
    }

    public void setFiber(int fiber) {
        this.fiber = fiber;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public UIComponent getImageUploadComponent() {
        return imageUploadComponent;
    }

    public void setImageUploadComponent(UIComponent imageUploadComponent) {
        this.imageUploadComponent = imageUploadComponent;
    }

    public boolean isAddIngredientCategory() {
        return addIngredientCategory;
    }

    public void setAddIngredientCategory(boolean addIngredientCategory) {
        this.addIngredientCategory = addIngredientCategory;
    }

    public List<IngredientCategory> getIngredientCategories() {
        return productManager.getIngredientCategories();
    }

    public List<IngredientCategory> getSelectedIngredientCategories() {
        return selectedIngredientCategories;
    }

    public void setSelectedIngredientCategories(List<IngredientCategory> selectedIngredientCategories) {
        this.selectedIngredientCategories = selectedIngredientCategories;
    }

    private Product selectedProduct;

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        LOG.log(Level.INFO, "selected Product changed");
        this.selectedProduct = selectedProduct;
    }

    public String beforeJumpToEditProduct() {
        name = selectedProduct.getName();
        description = selectedProduct.getDescription();
        cost = selectedProduct.getCost();
        category = selectedProduct.getCategoryId();
        if (selectedProduct.getNutritionId() == null) {
            addNutrition = false;
        } else {
            addNutrition = true;
            calories = selectedProduct.getNutritionId().getCalories();
            fat = selectedProduct.getNutritionId().getFat();
            carbon = selectedProduct.getNutritionId().getCarbon();
            fiber = selectedProduct.getNutritionId().getFiber();
            protein = selectedProduct.getNutritionId().getProtein();
            sodium = selectedProduct.getNutritionId().getSodium();
        }
        LOG.log(Level.INFO, "start to get image");
        bytes = selectedProduct.getImageUuid().getContent();
        imageContentType = selectedProduct.getImageUuid().getMediaType();
        LOG.log(Level.INFO, "get image finish");
        selectedIngredientCategories = selectedProduct.getIngredientCategoryList();
        return "/admin/edit-product";
    }

    public String editProduct() throws ProductManagerException {
        productManager.editProduct(selectedProduct, name, description, cost, category,
                addNutrition, calories, fat, carbon, fiber, protein, sodium,
                bytes, imageContentType, selectedIngredientCategories);
        name = description = null;
        category = null;
        cost = null;
        image = null;
        bytes = null;
        imageContentType = selectedFile = null;
        addNutrition = false;
        calories = fat = carbon = fiber = protein = sodium = 0;
        selectedIngredientCategories = null;
        selectedProduct=null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Success!", bundle.getString("Ui.Product.Edit")));
        return "/admin/manage-product";
    }
}

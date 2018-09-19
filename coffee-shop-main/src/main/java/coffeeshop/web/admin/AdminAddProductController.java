package coffeeshop.web.admin;

import coffeeshop.ejb.ProductManager;
import coffeeshop.entity.Category;
import coffeeshop.entity.Product;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@SessionScoped
public class AdminAddProductController implements Serializable {

    private static final Logger LOG = Logger.getLogger(AdminAddProductController.class.getName());

    @EJB
    ProductManager productManager;

    private String name;
    private String description;
    private Category category;
    private BigDecimal cost;
    private boolean addNutrition;
    
    private UploadedFile image;
    private byte[] bytes;
    private String imageSuffix;
    private String selectedFile;
    private static Pattern getFileNameExtension = Pattern.compile(".+?//.([a-zA-z]+)");
    private String imageContentType;
    
    private int calories;
    private int fat;
    private int carbon;
    private int fiber;
    private int protein;
    private int sodium;

    private static String getExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        int index = fileName.indexOf('.');
        if (index == fileName.length() || index == fileName.length() - 1) {
            return null;
        }
        return fileName.substring(index + 1);
    }
    
    public String getImageSuffix(){
        return this.imageSuffix;
    }
    
    public String getSelectedFile(){
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
            FacesMessage message = new FacesMessage("Succesful", image.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            InputStream is = image.getInputstream();
            this.bytes = IOUtils.toByteArray(is);
            this.imageSuffix = getExtension(image.getFileName()).toLowerCase();
            switch (this.imageSuffix) {
                case "jpg": case "jpeg":
                    this.imageContentType = "image/jpeg";
                    break;
                case "png":
                    this.imageContentType = "image/png";
                    break;
                default:
                    throw new ValidatorException(new FacesMessage("invalid image suffx"));
            }
            this.selectedFile=image.getFileName();
            LOG.log(Level.INFO, "image extension:{0}", imageSuffix);
        }
    }
    
    public StreamedContent getSelectedFileContent() {
        LOG.log(Level.INFO, "File: {0}", selectedFile);
        InputStream is = new ByteArrayInputStream(bytes);     
        LOG.log(Level.INFO, "bytes to inputstream successfully");
        return new DefaultStreamedContent(is, imageContentType, selectedFile);
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

    public void createProduct() throws IOException, URISyntaxException {
        if (addNutrition == true) {
            Product newProduct = productManager.createProduct(name, description, cost, category,
                    calories, fat, carbon, fiber, protein, sodium, bytes, imageSuffix);
            LOG.log(Level.INFO, "create product with nutrition success{0}", newProduct.getId());
        }else{
            Product newProduct=productManager.createProduct(name, description, cost, category,
                     bytes, imageSuffix);
            LOG.log(Level.INFO, "create product without nutrition success{0}", newProduct.getId());
        }
        
    }

}

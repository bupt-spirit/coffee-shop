
package coffeeshop.web.admin;

    
    
import coffeeshop.ejb.InitManager;
import coffeeshop.ejb.ProductManager;
import coffeeshop.entity.Category;
import coffeeshop.facade.ImageFacade;
import coffeeshop.facade.NutritionFacade;
import coffeeshop.facade.ProductFacade;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;
import org.apache.commons.io.IOUtils;

@Named
@RequestScoped
public class AdminAddProductController {

    private static final Logger LOG = Logger.getLogger(AdminAddProductController.class.getName());
    
    @EJB
    InitManager initManager;
    
    private String name;
    private String description;
    private Category category;
    private BigDecimal cost;
    private boolean addNutrition;
    private UploadedFile image;
    private byte[] bytes;
    private BigDecimal calories;
    private BigDecimal fat;
    private BigDecimal carbon;
    private BigDecimal fiber;
    private BigDecimal protein;
    private BigDecimal sodium;
    
    @EJB
    ImageFacade imageFacade;
    @EJB
    ProductFacade productFacade;
    @EJB
    NutritionFacade nutritionFacade;
    
    public UploadedFile getImage() {
        return image;
    }

    public void setImage(UploadedFile image) {
        this.image = image;
    }
    
    public void uploadHandler() throws IOException {
        if (image != null) {
            FacesMessage message = new FacesMessage("Succesful", image.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            InputStream is = image.getInputstream();
            this.bytes = IOUtils.toByteArray(is);
            //initManager.save(image.getFileName(), bytes);
        }
    }

    public boolean isAddNutrition() {
        return addNutrition;
    }

    public void setAddNutrition(boolean addNutrition) {
        this.addNutrition = addNutrition;
        LOG.log(Level.INFO,"current isAddNutrition {0}",addNutrition);
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

    public BigDecimal getCalories() {
        return calories;
    }

    public void setCalories(BigDecimal calories) {
        this.calories = calories;
    }

    public BigDecimal getFat() {
        return fat;
    }

    public void setFat(BigDecimal fat) {
        this.fat = fat;
    }

    public BigDecimal getCarbon() {
        return carbon;
    }

    public void setCarbon(BigDecimal carbon) {
        this.carbon = carbon;
    }

    public BigDecimal getFiber() {
        return fiber;
    }

    public void setFiber(BigDecimal fiber) {
        this.fiber = fiber;
    }

    public BigDecimal getProtein() {
        return protein;
    }

    public void setProtein(BigDecimal protein) {
        this.protein = protein;
    }

    public BigDecimal getSodium() {
        return sodium;
    }

    public void setSodium(BigDecimal sodium) {
        this.sodium = sodium;
    }
    
    
    
    public void createProduct(){
        
    }
    
}

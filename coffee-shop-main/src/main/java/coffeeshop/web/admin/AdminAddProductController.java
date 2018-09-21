package coffeeshop.web.admin;

import coffeeshop.ejb.ProductManager;
import coffeeshop.ejb.ProductManagerException;
import coffeeshop.entity.Category;
import coffeeshop.entity.Image;
import coffeeshop.entity.IngredientCategory;
import coffeeshop.entity.Nutrition;
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
import javax.annotation.PostConstruct;
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
public class AdminAddProductController implements Serializable {

    private static final Logger LOG = Logger.getLogger(AdminAddProductController.class.getName());

    private Product newProduct;
    private Nutrition newNutrition;
    private Image newImage;
    @Inject
    private MessageBundle bundle;

    @EJB
    ProductManager productManager;

    private UploadedFile image;
    private byte[] bytes;
    private String selectedFile;
    private String imageContentType;

    private boolean addNutrition;

    private UIComponent imageUploadComponent;

    @PostConstruct
    private void init() {
        newProduct = new Product();
        newNutrition = new Nutrition();
        newImage = new Image();
        addNutrition = false;
    }

    public Product getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(Product newProduct) {
        this.newProduct = newProduct;
    }

    public Nutrition getNewNutrition() {
        return newNutrition;
    }

    public void setNewNutrition(Nutrition newNutrition) {
        this.newNutrition = newNutrition;
    }

    public Image getNewImage() {
        return newImage;
    }

    public void setNewImage(Image newImage) {
        this.newImage = newImage;
    }

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
            newImage.setContent(bytes);
            newImage.setMediaType(imageContentType);
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

    public ProductManager getProductManager() {
        return productManager;
    }

    public void setProductManager(ProductManager productManager) {
        this.productManager = productManager;
    }

    public UIComponent getImageUploadComponent() {
        return imageUploadComponent;
    }

    public void setImageUploadComponent(UIComponent imageUploadComponent) {
        this.imageUploadComponent = imageUploadComponent;
    }

    public void createProduct() throws IOException, URISyntaxException {
        if (bytes == null || bytes.length == 0 || imageContentType == null) {
            FacesContext.getCurrentInstance().addMessage(imageUploadComponent.getClientId(),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            bundle.getString("Ui.Product.PhotoError"), null));
            return;
        }
        productManager.createProduct(newProduct, addNutrition, newNutrition, newImage);
        LOG.log(Level.INFO, "create product with nutrition success{0}", newProduct.getId());
        init();
        image = null;
        bytes = null;
        selectedFile = null;
        imageContentType = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundle.getString("Ui.Product.Success")));
    }

    public List<IngredientCategory> getIngredientCategories() {
        return productManager.getIngredientCategories();
    }

}

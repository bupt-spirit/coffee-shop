package coffeeshop.web.admin;

import coffeeshop.ejb.ProductManager;
import coffeeshop.ejb.ProductManagerException;
import coffeeshop.ejb.SeasonSpecialManagerException;
import coffeeshop.entity.Product;
import coffeeshop.web.util.MessageBundle;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AdminRemoveProductController {

    @EJB
    ProductManager productManager;
    
    @Inject
    private MessageBundle bundle;

    private Product selectedProduct;

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public List<Product> getAllProducts() {
        return productManager.getAllProduct();
    }

    public void removeProduct() throws ProductManagerException,SeasonSpecialManagerException {
        productManager.removeProduct(selectedProduct);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Success!", bundle.getString("Ui.Product.Remove")));
    }
    
}

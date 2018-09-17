package coffeeshop.web;

import coffeeshop.ejb.SeasonSpecialManager;
import coffeeshop.entity.Product;
import coffeeshop.facade.ProductFacade;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class SeasonSpecialContorller implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());

    @EJB
    private SeasonSpecialManager seasonSpecialManager;
    
    @EJB
    private ProductFacade productfacade;
    
    private Product selectedSeasonSpecial;

    private Product selectedProduct;

    
    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }
    
    public Product getSelectedSeasonSpecial() {
        return selectedSeasonSpecial;
    }

    public void setSelectedSeasonSpecial(Product selectedSeasonSpecial) {
        this.selectedSeasonSpecial = selectedSeasonSpecial;
        
    }


    public List<Product> getSpecials() {
        return  seasonSpecialManager.getAllSeasonSpecial();
    }
    
    public void removeSeasonSpecial()
    {
        seasonSpecialManager.removeSeasonSpecial(productfacade.find(this.selectedSeasonSpecial.getId()));        
    }
    
    public void addSeasonSpecial()
    {
        seasonSpecialManager.addSeasonSpecial(this.selectedProduct);
    }

}

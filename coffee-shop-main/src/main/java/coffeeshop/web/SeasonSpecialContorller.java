package coffeeshop.web;

import coffeeshop.ejb.SeasonSpecialManager;
import coffeeshop.entity.Product;
import coffeeshop.facade.SeasonSpecialFacade;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class SeasonSpecialContorller implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());

    @EJB
    private SeasonSpecialManager seasonSpecialManager;

    private List<Product> specials;

    public List<Product> getSpecials() {
        this.specials = seasonSpecialManager.getAllSeasonSpecial();
        return seasonSpecialManager.getAllSeasonSpecial();
    }

    public void setSpecials(List<Product> specials) {
        this.specials = specials;
    }

}

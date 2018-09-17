package coffeeshop.web;

import coffeeshop.ejb.SeasonSpecialManager;
import coffeeshop.entity.Product;
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
   
    public List<Product> getSpecials() {
        return seasonSpecialManager.getAllSeasonSpecial();
    }

}

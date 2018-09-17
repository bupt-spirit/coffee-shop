package coffeeshop.ejb;

import coffeeshop.entity.Product;
import coffeeshop.entity.SeasonSpecial;
import coffeeshop.facade.ProductFacade;
import coffeeshop.facade.SeasonSpecialFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;


@Stateless
public class SeasonSpecialManagerBean implements SeasonSpecialManager{
    
    @EJB
    private SeasonSpecialFacade seasonSpecialFacade;
    
    @EJB
    private ProductFacade productFacade;
    
    @Override
    public List<Product> getAllSeasonSpecial()
    {
        List<SeasonSpecial> seasonspecial =  seasonSpecialFacade.findAll();
        List<Product> result = new ArrayList<>();
        for ( SeasonSpecial ss:seasonspecial)
        {
            result.add(ss.getProduct());
        }
        return result;
    }

    @Override
    public void addSeasonSpecial(Product product) {
        SeasonSpecial seasonspecial = new SeasonSpecial(product.getId());
        seasonspecial.setProduct(product);
        seasonSpecialFacade.create(seasonspecial);
    }

    @Override
    public void removeSeasonSpecial(Product product) {
        SeasonSpecial seasonspecial = new SeasonSpecial(product.getId());
        seasonspecial.setProduct(product);
        seasonSpecialFacade.remove(seasonspecial);
               
    }
}

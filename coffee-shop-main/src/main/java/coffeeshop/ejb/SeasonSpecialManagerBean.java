package coffeeshop.ejb;

import coffeeshop.entity.Product;
import coffeeshop.entity.SeasonSpecial;
import coffeeshop.facade.ProductFacade;
import coffeeshop.facade.SeasonSpecialFacade;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class SeasonSpecialManagerBean implements SeasonSpecialManager {

    @EJB
    private SeasonSpecialFacade seasonSpecialFacade;

    @EJB
    private ProductFacade productFacade;

    @Override
    public List<Product> getAllSeasonSpecial() {
        return seasonSpecialFacade.findAll().stream().map(
                (special) -> special.getProduct()
        ).collect(Collectors.toList());
    }

    @Override
    public void addSeasonSpecial(Product product) {
        SeasonSpecial seasonspecial = new SeasonSpecial(product.getId());
        seasonspecial.setProduct(product);
        product.setSeasonSpecial(seasonspecial);

        seasonSpecialFacade.create(seasonspecial);
        productFacade.edit(product);
    }

    @Override
    public void removeSeasonSpecial(Product product) throws SeasonSpecialManagerException {
        SeasonSpecial seasonSpecial = product.getSeasonSpecial();
        if (seasonSpecial == null) {
            throw new SeasonSpecialManagerException("Product " + product + "is not a season special");
        }
        seasonSpecialFacade.remove(seasonSpecial);
        product.setSeasonSpecial(null);
        productFacade.edit(product);
    }
}

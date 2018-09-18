package coffeeshop.ejb;

import coffeeshop.entity.Product;
import java.util.List;

public interface SeasonSpecialManager {

    List<Product> getAllSeasonSpecial();
    
    void addSeasonSpecial(Product product);
    
    void removeSeasonSpecial(Product product) throws SeasonSpecialManagerException;
}

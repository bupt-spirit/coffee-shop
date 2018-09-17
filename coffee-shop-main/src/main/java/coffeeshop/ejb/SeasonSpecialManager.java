package coffeeshop.ejb;

import coffeeshop.entity.Product;
import java.util.List;

public interface SeasonSpecialManager {

    public List<Product> getAllSeasonSpecial();

    public void addSeasonSpecial(Product product);

    public void removeSeasonSpecial(Product product);

}

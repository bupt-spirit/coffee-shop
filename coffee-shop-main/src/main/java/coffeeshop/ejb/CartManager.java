package coffeeshop.ejb;

import coffeeshop.entity.Address;
import coffeeshop.entity.Ingredient;
import coffeeshop.entity.OrderInfo;
import coffeeshop.entity.Product;
import coffeeshop.entity.Store;
import java.util.List;

// Stateful cart ejb interface which stores user's cart info
public interface CartManager {
    
    void add(Product product, List<Ingredient> ingredients, short quality) throws CartManagerException;

    OrderInfo check();
}

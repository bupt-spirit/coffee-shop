package coffeeshop.ejb;

import coffeeshop.entity.Ingredient;
import coffeeshop.entity.OrderInfo;
import coffeeshop.entity.Product;
import coffeeshop.entity.Suborder;
import java.util.List;

// Stateful cart ejb interface which stores user's cart info
public interface CartManager {
    
    void add(Product product, List<Ingredient> ingredients, short quality) throws CartManagerException;
    
    void remove(Suborder suborder) throws CartManagerException;
    
    List<Suborder> getSuborders();

    OrderInfo check();
}

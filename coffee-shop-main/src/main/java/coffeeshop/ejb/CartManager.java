package coffeeshop.ejb;

import coffeeshop.entity.Address;
import coffeeshop.entity.Ingredient;
import coffeeshop.entity.OrderInfo;
import coffeeshop.entity.Product;
import coffeeshop.entity.Store;
import coffeeshop.entity.Suborder;
import java.math.BigDecimal;
import java.util.List;

// Stateful cart ejb interface which stores user's cart info
public interface CartManager {

    void add(Product product, List<Ingredient> ingredients, short quantity) throws CartManagerException;

    void remove(Suborder suborder) throws CartManagerException;

    BigDecimal getOrderAmount();
    
    BigDecimal getSuborderAmount(Suborder suborder);

    int getItemCount();

    List<Suborder> getSuborders();
    
    OrderInfo getOrder();

    OrderInfo saveAndGetOrderInfo(Store store, Address address);
    
    void removeAll();
}

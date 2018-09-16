package coffeeshop.ejb;

import coffeeshop.entity.Address;
import coffeeshop.entity.Store;
import javax.ejb.Stateful;
import javax.persistence.criteria.Order;

@Stateful
public class CartManagerBean implements CartManager {

    @Override
    public Order check(Address address, Store store) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

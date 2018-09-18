package coffeeshop.web;

import coffeeshop.ejb.CartManager;
import coffeeshop.ejb.CartManagerException;
import coffeeshop.entity.Address;
import coffeeshop.entity.OrderInfo;
import coffeeshop.entity.Store;
import coffeeshop.entity.Suborder;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class CartController implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(CartController.class.getName());

    @EJB
    private CartManager cartManager;

    private Suborder selectedSuborder;

    private Date currentTime;

    private Store selectedStore;

    private Address selectedAddress;

    public Address getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public Store getSelectedStore() {
        return selectedStore;
    }

    public void setSelectedStore(Store selectedStore) {
        this.selectedStore = selectedStore;
    }

    public CartManager getCartManager() {
        return cartManager;
    }

    public Suborder getSelectedSuborder() {
        return selectedSuborder;
    }

    public void setSelectedSuborder(Suborder selectedSuborder) {
        this.selectedSuborder = selectedSuborder;
    }

    public List<Suborder> getSuborders() {
        return cartManager.getSuborders();
    }
    
    public OrderInfo getOrder() {
        return cartManager.getOrder();
    }

    public void removeAll() {
        cartManager.removeAll();
    }

    public void removeSuborder() throws CartManagerException {
        cartManager.remove(selectedSuborder);
    }

    public BigDecimal getAmount() {
        return cartManager.getOrderAmount();
    }

    public int getItemCount() {
        return cartManager.getItemCount();
    }

    public Date getCurrentTime() {
        return new Date();
    }

    public void saveOrder() {
        OrderInfo orderInfo = cartManager.saveAndGetOrderInfo(selectedStore, selectedAddress);
        LOG.log(Level.INFO, "create order successfully {0}", orderInfo.getId());
    }
}

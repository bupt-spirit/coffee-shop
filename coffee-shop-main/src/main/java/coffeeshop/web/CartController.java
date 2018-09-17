package coffeeshop.web;

import coffeeshop.ejb.CartManager;
import coffeeshop.ejb.CartManagerException;
import coffeeshop.entity.Suborder;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class CartController implements Serializable {

    private static final Logger LOG = Logger.getLogger(CartController.class.getName());
    
    private static final long serialVersionUID = 1L;
    
    @EJB
    private CartManager cartManager;
    
    private Suborder selectedSuborder;

    public CartManager getCartManager() {
        return cartManager;
    }

    public Suborder getSelectedSuborder() {
        return selectedSuborder;
    }

    public void removeAll(){
        cartManager.removeAll();
    }
    
    public void setSelectedSuborder(Suborder selectedSuborder) {
        this.selectedSuborder = selectedSuborder;
    }
    
    public List<Suborder> getSuborders() {
        return cartManager.getSuborders();
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
}

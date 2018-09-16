package coffeeshop.web;

import coffeeshop.ejb.CartManager;
import coffeeshop.ejb.CartManagerException;
import coffeeshop.entity.Suborder;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class CartController implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private CartManager cartManager;
    
    private Suborder selectedSuborder;

    public Suborder getSelectedSuborder() {
        return selectedSuborder;
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
}

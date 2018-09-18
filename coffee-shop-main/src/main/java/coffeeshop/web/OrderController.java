
package coffeeshop.web;

import coffeeshop.ejb.OrderManager;
import coffeeshop.ejb.UserManagerException;
import coffeeshop.entity.OrderInfo;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.ToggleEvent;

@Named
@RequestScoped
public class OrderController {
    
    @EJB
    OrderManager orderManager;
    @Inject
    UserInfoController userInfoController;
    
    private OrderInfo selectedOrder;
    
    private List<OrderInfo> customerAllOrder;
    private List<OrderInfo> customerUnfinishedOrder;
    private List<OrderInfo> customerFinishedOrder;
    
    private List<OrderInfo> storeAllOrder;
    private List<OrderInfo> storeUnfinishedOrder;
    private List<OrderInfo> stroeFinishedOrder;

    public OrderInfo getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(OrderInfo selectedOrder) {
        this.selectedOrder = selectedOrder;
    }
   
    public void handleToggle(ToggleEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Toggled", "Visibility:" + event.getVisibility());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public List<OrderInfo> getCustomerAllOrder() throws UserManagerException{        
        return orderManager.getCustomerAllOrder(userInfoController.getCurrentUser().getCustomer());
    }
    
    public List<OrderInfo> getCustomerUnfinishedOrder() throws UserManagerException{
        return orderManager.getCustomerUnfinishedOrder(userInfoController.getCurrentUser().getCustomer());
    }
    
    public void hasPrepared(){
    }
    
    public void hasFinished(){
    }
    
}

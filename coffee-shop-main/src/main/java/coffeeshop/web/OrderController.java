package coffeeshop.web;

import coffeeshop.ejb.OrderManager;
import coffeeshop.ejb.OrderManagerException;
import coffeeshop.ejb.UserManagerException;
import coffeeshop.entity.OrderInfo;
import coffeeshop.web.util.MessageBundle;
import java.util.List;
import javax.annotation.PostConstruct;
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

    @Inject
    private MessageBundle bundle;

    private FacesContext facesContext;

    @PostConstruct
    private void getFacesContext() {
        facesContext = FacesContext.getCurrentInstance();
    }

    private OrderInfo selectedOrder;

    public OrderInfo getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(OrderInfo selectedOrder) {
        this.selectedOrder = selectedOrder;
    }
    
    public void changeOrderStateToPrepared() throws OrderManagerException
    {
        orderManager.changeOrderStateToPrepared(selectedOrder);
    }
   
    public void finishOrder() throws OrderManagerException {
        orderManager.finishOrder(selectedOrder);
        facesContext.addMessage(null, new FacesMessage(bundle.getString("Ui.Order.FinishSuccess")));
    }

    public void handleToggle(ToggleEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Toggled", "Visibility:" + event.getVisibility());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<OrderInfo> getCustomerAllOrder() throws UserManagerException {
        return orderManager.getCustomerAllOrder(userInfoController.getCurrentUser().getCustomer());
    }

    public List<OrderInfo> getCustomerUnfinishedOrder() throws UserManagerException {
        return orderManager.getCustomerUnfinishedOrder(userInfoController.getCurrentUser().getCustomer());
    }

    public List<OrderInfo> getCustomerFinishedOrder() throws UserManagerException {
        return orderManager.getCustomerFinishedOrder(userInfoController.getCurrentUser().getCustomer());
    }

    public List<OrderInfo> getStoreAllOrder() throws UserManagerException {
        return orderManager.getStoreAllOrder(userInfoController.getCurrentUser().getStaff().getStoreId());
    }

    public List<OrderInfo> getStoreFinishedOrder() throws UserManagerException {
        return orderManager.getStoreFinishedOrder(userInfoController.getCurrentUser().getStaff().getStoreId());
    }

    public List<OrderInfo> getStoreUninishedOrder() throws UserManagerException {
        return orderManager.getStoreUnfinishedOrder(userInfoController.getCurrentUser().getStaff().getStoreId());
    }

    public List<OrderInfo> getStoreUnpreparedOrder() throws UserManagerException {
        return orderManager.getStoreUnpreparedOrder(userInfoController.getCurrentUser().getStaff().getStoreId());
    }

    public List<OrderInfo> getStorePreparedButUnfinishedOrder() throws UserManagerException {
        return orderManager.getStorePreparedButUnfinishedOrder(userInfoController.getCurrentUser().getStaff().getStoreId());
    }
}

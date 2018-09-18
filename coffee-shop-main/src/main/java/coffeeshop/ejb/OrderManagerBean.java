package coffeeshop.ejb;

import coffeeshop.entity.Customer;
import coffeeshop.entity.OrderInfo;
import coffeeshop.entity.Store;
import coffeeshop.facade.OrderInfoFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class OrderManagerBean implements OrderManager {

    @EJB
    OrderInfoFacade orderInfoFacade;

    @Override
    public List<OrderInfo> getCustomerAllOrder(Customer customer) {
        return orderInfoFacade.findByCustomer(customer);
    }

    @Override
    public List<OrderInfo> getCustomerFinishedOrder(Customer customer) {
        return orderInfoFacade.findFinishedByCustomer(customer);
    }

    @Override
    public List<OrderInfo> getCustomerUnfinishedOrder(Customer customer) {
        return orderInfoFacade.findUnfinishedByCustomer(customer);
    }

    @Override
    public List<OrderInfo> getStoreAllOrder(Store store) {
        return orderInfoFacade.findByStore(store);
    }

    @Override
    public List<OrderInfo> getStoreFinishedOrder(Store store) {
        return orderInfoFacade.findFinishedByStore(store);
    }

    @Override
    public List<OrderInfo> getStoreUnfinishedOrder(Store store) {
        return orderInfoFacade.findUnfinishedByStore(store);
    }

    @Override
    public List<OrderInfo> getStorePreparedButUnfinishedOrder(Store store) {
        return orderInfoFacade.findPreparedButUnfinishedByStore(store);
    }

    @Override
    public List<OrderInfo> getStoreUnpreparedOrder(Store store) {
        return orderInfoFacade.findUnpreparedByStore(store);
    }

    @Override
    public OrderInfo getOrderById(int id) throws OrderManagerException {
        OrderInfo orderInfo = orderInfoFacade.find(id);
        if (orderInfo == null) {
            throw new OrderManagerException("No orderInfo have id " + id);
        }
        return orderInfo;
    }

    @Override
    public void changeOrderStateToPrepared(OrderInfo orderInfo) throws OrderManagerException {
        if (orderInfo.getIsPrepared() == 0) {
            throw new OrderManagerException("Order has been prepared");
        }
        orderInfo.setIsPrepared((short) (1));
        orderInfoFacade.edit(orderInfo);
    }

    @Override
    public void finishOrder(OrderInfo orderInfo) throws OrderManagerException {
        if (orderInfo.getIsPrepared() == (short) 1) {
            orderInfo.setIsFinished((short) 1);
            orderInfoFacade.edit(orderInfo);
        } else {
            throw new OrderManagerException("can not finish this order");
        }
    }
}

package coffeeshop.ejb;

import coffeeshop.entity.Customer;
import coffeeshop.entity.OrderInfo;
import coffeeshop.entity.Store;
import java.util.List;

public interface OrderManager {
    
   List<OrderInfo> getCustomerAllOrder(Customer customer);
   
   List<OrderInfo> getCustomerFinishedOrder(Customer customer);
   
   List<OrderInfo> getCustomerUnfinishedOrder(Customer customer);
   
   List<OrderInfo> getStoreAllOrder(Store store);
   
   List<OrderInfo> getStoreFinishedOrder(Store store);
   
   List<OrderInfo> getStoreUnfinishedOrder(Store store);
   
   List<OrderInfo> getStorePreparedButUnfinishedOrder(Store store);
   
   List<OrderInfo> getStoreUnpreparedOrder(Store store);
   
   OrderInfo getOrderById(int id) throws OrderManagerException;
   
   void finishOrder(OrderInfo orderInfo) throws OrderManagerException;
}

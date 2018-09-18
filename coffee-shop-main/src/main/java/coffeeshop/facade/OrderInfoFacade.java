package coffeeshop.facade;

import coffeeshop.entity.Customer;
import coffeeshop.entity.OrderInfo;
import coffeeshop.entity.Store;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OrderInfoFacade extends AbstractFacade<OrderInfo> {

    @PersistenceContext(unitName = "bupt-spirit.projects.coffeeshop")
    private EntityManager em;

    public OrderInfoFacade() {
        super(OrderInfo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<OrderInfo> findByCustomer(Customer customer) {
        return getEntityManager().createQuery("SELECT o FROM OrderInfo o WHERE o.addressId.customerUserId = :customer "
                + "ORDER BY o.dateCreate")
                .setParameter("customer", customer)
                .getResultList();
    }
    
    public List<OrderInfo> findFinishedByCustomer(Customer customer){
        return getEntityManager().createQuery("SELECT o FROM OrderInfo o  WHERE o.isFinished = 1 "
                + "AND o.addressId.customerUserId = :customer ORDER BY o.dateCreate ")
                .setParameter("customer", customer)
                .getResultList();
    }
    
    public List<OrderInfo> findUnfinishedByCustomer(Customer customer){
        return getEntityManager().createQuery("SELECT o FROM OrderInfo o  WHERE o.isFinished = 0 "
                + "AND o.addressId.customerUserId = :customer ORDER BY o.dateCreate ")
                .setParameter("customer", customer)
                .getResultList();
    }
    
    public List<OrderInfo> findByStore(Store store) {
        return getEntityManager().createQuery("SELECT o FROM OrderInfo o WHERE o.storeId = :store ORDER BY o.dateCreate")
                .setParameter("store", store)
                .getResultList();
    }
    
    public List<OrderInfo> findFinishedByStore(Store store) {
        return getEntityManager().createQuery("SELECT o FROM OrderInfo o WHERE o.storeId = :store AND o.isFinished = 1"
                + "ORDER BY o.dateCreate")
                .setParameter("store", store)
                .getResultList();
    }
    
    public List<OrderInfo> findUnfinishedByStore(Store store) {
        return getEntityManager().createQuery("SELECT o FROM OrderInfo o WHERE o.storeId = :store AND o.isFinished = 0"
                + "ORDER BY o.dateCreate")
                .setParameter("store", store)
                .getResultList();
    }
    
    public List<OrderInfo> findPreparedButUnfinishedByStore(Store store) {
        return getEntityManager().createQuery("SELECT o FROM OrderInfo o WHERE o.storeId = :store AND o.isPrepared = 1 "
                + "AND o.isFinished = 0 ORDER BY o.dateCreate")
                .setParameter("store", store)
                .getResultList();
    }
    
    public List<OrderInfo> findUnpreparedByStore(Store store) {
        return getEntityManager().createQuery("SELECT o FROM OrderInfo o WHERE o.storeId = :store AND o.isPrepared = 0"
                + "ORDER BY o.dateCreate")
                .setParameter("store", store)
                .getResultList();
    }
}

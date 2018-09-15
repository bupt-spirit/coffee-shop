package coffeeshop.facade;

import coffeeshop.entity.OrderInfo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OrderInfoFacade extends AbstractFacade<OrderInfo> {

    @PersistenceContext(unitName = "bupt-spirit.projects.coffeeshop")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderInfoFacade() {
        super(OrderInfo.class);
    }
    
}

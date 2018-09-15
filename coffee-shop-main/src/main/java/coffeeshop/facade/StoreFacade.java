package coffeeshop.facade;

import coffeeshop.entity.Store;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class StoreFacade extends AbstractFacade<Store> {

    @PersistenceContext(unitName = "bupt-spirit.projects.coffeeshop")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StoreFacade() {
        super(Store.class);
    }
    
}

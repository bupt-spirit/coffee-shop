package coffeeshop.facade;

import coffeeshop.entity.Address;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AddressFacade extends AbstractFacade<Address> {

    @PersistenceContext(unitName = "bupt-spirit.projects.coffeeshop")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AddressFacade() {
        super(Address.class);
    }
    
}

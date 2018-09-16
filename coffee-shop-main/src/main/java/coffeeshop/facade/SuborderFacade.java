package coffeeshop.facade;

import coffeeshop.entity.Suborder;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SuborderFacade extends AbstractFacade<Suborder> {

    @PersistenceContext(unitName = "bupt-spirit.projects.coffeeshop")
    private EntityManager em;

    public SuborderFacade() {
        super(Suborder.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}

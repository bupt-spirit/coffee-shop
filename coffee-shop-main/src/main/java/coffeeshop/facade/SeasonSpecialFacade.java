package coffeeshop.facade;

import coffeeshop.entity.SeasonSpecial;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SeasonSpecialFacade extends AbstractFacade<SeasonSpecial> {

    @PersistenceContext(unitName = "bupt-spirit.projects.coffeeshop")
    private EntityManager em;

    public SeasonSpecialFacade() {
        super(SeasonSpecial.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}

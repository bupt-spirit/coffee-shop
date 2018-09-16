package coffeeshop.facade;

import coffeeshop.entity.Nutrition;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class NutritionFacade extends AbstractFacade<Nutrition> {

    @PersistenceContext(unitName = "bupt-spirit.projects.coffeeshop")
    private EntityManager em;

    public NutritionFacade() {
        super(Nutrition.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}

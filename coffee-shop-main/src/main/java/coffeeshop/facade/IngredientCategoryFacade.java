package coffeeshop.facade;

import coffeeshop.entity.IngredientCategory;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class IngredientCategoryFacade extends AbstractFacade<IngredientCategory> {

    @PersistenceContext(unitName = "bupt-spirit.projects.coffeeshop")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IngredientCategoryFacade() {
        super(IngredientCategory.class);
    }
    
}

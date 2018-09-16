package coffeeshop.facade;

import coffeeshop.entity.Ingredient;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class IngredientFacade extends AbstractFacade<Ingredient> {

    @PersistenceContext(unitName = "bupt-spirit.projects.coffeeshop")
    private EntityManager em;

    public IngredientFacade() {
        super(Ingredient.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}

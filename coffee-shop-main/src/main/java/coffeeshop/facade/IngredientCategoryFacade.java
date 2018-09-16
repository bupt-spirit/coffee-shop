package coffeeshop.facade;

import coffeeshop.entity.IngredientCategory;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class IngredientCategoryFacade extends AbstractFacade<IngredientCategory> {

    @PersistenceContext(unitName = "bupt-spirit.projects.coffeeshop")
    private EntityManager em;

    public IngredientCategoryFacade() {
        super(IngredientCategory.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IngredientCategory findByName(String categoryName) {
        try {
            return getEntityManager().createNamedQuery("IngredientCategory.findByName", IngredientCategory.class)
                    .setParameter("name", categoryName)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

}

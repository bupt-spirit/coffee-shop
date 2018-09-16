package coffeeshop.facade;

import coffeeshop.entity.Category;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class CategoryFacade extends AbstractFacade<Category> {

    @PersistenceContext(unitName = "bupt-spirit.projects.coffeeshop")
    private EntityManager em;

    public CategoryFacade() {
        super(Category.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Category findByName(String categoryName) {
        try {
            return getEntityManager().createNamedQuery("Category.findByName", Category.class)
                    .setParameter("name", categoryName)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

}

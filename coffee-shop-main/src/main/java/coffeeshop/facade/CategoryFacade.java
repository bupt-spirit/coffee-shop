package coffeeshop.facade;

import coffeeshop.entity.Category;
import coffeeshop.entity.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class CategoryFacade extends AbstractFacade<Category> {

    @PersistenceContext(unitName = "bupt-spirit.projects.coffeeshop")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public CategoryFacade() {
        super(Category.class);
    }
    
    public Category findByName(String categoryName){
        try{
            return getEntityManager().createNamedQuery("Category.findByName",Category.class)
                    .setParameter("name", categoryName)
                    .getSingleResult();
        }catch(NoResultException ex){
            return null;
        }
    }
    
}

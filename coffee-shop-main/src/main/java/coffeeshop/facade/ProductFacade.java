package coffeeshop.facade;

import coffeeshop.entity.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class ProductFacade extends AbstractFacade<Product> {

    @PersistenceContext(unitName = "bupt-spirit.projects.coffeeshop")
    private EntityManager em;

    public ProductFacade() {
        super(Product.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Product findByName(String productName) {
        try {
            return getEntityManager().createNamedQuery("Product.findByName", Product.class)
                    .setParameter("name", productName)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    public List<Product> findIsAvailable(){
        try{
            return getEntityManager().createNamedQuery("Product.findByIsAvailable", Product.class)
                    .setParameter("isAvailable", 1)
                    .getResultList();
        }catch(NoResultException ex) {
            return null;
        }
    }
}

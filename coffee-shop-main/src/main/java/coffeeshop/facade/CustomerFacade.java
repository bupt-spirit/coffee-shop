package coffeeshop.facade;

import coffeeshop.entity.Customer;
import coffeeshop.entity.UserInfo;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "bupt-spirit.projects.coffeeshop")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }

    @Inject
    private UserInfoFacade userInfoFacade;

    public Customer findByUsername(String username) {
        UserInfo userInfo = userInfoFacade.findByUsername(username);
        if (userInfo == null) {
            return null;
        } else {
            return userInfo.getCustomer();
        }
    }

}

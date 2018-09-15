package coffeeshop.facade;

import coffeeshop.entity.UserInfo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserInfoFacade extends AbstractFacade<UserInfo> {

    @PersistenceContext(unitName = "bupt-spirit.projects.coffeeshop")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserInfoFacade() {
        super(UserInfo.class);
    }
    
}

package coffeeshop.facade;

import coffeeshop.entity.UserInfo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class UserInfoFacade extends AbstractFacade<UserInfo> {

    @PersistenceContext(unitName = "bupt-spirit.projects.coffeeshop")
    private EntityManager em;

    public UserInfoFacade() {
        super(UserInfo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserInfo findByUsername(String username) {
        try {
            return getEntityManager().createNamedQuery("UserInfo.findByUsername", UserInfo.class)
                    .setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}

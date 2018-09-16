package coffeeshop.ejb;

import coffeeshop.entity.Customer;
import coffeeshop.facade.CustomerFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class CustomerInfoManagerBean implements CustomerInfoManager {

    @EJB
    CustomerFacade customerFacade;

    @Override
    public String getNickname(String username) throws CustomerInfoManagerException {
        Customer customer = customerFacade.findByUsername(username);
        if (customer == null) {
            throw new CustomerInfoManagerException("No such customer");
        } else {
            return customer.getNickname();
        }
    }

    @Override
    public boolean isCustomer(String username) {
        return customerFacade.findByUsername(username) != null;
    }
}

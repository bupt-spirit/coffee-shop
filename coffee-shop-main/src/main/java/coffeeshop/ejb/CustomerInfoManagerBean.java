package coffeeshop.ejb;

import coffeeshop.entity.Address;
import coffeeshop.entity.Customer;
import coffeeshop.facade.AddressFacade;
import coffeeshop.facade.CustomerFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class CustomerInfoManagerBean implements CustomerInfoManager {

    @EJB
    CustomerFacade customerFacade;
    
    @EJB
    AddressFacade addressFacade;

    @Override
    public boolean isCustomer(String username) {
        return customerFacade.findByUsername(username) != null;
    }

    @Override
    public void addAddress(Customer customer, String country, String province, String city, String district, String detail, String receiver, String receiverPhone) {
        Address address = new Address();
        address.setCustomerUserId(customer);
        address.setCountry(country);
        address.setProvince(province);
        address.setCity(city);
        address.setDistrict(district);
        address.setDetail(detail);
        address.setReceiver(receiver);
        address.setReceiverPhone(receiverPhone);
        address.setIsAvailable((short) 1);
        customer.getAddressList().add(address);
        customerFacade.edit(customer);
        addressFacade.create(address);
    }
    
    @Override
    public void removeAddress(Address address, Customer customer) {
        address.setIsAvailable((short) 0);
        addressFacade.edit(address);
        List<Address> addresses = customer.getAddressList();
        for (int i = 0; i < addresses.size(); i++) {
            if (addresses.get(i) == address) {
                addresses.remove(i);
                customerFacade.edit(customer);
            }
        }
    }
}

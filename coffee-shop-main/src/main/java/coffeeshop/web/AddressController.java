/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop.web;

import coffeeshop.ejb.CustomerInfoManager;
import coffeeshop.ejb.UserManagerException;
import coffeeshop.entity.Address;
import coffeeshop.entity.Customer;
import coffeeshop.web.util.MessageBundle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

@Named
@RequestScoped
public class AddressController {

    private String country;
    private String province;
    private String city;
    private String district;
    private String detail;
    private String receiver;
    private String receiverPhone;
    Customer customer;
    private List<Address> addresses;
    private Address selectedAddress;
    private static final Logger LOG = Logger.getLogger(AddressController.class.getName());

    @EJB
    CustomerInfoManager customerInfoManager;
    @Inject
    UserInfoController userInfoController;

    @Inject
    private MessageBundle bundle;

    private FacesContext facesContext;

    @PostConstruct
    private void getFacesContext() {
        facesContext = FacesContext.getCurrentInstance();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void addAddress() throws UserManagerException {
        this.customerInfoManager.addAddress(customer, country, province, city, district, detail, receiver, receiverPhone);
        facesContext.addMessage(null, new FacesMessage(bundle.getString("Ui.Address.AddSuccess")));
    }

    public List<Address> getAddresses() throws UserManagerException {
        this.addresses = userInfoController.getCurrentUser().getCustomer().getAddressList();
        LOG.log(Level.INFO, "get addresses successfully ");
        return addresses;
    }

    public Address getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public void removeAddress() throws UserManagerException {
        this.customer = userInfoController.getCurrentUser().getCustomer();
        this.customerInfoManager.removeAddress(selectedAddress, customer);
        LOG.log(Level.INFO, "remove address successfully ");
    }

}

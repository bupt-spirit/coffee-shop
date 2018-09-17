/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop.web;

import coffeeshop.ejb.CustomerInfoManager;
import coffeeshop.ejb.CustomerInfoManagerException;
import coffeeshop.ejb.UserManagerException;
import coffeeshop.entity.Address;
import coffeeshop.entity.Customer;
import coffeeshop.web.util.MessageBundle;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AddressController {

    private static final Logger LOG = Logger.getLogger(AddressController.class.getName());

    private String country;
    private String province;
    private String city;
    private String district;
    private String detail;
    private String receiver;
    private String receiverPhone;
    private Address selectedAddress;

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

    public void addAddress() throws UserManagerException {
        Customer customer = userInfoController.getCurrentUser().getCustomer();
        customerInfoManager.addAddress(customer, country, province, city, district, detail, receiver, receiverPhone);
        facesContext.addMessage(null, new FacesMessage(bundle.getString("Ui.Address.AddSuccess")));
    }

    public List<Address> getAddresses() throws UserManagerException {
        return userInfoController.getCurrentUser().getCustomer().getAddressList();
    }

    public Address getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public void removeAddress() throws UserManagerException, CustomerInfoManagerException {
        Customer customer = userInfoController.getCurrentUser().getCustomer();
        customerInfoManager.removeAddress(selectedAddress, customer);
    }

}

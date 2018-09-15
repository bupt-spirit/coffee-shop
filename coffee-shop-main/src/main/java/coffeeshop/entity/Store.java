package coffeeshop.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(catalog = "coffee_shop", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Store.findAll", query = "SELECT s FROM Store s")
    , @NamedQuery(name = "Store.findById", query = "SELECT s FROM Store s WHERE s.id = :id")
    , @NamedQuery(name = "Store.findByCountry", query = "SELECT s FROM Store s WHERE s.country = :country")
    , @NamedQuery(name = "Store.findByProvince", query = "SELECT s FROM Store s WHERE s.province = :province")
    , @NamedQuery(name = "Store.findByCity", query = "SELECT s FROM Store s WHERE s.city = :city")
    , @NamedQuery(name = "Store.findByDistrict", query = "SELECT s FROM Store s WHERE s.district = :district")
    , @NamedQuery(name = "Store.findByDetail", query = "SELECT s FROM Store s WHERE s.detail = :detail")
    , @NamedQuery(name = "Store.findByOpenDate", query = "SELECT s FROM Store s WHERE s.openDate = :openDate")
    , @NamedQuery(name = "Store.findByOpenTime", query = "SELECT s FROM Store s WHERE s.openTime = :openTime")
    , @NamedQuery(name = "Store.findByCloseTime", query = "SELECT s FROM Store s WHERE s.closeTime = :closeTime")
    , @NamedQuery(name = "Store.findByIsAvailable", query = "SELECT s FROM Store s WHERE s.isAvailable = :isAvailable")})
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String country;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String province;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String city;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String district;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(nullable = false, length = 45)
    private String detail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "open_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date openDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "open_time", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date openTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "close_time", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date closeTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_available", nullable = false)
    private short isAvailable;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storeId")
    private List<Staff> staffList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storeId")
    private List<OrderInfo> orderInfoList;

    public Store() {
    }

    public Store(Integer id) {
        this.id = id;
    }

    public Store(Integer id, String country, String province, String city, String district, String detail, Date openDate, Date openTime, Date closeTime, short isAvailable) {
        this.id = id;
        this.country = country;
        this.province = province;
        this.city = city;
        this.district = district;
        this.detail = detail;
        this.openDate = openDate;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.isAvailable = isAvailable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public short getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(short isAvailable) {
        this.isAvailable = isAvailable;
    }

    @XmlTransient
    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    @XmlTransient
    public List<OrderInfo> getOrderInfoList() {
        return orderInfoList;
    }

    public void setOrderInfoList(List<OrderInfo> orderInfoList) {
        this.orderInfoList = orderInfoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Store)) {
            return false;
        }
        Store other = (Store) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coffeeshop.entity.Store[ id=" + id + " ]";
    }
    
}

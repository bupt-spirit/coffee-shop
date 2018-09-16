package coffeeshop.entity;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order_info")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "OrderInfo.findAll", query = "SELECT o FROM OrderInfo o"),
        @NamedQuery(name = "OrderInfo.findById", query = "SELECT o FROM OrderInfo o WHERE o.id = :id"),
        @NamedQuery(name = "OrderInfo.findByDateCreate", query = "SELECT o FROM OrderInfo o WHERE o.dateCreate = :dateCreate"),
        @NamedQuery(name = "OrderInfo.findByAmount", query = "SELECT o FROM OrderInfo o WHERE o.amount = :amount"),
        @NamedQuery(name = "OrderInfo.findByConfirmation", query = "SELECT o FROM OrderInfo o WHERE o.confirmation = :confirmation"),
        @NamedQuery(name = "OrderInfo.findByIsPrepared", query = "SELECT o FROM OrderInfo o WHERE o.isPrepared = :isPrepared"),
        @NamedQuery(name = "OrderInfo.findByIsFinished", query = "SELECT o FROM OrderInfo o WHERE o.isFinished = :isFinished")})
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_create", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal amount;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int confirmation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_prepared", nullable = false)
    private short isPrepared;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_finished", nullable = false)
    private short isFinished;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private List<Suborder> suborderList;
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Address addressId;
    @JoinColumn(name = "store_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Store storeId;

    public OrderInfo() {
    }

    public OrderInfo(Integer id) {
        this.id = id;
    }

    public OrderInfo(Integer id, Date dateCreate, BigDecimal amount, int confirmation, short isPrepared, short isFinished) {
        this.id = id;
        this.dateCreate = dateCreate;
        this.amount = amount;
        this.confirmation = confirmation;
        this.isPrepared = isPrepared;
        this.isFinished = isFinished;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(int confirmation) {
        this.confirmation = confirmation;
    }

    public short getIsPrepared() {
        return isPrepared;
    }

    public void setIsPrepared(short isPrepared) {
        this.isPrepared = isPrepared;
    }

    public short getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(short isFinished) {
        this.isFinished = isFinished;
    }

    @XmlTransient
    public List<Suborder> getSuborderList() {
        return suborderList;
    }

    public void setSuborderList(List<Suborder> suborderList) {
        this.suborderList = suborderList;
    }

    public Address getAddressId() {
        return addressId;
    }

    public void setAddressId(Address addressId) {
        this.addressId = addressId;
    }

    public Store getStoreId() {
        return storeId;
    }

    public void setStoreId(Store storeId) {
        this.storeId = storeId;
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
        if (!(object instanceof OrderInfo)) {
            return false;
        }
        OrderInfo other = (OrderInfo) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "coffeeshop.entity.OrderInfo[ id=" + id + " ]";
    }

}

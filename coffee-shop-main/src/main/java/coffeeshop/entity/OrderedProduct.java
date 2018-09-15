package coffeeshop.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "ordered_product", catalog = "coffee_shop", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderedProduct.findAll", query = "SELECT o FROM OrderedProduct o")
    , @NamedQuery(name = "OrderedProduct.findByOrderId", query = "SELECT o FROM OrderedProduct o WHERE o.orderedProductPK.orderId = :orderId")
    , @NamedQuery(name = "OrderedProduct.findByProductId", query = "SELECT o FROM OrderedProduct o WHERE o.orderedProductPK.productId = :productId")
    , @NamedQuery(name = "OrderedProduct.findByQuantity", query = "SELECT o FROM OrderedProduct o WHERE o.quantity = :quantity")})
public class OrderedProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrderedProductPK orderedProductPK;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private short quantity;
    @JoinTable(name = "ordered_product_ingredient", joinColumns = {
        @JoinColumn(name = "order_has_product_order_id", referencedColumnName = "order_id", nullable = false)
        , @JoinColumn(name = "order_has_product_product_id", referencedColumnName = "product_id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "ingredient_id", referencedColumnName = "id", nullable = false)})
    @ManyToMany
    private List<Ingredient> ingredientList;
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private OrderInfo orderInfo;
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;

    public OrderedProduct() {
    }

    public OrderedProduct(OrderedProductPK orderedProductPK) {
        this.orderedProductPK = orderedProductPK;
    }

    public OrderedProduct(OrderedProductPK orderedProductPK, short quantity) {
        this.orderedProductPK = orderedProductPK;
        this.quantity = quantity;
    }

    public OrderedProduct(int orderId, int productId) {
        this.orderedProductPK = new OrderedProductPK(orderId, productId);
    }

    public OrderedProductPK getOrderedProductPK() {
        return orderedProductPK;
    }

    public void setOrderedProductPK(OrderedProductPK orderedProductPK) {
        this.orderedProductPK = orderedProductPK;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    @XmlTransient
    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderedProductPK != null ? orderedProductPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderedProduct)) {
            return false;
        }
        OrderedProduct other = (OrderedProduct) object;
        if ((this.orderedProductPK == null && other.orderedProductPK != null) || (this.orderedProductPK != null && !this.orderedProductPK.equals(other.orderedProductPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coffeeshop.entity.OrderedProduct[ orderedProductPK=" + orderedProductPK + " ]";
    }
    
}

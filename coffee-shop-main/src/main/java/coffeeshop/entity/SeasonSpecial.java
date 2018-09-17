package coffeeshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import javax.persistence.MapsId;

@Entity
@Table(name = "season_special")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "SeasonSpecial.findAll", query = "SELECT s FROM SeasonSpecial s"),
        @NamedQuery(name = "SeasonSpecial.findByProductId", query = "SELECT s FROM SeasonSpecial s WHERE s.productId = :productId")})
public class SeasonSpecial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    @OneToOne(optional = false)
    @NotNull
    @MapsId
    private Product product;

    public SeasonSpecial() {
    }

    public SeasonSpecial(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SeasonSpecial)) {
            return false;
        }
        SeasonSpecial other = (SeasonSpecial) object;
        return (this.productId != null || other.productId == null) && (this.productId == null || this.productId.equals(other.productId));
    }

    @Override
    public String toString() {
        return "coffeeshop.entity.SeasonSpecial[ productId=" + productId + " ]";
    }

}

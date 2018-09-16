package coffeeshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Nutrition.findAll", query = "SELECT n FROM Nutrition n"),
        @NamedQuery(name = "Nutrition.findById", query = "SELECT n FROM Nutrition n WHERE n.id = :id"),
        @NamedQuery(name = "Nutrition.findByCalories", query = "SELECT n FROM Nutrition n WHERE n.calories = :calories"),
        @NamedQuery(name = "Nutrition.findByFat", query = "SELECT n FROM Nutrition n WHERE n.fat = :fat"),
        @NamedQuery(name = "Nutrition.findByCarbon", query = "SELECT n FROM Nutrition n WHERE n.carbon = :carbon"),
        @NamedQuery(name = "Nutrition.findByFiber", query = "SELECT n FROM Nutrition n WHERE n.fiber = :fiber"),
        @NamedQuery(name = "Nutrition.findByProtein", query = "SELECT n FROM Nutrition n WHERE n.protein = :protein"),
        @NamedQuery(name = "Nutrition.findBySodium", query = "SELECT n FROM Nutrition n WHERE n.sodium = :sodium")})
public class Nutrition implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    private Integer calories;
    private Integer fat;
    private Integer carbon;
    private Integer fiber;
    private Integer protein;
    private Integer sodium;
    @OneToMany(mappedBy = "nutritionId")
    private List<Product> productList;

    public Nutrition() {
    }

    public Nutrition(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getFat() {
        return fat;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }

    public Integer getCarbon() {
        return carbon;
    }

    public void setCarbon(Integer carbon) {
        this.carbon = carbon;
    }

    public Integer getFiber() {
        return fiber;
    }

    public void setFiber(Integer fiber) {
        this.fiber = fiber;
    }

    public Integer getProtein() {
        return protein;
    }

    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    public Integer getSodium() {
        return sodium;
    }

    public void setSodium(Integer sodium) {
        this.sodium = sodium;
    }

    @XmlTransient
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
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
        if (!(object instanceof Nutrition)) {
            return false;
        }
        Nutrition other = (Nutrition) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "coffeeshop.entity.Nutrition[ id=" + id + " ]";
    }

}

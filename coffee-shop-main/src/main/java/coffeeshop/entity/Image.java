/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshop.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yinfeng
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i"),
    @NamedQuery(name = "Image.findByUuid", query = "SELECT i FROM Image i WHERE i.uuid = :uuid"),
    @NamedQuery(name = "Image.findByMediaType", query = "SELECT i FROM Image i WHERE i.mediaType = :mediaType")})
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Size(min = 36, max = 36)
    @Column(nullable = false, length = 36)
    private String uuid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "media_type", nullable = false, length = 45)
    private String mediaType;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(nullable = false)
    private byte[] content;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "imageUuid")
    private Product product;

    public Image() {
    }

    public Image(String uuid) {
        this.uuid = uuid;
    }

    public Image(String uuid, String mediaType, byte[] content) {
        this.uuid = uuid;
        this.mediaType = mediaType;
        this.content = content;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
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
        hash += (uuid != null ? uuid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Image)) {
            return false;
        }
        Image other = (Image) object;
        return !((this.uuid == null && other.uuid != null) || (this.uuid != null && !this.uuid.equals(other.uuid)));
    }

    @Override
    public String toString() {
        return "coffeeshop.entity.Image[ uuid=" + uuid + " ]";
    }
    
}

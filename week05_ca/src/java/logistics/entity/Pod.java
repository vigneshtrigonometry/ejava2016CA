/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vignesh
 */
@Entity
@Table(name = "pod")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pod.findAll", query = "SELECT p FROM Pod p")
    , @NamedQuery(name = "Pod.findByPodId", query = "SELECT p FROM Pod p WHERE p.podId = :podId")
    , @NamedQuery(name = "Pod.findByDeliveryDate", query = "SELECT p FROM Pod p WHERE p.deliveryDate = :deliveryDate")
    , @NamedQuery(name = "Pod.findByAckId", query = "SELECT p FROM Pod p WHERE p.ackId = :ackId")})
public class Pod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pod_id")
    private Integer podId;
    @Lob
    @Size(max = 65535)
    @Column(name = "note")
    private String note;
    @Lob
    @Column(name = "image")
    private byte[] image;
    @Basic(optional = false)
    @NotNull
    @Column(name = "delivery_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;
    @Size(max = 16)
    @Column(name = "ack_id")
    private String ackId;
    
    @JoinColumn(name = "pkg_id",referencedColumnName = "pkg_id")
    @OneToOne
    private Delivery pkg;

    public Pod() {
    }

    public Pod(Integer podId) {
        this.podId = podId;
    }

    public Pod(Integer podId, Date deliveryDate) {
        this.podId = podId;
        this.deliveryDate = deliveryDate;
    }

    public Integer getPodId() {
        return podId;
    }

    public void setPodId(Integer podId) {
        this.podId = podId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getAckId() {
        return ackId;
    }

    public void setAckId(String ackId) {
        this.ackId = ackId;
    }

    public Delivery getPkg() {
        return pkg;
    }

    public void setPkgId(Delivery pkg) {
        this.pkg = pkg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (podId != null ? podId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pod)) {
            return false;
        }
        Pod other = (Pod) object;
        if ((this.podId == null && other.podId != null) || (this.podId != null && !this.podId.equals(other.podId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logistics.entity.Pod[ podId=" + podId + " ]";
    }
    
}

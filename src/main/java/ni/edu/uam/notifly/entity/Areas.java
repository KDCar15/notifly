/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ni.edu.uam.notifly.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author charly
 */
@Entity
@Table(name = "gestion.areas")
@NamedQueries({
    @NamedQuery(name = "Areas.findAll", query = "SELECT a FROM Areas a"),
    @NamedQuery(name = "Areas.findByIdArea", query = "SELECT a FROM Areas a WHERE a.idArea = :idArea"),
    @NamedQuery(name = "Areas.findByNombreArea", query = "SELECT a FROM Areas a WHERE a.nombreArea = :nombreArea")})
public class Areas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_area")
    private Integer idArea;
    @Column(name = "nombre_area")
    private String nombreArea;
    @OneToMany(mappedBy = "idArea")
    private List<Notas> notasList;
    @JoinColumn(name = "id_contacto", referencedColumnName = "id_contacto")
    @ManyToOne
    private Contactos idContacto;

    public Areas() {
    }

    public Areas(Integer idArea) {
        this.idArea = idArea;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public List<Notas> getNotasList() {
        return notasList;
    }

    public void setNotasList(List<Notas> notasList) {
        this.notasList = notasList;
    }

    public Contactos getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(Contactos idContacto) {
        this.idContacto = idContacto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArea != null ? idArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Areas)) {
            return false;
        }
        Areas other = (Areas) object;
        if ((this.idArea == null && other.idArea != null) || (this.idArea != null && !this.idArea.equals(other.idArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ni.edu.uam.notifly.entity.Areas[ idArea=" + idArea + " ]";
    }
    
}

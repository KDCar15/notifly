/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ni.edu.uam.notifly.entity;

import java.io.Serializable;
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

/**
 *
 * @author charly
 */
@Entity
@Table(name = "gestion.tipos_nota")
@NamedQueries({
    @NamedQuery(name = "TiposNota.findAll", query = "SELECT t FROM TiposNota t"),
    @NamedQuery(name = "TiposNota.findByIdTiponota", query = "SELECT t FROM TiposNota t WHERE t.idTiponota = :idTiponota"),
    @NamedQuery(name = "TiposNota.findByNombreTipo", query = "SELECT t FROM TiposNota t WHERE t.nombreTipo = :nombreTipo")})
public class TiposNota implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tiponota")
    private Integer idTiponota;
    @Basic(optional = false)
    @Column(name = "nombre_tipo")
    private String nombreTipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTiponota")
    private List<Notas> notasList;

    public TiposNota() {
    }

    public TiposNota(Integer idTiponota) {
        this.idTiponota = idTiponota;
    }

    public TiposNota(Integer idTiponota, String nombreTipo) {
        this.idTiponota = idTiponota;
        this.nombreTipo = nombreTipo;
    }

    public Integer getIdTiponota() {
        return idTiponota;
    }

    public void setIdTiponota(Integer idTiponota) {
        this.idTiponota = idTiponota;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public List<Notas> getNotasList() {
        return notasList;
    }

    public void setNotasList(List<Notas> notasList) {
        this.notasList = notasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTiponota != null ? idTiponota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiposNota)) {
            return false;
        }
        TiposNota other = (TiposNota) object;
        if ((this.idTiponota == null && other.idTiponota != null) || (this.idTiponota != null && !this.idTiponota.equals(other.idTiponota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ni.edu.uam.notifly.entity.TiposNota[ idTiponota=" + idTiponota + " ]";
    }
    
}

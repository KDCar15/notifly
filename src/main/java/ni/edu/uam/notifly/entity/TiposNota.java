/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ni.edu.uam.notifly.entity;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author charly
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "gestion.tipos_nota")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "TiposNota.findAll", query = "SELECT t FROM TiposNota t"),
    @javax.persistence.NamedQuery(name = "TiposNota.findByIdTiponota", query = "SELECT t FROM TiposNota t WHERE t.idTiponota = :idTiponota"),
    @javax.persistence.NamedQuery(name = "TiposNota.findByNombreTipo", query = "SELECT t FROM TiposNota t WHERE t.nombreTipo = :nombreTipo")})
public class TiposNota implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "id_tiponota")
    private Integer idTiponota;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "nombre_tipo")
    private String nombreTipo;
    @javax.persistence.OneToMany(mappedBy = "idTiponota")
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
        return "ni.edu.uam.notifly.model.TiposNota[ idTiponota=" + idTiponota + " ]";
    }
    
}

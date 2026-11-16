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
@javax.persistence.Table(name = "gestion.clasificaciones")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Clasificaciones.findAll", query = "SELECT c FROM Clasificaciones c"),
    @javax.persistence.NamedQuery(name = "Clasificaciones.findByIdClasificacion", query = "SELECT c FROM Clasificaciones c WHERE c.idClasificacion = :idClasificacion"),
    @javax.persistence.NamedQuery(name = "Clasificaciones.findByNombreClasificacion", query = "SELECT c FROM Clasificaciones c WHERE c.nombreClasificacion = :nombreClasificacion")})
public class Clasificaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "id_clasificacion")
    private Integer idClasificacion;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "nombre_clasificacion")
    private String nombreClasificacion;
    @javax.persistence.OneToMany(mappedBy = "idClasificacion")
    private List<Notas> notasList;

    public Clasificaciones() {
    }

    public Clasificaciones(Integer idClasificacion) {
        this.idClasificacion = idClasificacion;
    }

    public Clasificaciones(Integer idClasificacion, String nombreClasificacion) {
        this.idClasificacion = idClasificacion;
        this.nombreClasificacion = nombreClasificacion;
    }

    public Integer getIdClasificacion() {
        return idClasificacion;
    }

    public void setIdClasificacion(Integer idClasificacion) {
        this.idClasificacion = idClasificacion;
    }

    public String getNombreClasificacion() {
        return nombreClasificacion;
    }

    public void setNombreClasificacion(String nombreClasificacion) {
        this.nombreClasificacion = nombreClasificacion;
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
        hash += (idClasificacion != null ? idClasificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clasificaciones)) {
            return false;
        }
        Clasificaciones other = (Clasificaciones) object;
        if ((this.idClasificacion == null && other.idClasificacion != null) || (this.idClasificacion != null && !this.idClasificacion.equals(other.idClasificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ni.edu.uam.notifly.model.Clasificaciones[ idClasificacion=" + idClasificacion + " ]";
    }
    
}

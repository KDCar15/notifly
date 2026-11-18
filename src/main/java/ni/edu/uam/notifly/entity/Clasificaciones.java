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
@Table(name = "gestion.clasificaciones")
@NamedQueries({
    @NamedQuery(name = "Clasificaciones.findAll", query = "SELECT c FROM Clasificaciones c"),
    @NamedQuery(name = "Clasificaciones.findByIdClasificacion", query = "SELECT c FROM Clasificaciones c WHERE c.idClasificacion = :idClasificacion"),
    @NamedQuery(name = "Clasificaciones.findByNombreClasificacion", query = "SELECT c FROM Clasificaciones c WHERE c.nombreClasificacion = :nombreClasificacion")})
public class Clasificaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_clasificacion")
    private Integer idClasificacion;
    @Basic(optional = false)
    @Column(name = "nombre_clasificacion")
    private String nombreClasificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClasificacion")
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
        return "ni.edu.uam.notifly.entity.Clasificaciones[ idClasificacion=" + idClasificacion + " ]";
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ni.edu.uam.notifly.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author charly
 */
@Entity
@Table(name = "historial_notas", schema = "auditoria")
@NamedQueries({
    @NamedQuery(name = "HistorialNotas.findAll", query = "SELECT h FROM HistorialNotas h"),
    @NamedQuery(name = "HistorialNotas.findByIdHistorial", query = "SELECT h FROM HistorialNotas h WHERE h.idHistorial = :idHistorial"),
    @NamedQuery(name = "HistorialNotas.findByIdNota", query = "SELECT h FROM HistorialNotas h WHERE h.idNota = :idNota"),
    @NamedQuery(name = "HistorialNotas.findByFechaAcceso", query = "SELECT h FROM HistorialNotas h WHERE h.fechaAcceso = :fechaAcceso")})
public class HistorialNotas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_historial")
    private Integer idHistorial;
    @Basic(optional = false)
    @Column(name = "id_nota")
    private int idNota;
    @Basic(optional = false)
    @Column(name = "fecha_acceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAcceso;

    public HistorialNotas() {
    }

    public HistorialNotas(Integer idHistorial) {
        this.idHistorial = idHistorial;
    }

    public HistorialNotas(Integer idHistorial, int idNota, Date fechaAcceso) {
        this.idHistorial = idHistorial;
        this.idNota = idNota;
        this.fechaAcceso = fechaAcceso;
    }

    public Integer getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Integer idHistorial) {
        this.idHistorial = idHistorial;
    }

    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public Date getFechaAcceso() {
        return fechaAcceso;
    }

    public void setFechaAcceso(Date fechaAcceso) {
        this.fechaAcceso = fechaAcceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHistorial != null ? idHistorial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistorialNotas)) {
            return false;
        }
        HistorialNotas other = (HistorialNotas) object;
        if ((this.idHistorial == null && other.idHistorial != null) || (this.idHistorial != null && !this.idHistorial.equals(other.idHistorial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ni.edu.uam.notifly.entity.HistorialNotas[ idHistorial=" + idHistorial + " ]";
    }
    
}

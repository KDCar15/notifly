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
@Table(name = "auditoria.seguimiento")
@NamedQueries({
    @NamedQuery(name = "Seguimiento.findAll", query = "SELECT s FROM Seguimiento s"),
    @NamedQuery(name = "Seguimiento.findByIdSeguimiento", query = "SELECT s FROM Seguimiento s WHERE s.idSeguimiento = :idSeguimiento"),
    @NamedQuery(name = "Seguimiento.findByTabla", query = "SELECT s FROM Seguimiento s WHERE s.tabla = :tabla"),
    @NamedQuery(name = "Seguimiento.findByIdRegistro", query = "SELECT s FROM Seguimiento s WHERE s.idRegistro = :idRegistro"),
    @NamedQuery(name = "Seguimiento.findByColumna", query = "SELECT s FROM Seguimiento s WHERE s.columna = :columna"),
    @NamedQuery(name = "Seguimiento.findByFechahora", query = "SELECT s FROM Seguimiento s WHERE s.fechahora = :fechahora"),
    @NamedQuery(name = "Seguimiento.findByIdAccion", query = "SELECT s FROM Seguimiento s WHERE s.idAccion = :idAccion")})
public class Seguimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seguimiento")
    private Integer idSeguimiento;
    @Basic(optional = false)
    @Column(name = "tabla")
    private String tabla;
    @Basic(optional = false)
    @Column(name = "id_registro")
    private int idRegistro;
    @Column(name = "columna")
    private String columna;
    @Column(name = "fechahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Column(name = "id_accion")
    private Integer idAccion;

    public Seguimiento() {
    }

    public Seguimiento(Integer idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public Seguimiento(Integer idSeguimiento, String tabla, int idRegistro) {
        this.idSeguimiento = idSeguimiento;
        this.tabla = tabla;
        this.idRegistro = idRegistro;
    }

    public Integer getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(Integer idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public Integer getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(Integer idAccion) {
        this.idAccion = idAccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSeguimiento != null ? idSeguimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seguimiento)) {
            return false;
        }
        Seguimiento other = (Seguimiento) object;
        if ((this.idSeguimiento == null && other.idSeguimiento != null) || (this.idSeguimiento != null && !this.idSeguimiento.equals(other.idSeguimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ni.edu.uam.notifly.entity.Seguimiento[ idSeguimiento=" + idSeguimiento + " ]";
    }
    
}

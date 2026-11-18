/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ni.edu.uam.notifly.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author charly
 */
@Entity
@Table(name = "gestion.contactos")
@NamedQueries({
    @NamedQuery(name = "Contactos.findAll", query = "SELECT c FROM Contactos c"),
    @NamedQuery(name = "Contactos.findByIdContacto", query = "SELECT c FROM Contactos c WHERE c.idContacto = :idContacto"),
    @NamedQuery(name = "Contactos.findByNombresapellidos", query = "SELECT c FROM Contactos c WHERE c.nombresapellidos = :nombresapellidos"),
    @NamedQuery(name = "Contactos.findByOcupacion", query = "SELECT c FROM Contactos c WHERE c.ocupacion = :ocupacion"),
    @NamedQuery(name = "Contactos.findByCelular", query = "SELECT c FROM Contactos c WHERE c.celular = :celular"),
    @NamedQuery(name = "Contactos.findByCorreo", query = "SELECT c FROM Contactos c WHERE c.correo = :correo")})
public class Contactos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contacto")
    private Integer idContacto;
    @Basic(optional = false)
    @Column(name = "nombresapellidos")
    private String nombresapellidos;
    @Basic(optional = false)
    @Column(name = "ocupacion")
    private String ocupacion;
    @Column(name = "celular")
    private String celular;
    @Column(name = "correo")
    private String correo;
    @JoinColumn(name = "id_area", referencedColumnName = "id_area")
    @ManyToOne
    private Areas idArea;

    public Contactos() {
    }

    public Contactos(Integer idContacto) {
        this.idContacto = idContacto;
    }

    public Contactos(Integer idContacto, String nombresapellidos, String ocupacion) {
        this.idContacto = idContacto;
        this.nombresapellidos = nombresapellidos;
        this.ocupacion = ocupacion;
    }

    public Integer getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(Integer idContacto) {
        this.idContacto = idContacto;
    }

    public String getNombresapellidos() {
        return nombresapellidos;
    }

    public void setNombresapellidos(String nombresapellidos) {
        this.nombresapellidos = nombresapellidos;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Areas getIdArea() {
        return idArea;
    }

    public void setIdArea(Areas idArea) {
        this.idArea = idArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContacto != null ? idContacto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contactos)) {
            return false;
        }
        Contactos other = (Contactos) object;
        if ((this.idContacto == null && other.idContacto != null) || (this.idContacto != null && !this.idContacto.equals(other.idContacto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ni.edu.uam.notifly.entity.Contactos[ idContacto=" + idContacto + " ]";
    }
    
}

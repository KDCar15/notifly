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
@javax.persistence.Table(name = "contactos")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Contactos.findAll", query = "SELECT c FROM Contactos c"),
    @javax.persistence.NamedQuery(name = "Contactos.findByIdContacto", query = "SELECT c FROM Contactos c WHERE c.idContacto = :idContacto"),
    @javax.persistence.NamedQuery(name = "Contactos.findByNombres", query = "SELECT c FROM Contactos c WHERE c.nombres = :nombres"),
    @javax.persistence.NamedQuery(name = "Contactos.findByApellidos", query = "SELECT c FROM Contactos c WHERE c.apellidos = :apellidos"),
    @javax.persistence.NamedQuery(name = "Contactos.findByOcupacion", query = "SELECT c FROM Contactos c WHERE c.ocupacion = :ocupacion"),
    @javax.persistence.NamedQuery(name = "Contactos.findByCelular", query = "SELECT c FROM Contactos c WHERE c.celular = :celular"),
    @javax.persistence.NamedQuery(name = "Contactos.findByCorreo", query = "SELECT c FROM Contactos c WHERE c.correo = :correo")})
public class Contactos implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "id_contacto")
    private Integer idContacto;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "nombres")
    private String nombres;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "apellidos")
    private String apellidos;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ocupacion")
    private String ocupacion;
    @javax.persistence.Column(name = "celular")
    private String celular;
    @javax.persistence.Column(name = "correo")
    private String correo;
    @javax.persistence.OneToMany(mappedBy = "idContacto")
    private List<Areas> areasList;

    public Contactos() {
    }

    public Contactos(Integer idContacto) {
        this.idContacto = idContacto;
    }

    public Contactos(Integer idContacto, String nombres, String apellidos, String ocupacion) {
        this.idContacto = idContacto;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.ocupacion = ocupacion;
    }

    public Integer getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(Integer idContacto) {
        this.idContacto = idContacto;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public List<Areas> getAreasList() {
        return areasList;
    }

    public void setAreasList(List<Areas> areasList) {
        this.areasList = areasList;
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
        return "ni.edu.uam.notifly.model.Contactos[ idContacto=" + idContacto + " ]";
    }
    
}

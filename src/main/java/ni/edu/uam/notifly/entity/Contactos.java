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
@Table(name = "gestion.contactos")
@NamedQueries({
    @NamedQuery(name = "Contactos.findAll", query = "SELECT c FROM Contactos c"),
    @NamedQuery(name = "Contactos.findByIdContacto", query = "SELECT c FROM Contactos c WHERE c.idContacto = :idContacto"),
    @NamedQuery(name = "Contactos.findByApellidos", query = "SELECT c FROM Contactos c WHERE c.apellidos = :apellidos"),
    @NamedQuery(name = "Contactos.findByCelular", query = "SELECT c FROM Contactos c WHERE c.celular = :celular"),
    @NamedQuery(name = "Contactos.findByCorreo", query = "SELECT c FROM Contactos c WHERE c.correo = :correo"),
    @NamedQuery(name = "Contactos.findByNombres", query = "SELECT c FROM Contactos c WHERE c.nombres = :nombres"),
    @NamedQuery(name = "Contactos.findByOcupacion", query = "SELECT c FROM Contactos c WHERE c.ocupacion = :ocupacion")})
public class Contactos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contacto")
    private Integer idContacto;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "celular")
    private String celular;
    @Column(name = "correo")
    private String correo;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "ocupacion")
    private String ocupacion;
    @OneToMany(mappedBy = "idContacto")
    private List<Areas> areasList;

    public Contactos() {
    }

    public Contactos(Integer idContacto) {
        this.idContacto = idContacto;
    }

    public Integer getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(Integer idContacto) {
        this.idContacto = idContacto;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
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
        return "ni.edu.uam.notifly.entity.Contactos[ idContacto=" + idContacto + " ]";
    }
    
}

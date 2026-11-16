/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ni.edu.uam.notifly.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author charly
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "gestion.notas")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Notas.findAll", query = "SELECT n FROM Notas n"),
    @javax.persistence.NamedQuery(name = "Notas.findByIdNota", query = "SELECT n FROM Notas n WHERE n.idNota = :idNota"),
    @javax.persistence.NamedQuery(name = "Notas.findByNombres", query = "SELECT n FROM Notas n WHERE n.nombres = :nombres"),
    @javax.persistence.NamedQuery(name = "Notas.findByApellidos", query = "SELECT n FROM Notas n WHERE n.apellidos = :apellidos"),
    @javax.persistence.NamedQuery(name = "Notas.findByCorreo", query = "SELECT n FROM Notas n WHERE n.correo = :correo"),
    @javax.persistence.NamedQuery(name = "Notas.findByCelular", query = "SELECT n FROM Notas n WHERE n.celular = :celular"),
    @javax.persistence.NamedQuery(name = "Notas.findByFechahora", query = "SELECT n FROM Notas n WHERE n.fechahora = :fechahora"),
    @javax.persistence.NamedQuery(name = "Notas.findByDescripcion", query = "SELECT n FROM Notas n WHERE n.descripcion = :descripcion")})
public class Notas implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "id_nota")
    private Integer idNota;
    @javax.persistence.Column(name = "nombres")
    private String nombres;
    @javax.persistence.Column(name = "apellidos")
    private String apellidos;
    @javax.persistence.Column(name = "correo")
    private String correo;
    @javax.persistence.Column(name = "celular")
    private String celular;
    @javax.persistence.Column(name = "fechahora")
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    private Date fechahora;
    @javax.persistence.Column(name = "descripcion")
    private String descripcion;
    @javax.persistence.JoinColumn(name = "id_area", referencedColumnName = "id_area")
    @javax.persistence.ManyToOne
    private Areas idArea;
    @javax.persistence.JoinColumn(name = "id_clasificacion", referencedColumnName = "id_clasificacion")
    @javax.persistence.ManyToOne
    private Clasificaciones idClasificacion;
    @javax.persistence.JoinColumn(name = "id_estadonota", referencedColumnName = "id_estadonota")
    @javax.persistence.ManyToOne
    private EstadosNota idEstadonota;
    @javax.persistence.JoinColumn(name = "id_tiponota", referencedColumnName = "id_tiponota")
    @javax.persistence.ManyToOne
    private TiposNota idTiponota;

    public Notas() {
    }

    public Notas(Integer idNota) {
        this.idNota = idNota;
    }

    public Integer getIdNota() {
        return idNota;
    }

    public void setIdNota(Integer idNota) {
        this.idNota = idNota;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Areas getIdArea() {
        return idArea;
    }

    public void setIdArea(Areas idArea) {
        this.idArea = idArea;
    }

    public Clasificaciones getIdClasificacion() {
        return idClasificacion;
    }

    public void setIdClasificacion(Clasificaciones idClasificacion) {
        this.idClasificacion = idClasificacion;
    }

    public EstadosNota getIdEstadonota() {
        return idEstadonota;
    }

    public void setIdEstadonota(EstadosNota idEstadonota) {
        this.idEstadonota = idEstadonota;
    }

    public TiposNota getIdTiponota() {
        return idTiponota;
    }

    public void setIdTiponota(TiposNota idTiponota) {
        this.idTiponota = idTiponota;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNota != null ? idNota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notas)) {
            return false;
        }
        Notas other = (Notas) object;
        if ((this.idNota == null && other.idNota != null) || (this.idNota != null && !this.idNota.equals(other.idNota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ni.edu.uam.notifly.model.Notas[ idNota=" + idNota + " ]";
    }
    
}

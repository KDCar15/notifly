/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ni.edu.uam.notifly.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author charly
 */
@Entity
@Table(name = "gestion.notas")
@NamedQueries({
    @NamedQuery(name = "Notas.findAll", query = "SELECT n FROM Notas n"),
    @NamedQuery(name = "Notas.findByIdNota", query = "SELECT n FROM Notas n WHERE n.idNota = :idNota"),
    @NamedQuery(name = "Notas.findByNombresapellidos", query = "SELECT n FROM Notas n WHERE n.nombresapellidos = :nombresapellidos"),
    @NamedQuery(name = "Notas.findByCorreo", query = "SELECT n FROM Notas n WHERE n.correo = :correo"),
    @NamedQuery(name = "Notas.findByCelular", query = "SELECT n FROM Notas n WHERE n.celular = :celular"),
    @NamedQuery(name = "Notas.findByFechahora", query = "SELECT n FROM Notas n WHERE n.fechahora = :fechahora"),
    @NamedQuery(name = "Notas.findByDescripcion", query = "SELECT n FROM Notas n WHERE n.descripcion = :descripcion")})
public class Notas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_nota")
    private Integer idNota;
    @Basic(optional = false)
    @Column(name = "nombresapellidos")
    private String nombresapellidos;
    @Basic(optional = false)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "celular")
    private String celular;
    @Basic(optional = false)
    @Column(name = "fechahora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNota")
    private List<Checklists> checklistsList;
    @JoinColumn(name = "id_area", referencedColumnName = "id_area")
    @ManyToOne
    private Areas idArea;
    @JoinColumn(name = "id_clasificacion", referencedColumnName = "id_clasificacion")
    @ManyToOne(optional = false)
    private Clasificaciones idClasificacion;
    @JoinColumn(name = "id_estadonota", referencedColumnName = "id_estadonota")
    @ManyToOne(optional = false)
    private EstadosNota idEstadonota;
    @JoinColumn(name = "id_tiponota", referencedColumnName = "id_tiponota")
    @ManyToOne(optional = false)
    private TiposNota idTiponota;

    public Notas() {
    }

    public Notas(Integer idNota) {
        this.idNota = idNota;
    }

    public Notas(Integer idNota, String nombresapellidos, String correo, String celular, Date fechahora, String descripcion) {
        this.idNota = idNota;
        this.nombresapellidos = nombresapellidos;
        this.correo = correo;
        this.celular = celular;
        this.fechahora = fechahora;
        this.descripcion = descripcion;
    }

    public Integer getIdNota() {
        return idNota;
    }

    public void setIdNota(Integer idNota) {
        this.idNota = idNota;
    }

    public String getNombresapellidos() {
        return nombresapellidos;
    }

    public void setNombresapellidos(String nombresapellidos) {
        this.nombresapellidos = nombresapellidos;
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

    public List<Checklists> getChecklistsList() {
        return checklistsList;
    }

    public void setChecklistsList(List<Checklists> checklistsList) {
        this.checklistsList = checklistsList;
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
        return "ni.edu.uam.notifly.entity.Notas[ idNota=" + idNota + " ]";
    }
    
}

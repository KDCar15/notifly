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
@Table(name = "gestion.estados_nota")
@NamedQueries({
    @NamedQuery(name = "EstadosNota.findAll", query = "SELECT e FROM EstadosNota e"),
    @NamedQuery(name = "EstadosNota.findByIdEstadonota", query = "SELECT e FROM EstadosNota e WHERE e.idEstadonota = :idEstadonota"),
    @NamedQuery(name = "EstadosNota.findByNombreEstado", query = "SELECT e FROM EstadosNota e WHERE e.nombreEstado = :nombreEstado")})
public class EstadosNota implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estadonota")
    private Integer idEstadonota;
    @Column(name = "nombre_estado")
    private String nombreEstado;
    @OneToMany(mappedBy = "idEstadonota")
    private List<Notas> notasList;

    public EstadosNota() {
    }

    public EstadosNota(Integer idEstadonota) {
        this.idEstadonota = idEstadonota;
    }

    public Integer getIdEstadonota() {
        return idEstadonota;
    }

    public void setIdEstadonota(Integer idEstadonota) {
        this.idEstadonota = idEstadonota;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
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
        hash += (idEstadonota != null ? idEstadonota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadosNota)) {
            return false;
        }
        EstadosNota other = (EstadosNota) object;
        if ((this.idEstadonota == null && other.idEstadonota != null) || (this.idEstadonota != null && !this.idEstadonota.equals(other.idEstadonota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ni.edu.uam.notifly.entity.EstadosNota[ idEstadonota=" + idEstadonota + " ]";
    }
    
}

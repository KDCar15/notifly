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
@Table(name = "gestion.checklists")
@NamedQueries({
    @NamedQuery(name = "Checklists.findAll", query = "SELECT c FROM Checklists c"),
    @NamedQuery(name = "Checklists.findByIdChecklist", query = "SELECT c FROM Checklists c WHERE c.idChecklist = :idChecklist"),
    @NamedQuery(name = "Checklists.findByNombreChecklist", query = "SELECT c FROM Checklists c WHERE c.nombreChecklist = :nombreChecklist"),
    @NamedQuery(name = "Checklists.findByCompletado", query = "SELECT c FROM Checklists c WHERE c.completado = :completado")})
public class Checklists implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_checklist")
    private Integer idChecklist;
    @Basic(optional = false)
    @Column(name = "nombre_checklist")
    private String nombreChecklist;
    @Basic(optional = false)
    @Column(name = "completado")
    private boolean completado;
    @JoinColumn(name = "id_nota", referencedColumnName = "id_nota")
    @ManyToOne(optional = false)
    private Notas idNota;

    public Checklists() {
    }

    public Checklists(Integer idChecklist) {
        this.idChecklist = idChecklist;
    }

    public Checklists(Integer idChecklist, String nombreChecklist, boolean completado) {
        this.idChecklist = idChecklist;
        this.nombreChecklist = nombreChecklist;
        this.completado = completado;
    }

    public Integer getIdChecklist() {
        return idChecklist;
    }

    public void setIdChecklist(Integer idChecklist) {
        this.idChecklist = idChecklist;
    }

    public String getNombreChecklist() {
        return nombreChecklist;
    }

    public void setNombreChecklist(String nombreChecklist) {
        this.nombreChecklist = nombreChecklist;
    }

    public boolean getCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public Notas getIdNota() {
        return idNota;
    }

    public void setIdNota(Notas idNota) {
        this.idNota = idNota;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idChecklist != null ? idChecklist.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Checklists)) {
            return false;
        }
        Checklists other = (Checklists) object;
        if ((this.idChecklist == null && other.idChecklist != null) || (this.idChecklist != null && !this.idChecklist.equals(other.idChecklist))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ni.edu.uam.notifly.entity.Checklists[ idChecklist=" + idChecklist + " ]";
    }
    
}

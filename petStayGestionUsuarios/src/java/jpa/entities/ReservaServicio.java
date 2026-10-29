/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tommy
 */
@Entity
@Table(name = "reserva_servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReservaServicio.findAll", query = "SELECT r FROM ReservaServicio r"),
    @NamedQuery(name = "ReservaServicio.findByIdReservaServicio", query = "SELECT r FROM ReservaServicio r WHERE r.idReservaServicio = :idReservaServicio"),
    @NamedQuery(name = "ReservaServicio.findByCantidad", query = "SELECT r FROM ReservaServicio r WHERE r.cantidad = :cantidad"),
    @NamedQuery(name = "ReservaServicio.findByPrecioUnitario", query = "SELECT r FROM ReservaServicio r WHERE r.precioUnitario = :precioUnitario"),
    @NamedQuery(name = "ReservaServicio.findBySubtotal", query = "SELECT r FROM ReservaServicio r WHERE r.subtotal = :subtotal")})
public class ReservaServicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id_Reserva_Servicio")
    private Integer idReservaServicio;
    @Column(name = "Cantidad")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Precio_Unitario")
    private BigDecimal precioUnitario;
    @Column(name = "Subtotal")
    private BigDecimal subtotal;
    @JoinColumn(name = "Id_Reserva", referencedColumnName = "Id_Reserva")
    @ManyToOne(optional = false)
    private Reserva idReserva;
    @JoinColumn(name = "Id_Servicio", referencedColumnName = "Id_Servicio")
    @ManyToOne(optional = false)
    private Servicio idServicio;

    public ReservaServicio() {
    }

    public ReservaServicio(Integer idReservaServicio) {
        this.idReservaServicio = idReservaServicio;
    }

    public Integer getIdReservaServicio() {
        return idReservaServicio;
    }

    public void setIdReservaServicio(Integer idReservaServicio) {
        this.idReservaServicio = idReservaServicio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Reserva getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Reserva idReserva) {
        this.idReserva = idReserva;
    }

    public Servicio getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Servicio idServicio) {
        this.idServicio = idServicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReservaServicio != null ? idReservaServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservaServicio)) {
            return false;
        }
        ReservaServicio other = (ReservaServicio) object;
        if ((this.idReservaServicio == null && other.idReservaServicio != null) || (this.idReservaServicio != null && !this.idReservaServicio.equals(other.idReservaServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.ReservaServicio[ idReservaServicio=" + idReservaServicio + " ]";
    }
    
}

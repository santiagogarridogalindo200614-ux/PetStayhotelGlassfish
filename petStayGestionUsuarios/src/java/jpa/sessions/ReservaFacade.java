/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.sessions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entities.Reserva;
import java.util.Date;
import javax.persistence.TemporalType;
import jpa.entities.Habitacion;

/**
 *
 * @author tommy
 */
@Stateless
public class ReservaFacade extends AbstractFacade<Reserva> {

    @PersistenceContext(unitName = "petStayGestionUsuariosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservaFacade() {
        super(Reserva.class);
    }
      /**
     * Cuenta las reservas de una habitacion cuyas fechas se cruzan con [ingreso, salida).
     * Con < y > estrictos, una reserva que termina el dia 30 NO choca con otra que empieza el 30.
     * Las reservas canceladas no cuentan como ocupadas.
     */
    public long contarCruces(Habitacion hab, Date ingreso, Date salida) {
        return getEntityManager().createQuery(
                "SELECT COUNT(r) FROM Reserva r WHERE r.idHabitacion = :hab "
                + "AND r.fechaIngreso < :salida AND r.fechaSalida > :ingreso "
                + "AND (r.estado IS NULL OR r.estado <> 'Cancelada')", Long.class)
                .setParameter("hab", hab)
                .setParameter("salida", salida, TemporalType.DATE)
                .setParameter("ingreso", ingreso, TemporalType.DATE)
                .getSingleResult();
    }  
}

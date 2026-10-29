/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.sessions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entities.ReservaServicio;

/**
 *
 * @author tommy
 */
@Stateless
public class ReservaServicioFacade extends AbstractFacade<ReservaServicio> {

    @PersistenceContext(unitName = "petStayGestionUsuariosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservaServicioFacade() {
        super(ReservaServicio.class);
    }
    
}

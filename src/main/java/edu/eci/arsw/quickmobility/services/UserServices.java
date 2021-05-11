package edu.eci.arsw.quickmobility.services;

import edu.eci.arsw.quickmobility.model.Usuario;
import edu.eci.arsw.quickmobility.persistence.ImplPersistencia;
import edu.eci.arsw.quickmobility.persistence.QuickMobilityException;
import edu.eci.arsw.quickmobility.persistence.QuickmobilityPersistence;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServices {
    @Autowired
    QuickmobilityPersistence quickmobilityPersistence;
    public Usuario getUserByUsername(String username) throws QuickMobilityException {
        return quickmobilityPersistence.getUserByUsername(username);
    }
    
    public Usuario getBasicInfoUser(String username) throws QuickMobilityException {
    	Usuario user = quickmobilityPersistence.getUserByUsername(username);
        // not show vars
        user.password = null;
        user.userId = null;
        user.carros = null;
        user.viajesConductor =null;
        user.viajesPasajero =null;
        return user;
    }
}

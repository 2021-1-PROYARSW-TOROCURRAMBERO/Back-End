package edu.eci.arsw.quickmobility.services;

import edu.eci.arsw.quickmobility.model.Usuario;
import edu.eci.arsw.quickmobility.persistence.QuickMobilityException;
import edu.eci.arsw.quickmobility.persistence.QuickMobilityPersistence;
import edu.eci.arsw.quickmobility.persistence.ImplPersistencia;

import org.springframework.beans.factory.annotation.Autowired;

public class UserServices {
    @Autowired
    QuickMobilityPersistence quickmobilityPersistence;
    public Usuario getUserByUsername(String username) throws QuickMobilityException {
        return quickmobilityPersistence.getUserByUsername(username);
    }
}

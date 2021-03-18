package edu.eci.arsw.quickmobility.services;


import edu.eci.arsw.quickmobility.model.DetallesUsuario;
import edu.eci.arsw.quickmobility.model.Usuario;
import edu.eci.arsw.quickmobility.persistence.ImplPersistencia;
import edu.eci.arsw.quickmobility.persistence.QuickMobilityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServices implements UserDetailsService {

    @Autowired
    private ImplPersistencia uwp;

    public void addUser(Usuario usuario) {

        uwp.saveUser(usuario);
    }

    @Override
    public DetallesUsuario loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = null;
        System.out.println(username+"    Servicess");
        try {
            usuario = uwp.getUserByUsername(username);

        } catch (QuickMobilityException e) {
            throw new UsernameNotFoundException("User not found");
        }
        return new DetallesUsuario(usuario);

    }
}


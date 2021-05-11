package edu.eci.arsw.quickmobility.services;


import edu.eci.arsw.quickmobility.model.DetallesUsuario;
import edu.eci.arsw.quickmobility.model.Usuario;
import edu.eci.arsw.quickmobility.persistence.ImplPersistencia;
import edu.eci.arsw.quickmobility.persistence.QuickMobilityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServices extends UserServices{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    public void addUser(Usuario usuario) {

        quickmobilityPersistence.saveUser(usuario);
    }



    public boolean login(String username,String password) throws QuickMobilityException {
        Usuario user = quickmobilityPersistence.getUserByUsername(username);
        return bCryptPasswordEncoder.matches(password,user.password);

    }


}


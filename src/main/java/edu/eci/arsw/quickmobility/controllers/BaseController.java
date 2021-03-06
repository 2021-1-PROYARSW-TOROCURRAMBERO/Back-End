package edu.eci.arsw.quickmobility.controllers;

import edu.eci.arsw.quickmobility.model.Usuario;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(origins = "*", methods= {RequestMethod.OPTIONS})
public class BaseController {
    public Usuario getCurrentUser(@AuthenticationPrincipal Usuario user) {
        return user;
    }
}

package edu.eci.arsw.quickmobility.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import edu.eci.arsw.quickmobility.model.Usuario;

public class BaseController {
    public Usuario getCurrentUser(@AuthenticationPrincipal Usuario user) {
        return user;
    }
}

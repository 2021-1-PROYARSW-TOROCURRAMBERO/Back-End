package edu.eci.arsw.quickmobility.controllers;

import edu.eci.arsw.quickmobility.model.TokenUser;
import edu.eci.arsw.quickmobility.model.Usuario;
import edu.eci.arsw.quickmobility.persistence.QuickMobilityException;
import edu.eci.arsw.quickmobility.services.QuickMobilityServices;
import edu.eci.arsw.quickmobility.services.AuthServices;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthQuickMobilityController extends BaseController{
    @Autowired
    private AuthServices authServices;


    @RequestMapping(value="/login",method=RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody TokenUser tokenUser){
        try {
            if(authServices.login(tokenUser.getUsername(),tokenUser.getPassword())){
                String token = getJWTToken(tokenUser.getUsername());
                tokenUser.setToken(token);
                tokenUser.setPassword(null);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (QuickMobilityException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(tokenUser,HttpStatus.OK);
    }

    @RequestMapping(value = "/loggedUser",method = RequestMethod.GET)
    public ResponseEntity<?> isLogged(@RequestBody String username) throws QuickMobilityException {
        Usuario user = authServices.getUserByUsername(username);
        return new ResponseEntity<>(getCurrentUser(user),HttpStatus.OK);

    }

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody Usuario usuario){

        authServices.addUser(usuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String getJWTToken(String username) {
        String secretKey = "quickmobilityAuth";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

}

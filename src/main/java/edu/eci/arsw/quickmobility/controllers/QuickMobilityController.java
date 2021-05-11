package edu.eci.arsw.quickmobility.controllers;


import edu.eci.arsw.quickmobility.model.Carro;
import edu.eci.arsw.quickmobility.model.Barrio;
import edu.eci.arsw.quickmobility.model.Usuario;
import edu.eci.arsw.quickmobility.persistence.QuickMobilityException;
import edu.eci.arsw.quickmobility.services.QuickMobilityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("/quickmobility")
@CrossOrigin(origins = "*")
public class QuickMobilityController extends BaseController{
    @Autowired
    QuickMobilityServices quickmobilityServices = null;


    @RequestMapping("/helloworld")
    public String helloWorld(){
        return quickmobilityServices.helloWorld();
    }

    @RequestMapping(value ="/cars/{username}",method = RequestMethod.GET)
    public ResponseEntity<?> getCarros(@PathVariable String username){
        try{
            Collection<Carro> carrosCarroCollection = quickmobilityServices.getCarros(username);
            return new ResponseEntity<>(carrosCarroCollection, HttpStatus.ACCEPTED);
        }
        catch (Exception e) {
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE,null,e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value="/cars/add/{username}", method= RequestMethod.POST)
    public ResponseEntity<?> addCarroUsuario(@RequestBody Carro carro,@PathVariable String username){
        try{
        	quickmobilityServices.addCarroUsuario(username,carro);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/cars/update/{username}",method = RequestMethod.PUT)
    public ResponseEntity<?> updateCarro(@RequestBody Carro carro,@PathVariable String username){
        try{
            Usuario usuario = getCurrentUser(quickmobilityServices.getUserByUsername(username));
            quickmobilityServices.updateCarro(carro,usuario);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value ="/neighborhood",method = RequestMethod.GET)
    public ResponseEntity<?> getBarrios(){
        try{
            List<Barrio> barrioCollection = quickmobilityServices.getBarrios();
            return new ResponseEntity<>(barrioCollection,HttpStatus.ACCEPTED);

        } catch (Exception e){
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value="/neighborhood", method=RequestMethod.POST)
    public ResponseEntity<?> addBarrio(@RequestBody Barrio barrio){
        try{
            System.out.println("w");
            quickmobilityServices.addBarrio(barrio);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/score/{nameConductor}/{namePasajero}/{calificacion}", method=RequestMethod.POST)
    public ResponseEntity<?> addCalificacion(@PathVariable String nameConductor,@PathVariable String namePasajero,@PathVariable double calificacion){
        try{
        	quickmobilityServices.addCalificacion(nameConductor,namePasajero,calificacion);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/userStatus/{username}",method = RequestMethod.GET)
    public ResponseEntity<?> getUserState(@PathVariable String username){
        try {
            String state = quickmobilityServices.getUserStatus(username);
            return new ResponseEntity<>(state,HttpStatus.OK);
        } catch (QuickMobilityException e) {
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value="/score/average/{username}/{type}",method=RequestMethod.GET)
    public ResponseEntity<?> getAverage(@PathVariable String username,@PathVariable String type){
        try {
            double average = quickmobilityServices.getAverage(username,type);
            return new ResponseEntity<>(average,HttpStatus.OK);
        } catch (QuickMobilityException e) {
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/user/update",method=RequestMethod.PUT)
    public ResponseEntity<?> getAverage(@RequestBody Usuario usuario){
        try {
        	quickmobilityServices.updateUserBasicInfo(usuario);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (QuickMobilityException e) {
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/travel/driver/{usernameDriver}",method = RequestMethod.GET)
    public ResponseEntity<?> getTravelDriverByUsername(@PathVariable String usernameDriver){
        try {
            return new ResponseEntity<>(quickmobilityServices.getTravelDriver(usernameDriver),HttpStatus.OK);
        } catch (QuickMobilityException e) {
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/travel/passenger/{usernamePassenger}")
    public ResponseEntity<?> getTravelPassengerByUsername(@PathVariable String usernamePassenger){
        try {
            return new ResponseEntity<>(quickmobilityServices.getTravelPassenger(usernamePassenger),HttpStatus.OK);
        } catch (QuickMobilityException e) {
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}

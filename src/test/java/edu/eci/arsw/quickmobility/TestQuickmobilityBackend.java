package edu.eci.arsw.quickmobility;

import static org.junit.Assert.*;
import edu.eci.arsw.quickmobility.model.*;
import edu.eci.arsw.quickmobility.persistence.QuickMobilityException;
import edu.eci.arsw.quickmobility.repository.UserRepository;
import edu.eci.arsw.quickmobility.services.AuthServices;
import edu.eci.arsw.quickmobility.services.QuickMobilityServices;
import junit.framework.TestSuite;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(properties = {"spring.data.mongodb.uri=mongodb+srv://admin:admin@clusterquickmobility.qrzr4.mongodb.net","spring.data.mongodb.database=QuickMobilityDataBaseTest"})
public class TestQuickmobilityBackend {
    @Autowired
    QuickMobilityServices quickmobilityServices;

    @Autowired
    AuthServices authServices;

    @Autowired
    UserRepository userRepository;


    @Test
    public void athisWillCreateAUser(){
        userRepository.deleteAll();
        Usuario user = new Usuario();
        user.setUsername("nigi");
        user.setNombreCompleto("Fulanito Perez");
        user.setDireccionResidencia("Calle 32 #65-9454");
        user.setPassword("prueba");
        user.setEmail("nigi@mail.com");
        user.setNumero("329485964");
        user.setBarrio("Colina Campestre");
        user.setCarros(new ArrayList<>());
        user.setViajesConductor(new ArrayList<>());
        user.setViajesPasajero(new ArrayList<>());
        authServices.addUser(user);
        Usuario otherUser = null;
        try {
            otherUser = authServices.getUserByUsername("nigi");
            otherUser = authServices.getUserByUsername("nigiDOs");
        } catch (QuickMobilityException e) {
            assertEquals("This user is not created",e.getMessage(),QuickMobilityException.USERNAME_NOT_FOUND);
        }
        assertEquals("Is created a User with nigi username",user.username,otherUser.username);
    }

    @Test
    public void bthisWillAddACarToAUser(){
        try {
            quickmobilityServices.addCarroUsuario("nigi",new Carro("XYZ123", "Lamborghini", "Gris", "Urus"));
            List<Carro> cars = quickmobilityServices.getCarros("nigi");
            assertEquals("The size of the list should be 1",cars.size(),1);
            assertEquals("The first element of the cars should be have a license plate called XYZ123",cars.get(0).placa,"XYZ123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void cthisWillOfferATravel(){
    	ArrayList<String> inicio = new ArrayList<String>( Arrays.asList("47.565656", "48.565656", "calle 127")); 
        ArrayList<String> destino = new ArrayList<String>( Arrays.asList("49.565656", "46.565656", "carrrear 24"));
        Viaje travel= new Viaje("3000", inicio, destino, "XYZ123");
        List<Conductor> driversAvailable = null;
        try {
            driversAvailable = quickmobilityServices.getConductoresDisponibles(travel,"nigi");
        } catch (QuickMobilityException e) {
            e.printStackTrace();
        }
        assertEquals("The size of the list of driversAvailable should be 1",driversAvailable.size(),1);
    }

    @Test
    public void dthisWillReleaseAPassengerPetition(){
        InformacionPasajero infoPassenger = new InformacionPasajero("otherNigi","Calle 34 #55b-95");
        Usuario user = new Usuario();
        user.setUsername("otherNigi");
        user.setNombreCompleto("Juancho Benito");
        user.setDireccionResidencia("Calle 96 #63-94");
        user.setPassword("prueba");
        user.setEmail("othernigi@mail.com");
        user.setNumero("329485566");
        user.setBarrio("Colina Campestre");
        user.setCarros(new ArrayList<>());
        user.setViajesConductor(new ArrayList<>());
        user.setViajesPasajero(new ArrayList<>());
        authServices.addUser(user);
        List<Pasajero> possiblePassengers = null;
        try {
            possiblePassengers = quickmobilityServices.solicitudDeViajePasajero(infoPassenger,"nigi");
        } catch (QuickMobilityException e) {
            e.printStackTrace();
        }
        assertEquals("The size of the list of possible passangers must be 1",possiblePassengers.size(),1);
    }

    @Test
    public void ethisWillAcceptAPassengerPetition(){
        NuevoEstado newState = new NuevoEstado("nigi",true);
        JSONObject info = null;
        try {
            info = quickmobilityServices.aceptarORechazarPasajero(newState,"otherNigi");
        } catch (QuickMobilityException e) {
            e.printStackTrace();
        }
        assertEquals("The status will be \"Disponible\"",info.get("estadoPasajero"),Estado.Aceptado);
    }

    @Test
    public void fthisWillPutAScoreToADriverAndFinishATravel(){
        try {
        	quickmobilityServices.addCalificacion("nigi","-1",3.7);
        	quickmobilityServices.addCalificacion("-1","otherNigi",4);
            List<Pasajero> passengers = new ArrayList<>();
            passengers.add(quickmobilityServices.getTravelPassenger("otherNigi"));
            quickmobilityServices.finishTravel("nigi",passengers);
            assertEquals("The score of the driver must be 3.7",Double.toString(3.7),Double.toString(quickmobilityServices.getAverage("nigi","Conductor")));
            assertEquals("The score of the passenger must be 4",Double.toString(4),Double.toString(quickmobilityServices.getAverage("otherNigi","Pasajero")));
        } catch (QuickMobilityException e) {
            e.printStackTrace();
        }
    }
}

package edu.eci.arsw.quickmobility.sockets;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import edu.eci.arsw.quickmobility.model.Conductor;
import edu.eci.arsw.quickmobility.model.Pasajero;
import edu.eci.arsw.quickmobility.persistence.QuickMobilityException;
import edu.eci.arsw.quickmobility.services.QuickMobilityServices;
import edu.eci.arsw.quickmobility.model.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebSocketController {

    @Autowired
    SimpMessagingTemplate msgt;

    @Autowired
    QuickMobilityServices quickmobilityServices;


    @MessageMapping("/solicitudPasajero.{usernameConductor}")
    public void pasajeroSolicitudDeViaje(String infoPasajero, @DestinationVariable String usernameConductor){
        JSONObject infoDelPasajero = new JSONObject(infoPasajero);
        try {
            List<Pasajero> posiblesPasajeros= quickmobilityServices.solicitudDeViajePasajero(infoDelPasajero,usernameConductor);
            msgt.convertAndSend("/solicitudPasajero."+usernameConductor,posiblesPasajeros);
        } catch (QuickMobilityException e) {
            e.printStackTrace();
            msgt.convertAndSend("/solicitudPasajero."+usernameConductor,"No encontré el usuario pasajero o el conductor");
        }
    }

    @MessageMapping("/ofrecerViaje/{conducNombre}")
    public void ofrecerViaje(String ruta, @DestinationVariable String conducNombre ) throws QuickMobilityException {
        JSONObject infoConductor = new JSONObject(ruta);
        try {
            List<Conductor> conductoresDisponibles = quickmobilityServices.getConductoresDisponibles(infoConductor,conducNombre);
            msgt.convertAndSend("/uniwheels",conductoresDisponibles);
        } catch (QuickMobilityException e) {
            e.printStackTrace();
            msgt.convertAndSend("/uniwheels","No se encontraron conductores disponibles");
        }
    }

    @MessageMapping("/aceptarORechazarPasajero.{usernamePasajero}")
    public void aceptarORechazarPasajero(String estado,@DestinationVariable String usernamePasajero){
        JSONObject estadoJSON = new JSONObject(estado);
        try{
            JSONObject json = quickmobilityServices.aceptarORechazarPasajero(estadoJSON,usernamePasajero);

            msgt.convertAndSend("/aceptarORechazarPasajero.{usernamePasajero}",json.toMap());
        } catch (Exception e){
            msgt.convertAndSend("/aceptarORechazarPasajero.{usernamePasajero}","No se encontró un pasajero o conductor con el username dado");
        }

    }
}

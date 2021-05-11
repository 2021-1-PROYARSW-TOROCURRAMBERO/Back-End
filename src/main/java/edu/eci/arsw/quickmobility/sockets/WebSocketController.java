package edu.eci.arsw.quickmobility.sockets;

import edu.eci.arsw.quickmobility.model.*;
import edu.eci.arsw.quickmobility.persistence.QuickMobilityException;
import edu.eci.arsw.quickmobility.services.QuickMobilityServices;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ws")
public class WebSocketController {

    @Autowired
    SimpMessagingTemplate msgt;

    @Autowired
    QuickMobilityServices quickmobilityServices;

    @MessageMapping("/passengerRequest.{usernameDriver}")
    public void pasajeroSolicitudDeViaje(InformacionPasajero infoPassenger, @DestinationVariable String usernameDriver){
        try {
            List<Pasajero> possiblePassengers= quickmobilityServices.solicitudDeViajePasajero(infoPassenger,usernameDriver);
            msgt.convertAndSend("/quickmobility/passengerRequest."+usernameDriver,possiblePassengers);
        } catch (QuickMobilityException e) {
            e.printStackTrace();
            msgt.convertAndSend("/quickmobility/passengerRequest."+usernameDriver,"No encontré el usuario pasajero o el conductor");
        }
    }

    @MessageMapping("/offerTravel.{driverUsername}")
    public void ofrecerViaje(Viaje travel, @DestinationVariable String driverUsername ) throws QuickMobilityException {
        boolean hasRoute = true;
        if(travel.getPrecio() == null){
            msgt.convertAndSend("/quickmobility/drivers",quickmobilityServices.getConductoresDisponibles());
            hasRoute = false;
        }
        try {
            if(hasRoute) {
                List<Conductor> availableDrivers = quickmobilityServices.getConductoresDisponibles(travel, driverUsername);
                msgt.convertAndSend("/quickmobility/drivers", availableDrivers);
            }
        } catch (QuickMobilityException e) {
            e.printStackTrace();
            msgt.convertAndSend("/quickmobility/drivers",e.getMessage());
        }
    }

    @MessageMapping("/acceptOrRejectPassenger.{usernamePassenger}")
    public void aceptarORechazarPasajero(NuevoEstado state, @DestinationVariable String usernamePassenger){
        try{
            JSONObject json = quickmobilityServices.aceptarORechazarPasajero(state,usernamePassenger);
            msgt.convertAndSend("/quickmobility/acceptOrRejectPassenger."+usernamePassenger,json.toMap());
        } catch (Exception e){
            msgt.convertAndSend("/quickmobility/acceptOrRejectPassenger."+usernamePassenger,"No se encontró un pasajero o conductor con el username dado");
        }

    }

    @MessageMapping("/passengerState.{usernamePassenger}")
    public void estadoPasajero(Estado state, @DestinationVariable String usernamePassenger){
        try {
            msgt.convertAndSend("/quickmobility/passengerState."+usernamePassenger,quickmobilityServices.estadoPasajero(state,usernamePassenger));
        } catch (QuickMobilityException e) {
            e.printStackTrace();
        }
    }

    @MessageMapping("/finishTravel.{useranameDriver}")
    public void finishTravel(List<Pasajero> passengers,@DestinationVariable String usernameDriver){
        try {
        	quickmobilityServices.finishTravel(usernameDriver,passengers);
            msgt.convertAndSend("/finishTravel."+usernameDriver,"The travel is finished");
        } catch (QuickMobilityException e) {
            msgt.convertAndSend("/finishTravel."+usernameDriver,"I got a error: "+e.getMessage());
            e.printStackTrace();
        }
    }
}

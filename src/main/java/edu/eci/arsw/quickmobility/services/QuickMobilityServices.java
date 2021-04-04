package edu.eci.arsw.quickmobility.services;



import edu.eci.arsw.quickmobility.model.Carro;
import edu.eci.arsw.quickmobility.model.Conductor;
import edu.eci.arsw.quickmobility.model.Pasajero;
import edu.eci.arsw.quickmobility.model.Barrio;
import edu.eci.arsw.quickmobility.model.Usuario;
import edu.eci.arsw.quickmobility.persistence.QuickMobilityException;
import edu.eci.arsw.quickmobility.persistence.QuickMobilityPersistence;
import edu.eci.arsw.quickmobility.model.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class QuickMobilityServices extends UserServices{

    @Autowired
    QuickMobilityPersistence quickmobilityPersistence;

    public String helloWorld(){
        return "Hello World Hola Mundo";
    }

    public List<Carro> getCarros(String username) throws Exception {
        return quickmobilityPersistence.getUserByUsername(username).getCarros();
    }

    public void addCarroUsuario(String user, Carro carro) throws Exception {
        Usuario usuario = quickmobilityPersistence.getUserByUsername(user);
        usuario.addCarros(carro);
        updateUser(usuario);
    }

    public List<Barrio> getBarrios(){
        return quickmobilityPersistence.getBarrio();
    }

    public void addBarrio(Barrio barrio) throws Exception {
    	quickmobilityPersistence.addBarrio(barrio);
    }

    public void addCalificacion(String idConductor,String idPasajero,int calificacion) throws Exception {
    	quickmobilityPersistence.addCalificacion(idConductor,idPasajero,calificacion);
    }

    public void updateCarro(Carro carro,Usuario usuario) throws Exception {
    	quickmobilityPersistence.updateCarro(carro,usuario);
    }

    public String getUserStatus(String username) throws QuickMobilityException {
        Usuario user = quickmobilityPersistence.getUserByUsername(username);
        String status = "Ninguno";
        if(user.viajesPasajero.size()>0||user.viajesConductor.size()>0){
            if (!user.viajesPasajero.get(user.viajesPasajero.size() - 1).estado.equals("Finalizado")) {
                status = "Pasajero";
            } else if (!user.viajesConductor.get(user.viajesConductor.size() - 1).estado.equals("Finalizado")){
                status = "Conductor";
            }
        }
        return status;
    }

    public float getAverage(String username, String type) throws QuickMobilityException {
        Usuario usuario = quickmobilityPersistence.getUserByUsername(username);
        float valueToReturn = 0;
        int totalScore = 0;
        if(type.equals("Conductor") && usuario.viajesConductor.size()>0){
            for(Conductor c : usuario.viajesConductor){
                if(c.estado.equals("Finalizado")){
                    valueToReturn+=c.calificacion.valor;
                    totalScore+=1;
                }
            }
        } else {
            for(Pasajero p : usuario.viajesPasajero){
                if(p.estado.equals("Finalizado")){
                    valueToReturn+=p.calificacion.valor;
                    totalScore+=1;
                }
            }
        }
        valueToReturn = valueToReturn/totalScore;
        return valueToReturn;
    }

    public void updateUser(Usuario user) throws QuickMobilityException {
    	quickmobilityPersistence.updateUser(user);

    }

    public List<Pasajero> solicitudDeViajePasajero(JSONObject infoPasajero, String usernameConductor) throws QuickMobilityException {
        Usuario pasajero = getUserByUsername(infoPasajero.getString("username"));
        Usuario conductor = getUserByUsername(usernameConductor);
        Conductor viajeConductor = null;
        for(int i =0;i<conductor.viajesConductor.size();i++){
            if(conductor.viajesConductor.get(i).estado.equals("Disponible")){
                viajeConductor = conductor.viajesConductor.get(i);
                break;
            }
        }

        Pasajero viajePasajero = new Pasajero();
        viajePasajero.estado = "Disponible";
        viajePasajero.username = pasajero.username;
        viajePasajero.direccionRecogida = infoPasajero.getString("direccion");
        viajeConductor.posiblesPasajeros.add(viajePasajero);
        pasajero.viajesPasajero.add(viajePasajero);
        quickmobilityPersistence.updateUser(pasajero);
        quickmobilityPersistence.updateUser(conductor);
        return viajeConductor.posiblesPasajeros;

    }

     public List<Conductor> getConductoresDisponibles(JSONObject jsonObject, String conducNombre) throws QuickMobilityException {
         Usuario usuario = getUserByUsername(conducNombre);
         Conductor conductor = new Conductor();

         conductor.setEstado("Disponible");
         conductor.setPrecio(jsonObject.getString("precio"));
         conductor.setDireccionInicio(jsonObject.getString("origen"));
         conductor.setDireccionFin(jsonObject.getString("destino"));

         List<Carro> carros = usuario.getCarros();
         for (Carro c:carros){
             if (c.getPlaca().equals(jsonObject.getString("carro"))){
                 conductor.setCarro(c);
                 break;
             }
         }
         usuario.viajesConductor.add(conductor);
         quickmobilityPersistence.updateUser(usuario);
         return quickmobilityPersistence.getConductoresDisponibles();
    }

    public JSONObject aceptarORechazarPasajero(JSONObject info, String usernamePasajero) throws QuickMobilityException {
        Usuario usuarioPasajero = getUserByUsername(usernamePasajero);
        Usuario usuarioConductor = getUserByUsername(info.getString("usuario"));
        boolean estado = info.getBoolean("estado");
        Pasajero pasajero = null;
        for(Pasajero p: usuarioPasajero.viajesPasajero){
            if(p.estado.equals("Disponible")){
                pasajero = p;
                break;
            }
        }
        Conductor conductor = null;
        for(Conductor c: usuarioConductor.viajesConductor){
            if(c.estado.equals("Disponible")){
                conductor = c;
                for(int i = 0;i<c.posiblesPasajeros.size();i++){
                    if(c.posiblesPasajeros.get(i).username.equals(pasajero.username)){
                        c.posiblesPasajeros.remove(i);
                        break;
                    }
                }
                break;
            }
        }
        pasajero.conductor = conductor;
        if(estado){
            pasajero.estado = "Aceptado";
            for(Conductor c: quickmobilityPersistence.getConductoresDisponibles()){
                for(int i = 0;i<c.posiblesPasajeros.size();i++){
                    if(c.posiblesPasajeros.get(i).username.equals(usernamePasajero)){
                        c.posiblesPasajeros.remove(i);
                        break;
                    }
                }
            }
        } else {
            pasajero.estado = "Rechazado";
        }
        quickmobilityPersistence.updateUser(usuarioConductor);
        quickmobilityPersistence.updateUser(usuarioPasajero);
        JSONObject jsonADevolver = new JSONObject(conductor);
        jsonADevolver.put("estado",pasajero.estado);
        return jsonADevolver;

    }
}

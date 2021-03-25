package edu.eci.arsw.quickmobility.services;


import edu.eci.arsw.quickmobility.model.Carro;
import edu.eci.arsw.quickmobility.model.Conductor;
import edu.eci.arsw.quickmobility.model.Pasajero;
import edu.eci.arsw.quickmobility.model.Barrio;
import edu.eci.arsw.quickmobility.model.Usuario;
import edu.eci.arsw.quickmobility.persistence.QuickMobilityException;
import edu.eci.arsw.quickmobility.persistence.QuickMobilityPersistence;
import edu.eci.arsw.quickmobility.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
public class QuickMobilityServices extends UserServices{


    public String helloWorld(){

        return "Hello World Hola Mundo";
    }

    public List<Carro> getCarros(String username) throws Exception {
        return quickmobilityPersistence.getCarros(username);
    }

    public void addCarroUsuario(Usuario user, Carro carro) throws Exception {
        quickmobilityPersistence.addCarroUsuario(carro);
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

    public void updateCarro(Carro carro) throws Exception {

        quickmobilityPersistence.updateCarro(carro);
    }

    public String getUserState(String username) throws QuickMobilityException {
        Usuario user = quickmobilityPersistence.getUserByUsername(username);
        String state = "Ninguno";
        if(user.viajesPasajero.size()>0||user.viajesConductor.size()>0){
            if (!user.viajesPasajero.get(user.viajesPasajero.size() - 1).estado.equals("Finished")) {
                state = "Pasajero";
            } else if (!user.viajesConductor.get(user.viajesConductor.size() - 1).estado.equals("Finished")){
                state = "Conductor";
            }
        }
        return state;
    }

    public float getAverage(String username, String type) throws QuickMobilityException {
        Usuario usuario = quickmobilityPersistence.getUserByUsername(username);
        float valueToReturn = 0;
        int totalCalifications = 0;
        if(type.equals("Conductor") && usuario.viajesConductor.size()>0){
            for(Conductor c : usuario.viajesConductor){
                if(c.estado.equals("Finalizado")){
                    valueToReturn+=c.calificacion.valor;
                    totalCalifications+=1;
                }
            }
            valueToReturn = valueToReturn/totalCalifications;
        } else {
            for(Pasajero p : usuario.viajesPasajero){
                if(p.estado.equals("Finalizado")){
                    valueToReturn+=p.calificacion.valor;
                    totalCalifications+=1;
                }
            }
            valueToReturn = valueToReturn/totalCalifications;
        }
        return valueToReturn;
    }

    public void updateUser(Usuario user) throws QuickMobilityException {
        quickmobilityPersistence.updateUser(user);

    }
}

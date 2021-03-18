package edu.eci.arsw.quickmobility.services;


import edu.eci.arsw.quickmobility.model.Carro;
import edu.eci.arsw.quickmobility.model.DetallesUsuario;
import edu.eci.arsw.quickmobility.model.Barrio;
import edu.eci.arsw.quickmobility.persistence.QuickMobilityException;
import edu.eci.arsw.quickmobility.persistence.QuickMobilityPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
public class QuickMobilityServices {

    @Autowired
    QuickMobilityPersistence quickmobilityPersistence = null;

    public String helloWorld(){

        return "Hello World Hola Mundo";
    }

    public List<Carro> getCarros(String username) throws Exception {
        return quickmobilityPersistence.getCarros(username);
    }

    public void addCarroUsuario(DetallesUsuario user, Carro carro) throws Exception {
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
}

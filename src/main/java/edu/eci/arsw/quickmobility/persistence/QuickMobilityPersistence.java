package edu.eci.arsw.quickmobility.persistence;

import edu.eci.arsw.quickmobility.model.Carro;
import edu.eci.arsw.quickmobility.model.Conductor;
import edu.eci.arsw.quickmobility.model.Barrio;
import edu.eci.arsw.quickmobility.model.Usuario;

import java.util.List;

public interface QuickMobilityPersistence {

    void saveUser(Usuario usuario);

    Usuario getUserByUsername(String username) throws QuickMobilityException;

    List<Barrio> getBarrio();

    void addBarrio(Barrio barrio) throws Exception;

    void addCalificacion(String idConductor, String idPasajero, int calificacion) throws Exception;

    void updateCarro(Carro carro,Usuario usuario) throws Exception;

    void updateUser(Usuario user) throws QuickMobilityException;

    List<Conductor> getConductoresDisponibles();
}

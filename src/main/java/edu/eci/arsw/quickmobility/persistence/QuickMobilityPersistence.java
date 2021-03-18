package edu.eci.arsw.quickmobility.persistence;

import edu.eci.arsw.quickmobility.model.Carro;
import edu.eci.arsw.quickmobility.model.Barrio;
import edu.eci.arsw.quickmobility.model.Usuario;

import java.util.Collection;
import java.util.List;

public interface QuickMobilityPersistence {

    void saveUser(Usuario usuario);

    Usuario getUserByUsername(String username) throws QuickMobilityException;

    List<Carro> getCarros(String username) throws Exception;

    void addCarroUsuario(Carro carro) throws Exception;

    List<Barrio> getBarrio();

    void addBarrio(Barrio barrio) throws Exception;

    void addCalificacion(String idConductor, String idPasajero, int calificacion) throws Exception;

    void updateCarro(Carro carro) throws Exception;
}

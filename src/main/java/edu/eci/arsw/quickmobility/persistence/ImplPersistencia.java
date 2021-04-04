package edu.eci.arsw.quickmobility.persistence;

import edu.eci.arsw.quickmobility.model.Carro;
import edu.eci.arsw.quickmobility.model.Conductor;
import edu.eci.arsw.quickmobility.model.Barrio;
import edu.eci.arsw.quickmobility.model.Usuario;
import edu.eci.arsw.quickmobility.repository.UserRepository;
import edu.eci.arsw.quickmobility.model.*;
import edu.eci.arsw.quickmobility.repository.NeighborhoodRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImplPersistencia implements QuickMobilityPersistence {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NeighborhoodRepository neighborhoodRepository;

    @Override
    public void saveUser(Usuario usuario){
        String pwd = usuario.getPassword();
        String encrypt = bCryptPasswordEncoder.encode(pwd);
        usuario.setPassword(encrypt);
        userRepository.save(usuario);
    }

    @Override
    public Usuario getUserByUsername(String username) throws QuickMobilityException {
        List<Usuario> allUsers = userRepository.findAll();
        System.out.println(username);
        Usuario usuario = null;
        for(int i =0;i<allUsers.size();i++){
            System.out.println(allUsers.get(i).toString());
            if(allUsers.get(i).username.equals(username)){
                System.out.println("Entre acá");
                usuario = allUsers.get(i);
            }
        }
        if(usuario == null){
            throw new QuickMobilityException(QuickMobilityException.USERNAME_NOT_FOUND);
        }
        return usuario;
    }

    @Override
    public List<Barrio> getBarrio() {
        List<Barrio> allNeighborhood = neighborhoodRepository.findAll();
        return allNeighborhood;
    }

    @Override
    public void addBarrio(Barrio barrio) throws Exception {
        if(barrio != null){
        	neighborhoodRepository.save(barrio);
        } else {
            throw new Exception(QuickMobilityException.INVALID_NEIGHBORHOOD);
        }
    }

    @Override
    public void addCalificacion(String idConductor, String idPasajero, int calificacion) throws Exception {
        if(calificacion > 0) {
            if(idPasajero != null) {
                if(idConductor != null){
                    //Connect with repository
                } else {
                    throw new Exception(QuickMobilityException.DRIVER_NOT_FOUND);
                }
            } else {
                throw new Exception(QuickMobilityException.PASANGER_NOT_FOUND);
            }
        } else {
            throw new Exception(QuickMobilityException.INVALID_RATING);
        }
    }

    @Override
    public void updateCarro(Carro carro, Usuario usuario) throws Exception {
        if(carro != null) {
           List<Carro> allCarros = usuario.getCarros();
           for(Carro car: allCarros){
               if(car.getPlaca().equals(carro.getPlaca())){
                   car.setColor(carro.getColor());
                   car.setMarca(carro.getMarca());
                   car.setModelo(carro.getModelo());
                   break;
               }
           }
           userRepository.save(usuario);
        } else {
            throw new Exception (QuickMobilityException.INVALID_CAR);
        }
    }

    @Override
    public void updateUser(Usuario user) throws QuickMobilityException {
        Usuario oldUser = getUserByUsername(user.username);
        oldUser.changeValues(user);
        userRepository.save(oldUser);
    }

    @Override
    public List<Conductor> getConductoresDisponibles(){
        List<Usuario> usuarios= userRepository.findAll();
        List<Conductor> conductorsTemp = new ArrayList<Conductor>();
        for(Usuario user:usuarios){
            for(Conductor con: user.getViajesConductor()){
                if(con.getEstado().equals("Disponible")){
                    conductorsTemp.add(con);
                }
            }
        }
        return conductorsTemp;
    }
}
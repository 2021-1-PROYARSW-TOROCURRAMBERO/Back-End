package edu.eci.arsw.quickmobility.persistence;

import edu.eci.arsw.quickmobility.model.Carro;
import edu.eci.arsw.quickmobility.model.Barrio;
import edu.eci.arsw.quickmobility.model.Usuario;
import edu.eci.arsw.quickmobility.repository.CarroRepository;
import edu.eci.arsw.quickmobility.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImplPersistencia implements QuickMobilityPersistence {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarroRepository carroRepository;

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
    public List<Carro> getCarros(String username) throws Exception {
        if(username != null)
        {
            //Connect with repository
        }
        else
            throw new Exception(QuickMobilityException.USERNAME_NOT_FOUND);
        return null;
    }

    @Override
    public void addCarroUsuario(Carro carro) throws Exception {
        if(carro != null){
            //Connect with repository
        }
        else
            throw new Exception(QuickMobilityException.CAR_NOT_FOUND);

    }

    @Override
    public List<Barrio> getBarrio() {
        return null;
    }

    @Override
    public void addBarrio(Barrio barrio) throws Exception {
        if(barrio != null){
            //Connect with repository
        }
        else
            throw new Exception(QuickMobilityException.INVALID_NEIGHBORHOOD);
    }

    @Override
    public void addCalificacion(String idConductor, String idPasajero, int calificacion) throws Exception {
        if(calificacion > 0)
        {
            if(idPasajero != null)
            {
                if(idConductor != null){
                    //Connect with repository
                }
                else
                    throw  new Exception(QuickMobilityException.DRIVER_NOT_FOUND);
            }
            else
                throw new Exception(QuickMobilityException.PASANGER_NOT_FOUND);
        }
        else
            throw new Exception(QuickMobilityException.INVALID_RATING);
    }

    @Override
    public void updateCarro(Carro carro) throws Exception {
        if(carro != null)
        {
            //Connect with repository
        }
        else
            throw new Exception(QuickMobilityException.INVALID_CAR);
    }
}

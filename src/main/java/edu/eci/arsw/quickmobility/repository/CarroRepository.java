package edu.eci.arsw.quickmobility.repository;

import edu.eci.arsw.quickmobility.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarroRepository extends MongoRepository<Usuario,String> {
}

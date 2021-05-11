package edu.eci.arsw.quickmobility.repository;

import edu.eci.arsw.quickmobility.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Usuario,String> {
    public Usuario findByUsername(String username);
}

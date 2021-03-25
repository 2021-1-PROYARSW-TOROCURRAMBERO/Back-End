package edu.eci.arsw.quickmobility.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.eci.arsw.quickmobility.model.Usuario;

public interface CarroRepository extends MongoRepository<Usuario,String> {
}

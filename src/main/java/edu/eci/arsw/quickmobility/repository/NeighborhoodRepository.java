package edu.eci.arsw.quickmobility.repository;

import edu.eci.arsw.quickmobility.model.Barrio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NeighborhoodRepository extends MongoRepository<Barrio,String> {
}

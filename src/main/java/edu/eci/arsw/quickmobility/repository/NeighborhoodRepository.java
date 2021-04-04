package edu.eci.arsw.quickmobility.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import edu.eci.arsw.quickmobility.model.Barrio;

public interface NeighborhoodRepository extends MongoRepository<Barrio,String> {
}

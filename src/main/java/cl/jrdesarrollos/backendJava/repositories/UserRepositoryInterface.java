package cl.jrdesarrollos.backendJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.jrdesarrollos.backendJava.entities.UserEntity;

@Repository
public interface UserRepositoryInterface extends CrudRepository<UserEntity, Long>  {
    
    UserEntity findByEmail(String email);

}

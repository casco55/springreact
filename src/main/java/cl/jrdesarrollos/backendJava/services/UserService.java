package cl.jrdesarrollos.backendJava.services;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.jrdesarrollos.backendJava.UserRepositoryInterface;
import cl.jrdesarrollos.backendJava.entities.UserEntity;
import cl.jrdesarrollos.backendJava.shared.dto.UserDto;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepositoryInterface userRepositoryInterface;

    @Override
    public UserDto createUser(UserDto user) {

        if(userRepositoryInterface.findByEmail(user.getEmail()) != null) throw new RuntimeException("El email ya se encuentra registrado");

        UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(user, userEntity);

        userEntity.setEncryptedPassword("testPass");

        UUID userId = UUID.randomUUID();
        userEntity.setUserId(userId.toString());

        UserEntity storedUserDetails = userRepositoryInterface.save(userEntity);

        UserDto userToReturn = new UserDto();

        BeanUtils.copyProperties(storedUserDetails, userToReturn);

        return userToReturn;
    }
    
}

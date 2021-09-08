package cl.jrdesarrollos.backendJava.services;

import cl.jrdesarrollos.backendJava.shared.dto.UserDto;

public interface UserServiceInterface {
    public UserDto createUser(UserDto user);
}

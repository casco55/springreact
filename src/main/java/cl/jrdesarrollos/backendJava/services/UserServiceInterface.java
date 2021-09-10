package cl.jrdesarrollos.backendJava.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import cl.jrdesarrollos.backendJava.shared.dto.UserDto;

public interface UserServiceInterface extends UserDetailsService {
    public UserDto createUser(UserDto user);

    public UserDto getUser(String email);
}

package cl.jrdesarrollos.backendJava.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.jrdesarrollos.backendJava.models.requests.UserDetailRequestModel;
import cl.jrdesarrollos.backendJava.models.responses.UserRest;
import cl.jrdesarrollos.backendJava.services.UserServiceInterface;
import cl.jrdesarrollos.backendJava.shared.dto.UserDto;

@RestController
@RequestMapping("/users") // localhost:8080/users
public class UserController {

    @Autowired
    UserServiceInterface userService;

    @GetMapping
    public String getUser() {
        return "get user details";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailRequestModel UserDetails) {

        UserRest userToReturn = new UserRest();

        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(UserDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);

        BeanUtils.copyProperties(createdUser, userToReturn);
        
        return userToReturn;

    }
}

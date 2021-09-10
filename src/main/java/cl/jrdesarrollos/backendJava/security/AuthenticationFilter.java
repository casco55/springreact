package cl.jrdesarrollos.backendJava.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cl.jrdesarrollos.backendJava.SpringApplicationContext;
import cl.jrdesarrollos.backendJava.models.requests.UserLoginRequestModel;
import cl.jrdesarrollos.backendJava.services.UserServiceInterface;
import cl.jrdesarrollos.backendJava.shared.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    private final AuthenticationManager authenticaionManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticaionManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse responses) throws AuthenticationException{
            try {
                UserLoginRequestModel userModel = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestModel.class);
                return authenticaionManager.authenticate(new UsernamePasswordAuthenticationToken(userModel.getEmail(), userModel.getPassword(), new ArrayList<>()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        String username = ((User) authentication.getPrincipal()).getUsername();

        String token = Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_DATE))
        .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();

        // añadir el header con id Público

        UserServiceInterface userService = (UserServiceInterface) SpringApplicationContext.getBean("userService");
        UserDto userDto = userService.getUser(username);
        

        response.addHeader("UserId", userDto.getUserId());
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        
        

    }

}

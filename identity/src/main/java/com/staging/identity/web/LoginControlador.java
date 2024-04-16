package com.staging.identity.web;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.staging.identity.modelo.LoginRequestDTO;
import com.staging.identity.servicio.JwtServicio;
import com.staging.identity.servicio.LoginServicio;


@RestController
public class LoginControlador {

	private final Logger logger = LogManager.getLogger(LoginControlador.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LoginServicio servicio;

    @Autowired
    private JwtServicio jwtServicio;
    
    
    @GetMapping(value = "/app")
    public String info(){
        return "la aplicación se encuentra arriba";
    }
        
    @PostMapping(value = "/login")
    public String obtenerJwtToken(@RequestBody LoginRequestDTO dto) throws Exception {
    	logger.info("Iniciando consumo del API UserIdentity Login");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        }catch (BadCredentialsException e){
        	logger.warn("Credenciales Incorrectas");
            throw new Exception("Usuario o Contraseña incorrectos ", e); 
        }

        UserDetails userDetails = servicio.loadUserByUsername(dto.getUsername());
        final String jwt = jwtServicio.getJWT(userDetails);
        return jwt;
    }
}


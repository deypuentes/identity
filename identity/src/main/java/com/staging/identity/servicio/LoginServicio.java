package com.staging.identity.servicio;

import com.staging.identity.dominio.UserIdentity;
import com.staging.identity.repositorio.UserIdentityJPA;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LoginServicio implements UserDetailsService {

	private final Logger logger = LogManager.getLogger(UserIdentityServicio.class);
	
    @Autowired
    UserIdentityJPA userIdentityJPA;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	logger.debug("Iniciando operacion LoginServicio");
    	UserIdentity user = userIdentityJPA.findByUsername(username);

        if(!(null==user)){
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Arrays.asList(user.getRoles().split(",")).stream().forEach(authority ->{
                authorities.add(new SimpleGrantedAuthority(authority));
            });
            return new User(user.getUsername(), user.getPassword(), authorities);
        }else {
        	logger.warn("No se encontro el usuario");
            throw new UsernameNotFoundException("El usuario: " + username + " No Existe");
        }
    }
}

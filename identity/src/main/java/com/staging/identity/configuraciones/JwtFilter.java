package com.staging.identity.configuraciones;

import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.staging.identity.servicio.JwtServicio;
import com.staging.identity.servicio.LoginServicio;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	 
    @Autowired
    private LoginServicio servicio;

    @Autowired
    private JwtServicio jwtServicio;

    private Logger logger = LoggerFactory.getLogger("JwtFilter.class");

   @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	logger.debug("Iniciando operacion JwtFilter");
        final String authHeader = request.getHeader("Authorization");

        String username = null;

        if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")){
            username = jwtServicio.extractClaim(authHeader.substring(7), Claims::getSubject);
            logger.info("*********username: " + username + "*********");
        }
        if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails userDetails = servicio.loadUserByUsername(username);
            if (jwtServicio.isValidToken(authHeader.substring(7), userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }



    
    
    
    
    
    
    
}

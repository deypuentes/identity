package com.staging.identity.web;


import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.staging.identity.conversor.UserIdentityConversor;
import com.staging.identity.modelo.UserIdentityDTO;
import com.staging.identity.servicio.UserIdentityServicio;

@RestController
@RequestMapping(path = "/UserIdentity")
@CrossOrigin
public class UserIdentityCrontrolador {

	private final Logger logger = LogManager.getLogger(UserIdentityCrontrolador.class);
	
	@Autowired
	private UserIdentityServicio servicio;
	 
	@Autowired
	private UserIdentityConversor conversor;
	
	@GetMapping
    public List <UserIdentityDTO> obtenerTodo() {
        logger.info("Iniciando consumo del API UserIdentityDTO obtenerTodo");

        try {
            return conversor.listaEntidadADto(servicio.obtenerTodo());
        } finally {
            logger.info("Finalizando consumo del API UserIdentityDTO obtenerTodo");
        }
    }
	
	@GetMapping(value = "/consulta/{id}")
    public UserIdentityDTO obtenerPorId(@PathVariable(name = "id")@NotNull int id) {
        logger.info("Iniciando consumo del API UserIdentity obtenerPorId");

        try {
            return conversor.entidadADto(servicio.obtenerPorId(id));
        } finally {
            logger.info("Finalizando consumo del API UserIdentity obtenerPorId");
        }
    }

	@GetMapping(value = "/consulta/usuario/{username}")
    public UserIdentityDTO obtenerPorUsuario(@PathVariable(name = "username")@NotNull String username) {
        logger.info("Iniciando consumo del API UserIdentity obtenerPorUsuario");

        try {
            return conversor.entidadADto(servicio.obtenerPorUserName(username));
        } finally {
            logger.info("Finalizando consumo del API UserIdentity obtenerPorUsuario");
        }
    }
	 
	@PostMapping
    public UserIdentityDTO insertar(@RequestBody UserIdentityDTO modelo) {
        logger.info("Iniciando consumo del API UserIdentity insertar");

        try {
            return conversor.entidadADto(servicio.insertar(conversor.dtoAEntidad(modelo)));
        } finally {
            logger.info("Finalizando consumo del API UserIdentity insertar");
        }
    }

		@PutMapping
		public UserIdentityDTO actualizar(@RequestBody UserIdentityDTO dto) {
			logger.info("Iniciando consumo del API UserIdentity actualizar");
			try {
				
				if (dto.getUsername().isEmpty()) {
					logger.warn("El nombre de usuario no puede ser nulo");
					throw new RuntimeException("El Username no puede ser nulo");
				}
				if (dto.getPassword().isEmpty()) {
					logger.warn("La  contraseña no puede ser nula");
					throw new RuntimeException("La  contraseña no puede ser nula");
				}
		
				servicio.actualizar(conversor.dtoAEntidad(dto));
				return dto;
			} finally {
				logger.info("Finalizando consumo del API UserIdentity actualizar");
			}
		}

		@DeleteMapping(value = "/{userName}")
		public boolean eliminar(@PathVariable(name = "userName") String userName) {
			logger.info("Iniciando consumo del API UserIdentity userName eliminar");

			try {
				servicio.eliminar(userName);
				return true;
			} finally {
				logger.info("Finalizando consumo del API UserIdentity userName eliminar");
			}
		}
}

package com.staging.identity.servicio;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.staging.identity.dominio.UserIdentity;
import com.staging.identity.repositorio.UserIdentityJPA;




@Service
public class UserIdentityServicio {
	private final Logger logger = LogManager.getLogger(UserIdentityServicio.class);

	@Autowired
	private UserIdentityJPA userIdentityJPA;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional(readOnly = true)
	public List <UserIdentity> obtenerTodo() {
		logger.debug("Iniciando operacion obtener todo");
		List <UserIdentity> lista = userIdentityJPA.findAll();
		
		if (lista.size()==0) {
				logger.warn("No se encontraron usuarios");
				throw new RuntimeException("No se encontraron usuarios");
			}
		for(UserIdentity entidad :lista) {
			entidad.setPassword("");
			
		}
		return lista;
	}
	
	@Transactional(readOnly = true)
	public UserIdentity obtenerPorId(int id) {
		logger.debug("Iniciando operacion obtenerPorId:{" + String.valueOf(id) + "}");
		Optional<UserIdentity>  item = userIdentityJPA.findById(id);
		if (!item.isPresent()) {
			logger.warn("No se encontro el usuario");
			throw new RuntimeException("No se encontro el usuario");

	}
		return item.get();
	}

	@Transactional(readOnly = true)
	public UserIdentity obtenerPorUserName(String username) {
		logger.debug("Iniciando operacion obtenerPorUserName:{" + String.valueOf(username) + "}");
		UserIdentity item = userIdentityJPA.findByUsername(username);
		
		if (null==item) {
			logger.warn("No se encontro el usuario");
			throw new RuntimeException("No se encontro el usuario");

	}
		return item;
	}

	@Transactional(readOnly = false)
	public  UserIdentity insertar(UserIdentity entidad) {
		logger.debug("Iniciando operacion insertar UserIdentity", entidad);

			UserIdentity item = userIdentityJPA.findByUsername(entidad.getUsername());

			if (!(null==item)) {
				logger.warn("Entidad no cumplio la validacion de negocio {UserIdentityLog}", entidad);
				throw new RuntimeException("Error Llave Unica");

		}
			entidad.setPassword(bCryptPasswordEncoder.encode(entidad.getPassword()));
			userIdentityJPA.save(entidad);

		return entidad;
	}

	@Transactional(readOnly = false)
	public void actualizar(UserIdentity entidad) {
		logger.debug("Iniciando operacion actualizar:{}", entidad);
		userIdentityJPA.save(entidad);
	}

	@Transactional(readOnly = false)
	public void eliminar(String userName) {
		logger.debug("Iniciando operacion eliminar:{" + userName + "}");
		UserIdentity item = userIdentityJPA.findByUsername(userName);
		userIdentityJPA.deleteById(item.getId());
	}

}

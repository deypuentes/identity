package com.staging.identity.conversor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.staging.identity.dominio.UserIdentity;
import com.staging.identity.modelo.UserIdentityDTO;


@Component
public class UserIdentityConversor {

	
	public UserIdentityDTO entidadADto(UserIdentity entidad) {
		UserIdentityDTO dto = new UserIdentityDTO();
		dto.setId(entidad.getId());
		dto.setUsername(entidad.getUsername());
		dto.setPassword(entidad.getPassword());
		dto.setRoles(entidad.getRoles());
		return dto;
	}
	
	public  UserIdentity dtoAEntidad(UserIdentityDTO dto) {
		UserIdentity entidad = new UserIdentity();
		entidad.setId(dto.getId());
		entidad.setUsername(dto.getUsername());
		entidad.setPassword(dto.getPassword());
		entidad.setRoles(dto.getRoles());
		return entidad;
	}
	
	
	public List<UserIdentity> listaDtoAEntidad(List<UserIdentityDTO> listaModelo) {
        List<UserIdentity> listaEntidad = new ArrayList<>();

        listaModelo.forEach((modelo) -> {
            listaEntidad.add(dtoAEntidad(modelo));
        });

        return listaEntidad;
    }

    public List<UserIdentityDTO> listaEntidadADto(List<UserIdentity> listaEntidad) {
        List<UserIdentityDTO> listaModelo = new ArrayList<>();

        listaEntidad.forEach((entity) -> {
            listaModelo.add(entidadADto(entity));
        });

        return listaModelo;
    }
}

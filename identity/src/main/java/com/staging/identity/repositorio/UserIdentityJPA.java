package com.staging.identity.repositorio;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.staging.identity.dominio.UserIdentity;



@Repository
public interface UserIdentityJPA extends JpaRepository<UserIdentity, Integer>{
	
	UserIdentity findByUsername(String username);



}

package br.eti.esabreu.oauth2.authorizationserverjdbc.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.eti.esabreu.oauth2.authorizationserverjdbc.domain.User;
import br.eti.esabreu.oauth2.authorizationserverjdbc.dto.UserDto;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findOneByUsername(String username);
	
	public UserDto findOneByUuid(UUID uuid);
}
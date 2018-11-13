package br.eti.esabreu.oauth2.authorizationserver.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.eti.esabreu.oauth2.authorizationserver.domain.User;
import br.eti.esabreu.oauth2.authorizationserver.dto.UserDto;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findOneByUsername(String username);
	
	public UserDto findOneByUuid(UUID uuid);
}
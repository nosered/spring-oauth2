package br.eti.esabreu.oauth2.authorizationserverjdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.eti.esabreu.oauth2.authorizationserverjdbc.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findOneByUsername(String username);
}

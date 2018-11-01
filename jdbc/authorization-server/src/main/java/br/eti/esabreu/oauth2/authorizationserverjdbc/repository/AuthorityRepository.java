package br.eti.esabreu.oauth2.authorizationserverjdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.eti.esabreu.oauth2.authorizationserverjdbc.domain.Authority;
import br.eti.esabreu.oauth2.authorizationserverjdbc.enums.AuthorityEnum;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
	
	public Authority findOneByAuthority(AuthorityEnum authority);
}
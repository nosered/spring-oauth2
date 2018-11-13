package br.eti.esabreu.oauth2.authorizationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.eti.esabreu.oauth2.authorizationserver.domain.Authority;
import br.eti.esabreu.oauth2.authorizationserver.enums.AuthorityEnum;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
	
	public Authority findOneByAuthority(AuthorityEnum authority);
}
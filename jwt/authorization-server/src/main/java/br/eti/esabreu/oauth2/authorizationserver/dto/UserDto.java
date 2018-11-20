package br.eti.esabreu.oauth2.authorizationserver.dto;

import br.eti.esabreu.oauth2.authorizationserver.enums.AuthorityEnum;

public interface UserDto {
	
	String getUsername();
	Boolean getEnabled();
	AuthorityDto getAuthorities();
	
	interface AuthorityDto {
		AuthorityEnum getAuthority();
	}
}
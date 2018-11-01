package br.eti.esabreu.oauth2.authorizationserverjdbc.dto;

import br.eti.esabreu.oauth2.authorizationserverjdbc.enums.AuthorityEnum;

public interface UserDto {
	
	String getUsername();
	Boolean getEnabled();
	AuthorityDto getAuthorities();
	
	interface AuthorityDto {
		AuthorityEnum getAuthority();
	}
}
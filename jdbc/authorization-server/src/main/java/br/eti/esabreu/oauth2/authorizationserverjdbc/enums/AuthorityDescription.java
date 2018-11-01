package br.eti.esabreu.oauth2.authorizationserverjdbc.enums;

public enum AuthorityDescription {

	ADMIN("ADMIN"), USER("USER");
	
	private String authorityDescription;
	
	private AuthorityDescription(String authorityDescription) {
		this.authorityDescription = authorityDescription;
	}
	
	public String getAuthorityDescription() {
		return authorityDescription;
	}
}

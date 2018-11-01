package br.eti.esabreu.oauth2.authorizationserverjdbc.enums;

public enum AuthorityEnum {

	FOO_READ("FOO_READ"), FOO_WRITE("FOO_WRITE");
	
	private String authority;
	
	private AuthorityEnum(String authority) {
		this.authority = authority;
	}
	
	public String getAuthority() {
		return authority;
	}
}
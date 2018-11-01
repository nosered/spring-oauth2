package br.eti.esabreu.oauth2.resourceserver.security;

import org.springframework.stereotype.Component;

@Component
public class OAuth2RuleBuilder {
	
	private final String SCOPE_SESSION = "#oauth2.hasScope('session')";
	private final String SCOPE_SIGN = "#oauth2.hasScope('sign')";
	private final String ROLE_FOO_READ = "hasAuthority('FOO_READ')";
	private final String ROLE_FOO_WRITE = "hasAuthority('FOO_WRITE')";
	
	private String rule;
	
	public OAuth2RuleBuilder() {
		this.rule = "";
	}

	public String build() {
		return rule;
	}
	
	public OAuth2RuleBuilder and() {
		this.rule += " AND";
		return this;
	}
	
	public OAuth2RuleBuilder or() {
		this.rule += " OR";
		return this;
	}
	
	public OAuth2RuleBuilder hasScopeSession() {
		this.concat();
		this.rule += SCOPE_SESSION;
		return this;
	}
	
	public OAuth2RuleBuilder hasScopeSign() {
		this.concat();
		this.rule += SCOPE_SIGN;
		return this;
	}
	
	public OAuth2RuleBuilder hasRoleFooRead() {
		this.concat();
		this.rule += ROLE_FOO_READ;
		return this;
	}
	
	public OAuth2RuleBuilder hasRoleFooWrite() {
		this.concat();
		this.rule += ROLE_FOO_WRITE;
		return this;
	}
	
	private void concat() {
		if(this.rule.endsWith("AND") || this.rule.endsWith("OR")) {
			this.rule += " ";
		}
	}
}
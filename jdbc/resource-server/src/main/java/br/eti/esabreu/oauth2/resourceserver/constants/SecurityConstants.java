package br.eti.esabreu.oauth2.resourceserver.constants;

public class SecurityConstants {

	// CONFIGURATION
	public static final String CHECK_TOKEN_ENDPOINT_URL = "http://localhost:8081/oauth/check_token";
	public static final String SESSION_SCOPE_CLIENT_ID = "sessionclient";
	public static final String SESSION_SCOPE_CLIENT_SECRET = "123456";
	public static final String SIGN_SCOPE_CLIENT_ID = "signclient";
	public static final String SIGN_SCOPE_CLIENT_SECRET = "123456";
	
	// AUTHORIZATION RULES
	public static final String HAS_SCOPE_SESSION = "#oauth2.hasScope('session')";
	public static final String HAS_SCOPE_SIGN = "#oauth2.hasScope('sign')";
	public static final String HAS_AUTHORITY_FOO_READ = "hasAuthority('FOO_READ')";
	public static final String HAS_AUTHORITY_FOO_WRITE = "hasAuthority('FOO_WRITE')";
}
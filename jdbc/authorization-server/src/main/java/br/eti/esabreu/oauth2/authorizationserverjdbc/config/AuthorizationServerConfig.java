package br.eti.esabreu.oauth2.authorizationserverjdbc.config;

import static br.eti.esabreu.oauth2.authorizationserverjdbc.constants.SecurityConstants.CLIENT_DEFAULT_PASSWORD;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	@Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;
	
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	
	@Bean
	public JdbcTokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer serverSecurityConfig) {
		serverSecurityConfig
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clientDetailsConfig) throws Exception {
		clientDetailsConfig
			.jdbc(dataSource)
			.withClient("client")
			.secret(CLIENT_DEFAULT_PASSWORD)
			.authorizedGrantTypes("password", "refresh_token")
			.scopes("read", "write");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpointsConfig) {
		endpointsConfig
			.tokenStore(tokenStore())
			.authenticationManager(authenticationManager);
	}
}
package br.eti.esabreu.oauth2.authorizationserverjdbc.config;

import static br.eti.esabreu.oauth2.authorizationserverjdbc.constants.SecurityConstants.SESSION_SCOPE_CLIENT_ID;
import static br.eti.esabreu.oauth2.authorizationserverjdbc.constants.SecurityConstants.SESSION_SCOPE_CLIENT_SECRET;
import static br.eti.esabreu.oauth2.authorizationserverjdbc.constants.SecurityConstants.SIGN_SCOPE_CLIENT_ID;
import static br.eti.esabreu.oauth2.authorizationserverjdbc.constants.SecurityConstants.SIGN_SCOPE_CLIENT_SECRET;

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
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
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
	
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}
	
	public AccessTokenConverter accessTokenConverter() {
		return new DefaultAccessTokenConverter();
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
			.withClient(SESSION_SCOPE_CLIENT_ID)
			.secret(SESSION_SCOPE_CLIENT_SECRET)
			.scopes("session")
			.authorizedGrantTypes("password", "refresh_token")
			.accessTokenValiditySeconds(86_400) // 1 DAY
			.refreshTokenValiditySeconds(31_536_000) // 1 YEAR
			.resourceIds("session")
			.and()
			.withClient(SIGN_SCOPE_CLIENT_ID)
			.secret(SIGN_SCOPE_CLIENT_SECRET)
			.scopes("sign")
			.authorizedGrantTypes("password")
			.accessTokenValiditySeconds(15) // 15 SECONDS
			.resourceIds("sign");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpointsConfig) {
		endpointsConfig
			.tokenStore(tokenStore())
			.tokenEnhancer(tokenEnhancer())
			.accessTokenConverter(accessTokenConverter())
			.authenticationManager(authenticationManager);
	}
}
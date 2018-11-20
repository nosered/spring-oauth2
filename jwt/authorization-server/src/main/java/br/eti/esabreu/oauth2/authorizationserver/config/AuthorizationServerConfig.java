package br.eti.esabreu.oauth2.authorizationserver.config;

import static br.eti.esabreu.oauth2.authorizationserver.constants.SecurityConstants.SESSION_SCOPE_CLIENT_ID;
import static br.eti.esabreu.oauth2.authorizationserver.constants.SecurityConstants.SESSION_SCOPE_CLIENT_SECRET;
import static br.eti.esabreu.oauth2.authorizationserver.constants.SecurityConstants.SIGN_SCOPE_CLIENT_ID;
import static br.eti.esabreu.oauth2.authorizationserver.constants.SecurityConstants.SIGN_SCOPE_CLIENT_SECRET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("123456");
		return accessTokenConverter;
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(tokenStore());
		tokenServices.setSupportRefreshToken(true);
		return tokenServices;
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer serverSecurityConfig) {
		serverSecurityConfig
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpointConfig) throws Exception {
		endpointConfig
			.tokenStore(tokenStore())
			.accessTokenConverter(accessTokenConverter())
			.authenticationManager(authenticationManager);
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clientDetailsConfig) throws Exception {
		clientDetailsConfig
			.inMemory()
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
}
package br.eti.esabreu.oauth2.resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

import static br.eti.esabreu.oauth2.resourceserver.constants.SecurityConstants.CHECK_TOKEN_ENDPOINT_URL;
import static br.eti.esabreu.oauth2.resourceserver.constants.SecurityConstants.DEFAULT_CLIENT_ID;
import static br.eti.esabreu.oauth2.resourceserver.constants.SecurityConstants.DEFAULT_CLIENT_SECRET;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Primary
	@Bean
	public RemoteTokenServices tokenService() {
		RemoteTokenServices tokenService = new RemoteTokenServices();
		tokenService.setCheckTokenEndpointUrl(CHECK_TOKEN_ENDPOINT_URL);
		tokenService.setClientId(DEFAULT_CLIENT_ID);
		tokenService.setClientSecret(DEFAULT_CLIENT_SECRET);
		return tokenService;
	}
}
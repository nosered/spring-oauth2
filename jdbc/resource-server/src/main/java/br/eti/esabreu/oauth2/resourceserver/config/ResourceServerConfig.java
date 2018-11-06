package br.eti.esabreu.oauth2.resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

import static br.eti.esabreu.oauth2.resourceserver.constants.SecurityConstants.CHECK_TOKEN_ENDPOINT_URL;
import static br.eti.esabreu.oauth2.resourceserver.constants.SecurityConstants.SESSION_SCOPE_CLIENT_ID;
import static br.eti.esabreu.oauth2.resourceserver.constants.SecurityConstants.SESSION_SCOPE_CLIENT_SECRET;
import static br.eti.esabreu.oauth2.resourceserver.constants.SecurityConstants.SIGN_SCOPE_CLIENT_ID;
import static br.eti.esabreu.oauth2.resourceserver.constants.SecurityConstants.SIGN_SCOPE_CLIENT_SECRET;
import static br.eti.esabreu.oauth2.resourceserver.constants.SecurityConstants.HAS_SCOPE_SESSION;
import static br.eti.esabreu.oauth2.resourceserver.constants.SecurityConstants.HAS_SCOPE_SIGN;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ResourceServerConfig {
	
	@Bean
	public AccessTokenConverter accessTokenConverter() {
		return new DefaultAccessTokenConverter();
	}
	
	@Bean
	protected ResourceServerConfiguration sessionResourceServerConfig() {
		ResourceServerConfiguration resourceServerConfig = new ResourceServerConfiguration() {
			public void setConfigurers(List<ResourceServerConfigurer> configurers) {
				super.setConfigurers(configurers);
			}

			@Override
			public int getOrder() {
				return 30;
			}
		};
		
		resourceServerConfig.setConfigurers(Arrays.<ResourceServerConfigurer>asList(new ResourceServerConfigurerAdapter() {
			@Override
			public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
				resources.resourceId("session").tokenServices(sessionTokenServices());
			}
			
			@Bean
			public RemoteTokenServices sessionTokenServices() {
				RemoteTokenServices tokenService = new RemoteTokenServices();
				tokenService.setCheckTokenEndpointUrl(CHECK_TOKEN_ENDPOINT_URL);
				tokenService.setClientId(SESSION_SCOPE_CLIENT_ID);
				tokenService.setClientSecret(SESSION_SCOPE_CLIENT_SECRET);
				tokenService.setAccessTokenConverter(accessTokenConverter());
				return tokenService;
			}

			@Override
			public void configure(HttpSecurity http) throws Exception {
				http
					.antMatcher("/session/**")
					.authorizeRequests()
					.anyRequest()
					.access(HAS_SCOPE_SESSION)
					.and()
					.csrf().disable();
			}
		}));
		return resourceServerConfig;
	}
	
	@Bean
	protected ResourceServerConfiguration signResourceServerConfig() {
		ResourceServerConfiguration resourceServerConfig = new ResourceServerConfiguration() {
			public void setConfigurers(List<ResourceServerConfigurer> configurers) {
				super.setConfigurers(configurers);
			}

			@Override
			public int getOrder() {
				return 40;
			}
		};
		
		resourceServerConfig.setConfigurers(Arrays.<ResourceServerConfigurer>asList(new ResourceServerConfigurerAdapter() {
			@Override
			public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
				resources.resourceId("sign").tokenServices(signTokenServices());
			}
			
			@Bean
			public RemoteTokenServices signTokenServices() {
				RemoteTokenServices tokenService = new RemoteTokenServices();
				tokenService.setCheckTokenEndpointUrl(CHECK_TOKEN_ENDPOINT_URL);
				tokenService.setClientId(SIGN_SCOPE_CLIENT_ID);
				tokenService.setClientSecret(SIGN_SCOPE_CLIENT_SECRET);
				tokenService.setAccessTokenConverter(accessTokenConverter());
				return tokenService;
			}
			
			@Override
			public void configure(HttpSecurity http) throws Exception {
				http
					.antMatcher("/sign/**")
					.authorizeRequests()
					.anyRequest()
					.access(HAS_SCOPE_SIGN)
					.and()
					.csrf().disable();
			}
		}));
		return resourceServerConfig;
	}
}
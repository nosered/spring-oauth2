package br.eti.esabreu.oauth2.authorizationserver.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class PostAuthenticationFilter implements ResponseBodyAdvice<OAuth2AccessToken> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken accessToken, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
		HttpServletRequest httpRequest = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse httpResponse = ((ServletServerHttpResponse) response).getServletResponse();
		
		moveRefreshTokenToCookie(accessToken, httpRequest, httpResponse);

		return accessToken;
	}
	
	private void moveRefreshTokenToCookie(OAuth2AccessToken accessToken, HttpServletRequest request, HttpServletResponse response) {
		DefaultOAuth2AccessToken defaultAccessToken = (DefaultOAuth2AccessToken) accessToken;
		
		// Add refresh token to secure http cookie
		Cookie refreshToken = new Cookie("refresh_token", defaultAccessToken.getRefreshToken().getValue());
		refreshToken.setHttpOnly(true);
		refreshToken.setSecure(false); // TODO: true if production
		refreshToken.setPath(request.getContextPath() + "/oauth/token");
		refreshToken.setMaxAge(31_536_000);
		response.addCookie(refreshToken);
		
		// Remove refresh token from body
		defaultAccessToken.setRefreshToken(null);
	}	
}
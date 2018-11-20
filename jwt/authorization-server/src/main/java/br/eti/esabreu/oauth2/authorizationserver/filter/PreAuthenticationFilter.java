package br.eti.esabreu.oauth2.authorizationserver.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PreAuthenticationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
			
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if ("/oauth/token".equalsIgnoreCase(httpRequest.getRequestURI()) 
				&& "refresh_token".equals(httpRequest.getParameter("grant_type"))
				&& httpRequest.getCookies() != null) {
			for (Cookie cookie : httpRequest.getCookies()) {
				if (cookie.getName().equals("refresh_token")) {
					String refreshToken = cookie.getValue();
					httpRequest = new CustomHttpServletRequestWrapper(httpRequest, refreshToken);
				}
			}
		}
		
		chain.doFilter(httpRequest, response);
	}
	
	private class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
		private String refreshToken;
		
		public CustomHttpServletRequestWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
		}
		
		@Override
		public Map<String, String[]> getParameterMap() {
			ParameterMap<String, String[]> parameters = new ParameterMap<String, String[]>(getRequest().getParameterMap());
			parameters.put("refresh_token", new String[] { this.refreshToken });
			parameters.setLocked(true);
			return parameters;
		}
	}
}
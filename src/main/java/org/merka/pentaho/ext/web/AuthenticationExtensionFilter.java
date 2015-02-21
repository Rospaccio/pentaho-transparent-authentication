package org.merka.pentaho.ext.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.platform.api.engine.security.userroledao.IUserRoleDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.ldap.SpringSecurityContextSource;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.SpringSecurityFilter;

public class AuthenticationExtensionFilter extends SpringSecurityFilter implements InitializingBean {

	private static final Log log = LogFactory.getLog(AuthenticationExtensionFilter.class);
	
	private IUserRoleDao userRoleDao;
	private AuthenticationManager authenticationManager;
	
	public IUserRoleDao getUserRoleDao() {
		return userRoleDao;
	}

	public void setUserRoleDao(IUserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public int getOrder() {
		return FilterChainOrder.AUTHENTICATION_PROCESSING_FILTER;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	@Override
	protected void doFilterHttp(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain) throws IOException,
			ServletException 
	{
				try
				{
					if(mustIgnore(request)){
						return;
					}
					
					
				}
				catch(NoClassDefFoundError e){
					log.error("An error occurred during the authentication process", e);
				}
				catch(Exception ex){
					log.error("an exception occurred duringthe authentication process", ex);
				}
				finally{
					filterChain.doFilter(request, response);
				}
	}

	private boolean mustIgnore(HttpServletRequest request) {
		return false;
	}

}

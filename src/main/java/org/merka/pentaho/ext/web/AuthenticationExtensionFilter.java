package org.merka.pentaho.ext.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.merka.pentaho.ext.exception.UserNotFoundException;
import org.merka.pentaho.ext.security.ExtensionAuthenticationToken;
import org.merka.pentaho.ext.service.UsernameProvider;
import org.pentaho.platform.api.engine.security.userroledao.IPentahoRole;
import org.pentaho.platform.api.engine.security.userroledao.IPentahoUser;
import org.pentaho.platform.api.engine.security.userroledao.IUserRoleDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.SpringSecurityFilter;
import org.springframework.security.ui.WebAuthenticationDetails;

public class AuthenticationExtensionFilter extends SpringSecurityFilter implements InitializingBean {

	private static final Log log = LogFactory.getLog(AuthenticationExtensionFilter.class);
	
	private UsernameProvider usernameProvider;
	private IUserRoleDao userRoleDao;
	private AuthenticationManager authenticationManager;
	
	public UsernameProvider getUsernameProvider() {
		return usernameProvider;
	}

	public void setUsernameProvider(UsernameProvider usernameProvider) {
		this.usernameProvider = usernameProvider;
	}

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
										
					// gets the username of the user that is requesting authentication
					String requestingUserName = getUsernameProvider().getUsername();
					if(requestingUserName != null && !"".equals(requestingUserName))
					{
						authenticateUser(requestingUserName, request);
					}
					else
					{
						log.warn("Requesting username is not available, continuing with the filter chain");
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

	private void authenticateUser(String requestingUserName, HttpServletRequest request) throws UserNotFoundException 
	{
		IPentahoUser user = getUserRoleDao().getUser(null, requestingUserName);
		if(user == null){
			// TODO: implement alternative behavior if needed, e.g. create the user if 
			// it does not exist
			throw new UserNotFoundException("User '" + requestingUserName + "' not found in the current system using the default UserRoleDao bean");
		}
		
		List<IPentahoRole> roles = getUserRoleDao().getUserRoles(null, requestingUserName);
		GrantedAuthority[] authorities = new GrantedAuthority[roles.size()];
		int index = 0;
		for(IPentahoRole role : roles){
			authorities[index] = new GrantedAuthorityImpl(role.getName());
		}
		ExtensionAuthenticationToken authRequestToken = new ExtensionAuthenticationToken(requestingUserName, null, authorities);
		authRequestToken.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticationOutcome = getAuthenticationManager().authenticate(authRequestToken);
		
		// TODO: manage possible errors (authenticationOutcome == null, Exception, etc...)
		SecurityContextHolder.getContext().setAuthentication(authenticationOutcome);
	}

	private boolean mustIgnore(HttpServletRequest request) {
		Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
		if(currentAuthentication != null && currentAuthentication.isAuthenticated()){
			return true;
		}
		// TODO: implement other conditions.
		return false;
	}

}

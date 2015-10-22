/*
	This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.merka.pentaho.ext.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.merka.pentaho.ext.exception.UserNotFoundException;
import org.merka.pentaho.ext.security.ExtensionAuthenticationToken;
import org.merka.pentaho.ext.service.UsernameProvider;
import org.merka.pentaho.ext.ticket.LoginTicket;
import org.merka.pentaho.ext.ticket.LoginTicketManager;
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

public class AuthenticationExtensionFilter extends SpringSecurityFilter implements InitializingBean
{
	public static final String AUTOLOGIN_PARAM_NAME = "autologin";

	private static final Log log = LogFactory.getLog(AuthenticationExtensionFilter.class);

	public static final String TICKET_PARAM_NAME = "ticket";

	private UsernameProvider usernameProvider;
	private IUserRoleDao userRoleDao;
	private AuthenticationManager authenticationManager;
	private LoginTicketManager loginTicketManager;

	public UsernameProvider getUsernameProvider()
	{
		return usernameProvider;
	}

	public void setUsernameProvider(UsernameProvider usernameProvider)
	{
		this.usernameProvider = usernameProvider;
	}

	public IUserRoleDao getUserRoleDao()
	{
		return userRoleDao;
	}

	public void setUserRoleDao(IUserRoleDao userRoleDao)
	{
		this.userRoleDao = userRoleDao;
	}

	public AuthenticationManager getAuthenticationManager()
	{
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager)
	{
		this.authenticationManager = authenticationManager;
	}

	public LoginTicketManager getLoginTicketManager()
	{
		return loginTicketManager;
	}

	public void setLoginTicketManager(LoginTicketManager loginTicketManager)
	{
		this.loginTicketManager = loginTicketManager;
	}

	@Override
	public int getOrder()
	{
		return FilterChainOrder.AUTHENTICATION_PROCESSING_FILTER;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{

	}

	@Override
	protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException
	{
		if (!mustIgnore(request))
		{
			try
			{
				String ticketId = request.getParameter(TICKET_PARAM_NAME);
				if (StringUtils.isEmpty(ticketId))
				{
					throw new IllegalArgumentException("The required parameter " + TICKET_PARAM_NAME + " is not set");
				}

				// checks with the ticket manager whether the ticket is valid
				LoginTicket ticket = null;

				try
				{
					ticket = loginTicketManager.removeTicket(ticketId);
				}
				catch (Exception e)
				{
					throw e;
				}

				// TODO: read app name and username from the ticket object and
				// search for
				// the pentaho user mapped to those parameters.

				// gets the username of the user that is requesting
				// authentication
				String requestingUserName = getUsernameProvider().getUsername(ticket.getRequistingApplication(),
						ticket.getRequestingApplicationUsername());
				if (requestingUserName != null && !"".equals(requestingUserName))
				{
					authenticateUser(requestingUserName, request);
				}
				else
				{
					log.warn("Requesting username is not available, continuing with the filter chain");
				}
				String rebuiltURL = request.getRequestURL() + "?" + request.getQueryString();
				logger.info("request URI = " + rebuiltURL);
				// TODO: build this URL in a safer way, possibly.
				String refinedURI = rebuiltURL.replace("autologin=true&", "");
				logger.info("redirect URI = " + refinedURI);				
				// Why a redirect? it is the best way to deal with unwanted 
				// filter chain's interactions when acccessing URL behind /plugin, /api, etc...
				response.sendRedirect(refinedURI);
			}
			catch (NoClassDefFoundError e)
			{
				log.error("An error occurred during the authentication process", e);
				filterChain.doFilter(request, response);
			}
			catch (Exception ex)
			{
				log.error("An exception occurred during the authentication process", ex);
				filterChain.doFilter(request, response);
			}
		}
		else
		{
			filterChain.doFilter(request, response);
		}
	}

	private void authenticateUser(String requestingUserName, HttpServletRequest request) throws UserNotFoundException
	{
		IPentahoUser user = getUserRoleDao().getUser(null, requestingUserName);
		if (user == null)
		{
			// TODO: implement alternative behavior if needed, e.g. create the
			// user if it does not exist
			throw new UserNotFoundException("User '" + requestingUserName
					+ "' not found in the current system using the default UserRoleDao bean");
		}

		List<IPentahoRole> roles = getUserRoleDao().getUserRoles(null, requestingUserName);
		GrantedAuthority[] authorities = new GrantedAuthority[roles.size()];
		int index = 0;
		for (IPentahoRole role : roles)
		{
			authorities[index] = new GrantedAuthorityImpl(role.getName());
		}
		ExtensionAuthenticationToken authRequestToken = new ExtensionAuthenticationToken(requestingUserName, null,
				authorities);
		authRequestToken.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticationOutcome = getAuthenticationManager().authenticate(authRequestToken);

		// TODO: manage possible errors (authenticationOutcome == null,
		// Exception, etc...)
		SecurityContextHolder.getContext().setAuthentication(authenticationOutcome);
	}

	private boolean mustIgnore(HttpServletRequest request)
	{
		Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
		if (currentAuthentication != null && currentAuthentication.isAuthenticated())
		{
			return true;
		}

		String autologinParam = request.getParameter(AUTOLOGIN_PARAM_NAME);
		if (!"true".equals(autologinParam))
		{
			return true;
		}

		// TODO: implement other conditions if appropriate.
		return false;
	}

}

package org.merka.pentaho.ext.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.merka.pentaho.ext.exception.ExternalAppNotMappedException;
import org.merka.pentaho.ext.service.UsernameProvider;
import org.merka.pentaho.ext.ticket.LoginTicket;
import org.merka.pentaho.ext.ticket.LoginTicketManager;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.SpringSecurityFilter;

/**
 * A filter that issues login tickets.
 * @author merka
 *
 */
public class LoginTicketGeneratorFilter extends SpringSecurityFilter 
{
	private static final Log log = LogFactory.getLog(LoginTicketGeneratorFilter.class);
	
	public static final String GENERATE_TICKET_PARAM_NAME = "generate-ticket";
	public static final String REQUESTING_APP_PARAM_NAME = "app";
	public static final String REQUESTING_USERNAME_PARAM_NAME = "username";
	
	LoginTicketManager loginTicketManager;
	UsernameProvider usernameProvider;
	
	public LoginTicketManager getLoginTicketManager() {
		return loginTicketManager;
	}

	public void setLoginTicketManager(LoginTicketManager loginTicketManager) {
		this.loginTicketManager = loginTicketManager;
	}
	
	public UsernameProvider getUsernameProvider()
	{
		return usernameProvider;
	}

	public void setUsernameProvider(UsernameProvider usernameProvider)
	{
		this.usernameProvider = usernameProvider;
	}

	@Override
	public int getOrder() {
		return FilterChainOrder.PRE_AUTH_FILTER;
	}

	@Override
	protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException 
	{
		String ticketParameter = request.getParameter(GENERATE_TICKET_PARAM_NAME);
		if(! StringUtils.isEmpty(ticketParameter))
		{
			if(SecurityContextHolder.getContext().getAuthentication() == null)
			{
				log.error("No authentication found: authentication is mandatory to process the request!");
				sendError(response, "No authentication found: authentication is mandatory to process the request!");
				response.flushBuffer();
				return;
			}
			
			String appName = request.getParameter(REQUESTING_APP_PARAM_NAME);
			if(StringUtils.isEmpty(appName))
			{
				sendError(response, "Required parameter " + REQUESTING_APP_PARAM_NAME + " is not set");
				return;
			}
			
			String username = request.getParameter(REQUESTING_USERNAME_PARAM_NAME);
			if(StringUtils.isEmpty(username))
			{
				sendError(response, "Required parameter " + REQUESTING_USERNAME_PARAM_NAME + " is not set");
				return;
			}
						
			LoginTicket ticket = null;
			try
			{
				ticket = getLoginTicketManager().generateNewTicket(appName, username);
			}
			catch(ExternalAppNotMappedException e)
			{
				sendError(response, "Application name '" + appName + "' was not found in the current Pentaho mappings");
				return;
			}
			// TODO: use a cleaner way to output JSON, maybe?
			response.getWriter().write("{\"ticketId\": \"" + ticket.getIdAsString() + "\"}");
			response.flushBuffer();
			return;
		}
		else
		{
			chain.doFilter(request, response);
		}
	}
	
	private void sendError(HttpServletResponse response, String message) throws IOException
	{
		response.sendError(500, message);
		response.flushBuffer();
	}

}

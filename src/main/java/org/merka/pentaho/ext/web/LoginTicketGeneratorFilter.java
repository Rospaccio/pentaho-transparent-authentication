package org.merka.pentaho.ext.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.merka.pentaho.ext.ticket.LoginTicket;
import org.merka.pentaho.ext.ticket.LoginTicketManager;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.SpringSecurityFilter;
import org.springframework.web.util.HtmlUtils;

/**
 * A filter that issues login tickets.
 * @author merka
 *
 */
public class LoginTicketGeneratorFilter extends SpringSecurityFilter 
{
	public static final String GENERATE_TICKET_PARAM_NAME = "generate-ticket";
	public static final String REQUESTING_APP_PARAM_NAME = "app";
	public static final String REQUESTING_USERNAME_PARAM_NAME = "username";
	
	LoginTicketManager loginTicketManager;
	
	public LoginTicketManager getLoginTicketManager() {
		return loginTicketManager;
	}

	public void setLoginTicketManager(LoginTicketManager loginTicketManager) {
		this.loginTicketManager = loginTicketManager;
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
			
			LoginTicket ticket = getLoginTicketManager().generateNewTicket(appName, username);
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

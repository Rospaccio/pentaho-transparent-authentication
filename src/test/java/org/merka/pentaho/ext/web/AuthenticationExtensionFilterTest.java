package org.merka.pentaho.ext.web;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.merka.pentaho.ext.service.InMemoryUsernameProvider;
import org.merka.pentaho.ext.ticket.LoginTicket;
import org.merka.pentaho.ext.ticket.LoginTicketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/web-test-context.xml"})
public class AuthenticationExtensionFilterTest {

	@Autowired
	AuthenticationExtensionFilter authFilter;
	
	@Autowired
	LoginTicketManager loginTicketManager;
	
	@Autowired 
	InMemoryUsernameProvider usernameProvider;

	@Before
	public void addUsernameMappings(){
		usernameProvider.addMapping("test", "externalTestUser", "admin");
	}
	
	//@After
	public void removeUserMappings()
	{
		usernameProvider.removeMapping("test", "externalTestUser");
	}
	
	@Test
	public void testDoFilter() throws IOException, ServletException
	{
		assertNotNull(loginTicketManager);
		
		//makes the ticket manager issue a ticket
		LoginTicket ticket = loginTicketManager.generateNewTicket("test", "externalTestUser");
		String ticketId = ticket.getIdAsString();
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		
		request.addParameter(AuthenticationExtensionFilter.AUTOLOGIN_PARAM_NAME, "true");
		request.addParameter(AuthenticationExtensionFilter.TICKET_PARAM_NAME, ticketId);
		
		authFilter.doFilter(request, response, chain);
		String content = response.getContentAsString();
		assertNotNull(content);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		assertNotNull(auth);
	}
	
}

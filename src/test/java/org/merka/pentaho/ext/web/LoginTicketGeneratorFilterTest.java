package org.merka.pentaho.ext.web;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.netbeans.mdr.persistence.btreeimpl.btreestorage.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/web-test-context.xml"})
public class LoginTicketGeneratorFilterTest {
	
	@Autowired
	LoginTicketGeneratorFilter loginTicketGeneratorFilter;
	
	@Test
	public void testDoFilter() throws IOException, ServletException 
	{
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		
		request.addParameter(LoginTicketGeneratorFilter.GENERATE_TICKET_PARAM_NAME, "1");
		request.addParameter(LoginTicketGeneratorFilter.REQUESTING_APP_PARAM_NAME, "test");
		request.addParameter(LoginTicketGeneratorFilter.REQUESTING_USERNAME_PARAM_NAME, "testUser");
		
		loginTicketGeneratorFilter.doFilter(request, response, chain);
		
		assertNotNull(response);
		assertEquals(200, response.getStatus());
		String content = response.getContentAsString();
		assertNotNull(content);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(content);
		assertNotNull(node.get("ticketId"));
		JsonNode valueNode = node.findPath("ticketId");
		assertNotNull(valueNode);
		assertTrue(valueNode instanceof TextNode);
		TextNode textNode = (TextNode)valueNode;
		UUID uuid = new UUID(textNode.textValue());
		assertNotNull(uuid);
	}
	
	@Test
	public void testDofilterNoParam() throws IOException, ServletException
	{
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		
		request.addParameter(LoginTicketGeneratorFilter.GENERATE_TICKET_PARAM_NAME, "1");
		request.addParameter(LoginTicketGeneratorFilter.REQUESTING_APP_PARAM_NAME, "test");
		
		loginTicketGeneratorFilter.doFilter(request, response, chain);
		
		assertNotNull(response);
		assertEquals(500, response.getStatus());
		
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		chain = new MockFilterChain();
		
		request.addParameter(LoginTicketGeneratorFilter.GENERATE_TICKET_PARAM_NAME, "1");
		request.addParameter(LoginTicketGeneratorFilter.REQUESTING_USERNAME_PARAM_NAME, "testUser");
		
		loginTicketGeneratorFilter.doFilter(request, response, chain);
		
		assertNotNull(response);
		assertEquals(500, response.getStatus());
	}

}

package org.merka.pentaho.ext.web;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
		
		loginTicketGeneratorFilter.doFilter(request, response, chain);
		
		assertNotNull(response);
		String content = response.getContentAsString();
		assertNotNull(content);
		
	}

}

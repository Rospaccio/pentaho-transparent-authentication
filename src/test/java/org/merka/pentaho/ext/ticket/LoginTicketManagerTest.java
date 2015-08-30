package org.merka.pentaho.ext.ticket;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.merka.pentaho.ext.exception.LoginTicketNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/utility-test-context.xml"})
public class LoginTicketManagerTest {

	@Autowired
	private LoginTicketManager manager;
	
	@Test
	public void testCreateNewTicket() throws LoginTicketNotFoundException 
	{
		assertNotNull(manager);
		LoginTicket ticket = manager.generateNewTicket("test", "testUser");
		assertNotNull(ticket);
		assertFalse(ticket.isExpired());
		
		LoginTicket removed = manager.removeTicket(ticket.getIdAsString());
		assertEquals(ticket.getId(), removed.getId());
		assertEquals(ticket.getIdAsString(), removed.getIdAsString());
		assertEquals(ticket.getCreationTime(), removed.getCreationTime());
		assertEquals(ticket.getRequistingApplication(), removed.getRequistingApplication());
		assertEquals(ticket.getRequestingApplicationUsername(), removed.getRequestingApplicationUsername());
		assertEquals(ticket.getValidityDuration(), removed.getValidityDuration());
	}
}

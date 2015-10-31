package org.merka.pentaho.ext.ticket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.merka.pentaho.ext.exception.ExternalAppNotMappedException;
import org.merka.pentaho.ext.exception.LoginTicketNotFoundException;
import org.merka.pentaho.ext.service.InMemoryUsernameProvider;
import org.merka.pentaho.ext.service.UsernameProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/utility-test-context.xml" })
public class LoginTicketManagerTest
{

	@Autowired
	private LoginTicketManager manager;

	@Before
	public void addMappings()
	{
		InMemoryUsernameProvider usernameProvider = new InMemoryUsernameProvider();
		usernameProvider.addMapping("test", "testUser", "testUser");
		manager.setUsernameProvider(usernameProvider);
	}

	@Test
	public void testGenerateNewTicket() throws LoginTicketNotFoundException, ExternalAppNotMappedException
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
		assertEquals(0, manager.getSize());
	}
	
	@Test
	public void testGenerateTicketsOverThreshold() throws ExternalAppNotMappedException
	{
		int oldValidity = manager.getTicketValiditySeconds();
		int testLimit = LoginTicketManager.TICKETS_CLEANING_THRESHOLD * 3;
		manager.setTicketValiditySeconds(0);
		for(int i = 0; i < testLimit; i++ )
		{
			manager.generateNewTicket("test", "testUser");
			long size = manager.getSize();
			assertTrue("Size = " + size, size <= LoginTicketManager.TICKETS_CLEANING_THRESHOLD);
		}
		manager.setTicketValiditySeconds(oldValidity);
	}

	@Test
	public void testGetSize() throws LoginTicketNotFoundException, ExternalAppNotMappedException
	{
		ArrayList<String> toRemove = new ArrayList<>();
		long initialSize = manager.getSize();
		assertTrue(initialSize >= 0);
		for (int i = 0; i < 10; i++)
		{
			LoginTicket ticket = manager.generateNewTicket("test", "testUser");
			assertEquals(i + 1 + initialSize, manager.getSize());
			toRemove.add(ticket.getIdAsString());
		}

		for (String id : toRemove)
		{
			manager.removeTicket(id);
		}
		assertTrue(manager.getSize() == initialSize);
	}

	@Test
	public void testDeleteExpired() throws ExternalAppNotMappedException
	{
		int oldValidity = manager.getTicketValiditySeconds();
		manager.setTicketValiditySeconds(0);
		manager.resetTickets();
		assertEquals(0, manager.getSize());
		int i = 0;
		for (; i < 100; i++)
		{
			manager.generateNewTicket("test", "testUser");
		}

		manager.deleteExpired();
		assertEquals(0, manager.getSize());
		manager.setTicketValiditySeconds(oldValidity);
	}

	@Test(expected = ExternalAppNotMappedException.class)
	public void testGenerateTicketWithWrongApp() throws ExternalAppNotMappedException
	{
		manager.generateNewTicket("IdoNOTexist", "doesntREALLYmatter");
	}
}

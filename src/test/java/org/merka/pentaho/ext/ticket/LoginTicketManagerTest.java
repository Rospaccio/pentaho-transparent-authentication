package org.merka.pentaho.ext.ticket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.merka.pentaho.ext.exception.LoginTicketNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/utility-test-context.xml" })
public class LoginTicketManagerTest
{

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
		assertEquals(0, manager.getSize());
	}

	@Test
	public void testGetSize() throws LoginTicketNotFoundException
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
		
		for(String id : toRemove)
		{
			manager.removeTicket(id);
		}
		assertTrue(manager.getSize() == initialSize);
	}

	@Test
	public void testDeleteExpired()
	{
		int oldValidity = manager.getTicketValiditySeconds();
		manager.setTicketValiditySeconds(0);
		assertEquals(0, manager.getSize());
		int i = 0;
		for (; i < 100; i++)
		{
			manager.generateNewTicket("test", "testUser");
		}

		assertEquals(i + 0, manager.getSize());
		manager.deleteExpired();
		assertEquals(0, manager.getSize());
		manager.setTicketValiditySeconds(oldValidity);
	}
}

package org.merka.pentaho.ext.ticket;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

public class LoginTicketTest {

	@Test
	public void testNewTicket() 
	{
		int validity = 10;
		DateTime start = new DateTime();
		LoginTicket ticket = LoginTicket.newTicket(validity);
		DateTime end = new DateTime();
		
		assertNotNull(ticket);
		assertEquals(validity, ticket.getValidityDuration().getStandardSeconds());
		assertTrue(ticket.getCreationTime().compareTo(start) >= 0);
		assertTrue(ticket.getCreationTime().compareTo(end) <= 0);
	}
	
	@Test
	public void testInvalidTicket() throws InterruptedException
	{
		int validity = 0;
		DateTime start = new DateTime();
		LoginTicket ticket = LoginTicket.newTicket(validity);
		Thread.sleep(5);
		DateTime end = new DateTime();
		
		DateTime elapsedInstant = ticket.getCreationTime().withDurationAdded(ticket.getValidityDuration(), 1);
		assertTrue(elapsedInstant.compareTo(end) <= 0);
		
		assertTrue(ticket.isExpired());
	}
}

package org.merka.pentaho.ext.ticket;

import java.util.HashMap;
import java.util.Map;

import org.merka.pentaho.ext.exception.LoginTicketNotFoundException;

public class LoginTicketManager 
{

	private Map<String, LoginTicket> tickets;
	
	
	public LoginTicketManager()
	{
		tickets = new HashMap<>();
	}

	/**
	 * Creates a new {@link LoginTicket} and registers it in the internal cache.
	 * @return
	 */
	public LoginTicket generateNewTicket() 
	{
		LoginTicket ticket = LoginTicket.newTicket(100);
		synchronized (this.tickets) {
			tickets.put(ticket.getIdAsString(), ticket);
		}
		return ticket;
	}
	
	public LoginTicket removeTicket(String ticketId) throws LoginTicketNotFoundException
	{
		LoginTicket removed = null;
		synchronized (this.tickets) {
			removed = tickets.remove(ticketId);
			if (removed == null)
			{
				throw new LoginTicketNotFoundException(ticketId);
			}
		}
		
		return removed;
	}
	
//	public boolean getTicketById(String ticketId)
//	{
//		LoginTicket found = null;
//		synchronized (tickets) 
//		{
//			found = tickets.get(ticketId);
//		}
//		return found;
//	}
}

package org.merka.pentaho.ext.ticket;

import java.util.HashMap;
import java.util.Map;

import org.merka.pentaho.ext.exception.LoginTicketNotFoundException;

public class LoginTicketManager 
{
	public static final int DAFAULT_TICKET_VALIDITY_SECONDS = 100;
	private Map<String, LoginTicket> tickets;
	
	public LoginTicketManager()
	{
		tickets = new HashMap<>();
	}

	/**
	 * Creates a new {@link LoginTicket} and registers it in the internal cache.
	 * @param requestingApplicationName The name of the application requesting the ticket. Can be an arbitrary string.
	 * @param requestingApplicationUsername The username (in the requesting application) of the user for which 
	 * the ticket is issued.
	 * @return The newly created {@code LoginTicket}.
	 */
	public LoginTicket generateNewTicket(String requestingApplicationName, String requestingApplicationUsername) 
	{
		LoginTicket ticket = LoginTicket.newTicket(DAFAULT_TICKET_VALIDITY_SECONDS, 
				requestingApplicationName, requestingApplicationUsername);
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

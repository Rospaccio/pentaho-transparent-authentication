package org.merka.pentaho.ext.ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.merka.pentaho.ext.exception.LoginTicketNotFoundException;
import org.pentaho.platform.util.logging.Logger;
import org.slf4j.LoggerFactory;

public class LoginTicketManager
{
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(LoginTicketManager.class);

	public static final int DEFAULT_TICKET_VALIDITY_SECONDS = 100;
	private Map<String, LoginTicket> tickets;

	private int ticketValiditySeconds;

	public int getTicketValiditySeconds()
	{
		return ticketValiditySeconds;
	}

	public void setTicketValiditySeconds(int ticketValiditySeconds)
	{
		this.ticketValiditySeconds = ticketValiditySeconds;
	}

	public LoginTicketManager()
	{
		tickets = new HashMap<>();
	}

	/**
	 * Creates a new {@link LoginTicket} and registers it in the internal cache.
	 * 
	 * @param requestingApplicationName
	 *            The name of the application requesting the ticket. Can be an
	 *            arbitrary string.
	 * @param requestingApplicationUsername
	 *            The username (in the requesting application) of the user for
	 *            which the ticket is issued.
	 * @return The newly created {@code LoginTicket}.
	 */
	public LoginTicket generateNewTicket(String requestingApplicationName, String requestingApplicationUsername)
	{
		if (getTicketValiditySeconds() <= 0)
		{
			logger.warn("WARN! ticket validity is <= 0 [value = " + getTicketValiditySeconds() + "]");
		}
		// deleteExpired();
		LoginTicket ticket = LoginTicket.newTicket(getTicketValiditySeconds(), requestingApplicationName,
				requestingApplicationUsername);
		synchronized (this.tickets)
		{
			tickets.put(ticket.getIdAsString(), ticket);
		}
		return ticket;
	}

	public LoginTicket removeTicket(String ticketId) throws LoginTicketNotFoundException
	{
		LoginTicket removed = null;
		synchronized (this.tickets)
		{
			removed = tickets.remove(ticketId);
			if (removed == null)
			{
				throw new LoginTicketNotFoundException(ticketId);
			}
		}

		return removed;
	}

	/**
	 * Scans the internal ticket list and deletes all the expired tickets.
	 */
	public void deleteExpired()
	{
		Set<Map.Entry<String, LoginTicket>> entries = tickets.entrySet();
		ArrayList<String> idToRemove = new ArrayList<>();
		synchronized (tickets)
		{
			for (Map.Entry<String, LoginTicket> entry : entries)
			{
				if (entry.getValue().isExpired())
				{
					idToRemove.add(entry.getValue().getIdAsString());

				}
			}

			for (String id : idToRemove)
			{
				try
				{
					removeTicket(id);
				}
				catch (LoginTicketNotFoundException e)
				{
					logger.error("DeleteExpired: tried to remove a ticket that cannot be found [id = " + id + "]: ", e);
				}
			}
		}
	}

	public long getSize()
	{
		return tickets.size();
	}

	// public boolean getTicketById(String ticketId)
	// {
	// LoginTicket found = null;
	// synchronized (tickets)
	// {
	// found = tickets.get(ticketId);
	// }
	// return found;
	// }
}

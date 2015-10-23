package org.merka.pentaho.ext.exception;

public class LoginTicketNotFoundException extends AuthenticationExtensionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4464369424022073371L;

	
	private String ticketId;


	public String getTicketId() {
		return ticketId;
	}


	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	
	public LoginTicketNotFoundException(String ticketId)
	{
		setTicketId(ticketId);
	}
}

package org.merka.pentaho.ext.ticket;

import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.Duration;

public class LoginTicket {

	public static int SECONDS_MILLIS_RATIO = 1000;
	
	private UUID id;
	private DateTime creationTime;
	private Duration validityDuration;
	/**
	 * The name of the external application requesting the ticket. It can be an arbitrary string.
	 */
	private String requistingApplication;
	/**
	 * The username (in the requesting application) of the user for which the ticket is issued.
	 */
	private String requestingApplicationUsername;
	
	public UUID getId() {
		return id;
	}
	
	public String getIdAsString()
	{
		return getId().toString();
	}
	
	public DateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(org.joda.time.DateTime creationTime) {
		this.creationTime = creationTime;
	}

	public Duration getValidityDuration() {
		return validityDuration;
	}

	public void setValidityDuration(Duration validityDuration) {
		this.validityDuration = validityDuration;
	}

	public String getRequistingApplication() {
		return requistingApplication;
	}

	public void setRequistingApplication(String requistingApplication) {
		this.requistingApplication = requistingApplication;
	}

	public String getRequestingApplicationUsername() {
		return requestingApplicationUsername;
	}

	public void setRequestingApplicationUsername(String requestingApplicationUsername) {
		this.requestingApplicationUsername = requestingApplicationUsername;
	}

	private LoginTicket(UUID id, DateTime creationtime, Duration validityDuration, 
			String requestingApplication, String requestingApplicationUsername)
	{
		this.id = id;
		this.setCreationTime(creationtime);
		this.setValidityDuration(validityDuration);
		setRequistingApplication(requestingApplication);
		setRequestingApplicationUsername(requestingApplicationUsername);
	}

	public static LoginTicket newTicket(int validityTimeSeconds, String requestingApplication, String requestingApplicationUsername)
	{
		UUID id = UUID.randomUUID();
		Duration duration = new Duration(validityTimeSeconds * SECONDS_MILLIS_RATIO);
		DateTime creationTime = new DateTime();
		LoginTicket ticket = new LoginTicket(id, creationTime, duration, requestingApplication, requestingApplicationUsername);
		return ticket;
	}
	
	public boolean isExpired()
	{
		DateTime now = new DateTime();
		DateTime expirationTime = getCreationTime().withDurationAdded(getValidityDuration(), 1);
		return now.compareTo(expirationTime) >= 0;
	}
}


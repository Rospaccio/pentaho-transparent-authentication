package org.merka.pentaho.ext.ticket;

import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.Duration;

public class LoginTicket {

	public static int SECONDS_MILLIS_RATIO = 1000;
	
	private UUID id;
	private DateTime creationTime;
	private Duration validityDuration;
	
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

	private LoginTicket(UUID id, DateTime creationtime, Duration validityDuration)
	{
		this.id = id;
		this.setCreationTime(creationtime);
		this.setValidityDuration(validityDuration);
	}

	public static LoginTicket newTicket(int validityTimeSeconds)
	{
		UUID id = UUID.randomUUID();
		Duration duration = new Duration(validityTimeSeconds * SECONDS_MILLIS_RATIO);
		DateTime creationTime = new DateTime();
		LoginTicket ticket = new LoginTicket(id, creationTime, duration);
		return ticket;
	}
	
	public boolean isExpired()
	{
		DateTime now = new DateTime();
		DateTime expirationTime = getCreationTime().withDurationAdded(getValidityDuration(), 1);
		return now.compareTo(expirationTime) >= 0;
	}
}


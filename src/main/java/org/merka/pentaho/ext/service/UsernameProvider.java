package org.merka.pentaho.ext.service;

public interface UsernameProvider {

	/**
	 * Gets the Pentaho user that has to be authenticated
	 * @return
	 */
	public String getUsername();
	
}

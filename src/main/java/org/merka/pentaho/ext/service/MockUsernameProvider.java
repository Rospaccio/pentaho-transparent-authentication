package org.merka.pentaho.ext.service;

public class MockUsernameProvider implements UsernameProvider {

	@Override
	public String getUsername() {
		return "admin";
	}

}

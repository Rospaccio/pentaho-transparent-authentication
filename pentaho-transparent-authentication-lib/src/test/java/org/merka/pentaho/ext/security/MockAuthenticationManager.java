package org.merka.pentaho.ext.security;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationManager;

@SuppressWarnings("unused")
public class MockAuthenticationManager implements AuthenticationManager {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Authentication mockAuth = authentication;
		return mockAuth;
	}

}

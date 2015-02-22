package org.merka.pentaho.ext.security;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.AbstractAuthenticationToken;

public class ExtensionAuthenticationToken extends AbstractAuthenticationToken{
	
	private Object principal;
	private Object credentials;
	
	public void setPrincipal(Object principal) {
		this.principal = principal;
	}

	public void setCredentials(Object credentials) {
		this.credentials = credentials;
	}

	public ExtensionAuthenticationToken(Object principal, Object credentials, GrantedAuthority[] arg0) {
		super(arg0);
		setPrincipal(principal);
		setCredentials(credentials);
	}

	@Override
	public Object getCredentials() {
		return this.credentials;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

}

/*
	This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.merka.pentaho.ext.security;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

public class ExtensionAuthenticationProvider implements AuthenticationProvider {

	private static final String AUTHENTICATED_AUTHORITY_NAME = "Authenticated";
	private Object principal;
	private Object credentials;
	
	public Object getPrincipal() {
		return principal;
	}

	public void setPrincipal(Object principal) {
		this.principal = principal;
	}

	public Object getCredentials() {
		return credentials;
	}

	public void setCredentials(Object credentials) {
		this.credentials = credentials;
	}

	@Override
	public Authentication authenticate(Authentication authenticationRequest)
			throws AuthenticationException {
		GrantedAuthority[] authorities = new GrantedAuthorityImpl[authenticationRequest.getAuthorities().length + 1];
		authorities[0] = new GrantedAuthorityImpl(AUTHENTICATED_AUTHORITY_NAME);
		int i = 1;
		for(GrantedAuthority originalAuth : authenticationRequest.getAuthorities()){
			authorities[i] = new GrantedAuthorityImpl(originalAuth.getAuthority());
		}
		
		UsernamePasswordAuthenticationToken authenticationOutcome = new UsernamePasswordAuthenticationToken(authenticationRequest.getPrincipal(), 
				authenticationRequest.getCredentials(), authorities);
		authenticationOutcome.setDetails(authenticationRequest.getDetails());
		return authenticationOutcome;
	}

	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return clazz.equals(ExtensionAuthenticationToken.class);
	}

}

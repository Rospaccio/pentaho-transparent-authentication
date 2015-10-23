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

import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.AbstractAuthenticationToken;

public class ExtensionAuthenticationToken extends AbstractAuthenticationToken{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8992885651997621514L;
	
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

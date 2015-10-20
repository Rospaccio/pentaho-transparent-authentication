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

package org.merka.pentaho.ext.service;

public interface UsernameProvider {

	/**
	 * Gets the Pentaho username, if any exists, that corresponds to the <code>externalUsername</code>
	 * of the requesting application.
	 * @param externalApplicationName the arbitrary name, as it is know to this pentaho-authentication-ext module, of an external application.
	 * @param externalUsername The username (in the external application) of the user to log in.
	 * @return The username of the Pentaho account corresponding to the external username.
	 */
	public String getUsername(String externalApplicationName, String externalUsername);
	
	/**
	 * Tells if {@code externalAppName} corresponds to a mapped application
	 * in this {@code UsernameProvider}. 
	 * @param externalAppName The name of the external application.
	 * @return {@code true}, if {@code externalAppName} is found in the internal mappings, {@code false} otherwise.
	 */
	public boolean isAppNameMapped(String externalAppName);
}

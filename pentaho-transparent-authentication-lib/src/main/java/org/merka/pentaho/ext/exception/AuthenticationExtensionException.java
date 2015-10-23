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

package org.merka.pentaho.ext.exception;

public class AuthenticationExtensionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7190410544091092231L;

	public AuthenticationExtensionException() {
		super();
	}

	public AuthenticationExtensionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AuthenticationExtensionException(String arg0) {
		super(arg0);
	}

	public AuthenticationExtensionException(Throwable arg0) {
		super(arg0);
	}
}

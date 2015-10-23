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

public class UserNotFoundException extends AuthenticationExtensionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8468087556267337521L;

	public UserNotFoundException() {
		super();
		
	}

	public UserNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

	public UserNotFoundException(String arg0) {
		super(arg0);
		
	}

	public UserNotFoundException(Throwable arg0) {
		super(arg0);
		
	}

	
	
}

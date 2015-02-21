package org.merka.pentaho.ext.service;

import org.pentaho.platform.api.engine.security.userroledao.IPentahoUser;

public interface UserProvider {

	/**
	 * Gets the Pentaho user that has to be authenticated
	 * @return
	 */
	public IPentahoUser getUser();
	
}

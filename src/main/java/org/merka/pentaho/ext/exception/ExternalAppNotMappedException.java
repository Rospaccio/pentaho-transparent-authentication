package org.merka.pentaho.ext.exception;

public class ExternalAppNotMappedException extends AuthenticationExtensionException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1825470253538514591L;

	private String externalAppName;
	
	public String getExternalAppName()
	{
		return externalAppName;
	}

	public void setExternalAppName(String externalAppName)
	{
		this.externalAppName = externalAppName;
	}

	public ExternalAppNotMappedException(String externalAppName)
	{
		super();
		setExternalAppName(externalAppName);
	}

	public ExternalAppNotMappedException(String message, Throwable cause, String externalAppName)
	{
		super(message, cause);		
		setExternalAppName(externalAppName);
	}

	public ExternalAppNotMappedException(String message, String externalAppName)
	{
		super(message);		
		setExternalAppName(externalAppName);
	}

	public ExternalAppNotMappedException(Throwable cause)
	{
		super(cause);		
	}

}

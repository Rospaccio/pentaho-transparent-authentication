package org.merka.pentaho.ext.exception;

public class MappingNotFoundException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8940546369281598828L;

	public MappingNotFoundException()
	{
	}

	public MappingNotFoundException(String message)
	{
		super(message);
	}

	public MappingNotFoundException(Throwable cause)
	{
		super(cause);
	}

	public MappingNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}
}

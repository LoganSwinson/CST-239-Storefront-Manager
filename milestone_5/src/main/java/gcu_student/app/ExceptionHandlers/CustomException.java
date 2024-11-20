package gcu_student.app.ExceptionHandlers;

/**
 * A custom exception that is very basic for now
 */
public class CustomException extends Exception
{
    private static final long serialVersionUID = 5318008L;

	/**
	 * A non-default constructor that allows the Exception to have a custom error message
	 * @param msg Input string
	 */
    public CustomException(String msg)
	{
		super(msg);
	}
	
	/**
	 * Non-default constructor to support Exception Wrapping (to avoid losing some of the Stack Trace)
	 * @param e Source of exception.
	 * @param msg Custom error message for exception.
	 */
	public CustomException(Throwable e, String msg)
	{
		super(msg,e);
	}
}

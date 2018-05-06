package il.ac.hit.model;
/**
 * 
 * @author linoy and liana
 *
 */
public class TodolistException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * @param msg
	 */
	public TodolistException (String msg) {
		super (msg);
	}
	/**
	 * 
	 * @param desc
	 * @param throwable
	 */
	public TodolistException (String desc , Throwable throwable)
	{
		super (desc , throwable);
	}

}

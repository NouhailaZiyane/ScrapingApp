package shared;

public class MyException extends Exception {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyException(String s)
	    {
	        // Call constructor of parent Exception
	        super(Helpers.showMessage(s));
	    }
}

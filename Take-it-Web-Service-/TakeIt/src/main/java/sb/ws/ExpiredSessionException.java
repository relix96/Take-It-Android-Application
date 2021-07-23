package sb.ws;

public class ExpiredSessionException extends Exception {

	private static final long serialVersionUID = 1L;

	public ExpiredSessionException(String msg) {
		super(msg);
	}
	
}

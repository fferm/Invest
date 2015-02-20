package se.fermitet.invest.exception;

public class InvestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvestException() {
		super();
	}
	
	public InvestException(String msg) {
		super(msg);
	}
	
	public InvestException(String msg, Throwable t) {
		super(msg, t);
	}
}

package se.fermitet.vaadin.widgets;

public class POJOTableAdapterException extends RuntimeException {
	private static final long serialVersionUID = 4307452299122315876L;
	
	public POJOTableAdapterException(String msg) {
		super(msg);
	}
	
	public POJOTableAdapterException(String msg, Throwable underlying) {
		super(msg, underlying);
	}
}

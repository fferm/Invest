package se.fermitet.vaadin.widgets;

public class POJOUIException extends RuntimeException {
	private static final long serialVersionUID = 4307452299122315876L;
	
	public POJOUIException(String msg) {
		super(msg);
	}
	
	public POJOUIException(String msg, Throwable underlying) {
		super(msg, underlying);
	}
}

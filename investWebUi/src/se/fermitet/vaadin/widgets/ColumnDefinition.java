package se.fermitet.vaadin.widgets;

public class ColumnDefinition {
	String propertyName;
	String headerText;
	Class<?> clazz;
	public ColumnDefinition(String propertyName, String headerText, Class<?> clazz) {
		super();
		this.propertyName = propertyName;
		this.headerText = headerText;
		this.clazz = clazz;
	}
}


package se.fermitet.vaadin.navigation;

public class URIParameter {

	private String name;
	private String value;

	public URIParameter(String name, String value) {
		super();
		
		validateParameter("name", name);
		validateParameter("value", value);
		
		this.name = name;
		this.value = value;
	}

	private void validateParameter(String parameterName, String parameterValue) {
		if (parameterValue == null) return;
		
		if (parameterValue.contains("/")) throw new URIParameterException("Illegal character / found in parameter " + parameterName);
		if (parameterValue.contains("&")) throw new URIParameterException("Illegal character & found in parameter " + parameterName);
		if (parameterValue.contains("=")) throw new URIParameterException("Illegal character = found in parameter " + parameterName);
	}

	public URIParameter(String value) {
		this(null, value);
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		if (getName() == null) return getValue();
		
		return getName() + "=" + getValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		URIParameter other = (URIParameter) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	public class URIParameterException extends RuntimeException {
		private static final long serialVersionUID = 6561057167979465059L;
		
		public URIParameterException(String msg) {
			super(msg);
		}

	}



}

package se.fermitet.invest.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Portfolio extends InvestPOJO {

	@NotNull	
	@Size(min=1)
	private String name;
	
	public Portfolio() {
		super();
	}

	public Portfolio(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Portfolio other = (Portfolio) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	protected void initToStringProperties() throws NoSuchMethodException {
		setToStringClassName("Portfolio");
		addToStringPropDef("name", getClass().getMethod("getName"));
	}
	
	
}

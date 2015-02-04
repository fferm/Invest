package se.fermitet.invest.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class Stock extends InvestDomainObject {

	@NotNull	
	@Size(min=1)
	private String symbol;
	private String name;

	public Stock() {
		this(null, null);
	}
	
	public Stock(String symbol) {
		this(null, symbol);
	}

	public Stock(String name, String symbol) {
		super();
		
		setName(name);
		setSymbol(symbol);
	}

	public String getSymbol() {
		return symbol;
	}

	public Stock setSymbol(String symbol) {
		this.symbol = symbol;
		return this;
	}

	public String getName() {
		return name;
	}

	public Stock setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
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
		Stock other = (Stock) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

	
}

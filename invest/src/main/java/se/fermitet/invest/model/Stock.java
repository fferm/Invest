package se.fermitet.invest.model;

public class Stock {

	private String symbol;
	private String name;

	public Stock(String symbol) {
		super();
		validateSymbol(symbol);
		this.symbol = symbol;
	}

	private void validateSymbol(String symbol) {
		if (symbol == null) throw new IllegalArgumentException("The symbol of a stock cannot be null");
		if (symbol.length() == 0) throw new IllegalArgumentException("The symbol of a stock cannot be empty");
	}

	public String getSymbol() {
		return symbol;
	}

	public Stock setSymbol(String symbol) {
		validateSymbol(symbol);
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

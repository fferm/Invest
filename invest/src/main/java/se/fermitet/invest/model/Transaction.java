package se.fermitet.invest.model;

import org.joda.money.Money;
import org.joda.time.LocalDate;


public class Transaction {

	private LocalDate date;
	private Stock stock;
	private Integer number;
	private Money price;
	private Money fee;

	public Transaction() {
		super();
		this.setDate(LocalDate.now());
	}

	public LocalDate getDate() {
		return this.date;
	}

	public Transaction setDate(LocalDate date) {
		validateDate(date);
		this.date = date;
		return this;
	}

	private void validateDate(LocalDate date) {
		if (date == null) throw new IllegalArgumentException("Cannot set the date of a transaction to null");
	}
	
	public Stock getStock() {
		return this.stock;
	}

	public Transaction setStock(Stock stock) {
		this.stock = stock;
		return this;
	}

	public Integer getNumber() {
		return this.number;
	}

	public Transaction setNumber(Integer number) {
		this.number = number;
		return this;
	}

	public Money getPrice() {
		return this.price;
	}

	public Transaction setPrice(Money price) {
		this.price = price;
		return this;
	}

	public Money getFee() {
		return this.fee;
	}

	public Transaction setFee(Money fee) {
		this.fee = fee;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((fee == null) ? 0 : fee.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
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
		Transaction other = (Transaction) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (fee == null) {
			if (other.fee != null)
				return false;
		} else if (!fee.equals(other.fee))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		return true;
	}

	

}

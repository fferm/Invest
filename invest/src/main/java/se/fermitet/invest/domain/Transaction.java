package se.fermitet.invest.domain;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.joda.money.Money;
import org.joda.time.LocalDate;
import org.mongodb.morphia.annotations.Reference;

public class Transaction extends InvestDomainObject {

	@NotNull
	private LocalDate date;
	
	@NotNull
	@Valid
	@Reference
	private Stock stock;
	
	@Min(1)
	private Integer number;
	
	@NotNull
	private Money price;
	private Money fee;

	public Transaction() {
		this(null, LocalDate.now(), 0, null, null);
	}

	public Transaction(Stock stock, LocalDate date, int number,	Money price, Money fee) {
		super();
		setStock(stock);
		setDate(date);
		setNumber(number);
		setPrice(price);
		setFee(fee);
	}

	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(LocalDate localDate) {
		this.date = localDate;
	}

	public Stock getStock() {
		return this.stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		if (number == null) setNumber(0);
		else this.number = number;
	}
	
	public Money getPrice() {
		return this.price;
	}

	public void setPrice(Money price) {
		this.price = price;
	}

	public Money getFee() {
		return this.fee;
	}

	public void setFee(Money fee) {
		this.fee = fee;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((fee == null) ? 0 : fee.hashCode());
		result = prime * result + number;
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
		if (number != other.number)
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

	@Override
	protected void initToStringProperties() throws NoSuchMethodException {
		this.setToStringClassName("Transaction");
		this.addToStringPropDef("stock", this.getClass().getMethod("getStock"));
		this.addToStringPropDef("date", this.getClass().getMethod("getDate"));
		this.addToStringPropDef("number", this.getClass().getMethod("getNumber"));
		this.addToStringPropDef("price", this.getClass().getMethod("getPrice"));
		this.addToStringPropDef("fee", this.getClass().getMethod("getFee"));
	}

	
}

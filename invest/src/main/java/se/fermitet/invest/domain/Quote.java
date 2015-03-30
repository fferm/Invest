package se.fermitet.invest.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.joda.money.Money;
import org.joda.time.LocalDate;

public class Quote extends InvestPOJO {

	public Quote() {
		super();
	}
	
	Quote(Stock stock, LocalDate date, Money ask, Money bid, Money last, Money high, Money low, Integer volume, Money turnover) {
		this();
		
		this.stock = stock;
		this.date = date;
		this.bid = bid;
		this.ask = ask;
		this.last = last;
		this.high = high;
		this.low = low;
		this.volume = volume;
		this.turnover = turnover;
	}
	
	@NotNull
	@Valid
	private Stock stock;
	@NotNull
	private LocalDate date = LocalDate.now();
	
	private Money last;
	private Money bid;
	private Money ask;
	private Money high;
	private Money low;
	private Integer volume;
	private Money turnover;

	public Stock getStock() {
		return this.stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Money getLast() {
		return last;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setLast(Money last) {
		this.last = last;
	}

	public Money getBid() {
		return bid;
	}

	public void setBid(Money bid) {
		this.bid = bid;
	}

	public Money getAsk() {
		return ask;
	}

	public void setAsk(Money ask) {
		this.ask = ask;
	}

	public Money getHigh() {
		return high;
	}

	public void setHigh(Money high) {
		this.high = high;
	}

	public Money getLow() {
		return low;
	}

	public void setLow(Money low) {
		this.low = low;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Money getTurnover() {
		return turnover;
	}

	public void setTurnover(Money turnover) {
		this.turnover = turnover;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ask == null) ? 0 : ask.hashCode());
		result = prime * result + ((bid == null) ? 0 : bid.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((high == null) ? 0 : high.hashCode());
		result = prime * result + ((last == null) ? 0 : last.hashCode());
		result = prime * result + ((low == null) ? 0 : low.hashCode());
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		result = prime * result
				+ ((turnover == null) ? 0 : turnover.hashCode());
		result = prime * result + ((volume == null) ? 0 : volume.hashCode());
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
		Quote other = (Quote) obj;
		if (ask == null) {
			if (other.ask != null)
				return false;
		} else if (!ask.equals(other.ask))
			return false;
		if (bid == null) {
			if (other.bid != null)
				return false;
		} else if (!bid.equals(other.bid))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (high == null) {
			if (other.high != null)
				return false;
		} else if (!high.equals(other.high))
			return false;
		if (last == null) {
			if (other.last != null)
				return false;
		} else if (!last.equals(other.last))
			return false;
		if (low == null) {
			if (other.low != null)
				return false;
		} else if (!low.equals(other.low))
			return false;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		if (turnover == null) {
			if (other.turnover != null)
				return false;
		} else if (!turnover.equals(other.turnover))
			return false;
		if (volume == null) {
			if (other.volume != null)
				return false;
		} else if (!volume.equals(other.volume))
			return false;
		return true;
	}

	@Override
	protected void initToStringProperties() throws NoSuchMethodException {
		setToStringClassName("Quote");
		addToStringPropDef("stock", getClass().getMethod("getStock"));
		addToStringPropDef("date", getClass().getMethod("getDate"));
		addToStringPropDef("ask", getClass().getMethod("getAsk"));
		addToStringPropDef("bid", getClass().getMethod("getBid"));
		addToStringPropDef("last", getClass().getMethod("getLast"));
		addToStringPropDef("high", getClass().getMethod("getHigh"));
		addToStringPropDef("low", getClass().getMethod("getLow"));
		addToStringPropDef("volume", getClass().getMethod("getVolume"));
		addToStringPropDef("turnover", getClass().getMethod("getTurnover"));
	}


}

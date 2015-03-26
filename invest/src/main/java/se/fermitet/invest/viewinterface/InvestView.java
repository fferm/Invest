package se.fermitet.invest.viewinterface;

import se.fermitet.invest.model.ModelException;

public interface InvestView {
	public boolean hasApplicationException();
	public void displayApplicationException(ModelException error);
	public void clearApplicationException();

}

package se.fermitet.invest.viewinterface;

import java.util.List;

import se.fermitet.invest.model.ModelException;

public interface ListView<POJO> extends InvestView {
	public void displayData(List<POJO> data);
	public void navigateToSingleView(POJO data);
	
	public boolean hasApplicationException();
	public void displayApplicationException(ModelException error);
	public void clearApplicationException();

}

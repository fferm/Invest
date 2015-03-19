package se.fermitet.invest.viewinterface;

import java.util.List;

public interface ListView<POJO> extends InvestView {
	public void displayData(List<POJO> data);
	public void navigateToSingleView(POJO data);


}

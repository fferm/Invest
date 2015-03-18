package se.fermitet.invest.viewinterface;

import java.util.List;

public interface ListView<POJOCLASS> extends InvestView {
	public void displayData(List<POJOCLASS> data);

}

package se.fermitet.invest.model;

public class ModelException extends Exception {

	private static final long serialVersionUID = -7394909328890202728L;
	
	public static final ModelException CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS = new ModelException("Cannot delete stock since it has associated transactions");

	ModelException(String msg) {
		super(msg);
	}
	
	

}

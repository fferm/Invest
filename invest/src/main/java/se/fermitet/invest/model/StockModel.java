package se.fermitet.invest.model;

import java.util.List;
import java.util.UUID;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.ModelException.ModelExceptionType;

public class StockModel extends Model<Stock> {
	
	StockModel() {
		super();
	}

	@Override
	public List<Stock> getAll() {
		return storage.getAllStocks();
	}
	
	@Override
	public Stock getById(UUID id) {
		return storage.getStockById(id);
	}

	@Override
	public void save(Stock stock) {
		verifyNoStockWithSameSymbol(stock);
		
		storage.saveStock(stock);
	}

	@Override
	public void delete(Stock toDelete) {
		verifyNoAssociatedTransactions(toDelete);
		
		storage.deleteStock(toDelete);
	}

	private void verifyNoStockWithSameSymbol(Stock stock) {
		if (storage.getStockBySymbol(stock.getSymbol()) != null) throw new ModelException(ModelExceptionType.CANNOT_SAVE_STOCK_SINCE_THERE_IS_ALREADY_A_STOCK_WITH_THAT_SYMBOL, stock);
	}
	
	private void verifyNoAssociatedTransactions(Stock stock) {
		if (storage.getTransactionsForStock(stock).size() > 0) throw new ModelException(ModelExceptionType.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS, stock);
	}


}

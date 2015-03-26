package se.fermitet.invest.model;

import java.util.List;
import java.util.UUID;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.model.ModelException.ModelExceptionType;

public class PortfolioModel extends Model<Portfolio>{

	PortfolioModel() {
		super();
	}
	
	@Override
	public List<Portfolio> getAll() {
		return storage.getAllPortfolios();
	}

	@Override
	public Portfolio getById(UUID id) {
		return storage.getPortfolioById(id);
	}

	@Override
	public void save(Portfolio obj) {
		storage.savePortfolio(obj);
	}

	@Override
	public void delete(Portfolio obj) throws ModelException {
		verifyNoAssociatedTransactions(obj);
		
		storage.deletePortfolio(obj);
	}
	
	private void verifyNoAssociatedTransactions(Portfolio portfolio) throws ModelException {
		if (storage.getTransactionsForPortfolio(portfolio).size() > 0) throw new ModelException(ModelExceptionType.CANNOT_DELETE_PORTFOLIO_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS, portfolio);
	}


}

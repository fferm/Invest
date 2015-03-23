package se.fermitet.invest.model;

import java.util.List;
import java.util.UUID;

import se.fermitet.invest.domain.Portfolio;

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
	public void delete(Portfolio obj) {
		storage.deletePortfolio(obj);
	}

}

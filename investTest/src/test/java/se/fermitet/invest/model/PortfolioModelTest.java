package se.fermitet.invest.model;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.Test;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.storage.Storage;

public class PortfolioModelTest extends AbstractModelTest<PortfolioModel>{

	public PortfolioModelTest() {
		super(PortfolioModel.class);
	}

	@Override
	public PortfolioModel createModelWithMockedStorage() {
		return new TestPortfolioModel();
	}

	@Test
	public void testGetAll() throws Exception {
		model.getAll();
		verify(mockedStorage).getAllPortfolios();
	}

	@Test
	public void testGetById() throws Exception {
		UUID id = UUID.randomUUID();
		model.getById(id);
		verify(mockedStorage).getPortfolioById(id);
	}
	
	@Test
	public void testDelete() throws Exception {
		Portfolio toDelete = new Portfolio();
		model.delete(toDelete);
		
		verify(mockedStorage).deletePortfolio(toDelete);
	}
	
	@Test
	public void testSave() throws Exception {
		Portfolio toSave = new Portfolio();
		model.save(toSave);
		
		verify(mockedStorage).savePortfolio(toSave);
		
	}

}

class TestPortfolioModel extends PortfolioModel {
	@Override
	protected Storage createStorage() {
		return mock(Storage.class);
	}
}


package se.fermitet.vaadin.widgets;

import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;

import se.fermitet.general.IdAble;
import se.fermitet.vaadin.converters.MoneyVaadinConverter;

import com.vaadin.data.Property;
import com.vaadin.ui.Table;

public class POJOTableAdapter<POJO extends IdAble<?>> extends POJOAbstractSelectAdapter<POJO, Table> {
	private static final long serialVersionUID = -8088105189014857952L;

	public POJOTableAdapter(Class<POJO> pojoClass) {
		this(pojoClass, null);
	}

	public POJOTableAdapter(Class<POJO> pojoClass, String caption) {
		super(pojoClass, new Table(caption));
	}
	
	public void setVisibleData(List<ColumnDefinition> columnDefinitionsToShow) {
		try {
			List<String> existingPropIds = new ArrayList<String>(this.container.getContainerPropertyIds());

			List<String> propIdsToShow = new ArrayList<String>();
			for (ColumnDefinition def : columnDefinitionsToShow) {
				String propId = def.propertyName;
				propIdsToShow.add(propId);

				if (!existingPropIds.contains(propId)) {
					this.container.addNestedContainerProperty(propId);
				}

				if (def.headerText == null) {
					this.getUI().setColumnHeader(propId, "");
				} else {
					this.getUI().setColumnHeader(propId, def.headerText);
				}
			}

			this.getUI().setVisibleColumns(propIdsToShow.toArray());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new POJOUIException(e.getMessage(), e);
		}
	}

	@Override
	protected void updateUIFromData() {
		assignConverters();
	}

	protected void assignConverters() {
		Table table = getUI();
		if (table.size() == 0) return;
		
		Object firstItemId = table.getItemIds().iterator().next();
		for (Object propId : table.getContainerPropertyIds()) {
			Property<?> prop = table.getContainerProperty(firstItemId, propId);
			if (prop.getType().equals(Money.class)) {
				table.setConverter(propId, new MoneyVaadinConverter());
			}
		}
	}
}

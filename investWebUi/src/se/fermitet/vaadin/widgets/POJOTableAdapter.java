package se.fermitet.vaadin.widgets;

import java.util.ArrayList;
import java.util.List;

import se.fermitet.general.IdAble;

import com.vaadin.ui.Table;

public class POJOTableAdapter<POJOCLASS extends IdAble<?>> extends POJOAbstractSelectAdapter<POJOCLASS, Table> {
	private static final long serialVersionUID = -8088105189014857952L;

	public POJOTableAdapter(Class<POJOCLASS> pojoClass) {
		this(pojoClass, null);
	}

	public POJOTableAdapter(Class<POJOCLASS> pojoClass, String caption) {
		super(pojoClass, new Table(caption));
	}
	
//	public Table getTable() {
//		return super.getUI();
//	}

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
		// Do nothing - Everything is handled by the container
	}
}

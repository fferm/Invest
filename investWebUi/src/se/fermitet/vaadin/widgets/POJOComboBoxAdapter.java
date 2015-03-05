package se.fermitet.vaadin.widgets;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.ComboBox;

public class POJOComboBoxAdapter<POJOCLASS> extends POJOAbstractSelectAdapter<POJOCLASS, ComboBox>{
	private static final long serialVersionUID = -5091088337235569884L;

	private String displayColumn;

	public POJOComboBoxAdapter(Class<POJOCLASS> pojoClass) {
		this(pojoClass, null);
	}

	public POJOComboBoxAdapter(Class<POJOCLASS> pojoClass, String caption) {
		super(pojoClass, new ComboBox(caption));
	}


	public ComboBox getCombo() {
		return this.getUI();
	}

	public String getDisplayColumn() {
		return this.displayColumn;
	}

	public void setDisplayColumn(String propertyName) {
		this.displayColumn = propertyName;
		updateUIFromData();
	}

	@Override
	protected void updateUIFromData() {
		if (this.displayColumn == null) return;

		ensureNestedProperties();

		for (Integer itemId : container.getItemIds()) {
			BeanItem<POJOCLASS> item = container.getItem(itemId);

			Object value = item.getItemProperty(this.displayColumn).getValue();

			getUI().setItemCaption(itemId, value == null ? "" : value.toString());
		}
	}

	private void ensureNestedProperties() {
		try {
			List<String> existingPropIds = new ArrayList<String>(this.container.getContainerPropertyIds());

			if (! existingPropIds.contains(this.displayColumn)) {
				this.container.addNestedContainerProperty(this.displayColumn);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new POJOUIException(e.getMessage(), e);
		}
	}



}

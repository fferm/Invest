package se.fermitet.vaadin.widgets;

import java.util.ArrayList;
import java.util.List;

import se.fermitet.general.IdAble;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.ComboBox;

public class POJOComboBoxAdapter<POJO extends IdAble<?>> extends POJOAbstractSelectAdapter<POJO, ComboBox>{
	private static final long serialVersionUID = -5091088337235569884L;

	private String displayColumn;

	public POJOComboBoxAdapter(Class<POJO> pojoClass) {
		this(pojoClass, null);
	}

	public POJOComboBoxAdapter(Class<POJO> pojoClass, String caption) {
		super(pojoClass, new ComboBox(caption));
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

		for (Object itemId : container.getItemIds()) {
			BeanItem<POJO> item = container.getItem(itemId);

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

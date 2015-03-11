package se.fermitet.vaadin.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import se.fermitet.general.IdAble;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.DefaultItemSorter;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.AbstractSelect;

@SuppressWarnings("serial")
abstract class POJOAbstractSelectAdapter<POJOCLASS extends IdAble<?>, UICLASS extends AbstractSelect> implements POJOAdapter<POJOCLASS, UICLASS> {
	private POJOAdapterHelper<POJOCLASS, UICLASS> pojoAdapter;

	protected BeanContainer<Object, POJOCLASS> container;
	private List<SelectionListener<POJOCLASS>> listeners;
	private List<String> sortOrder;

	POJOAbstractSelectAdapter(Class<POJOCLASS> pojoClass, UICLASS ui) {
		super();
		this.pojoAdapter = new POJOAdapterHelper<POJOCLASS, UICLASS>(ui, pojoClass);
		
		this.listeners = new ArrayList<SelectionListener<POJOCLASS>>();

		initContainer();
		configureUI();
	}

	private void initContainer() {
		this.container = new BeanContainer<Object, POJOCLASS>(getPojoClass());

		this.container.setItemSorter(new DefaultItemSorter() {
			@Override
			protected int compareProperty(Object propertyId, boolean sortDirection, Item item1, Item item2) {
				Object val1 = item1.getItemProperty(propertyId).getValue();
				Object val2 = item2.getItemProperty(propertyId).getValue();

				if (val1 == null) return sortDirection ? 1 : -1;
				if (val2 == null) return sortDirection ? -1 : 1;

				return super.compareProperty(propertyId, sortDirection, item1, item2);
			}
		});
	}

	private void configureUI() {
		this.getUI().setContainerDataSource(this.container);
		this.getUI().addValueChangeListener((Property.ValueChangeEvent event) -> {
			fireSelectionEvent(event);
		});
	}

	protected abstract void updateUIFromData();

	public void setData(List<POJOCLASS> allData) {
		updateContainerFromData(allData);

		if (this.sortOrder != null) performSortOnData();

		updateUIFromData();
	}

	protected void updateContainerFromData(List<POJOCLASS> data) {
		getUI().removeAllItems();

		for (POJOCLASS oneData : data) {
			container.addItem(oneData.getId(), oneData);
		}
	}

	public List<POJOCLASS> getData() {
		List<POJOCLASS> ret = new ArrayList<POJOCLASS>();

		for (Object itemId : container.getItemIds()) {
			ret.add(container.getItem(itemId).getBean());
		}

		return Collections.unmodifiableList(ret);
	}

	public void setSortOrder(List<String> propertyIds) {
		this.sortOrder = propertyIds;
		performSortOnData();
		updateUIFromData();
	}

	public void setSortOrder(String propertyId) {
		List<String> propertyIds = new ArrayList<String>();
		propertyIds.add(propertyId);
		setSortOrder(propertyIds);
	}

	public void select(POJOCLASS toSelect) {
		if (toSelect == null) {
			getUI().select(null);
		} else {
			getUI().select(toSelect.getId());
		}
	}

	public void unselect() {
		select(null);
	}

	public POJOCLASS getSelectedData() {
		Object itemId = getUI().getValue();
		if (itemId != null) {
			BeanItem<POJOCLASS> item = container.getItem(itemId);
			
			return item == null ? null : item.getBean();
		} else {
			return null;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void bindSelectionToProperty(Object data, String propertyName) {
		BeanItem<?> item = new BeanItem<Object>(data);

		this.getUI().setConverter((Converter) new SelectionConverter());

		this.getUI().setPropertyDataSource((Property<?>) item.getItemProperty(propertyName));
	}
	
	private class SelectionConverter implements Converter<Object, POJOCLASS> {
		@Override
		public POJOCLASS convertToModel(Object value, Class<? extends POJOCLASS> targetType, Locale locale) throws ConversionException {
			BeanItem<POJOCLASS> selectedItem = (BeanItem<POJOCLASS>) container.getItem(value);

			return selectedItem == null ? null : selectedItem.getBean();
		}

		@Override
		public Object convertToPresentation(POJOCLASS value, Class<? extends Object> targetType, Locale locale) throws ConversionException {
			if (value == null) return null;
			
			return value.getId();
		}

		@Override
		public Class<POJOCLASS> getModelType() {
			return getPojoClass();
		}

		@Override
		public Class<Object> getPresentationType() {
			return Object.class;
		}
	}

	private void performSortOnData() {
		Object[] propArray =  sortOrder.toArray();
		boolean[] ascArray = new boolean[propArray.length];
		for (int i = 0; i < ascArray.length; i++) ascArray[i] = true;

		this.container.sort(propArray, ascArray);
	}

	public interface SelectionListener<T> {
		public void onSelect(Object itemId, T selectedPOJO);
	}

	public void addSelectionListener(SelectionListener<POJOCLASS> listener) {
		listeners.add(listener);
	}

	public void removeSelectionListener(SelectionListener<POJOCLASS> listener) {
		listeners.remove(listener);
	}

	protected void fireSelectionEvent(ValueChangeEvent event) {
		POJOCLASS selectedPOJO = getSelectedData();

		Object itemId = selectedPOJO == null ? null : selectedPOJO.getId();
		
		for (SelectionListener<POJOCLASS> listener : listeners) {
			listener.onSelect(itemId, selectedPOJO);
		}
	}
	
	@Override
	public UICLASS getUI() {
		return this.pojoAdapter.getUI();
	}
	
	@Override
	public Class<POJOCLASS> getPojoClass() {
		return this.pojoAdapter.getPojoClass();
	}



}

package se.fermitet.vaadin.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.DefaultItemSorter;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.AbstractSelect;

@SuppressWarnings("serial")
abstract class POJOAbstractSelectAdapter<POJOCLASS, UICLASS extends AbstractSelect> implements POJOAdapter<POJOCLASS, UICLASS> {
	private POJOAdapterHelper<POJOCLASS, UICLASS> pojoAdapter;

	protected BeanContainer<Integer, POJOCLASS> container;
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
		this.container = new BeanContainer<Integer, POJOCLASS>(getPojoClass());

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

		int i = 0;
		// itemId is the index of the pojo in the data
		for (POJOCLASS oneData : data) {
			container.addItem(i, oneData);
			i++;
		}
	}

	public List<POJOCLASS> getData() {
		List<POJOCLASS> ret = new ArrayList<POJOCLASS>();

		for (Integer itemId : container.getItemIds()) {
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
			for (Integer itemId : container.getItemIds()) {
				BeanItem<POJOCLASS> item = container.getItem(itemId);
				POJOCLASS bean = item.getBean();

				if (bean.equals(toSelect)) {
					getUI().select(itemId);
					return;
				}
			}
		}
	}

	public void unselect() {
		select(null);
	}

	public POJOCLASS getSelectedData() {
		Integer selectedIdx = (Integer) getUI().getValue();

		if (selectedIdx != null) {
			Integer itemId = container.getIdByIndex(selectedIdx);
			return container.getItem(itemId).getBean();
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
	
	private class SelectionConverter implements Converter<Integer, POJOCLASS> {
		@Override
		public POJOCLASS convertToModel(Integer value, Class<? extends POJOCLASS> targetType, Locale locale) throws ConversionException {
			BeanItem<POJOCLASS> selectedItem = (BeanItem<POJOCLASS>) container.getItem(value);

			return selectedItem == null ? null : selectedItem.getBean();
		}

		@Override
		public Integer convertToPresentation(POJOCLASS value, Class<? extends Integer> targetType, Locale locale) throws ConversionException {
			for (Integer itemId : container.getItemIds()) {
				BeanItem<POJOCLASS> candidateItem = container.getItem(itemId);
				if (candidateItem.getBean().equals(value)) return itemId;
			}
			return null;
		}

		@Override
		public Class<POJOCLASS> getModelType() {
			return getPojoClass();
		}

		@Override
		public Class<Integer> getPresentationType() {
			return Integer.class;
		}
	}

	private void performSortOnData() {
		Object[] propArray =  sortOrder.toArray();
		boolean[] ascArray = new boolean[propArray.length];
		for (int i = 0; i < ascArray.length; i++) ascArray[i] = true;

		this.container.sort(propArray, ascArray);
	}

	public interface SelectionListener<T> {
		public void onSelect(Integer selectedIdx, T selectedPOJO);
	}

	public void addSelectionListener(SelectionListener<POJOCLASS> listener) {
		listeners.add(listener);
	}

	public void removeSelectionListener(SelectionListener<POJOCLASS> listener) {
		listeners.remove(listener);
	}

	protected void fireSelectionEvent(ValueChangeEvent event) {
		Integer selectedIdx = (Integer) event.getProperty().getValue();

		POJOCLASS selectedPOJO = selectedIdx == null ? null : this.container.getItem(selectedIdx).getBean();

		for (SelectionListener<POJOCLASS> listener : listeners) {
			listener.onSelect(selectedIdx, selectedPOJO);
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

package se.fermitet.vaadin.widgets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.DefaultItemSorter;
import com.vaadin.ui.AbstractSelect;

@SuppressWarnings("serial")
abstract class POJOAbstractSelectAdapter<POJOCLASS, UICLASS extends AbstractSelect> implements Serializable {

	private Class<POJOCLASS> pojoClass;
	protected UICLASS ui;
	protected BeanContainer<Integer, POJOCLASS> container;
	private List<SelectionListener<POJOCLASS>> listeners;
	private List<String> sortOrder;

	POJOAbstractSelectAdapter(Class<POJOCLASS> pojoClass, String caption) {
		super();

		this.pojoClass = pojoClass;
		this.listeners = new ArrayList<SelectionListener<POJOCLASS>>();

		initContainer();
		initUI(caption);
	}

	private void initContainer() {
		this.container = new BeanContainer<Integer, POJOCLASS>(pojoClass);

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

	private void initUI(String caption) {
		this.ui = createUI(caption);
		this.ui.setContainerDataSource(this.container);
		this.ui.setImmediate(true);
		this.ui.addValueChangeListener((Property.ValueChangeEvent event) -> {
			fireSelectionEvent(event);
		});
	}

	protected abstract UICLASS createUI(String caption);
	protected abstract void updateUIFromData();

	public UICLASS getUI() {
		return ui;
	}

	public void setData(List<POJOCLASS> allData) {
		updateContainerFromData(allData);

		if (this.sortOrder != null) performSortOnData();

		updateUIFromData();
	}

	protected void updateContainerFromData(List<POJOCLASS> data) {
		ui.removeAllItems();

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
			ui.select(null);
		} else {
			for (Integer itemId : container.getItemIds()) {
				BeanItem<POJOCLASS> item = container.getItem(itemId);
				POJOCLASS bean = item.getBean();

				if (bean.equals(toSelect)) {
					ui.select(itemId);
					return;
				}
			}
		}
	}

	public void unselect() {
		select(null);
	}

	public POJOCLASS getSelectedData() {
		Integer selectedIdx = (Integer) ui.getValue();

		if (selectedIdx != null) {
			Integer itemId = container.getIdByIndex(selectedIdx);
			return container.getItem(itemId).getBean();
		} else {
			return null;
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



}

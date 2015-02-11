package se.fermitet.vaadin.widgets;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.AbstractSelect;

@SuppressWarnings("serial")
abstract class POJOAbstractSelectAdapter<POJOCLASS, UICLASS extends AbstractSelect> implements Serializable {

	private Class<POJOCLASS> pojoClass;
	protected UICLASS ui;
	protected BeanContainer<Integer, POJOCLASS> container;
	private List<POJOCLASS> data;
	private Method sortGetter;
	private List<SelectionListener<POJOCLASS>> listeners;

	POJOAbstractSelectAdapter(Class<POJOCLASS> pojoClass, String caption) {
		super();
		
		this.pojoClass = pojoClass;
		this.ui = createUI(caption);
		this.ui.setImmediate(true);
		
		this.listeners = new ArrayList<SelectionListener<POJOCLASS>>();
		
		this.container = new BeanContainer<Integer, POJOCLASS>(pojoClass);
		this.ui.setContainerDataSource(this.container);
		
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
		this.data = new ArrayList<POJOCLASS>(allData);

		if (sortGetter != null) performSortOnData();
		
		updateFromData();
	}
	
	private void updateFromData() {
		updateContainerFromData();
		updateUIFromData();
	}
	
	protected void updateContainerFromData() {
		ui.removeAllItems();

		int i = 0;
		// itemId is the index of the pojo in the data
		for (POJOCLASS oneData : data) {
			container.addItem(i, oneData);
			i++;
		}
	}

	public List<POJOCLASS> getData() {
		return Collections.unmodifiableList(this.data);
	}
	
	public void setSortOrder(String propertyId) {
		this.sortGetter = findGetterMethod(propertyId);
		
		if (this.data != null) {
			performSortOnData();
			updateFromData();
		}
	}
	
	public void select(POJOCLASS toSelect) {
		if (toSelect == null) {
			ui.select(null);
		} else {
			ui.select(this.data.indexOf(toSelect));
		}
	}

	public void unselect() {
		select(null);
	}

	public POJOCLASS getSelectedData() {
		Integer selectedIdx = (Integer) ui.getValue();

		if (selectedIdx != null) {
			return data.get(selectedIdx);
		} else {
			return null;
		}
	}


	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void performSortOnData() {
		Collections.sort(this.data, (Comparator<? super POJOCLASS>) (POJOCLASS o1, POJOCLASS o2) -> {
			try {
				Comparable s1 = (Comparable) sortGetter.invoke(o1);
				Comparable s2 = (Comparable) sortGetter.invoke(o2);
				
				if (s1 == null) return 1;
				if (s2 == null) return -1;
				
				else return s1.compareTo(s2);
			} catch (Exception e) {
				e.printStackTrace();
				throw new POJOUIException("Exception when trying to compare to property values", e);
			}
		});
	}

	private Method findGetterMethod(String propertyId) {
		String propertyName = propertyId.substring(0, 1).toUpperCase() + propertyId.substring(1);

		Method ret = null;

		try {
			try {
				ret = pojoClass.getMethod("get" + propertyName, new Class[] {});
			} catch (NoSuchMethodException e) {
				ret = pojoClass.getMethod("is" + propertyName, new Class[] {});
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new POJOUIException("Exception when trying to find method is" + propertyName + " on class " + pojoClass.getName(), e);
		}

		return ret;
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

		POJOCLASS selectedPOJO = selectedIdx == null ? null : this.data.get(selectedIdx);

		for (SelectionListener<POJOCLASS> listener : listeners) {
			listener.onSelect(selectedIdx, selectedPOJO);
		}
	}



}

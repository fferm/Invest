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
import com.vaadin.ui.Table;

public class POJOTableAdapter<POJOCLASS> implements Serializable {

	private static final long serialVersionUID = 3689523044777603458L;

	private Class<POJOCLASS> pojoClass;
	private List<POJOCLASS> data;
	private Table table;
	private BeanContainer<Integer, POJOCLASS> container;
	private List<SelectionListener<POJOCLASS>> listeners;

	private Method sortGetter;


	public POJOTableAdapter(Class<POJOCLASS> pojoClass) {
		this(pojoClass, null);
	}

	public POJOTableAdapter(Class<POJOCLASS> pojoClass, String myCaption) {
		super();

		this.pojoClass = pojoClass;
		this.table = new Table(myCaption);
		this.container = new BeanContainer<Integer, POJOCLASS>(pojoClass);
		this.listeners = new ArrayList<POJOTableAdapter.SelectionListener<POJOCLASS>>();

		this.table.setContainerDataSource(this.container);

		this.table.addValueChangeListener((Property.ValueChangeEvent event) -> {
			selectionMadeInTable(event);
		});
	}

	public Table getTable() {
		return table;
	}

	public void setColumns(List<ColumnDefinition> columnDefinitionsToShow) {
		List<String> existingPropIds = new ArrayList<String>(this.container.getContainerPropertyIds());

		List<String> propIdsToShow = new ArrayList<String>();
		for (ColumnDefinition def : columnDefinitionsToShow) {
			String propId = def.propertyName;
			propIdsToShow.add(propId);

			if (!existingPropIds.contains(propId)) {
				this.container.addNestedContainerProperty(propId);
			}

			if (def.headerText == null) {
				this.table.setColumnHeader(propId, "");
			} else {
				this.table.setColumnHeader(propId, def.headerText);
			}
		}

		this.table.setVisibleColumns(propIdsToShow.toArray());
	}

	public void setData(List<POJOCLASS> allData) {
		this.data = new ArrayList<POJOCLASS>(allData);

		if (sortGetter != null) performSortOnData();
		
		updateTableFromData();
	}
	
	private void updateTableFromData() {
		table.removeAllItems();

		int i = 0;
		// itemId is the index of the pojo in the data
		for (POJOCLASS oneData : data) {
			container.addItem(i, oneData);
			i++;
		}
	}

	protected void syncData(List<POJOCLASS> allData) {
		table.removeAllItems();

		int i = 0;
		// itemId is the index of the pojo in the data
		for (POJOCLASS data : allData) {
			container.addItem(i, data);
			i++;
		}
	}

	public List<POJOCLASS> getData() {
		return Collections.unmodifiableList(this.data);
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

	public void select(POJOCLASS toSelect) {
		if (toSelect == null) {
			table.select(null);
		} else {
			table.select(this.data.indexOf(toSelect));
		}
	}

	public void unselect() {
		select(null);
	}

	public POJOCLASS getSelectedData() {
		Integer selectedIdx = (Integer) table.getValue();

		if (selectedIdx != null) {
			return data.get(selectedIdx);
		} else {
			return null;
		}
	}

	private void selectionMadeInTable(ValueChangeEvent event) {
		Integer selectedIdx = (Integer) event.getProperty().getValue();

		POJOCLASS selectedPOJO = selectedIdx == null ? null : this.data.get(selectedIdx);

		for (SelectionListener<POJOCLASS> listener : listeners) {
			listener.onSelect(selectedIdx, selectedPOJO);
		}
	}

	public void setSortOrder(String propertyId) {
		this.sortGetter = findGetterMethod(propertyId);
		
		if (this.data != null) {
			performSortOnData();
			updateTableFromData();
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
				throw new POJOTableAdapterException("Exception when trying to compare to property values", e);
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
			throw new POJOTableAdapterException("Exception when trying to find method is" + propertyName + " on class " + pojoClass.getName(), e);
		}

		return ret;
	}
}

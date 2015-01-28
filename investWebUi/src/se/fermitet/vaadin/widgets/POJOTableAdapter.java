package se.fermitet.vaadin.widgets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.Table;

public class POJOTableAdapter<T> implements Serializable {

	private static final long serialVersionUID = 3689523044777603458L;

	private List<T> data;
	private Table table;
	private BeanContainer<Integer, T> container;
	private List<SelectionListener<T>> listeners;

	public POJOTableAdapter(Class<T> pojoClass) {
		this(pojoClass, null);
	}

	public POJOTableAdapter(Class<T> pojoClass, String myCaption) {
		super();
		
		this.table = new Table(myCaption);
		this.container = new BeanContainer<Integer, T>(pojoClass);
		this.listeners = new ArrayList<POJOTableAdapter.SelectionListener<T>>();

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

	public void setData(List<T> allData) {
		this.data = allData;
		
		table.removeAllItems();
		
		int i = 0;
		// itemId is the index of the pojo in the data
		for (T data : allData) {
			container.addItem(i, data);
			i++;
		}
	}

	protected void syncData(List<T> allData) {
		table.removeAllItems();
		
		int i = 0;
		// itemId is the index of the pojo in the data
		for (T data : allData) {
			container.addItem(i, data);
			i++;
		}
	}
	
	public List<T> getData() {
		return Collections.unmodifiableList(this.data);
	}
	
	public interface SelectionListener<T> {
		public void onSelect(Integer selectedIdx, T selectedPOJO);
	}
	
	public void addSelectionListener(SelectionListener<T> listener) {
		listeners.add(listener);
	}
	
	public void removeSelectionListener(SelectionListener<T> listener) {
		listeners.remove(listener);
	}

	public void select(T toSelect) {
		if (toSelect == null) {
			table.select(null);
		} else {
			table.select(this.data.indexOf(toSelect));
		}
	}

	public void unselect() {
		select(null);
	}

	public T getSelectedData() {
		Integer selectedIdx = (Integer) table.getValue();

		if (selectedIdx != null) {
			return data.get(selectedIdx);
		} else {
			return null;
		}
	}
	
	private void selectionMadeInTable(ValueChangeEvent event) {
		Integer selectedIdx = (Integer) event.getProperty().getValue();

		T selectedPOJO = selectedIdx == null ? null : this.data.get(selectedIdx);
		
		for (SelectionListener<T> listener : listeners) {
			listener.onSelect(selectedIdx, selectedPOJO);
		}
	}


	
}

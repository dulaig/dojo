package hu.dojo.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import org.eclipse.jdt.internal.core.CreateFieldOperation;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;

import hu.dojo.backend.IEntityDAO;
import hu.dojo.jpa.Train;

public class TrainGrid extends Grid<Train>{
	
	private DataProvider<Train, String> dataProvier;
	private Map<String, Object> filterData = new HashMap<String, Object>();
	
	@EJB(beanName = "TrainDAO")
	private IEntityDAO<Train> dao;
	
	@PostConstruct
	private void init() {
		setSizeFull();
		setBeanType(Train.class);
		initDataProvider();
		initColums();
	}
	
	private void initColums() {
		addColumn(train -> train.getType()).setId("type").setCaption("Type").setHidable(true);
		addColumn(train -> train.getColour()).setId("colour").setCaption("Colour").setHidable(true);
		HeaderRow filterRow = this.appendHeaderRow();
		setFilterComponent(filterRow, "type");
		setFilterComponent(filterRow, "colour");
	}
	
	private void setFilterComponent(HeaderRow filterRow, String columnId) {
		HeaderCell headerCell = filterRow.getCell(columnId);
		headerCell.setComponent(createFilterField(columnId));
	}
	
	private Component createFilterField(String columnId) {
		TextField filterText = new TextField();
		filterText.setStyleName(ValoTheme.TEXTFIELD_TINY);
		filterText.setWidth(100, Unit.PERCENTAGE);
		filterText.addValueChangeListener(valueChangeEvent ->{
			filterData.put(columnId, valueChangeEvent.getValue());
			dataProvier.refreshAll();
		});
		return filterText;
	}
	
	private void initDataProvider() {
		dataProvier = new CallbackDataProvider<>(
			query -> dao.fetchMultiple(filterData).stream()	,
			query -> dao.fetchMultiple(filterData).size());
		setDataProvider(dataProvier);
	}

}

package hu.dojo.web;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.persistence.Query;

import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;

import hu.dojo.backend.IEntityDAO;
import hu.dojo.backend.TrainDAO;
import hu.dojo.backend.TripDAO;
import hu.dojo.jpa.Trip;

public class TripGrid extends Grid<Trip>{

	private DataProvider<Trip, String> dataProvider;
	private Map<String, Object> filterData = new HashMap<String, Object>();
	
	@EJB(beanName = "TripDAO")
	private IEntityDAO<Trip> dao;
	
	@PostConstruct
	private void init() {
		setSizeFull();
		setBeanType(Trip.class);
		initDataProvider();
		initColums();
	}
	
	private void initColums() {
		addColumn(trip -> trip.getSerial_code()).setId("train").setCaption("Train").setHidable(true);
		addColumn(trip -> trip.getFrom()).setId("from").setCaption("From").setHidable(true);
		addColumn(trip -> trip.getTo()).setId("to").setCaption("To").setHidable(true);
		addColumn(trip -> trip.getDeparture()).setId("departure").setCaption("Departure").setHidable(true);
		addColumn(trip -> trip.getArrival()).setId("arrival").setCaption("Arrival").setHidable(true);
		HeaderRow filterRow = this.appendHeaderRow();
		setFilterComponent(filterRow, "train");
		setFilterComponent(filterRow, "from");
		setFilterComponent(filterRow, "to");
		setFilterComponent(filterRow, "departure");
		setFilterComponent(filterRow, "arrival");
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
			dataProvider.refreshAll();
		});
		return filterText;
	}
	
	private void initDataProvider() {
		dataProvider = new CallbackDataProvider<>(
			query -> dao.fetchMultiple(filterData).stream()	,
			query -> dao.fetchMultiple(filterData).size());
		setDataProvider(dataProvider);
	}

}

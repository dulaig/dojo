package hu.dojo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

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
import hu.dojo.jpa.Trip;

public class TripGrid extends Grid<Trip> {

	private DataProvider<Trip, String> dataProvider;
	private Map<String, Object> filterData = new HashMap<String, Object>();
	private List<Train> trainList = new ArrayList<Train>();

	@EJB(beanName = "TripDAO")
	private IEntityDAO<Trip> dao;

	@EJB(beanName = "TrainDAO")
	private IEntityDAO<Train> trainDao;

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
		filterText.addValueChangeListener(valueChangeEvent -> {
			filterData.put(columnId, valueChangeEvent.getValue());
			dataProvider.refreshAll();
		});
		return filterText;
	}

	private void initDataProvider() {
		trainList = trainDao.fetchMultiple(new HashMap<String,Object>());
		dataProvider = new CallbackDataProvider<>(query -> dao.fetchMultiple(filterData).stream(),
				query -> dao.fetchMultiple(filterData).size());
		setDataProvider(dataProvider);
		System.out.println(trainList.size()+" vonat van!!!!");
	}
	
	public List<Train> getTrains() {
		if(trainList != null && trainList.size() > 0)
			return trainList;
		return new ArrayList<Train>();
	}

}

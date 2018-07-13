package hu.dojo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;

import hu.dojo.backend.IEntityDAO;
import hu.dojo.jpa.Colour;
import hu.dojo.jpa.Train;
import hu.dojo.jpa.TrainType;

public class TrainGrid extends Grid<Train> {

	private DataProvider<Train, String> dataProvider;
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
		addColumn(train -> train.getSerialCode()).setId("serialCode").setCaption("Serial Code").setHidable(true);
		addColumn(train -> train.getType()).setId("type").setCaption("Type").setHidable(true);
		addColumn(train -> train.getColour()).setId("colour").setCaption("Colour").setHidable(true);
		HeaderRow filterRow = this.appendHeaderRow();
		setFilterComponent(filterRow, "serialCode");
		setFilterComponent(filterRow, "type");
		setFilterComponent(filterRow, "colour");
	}

	private void setFilterComponent(HeaderRow filterRow, String columnId) {
		HeaderCell headerCell = filterRow.getCell(columnId);
		if (!columnId.equals("serialCode"))
			headerCell.setComponent(createDropDown(columnId));
		else
			headerCell.setComponent(createFilterField(columnId));
	}

	private Component createDropDown(String columnId) {
		NativeSelect<String> filter;
		ArrayList<String> items = new ArrayList<>();
		if (columnId.equals("type")) {
			filter = new NativeSelect<>("Type filter");
			items.add("All types");
			items.addAll(Stream.of(TrainType.values()).map(TrainType::name).collect(Collectors.toList()));
		} else {
			filter = new NativeSelect<>("Colour filter");
			items.add("All colours");
			items.addAll(Stream.of(Colour.values()).map(Colour::name).collect(Collectors.toList()));
		}
		filter.setItems(items);
		filter.setValue(items.get(0));
		filter.setEmptySelectionAllowed(false);
		filter.setHeight(85, Unit.PERCENTAGE);
		filter.setWidth(100, Unit.PERCENTAGE);
		filter.addValueChangeListener(listener ->{
			filterData.put(columnId, listener.getValue());
			dataProvider.refreshAll();
		});
		return filter;
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
		dataProvider = new CallbackDataProvider<>(query -> dao.fetchMultiple(filterData).stream(),
				query -> dao.fetchMultiple(filterData).size());
		setDataProvider(dataProvider);
	}
}

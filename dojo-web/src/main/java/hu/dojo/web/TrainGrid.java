package hu.dojo.web;

import java.util.HashMap;
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

public class TrainGrid extends Grid<Train> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		addColumn(train -> train.getSerialCode()).setId("serialCode").setCaption("Serial Code");
		addColumn(train -> train.getColour()).setId("colour").setCaption("Colour").setHidable(true);
		addColumn(train -> train.getType()).setId("type").setCaption("Type");
		HeaderRow filterRow = this.appendHeaderRow();
		setFilterComponent(filterRow, "serialCode");
		setFilterComponent(filterRow, "colour");
		setFilterComponent(filterRow, "type");
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
		dataProvider = new CallbackDataProvider<>(query -> dao.fetchMultiple(filterData).stream(),
				query -> dao.fetchMultiple(filterData).size());
		setDataProvider(dataProvider);
	}
}

package hu.dojo.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import com.vaadin.cdi.ViewScoped;
import com.vaadin.data.provider.CallbackDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;

import hu.dojo.backend.IEntityDAO;
import hu.dojo.jpa.UserAccount;

@ViewScoped
public class UserAccountGrid extends Grid<UserAccount> {

	private DataProvider<UserAccount, String> dataProvider;
	private Map<String, Object> filterData = new HashMap<String, Object>();

	@EJB(beanName = "UserAccountDAO")
	private IEntityDAO<UserAccount> dao;

	@PostConstruct
	private void init() {
		setSizeFull();
		setBeanType(UserAccount.class);
		initDataProvider();
		initColums();
	}

	private void initColums() {
		addColumn(user -> user.getEmailAddress()).setId("emailAddress").setCaption("Email address").setHidable(true);
		addColumn(user -> user.getLastname()).setId("lastname").setCaption("Lastname");
		addColumn(user -> user.getFirstname()).setId("firstname").setCaption("Firstname");
		HeaderRow filterRow = this.appendHeaderRow();
		setFilterComponent(filterRow, "emailAddress");
		setFilterComponent(filterRow, "lastname");
		setFilterComponent(filterRow, "firstname");
	}

	private void setFilterComponent(HeaderRow filterRow, String columnId) {
		HeaderCell headerCell = filterRow.getCell(columnId);
		headerCell.setComponent(createFilterField(columnId));
	}

	@SuppressWarnings("serial")
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

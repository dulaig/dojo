package hu.dojo.web;

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
import hu.dojo.jpa.UserAccount;

public class UserAccountGrid extends Grid<UserAccount> {

	private DataProvider<UserAccount, String> dataProvider;

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
		addColumn(UserAccount::getFirstname).setCaption("Firstname");
		setFilterComponent("emailAddress");
	}

	private void setFilterComponent(String columnId) {
		HeaderRow filterRow = this.appendHeaderRow();
		HeaderCell headerCell = filterRow.getCell(columnId);
		headerCell.setComponent(createFilterField());
	}

	private Component createFilterField() {
		TextField filterText = new TextField();
		filterText.setStyleName(ValoTheme.TEXTFIELD_TINY);
		filterText.setWidth(100, Unit.PERCENTAGE);
		return filterText;
	}

	private void initDataProvider() {
		dataProvider = new CallbackDataProvider<>(query -> dao.fetchMultiple().stream(),
				query -> dao.fetchMultiple().size());
		setDataProvider(dataProvider);
	}
}

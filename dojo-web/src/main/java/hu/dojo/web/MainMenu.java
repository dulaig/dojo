package hu.dojo.web;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.themes.ValoTheme;

public class MainMenu  extends CssLayout{
	
	@PostConstruct
	private void init() {
		setStyleName(ValoTheme.MENU_ROOT);
		initItems();
	}
	
	private void initItems() {
		Button index = new Button("Home", listener -> { getUI().getNavigator().navigateTo("index");});
		index.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		Button userList = new Button("User List", listener -> { getUI().getNavigator().navigateTo("userList");});
		userList.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		Button trainList = new Button("Train List", listener -> { getUI().getNavigator().navigateTo("trainList");});
		trainList.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		Button tripList = new Button("Trip List", listener -> { getUI().getNavigator().navigateTo("tripList");});
		tripList.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		addComponents(index, userList, trainList, tripList);
	}
}

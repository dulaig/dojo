package hu.dojo.web;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.Navigator;
import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.themes.ValoTheme;

@UIScoped
public class MainMenu extends CssLayout{
	
	@PostConstruct
	private void init() {
		setStyleName(ValoTheme.MENU_ROOT);		
		initItems();
	}
	
	private void initItems() {		
		Button index = new Button("Index", Listener -> {
			getUI().getNavigator().navigateTo("index");
		});
		index.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		Button userList = new Button("User list", Listener -> {
			getUI().getNavigator().navigateTo("userList");
		});
		Button trainList = new Button("Train list", Listener ->{
			getUI().getNavigator().navigateTo("trainList");
		});
		Button tripList = new Button("Trip list", Listener ->{
			getUI().getNavigator().navigateTo("tripList");
		});
		userList.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		index.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		trainList.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		tripList.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		addComponents(index,userList, trainList, tripList);
		
	}
}

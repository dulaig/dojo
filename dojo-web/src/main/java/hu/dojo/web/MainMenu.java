package hu.dojo.web;

import javax.annotation.PostConstruct;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.cdi.UIScoped;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

import hu.dojo.jpa.UserAccount;

@UIScoped
public class MainMenu extends CssLayout{
	
	@PostConstruct
	private void init() {
		setStyleName(ValoTheme.MENU_ROOT);		
		initItems();
	}
	
	private void initItems() {
		HorizontalLayout userLayout = new HorizontalLayout();
		Label greetings = new Label();
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
		Button logout = new Button("",Listener -> {
			VaadinSession.getCurrent().setAttribute("user",null);
			Page.getCurrent().reload();
		});
		logout.setIcon(VaadinIcons.EXIT);
		userList.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		index.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		trainList.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		tripList.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		logout.setPrimaryStyleName(ValoTheme.BUTTON_BORDERLESS);
		greetings.setPrimaryStyleName(ValoTheme.LABEL_LIGHT);
		Object u = VaadinSession.getCurrent().getAttribute("user");
		if(u != null) {
			greetings.setValue("Hello, "+((UserAccount) u).getFirstname());	
			userLayout.addComponents(greetings,logout);
			userLayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
			addComponent(userLayout);
		}
		addComponents(index,userList, trainList, tripList);
		
	}
}

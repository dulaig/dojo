package hu.dojo.web;

import javax.inject.Inject;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import hu.dojo.jpa.SessionData;

@Theme("DojoWeb")
@CDIUI("")
public class DojoUI extends UI {

	@Inject
	private CDIViewProvider viewProvider;
	
	@Inject
	private MainLayout mainLayout;
	
	public static SessionData sessionData;
	
	@Override
	protected void init(VaadinRequest request) {
		sessionData = SessionData.getInstance();
		Navigator navigator = new Navigator(this, mainLayout.getContainer());
		navigator.addProvider(viewProvider);
		navigator.navigateTo("index");
		setContent(mainLayout);
	}
}

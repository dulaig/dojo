package hu.dojo.web;

import javax.inject.Inject;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Theme("DojoWeb")
@CDIUI("")
public class DojoUI extends UI {

	@Inject
	private CDIViewProvider viewProvider;
	
	@Inject
	private MainLayout mainLayout;
	
	@Override
	protected void init(VaadinRequest request) {		
		Navigator navigator = new Navigator(this, mainLayout.getContainer());
		navigator.addProvider(viewProvider);
		navigator.navigateTo("index");
		setContent(mainLayout);
	}
}

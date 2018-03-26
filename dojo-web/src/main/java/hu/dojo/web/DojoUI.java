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
	CDIViewProvider viewProvider;

	@Override
	protected void init(VaadinRequest request) {
		Navigator navigator = new Navigator(this, this);
		navigator.addProvider(viewProvider);
		navigator.navigateTo("userList");
	}
}

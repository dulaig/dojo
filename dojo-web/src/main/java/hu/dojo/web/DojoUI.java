package hu.dojo.web;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.Label;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;

@Theme("DojoWeb")
@CDIUI("")
public class DojoUI extends UI {

	@Override
	protected void init(VaadinRequest request) {
		Label lbl = new Label("Hello vaadin");
		setContent(lbl);
	}
}

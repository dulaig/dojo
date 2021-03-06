package hu.dojo.web;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

import hu.dojo.backend.IEntityDAO;
import hu.dojo.jpa.UserAccount;

@Theme("DojoWeb")
@CDIUI("")
public class DojoUI extends UI {

	@Inject
	UserAccountGrid grid;

	@Override
	protected void init(VaadinRequest request) {
		Label lbl = new Label("Hello Vaadin");
		setContent(grid);
	}
}

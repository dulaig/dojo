package hu.dojo.web;

import javax.ejb.EJB;

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

	@EJB(beanName = "UserAccountDAO")
	private IEntityDAO<UserAccount> dao;

	@Override
	protected void init(VaadinRequest request) {
		Label lbl = new Label("Hello vaadin");
		setContent(lbl);
	}
}

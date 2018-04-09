package hu.dojo.web;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

@UIScoped
public class MainLayout extends HorizontalLayout{
	
	@Inject
	private MainMenu mainMenu;
	
	private CssLayout container;	

	@PostConstruct
	private void init() {
		setSizeFull();
		container = new CssLayout();
		mainMenu.setHeight(100, Unit.PERCENTAGE);
		container.setHeight(100, Unit.PERCENTAGE);
		this.addComponent(mainMenu);
		this.addComponentsAndExpand(container);
	}
	
	public CssLayout getContainer() {
		return container;
	}
}

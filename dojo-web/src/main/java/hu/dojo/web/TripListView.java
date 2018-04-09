package hu.dojo.web;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

@CDIView("tripList")
public class TripListView extends VerticalLayout implements View{

	@Inject
	private TripGrid grid;
	private Button add;
	private Button edit;
	private Button remove;
	
	@PostConstruct
	private void init() {
		HorizontalLayout buttons = new HorizontalLayout();
		add = new Button("Add");
		edit = new Button("Edit");
		remove = new Button("Remove");
		buttons.addComponents(add, edit, remove);
		addComponents(buttons, grid);
	}
}

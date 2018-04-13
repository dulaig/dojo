package hu.dojo.web;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@CDIView("trainList")
public class TrainView extends VerticalLayout implements View {
	
	@Inject
	private TrainGrid grid;
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

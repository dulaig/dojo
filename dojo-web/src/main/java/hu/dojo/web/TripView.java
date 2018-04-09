package hu.dojo.web;

import javax.annotation.PostConstruct;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@CDIView("tripList")
public class TripView extends VerticalLayout implements View{

	@PostConstruct
	private void init() {
		setSizeFull();
		Label label = new Label("This is TrainList!!");
		label.setPrimaryStyleName(ValoTheme.LABEL_H1);
		addComponent(label);
	}
}

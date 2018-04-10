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
	@PostConstruct
	private void init() {
		setSizeFull();
		Label label = new Label("This is TrainList!!");
		label.setPrimaryStyleName(ValoTheme.LABEL_H1);
		addComponent(label);
	}
}

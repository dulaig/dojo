package hu.dojo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Grid.SelectionMode;

import hu.dojo.backend.Editor;
import hu.dojo.backend.Remover;
import hu.dojo.jpa.AbstractEntity;
import hu.dojo.jpa.Train;
import hu.dojo.jpa.TrainType;
import hu.dojo.jpa.Trip;

@CDIView("tripList")
public class TripListView extends VerticalLayout implements View {

	@Inject
	private TripGrid grid;
	private Button addBtn, removeBtn;
	private Remover remover;
	private Editor editor;
	private boolean hide;
	private ArrayList<String> trainList;
	private List<Train> gridList;
	private Window subWindow;

	@PostConstruct
	private void init() {
		setSizeFull();
		HorizontalLayout buttons = new HorizontalLayout();
		addBtn = new Button("Add");
		removeBtn = new Button("Remove");
		remover = new Remover();
		editor = new Editor();
		hide = true;
		trainList = new ArrayList<String>();
		subWindow = new Window("Add");
		subWindow.setResizable(false);
		subWindow.center();

		buttons.addComponents(addBtn, removeBtn);
		grid.setSelectionMode(SelectionMode.NONE);
		if (VaadinSession.getCurrent().getAttribute("user") != null)
			addComponents(buttons);
		addComponentsAndExpand(grid);
		gridList = grid.getTrains();
		for (int i = 0; i < gridList.size(); i++)
			trainList.add(gridList.get(i).getSerialCode());
		subWindow.setContent(initAddWindow(trainList));
		removeBtn.addClickListener(listener -> {
			if (hide) {
				grid.setSelectionMode(SelectionMode.MULTI);
				hide = false;
			} else {
				Set<Trip> selectedItems = grid.getSelectedItems();
				List<AbstractEntity> trips = selectedItems.stream().collect(Collectors.toList());
				if (remover.entityRemove(trips, "trip")) {
					grid.deselectAll();
					Notification.show("Success delete!");
					grid.getDataProvider().refreshAll();
				}
				hide = true;
				grid.setSelectionMode(SelectionMode.NONE);
			}
		});
		addBtn.addClickListener(listener -> {
			if (UI.getCurrent().getWindows().size() == 0) {
				UI.getCurrent().addWindow(subWindow);
			}
		});
	}

	private VerticalLayout initAddWindow(ArrayList<String> list) {

		VerticalLayout content = new VerticalLayout();
		HorizontalLayout dTimeInput = new HorizontalLayout();
		HorizontalLayout aTimeInput = new HorizontalLayout();
		TextField from, to;
		TextField dHour, dMin, aHour, aMin;
		Button save = new Button("Save");

		dHour = new TextField();
		dMin = new TextField();
		aHour = new TextField();
		aMin = new TextField();
		from = new TextField("From:");
		to = new TextField("To:");
		NativeSelect<String> serialCode = new NativeSelect<String>("Serial Code: ");

		serialCode.setItems(list);
		serialCode.setValue(list.get(0));
		serialCode.setEmptySelectionAllowed(false);
		dHour.setWidth(50, Unit.PIXELS);
		dMin.setWidth(50, Unit.PIXELS);
		aHour.setWidth(50, Unit.PIXELS);
		aMin.setWidth(50, Unit.PIXELS);

		save.addClickListener(listener -> {
			System.out.println(serialCode.getValue() + " a kiválasztott vonat!");
		});

		dHour.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);
		dMin.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);
		aHour.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);
		aMin.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);

		dTimeInput.addComponents(dHour, new Label(":"), dMin);
		aTimeInput.addComponents(aHour, new Label(":"), aMin);
		content.addComponents(serialCode, from, to, new Label("Departure: "), dTimeInput, new Label("Arrival: "),
				aTimeInput, save);

		return content;
	}
}

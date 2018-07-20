package hu.dojo.web;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;

import hu.dojo.backend.Editor;
import hu.dojo.backend.Remover;
import hu.dojo.jpa.AbstractEntity;
import hu.dojo.jpa.Trip;

@CDIView("tripList")
public class TripListView extends VerticalLayout implements View{

	@Inject
	private TripGrid grid;
	private Button addBtn, removeBtn;
	private Remover remover;
	private Editor editor;
	private boolean hide;
	
	@PostConstruct
	private void init() {
		setSizeFull();
		HorizontalLayout buttons = new HorizontalLayout();
		addBtn = new Button("Add");
		removeBtn = new Button("Remove");
		remover = new Remover();
		editor = new Editor();
		hide = true;
		buttons.addComponents(addBtn, removeBtn);
		grid.setSelectionMode(SelectionMode.NONE);		
		addComponents(buttons);
		addComponentsAndExpand(grid);
		
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
	}
}



















package hu.dojo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.google.gwt.view.client.SelectionModel;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import hu.dojo.backend.Remove;
import hu.dojo.jpa.UserAccount;

@CDIView("userList")
public class UserAccountListView extends VerticalLayout implements View {

	@Inject
	private UserAccountGrid grid;
	private Button edit;
	private Button removeBtn;
	private Remove delete;

	private boolean hide;
	@PostConstruct
	private void init() {
		setSizeFull();
		HorizontalLayout buttons = new HorizontalLayout();
		edit = new Button("Edit");
		removeBtn = new Button("Remove");
		delete = new Remove();
		hide = true;
		buttons.addComponents(edit, removeBtn);
		grid.setSelectionMode(SelectionMode.NONE);
		addComponents(buttons);
		addComponentsAndExpand(grid);
		
		removeBtn.addClickListener(listener -> {
			if (hide) {
				grid.setSelectionMode(SelectionMode.MULTI);
				hide =false;
			} else {
				Set<UserAccount> selectedItems = grid.getSelectedItems();
				List<UserAccount> users = selectedItems.stream().collect(Collectors.toList());
				if (delete.remove(users)) {
					grid.setSelectionMode(SelectionMode.NONE);
					Notification.show("Success delete!");
					hide = true;
				}else
					Notification.show("Failed delete!");
			}
		});

	}

}

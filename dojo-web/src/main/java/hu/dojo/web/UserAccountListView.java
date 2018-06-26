package hu.dojo.web;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import hu.dojo.backend.Remover;
import hu.dojo.jpa.UserAccount;

@CDIView("userList")
public class UserAccountListView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	@Inject
	private UserAccountGrid grid;
	private Button edit;
	private Button removeBtn;
	private Remover remove;

	private boolean hide;
	@PostConstruct
	private void init() {
		setSizeFull();
		HorizontalLayout buttons = new HorizontalLayout();
		edit = new Button("Edit");
		removeBtn = new Button("Remove");
		remove = new Remover();
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
				if (remove.userRemove(users)) {
					Notification.show("Success delete!");
					grid.getDataProvider().refreshAll();
				}else
					Notification.show("Failed delete!");
			}
		});

	}

}

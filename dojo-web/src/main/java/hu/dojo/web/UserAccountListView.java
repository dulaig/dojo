package hu.dojo.web;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import hu.dojo.backend.Editor;
import hu.dojo.backend.Remover;
import hu.dojo.jpa.UserAccount;

@CDIView("userList")
public class UserAccountListView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	@Inject
	private UserAccountGrid grid;
	private Button editBtn;
	private Button removeBtn;
	private Remover remover;
	private Editor editor;

	@PostConstruct
	private void init() {
		setSizeFull();
		HorizontalLayout buttons = new HorizontalLayout();
		editBtn = new Button("Edit");
		removeBtn = new Button("Remove");
		remover = new Remover();
		editor = new Editor();
		buttons.addComponents(editBtn, removeBtn);
		grid.setSelectionMode(SelectionMode.MULTI);
		addComponents(buttons);
		addComponentsAndExpand(grid);

		removeBtn.addClickListener(listener -> {
			Set<UserAccount> selectedItems = grid.getSelectedItems();
			List<UserAccount> users = selectedItems.stream().collect(Collectors.toList());
			if (remover.userRemove(users)) {
				grid.deselectAll();
				Notification.show("Success delete!");
				grid.getDataProvider().refreshAll();
			} else
				Notification.show("Failed delete!");
		});

		editBtn.addClickListener(listener -> {
			Set<UserAccount> selectedItems = grid.getSelectedItems();
			List<UserAccount> users = selectedItems.stream().collect(Collectors.toList());
			if (users.size() > 0) {
				//Táblázat átalakítása bemeneti mezõkkel
			} else
				Notification.show("No item selected!");
		});
	}
	
	private void changeTableCell() {
		
	}

	private boolean validEmail(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

}

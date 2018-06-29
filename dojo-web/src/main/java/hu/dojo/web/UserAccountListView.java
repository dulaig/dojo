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
	private Window window;

	@PostConstruct
	private void init() {
		setSizeFull();
		HorizontalLayout buttons = new HorizontalLayout();
		editBtn = new Button("Edit");
		removeBtn = new Button("Remove");
		remover = new Remover();
		editor = new Editor();
		window = new Window("Editor");
		window.center();
		window.setResizable(false);
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
				window.setContent(editorContent(users.get(0)));
				UI.getCurrent().addWindow(window);
			} else
				Notification.show("No item selected!");
		});
	}

	private VerticalLayout editorContent(UserAccount user) {
		VerticalLayout layout = new VerticalLayout();
		TextField email = new TextField("Email adress: ");
		email.setValue(user.getEmailAddress());
		TextField firstname = new TextField("Firstname: ");
		firstname.setValue(user.getFirstname());
		TextField lastname = new TextField("Lastname: ");
		lastname.setValue(user.getLastname());
		UserAccount newUser = new UserAccount();
		Button saveBtn = new Button("Save", event -> {
			lastname.setComponentError(null);
			email.setComponentError(null);
			firstname.setComponentError(null);
			if (validEmail(email.getValue())) {
				newUser.setEmailAddress(email.getValue());
				if (firstname.getValue().length() > 3 && firstname.getValue().length() < 50) {
					newUser.setFirstname(firstname.getValue());
					if (lastname.getValue().length() > 3 && lastname.getValue().length() < 50) {
						newUser.setLastname(lastname.getValue());
						newUser.setId(user.getId());
						if (editor.editUser(newUser)) {
							Notification.show("The changes saved!");
							UI.getCurrent().removeWindow(window);
							grid.getDataProvider().refreshAll();
							grid.deselectAll();
						} else
							Notification.show("The changes not saved!");
					} else
						lastname.setComponentError(new UserError("The surname must have 3 to 50 characters!"));
				} else
					firstname.setComponentError(new UserError("The first name must be between 3 and 50 character!"));
			} else
				email.setComponentError(new UserError("This doesn't look like a valid email address"));
		});
		Button cancelBtn = new Button("Cancel", event -> UI.getCurrent().removeWindow(window));
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponents(saveBtn, cancelBtn);
		layout.addComponents(email, firstname, lastname, buttons);
		return layout;
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

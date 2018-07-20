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
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import hu.dojo.backend.Remover;
import hu.dojo.jpa.UserAccount;
import hu.dojo.jpa.AbstractEntity;
import hu.dojo.backend.Editor;

@CDIView("userList")
public class UserAccountListView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	@Inject
	private UserAccountGrid grid;
	private Button removeBtn;
	private Remover remover;
	private Editor editor;
	private boolean hide;
	private TextField emailEditor, fnameEditor, lnameEditor;

	@PostConstruct
	private void init() {
		setSizeFull();
		hide = true;
		removeBtn = new Button("Remove");
		remover = new Remover();
		editor = new Editor();
		grid.setSelectionMode(SelectionMode.NONE);
		initEditor();
		addComponent(removeBtn);
		addComponentsAndExpand(grid);

		removeBtn.addClickListener(listener -> {
			if (hide) {
				grid.setSelectionMode(SelectionMode.MULTI);
				hide = false;
			} else {
				Set<UserAccount> selectedItems = grid.getSelectedItems();
				List<AbstractEntity> users = selectedItems.stream().collect(Collectors.toList());
				if (remover.entityRemove(users, "user")) {
					grid.deselectAll();
					Notification.show("Success delete!");
					grid.getDataProvider().refreshAll();
				}
				hide = true;
				grid.setSelectionMode(SelectionMode.NONE);
			}
		});
		grid.getEditor().addSaveListener(listener -> {
			UserAccount user = listener.getBean();
			editor.editUser(user);
			Notification.show("The changes have been saved.");
		});
	}

	private void initEditor() {
		emailEditor = new TextField();
		fnameEditor = new TextField();
		lnameEditor = new TextField();
		emailEditor.addValueChangeListener(event -> {
			if (!validEmail(event.getValue())) {
				emailEditor.setComponentError(new UserError("This doesn't look like a valid email address"));
				grid.getEditor().setSaveCaption("");
			} else {
				emailEditor.setComponentError(null);
				grid.getEditor().setSaveCaption("Save");
			}
		});
		fnameEditor.addValueChangeListener(event -> {
			if (event.getValue().length() < 3 || event.getValue().length() > 50) {
				fnameEditor.setComponentError(new UserError("The first name must be between 3 and 50 characters"));
				grid.getEditor().setSaveCaption("");
			} else {
				fnameEditor.setComponentError(null);
				grid.getEditor().setSaveCaption("Save");
			}
		});
		lnameEditor.addValueChangeListener(event -> {
			if (event.getValue().length() < 3 || event.getValue().length() > 50) {
				lnameEditor.setComponentError(new UserError("The last name must be between 3 and 50 characters"));
				grid.getEditor().setSaveCaption("");
			} else {
				lnameEditor.setComponentError(null);
				grid.getEditor().setSaveCaption("Save");
			}
		});
		grid.getEditor().setEnabled(true);
		grid.getColumn("emailAddress").setEditorComponent(emailEditor);
		grid.getColumn("firstname").setEditorComponent(fnameEditor);
		grid.getColumn("lastname").setEditorComponent(lnameEditor);
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

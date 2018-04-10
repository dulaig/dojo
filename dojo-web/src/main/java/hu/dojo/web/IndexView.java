package hu.dojo.web;

import javax.annotation.PostConstruct;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@CDIView("index")
public class IndexView extends VerticalLayout implements View {
	
	String mode;
	
	@PostConstruct
	private void init() {
		setSizeFull();
		mode = "login";
		TextField email, firstname, lastname;
		FormLayout form = new FormLayout();
		Label title = new Label("Login / Sign Up");
		title.setStyleName(ValoTheme.LABEL_H4);		
		email = new TextField("Email");
		firstname = new TextField("Firstname");
		lastname = new TextField("Lastname");
		PasswordField password = new PasswordField("Password");
		Button login = new Button("Sign in");
		Button register = new Button("Sign up");
		HorizontalLayout buttons = new HorizontalLayout();

		buttons.addComponents(login, register);
		form.addComponents(title, email, password, buttons);
		addComponent(form);

		register.addClickListener(listener -> {
			if ("login".equals(mode)) {
				mode = "register";
				form.removeComponent(buttons);
				form.addComponents(firstname, lastname, buttons);
			} else {
				Notification.show("Sign up!");
			}
		});
		login.addClickListener(listener -> {
			if ("login".equals(mode)) {
				Notification.show("Sign in!");
			} else {
				mode = "login";
				form.removeComponent(lastname);
				form.removeComponent(firstname);
			}
		});
	}
}

package hu.dojo.web;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tapestry.form.validator.Email;
import org.apache.tools.ant.taskdefs.email.EmailAddress;
import org.eclipse.persistence.internal.oxm.MappingNodeValue;

import com.gargoylesoftware.htmlunit.javascript.host.html.FormField;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.server.ErrorMessage;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import hu.dojo.backend.Authentication;
import hu.dojo.backend.IEntityDAO;
import hu.dojo.jpa.UserAccount;

@CDIView("index")
public class IndexView extends VerticalLayout implements View {

	@EJB(beanName = "Authentication")
	private Authentication auth;

	private Button login;
	private Button register;
	private Button sendregister;
	private Button back;
	private Label name;
	private MessageDigest digest;
	private ErrorMessage firstnameMessage = new ErrorMessage() {

		@Override
		public String getFormattedHtmlMessage() {
			return "Firstname is empty!";
		}

		@Override
		public ErrorLevel getErrorLevel() {
			return null;
		}
	};
	private ErrorMessage lastnameMessage = new ErrorMessage() {

		@Override
		public String getFormattedHtmlMessage() {
			return "Lastname is empty!";
		}

		@Override
		public ErrorLevel getErrorLevel() {
			return null;
		}
	};
	private ErrorMessage passwordMessage = new ErrorMessage() {

		@Override
		public String getFormattedHtmlMessage() {
			return "Password is empty!";
		}

		@Override
		public ErrorLevel getErrorLevel() {
			return null;
		}
	};
	private ErrorMessage emailMessage = new ErrorMessage() {

		@Override
		public String getFormattedHtmlMessage() {
			return "Email is empty!";
		}

		@Override
		public ErrorLevel getErrorLevel() {
			return null;
		}
	};
	private ErrorMessage loginMessage = new ErrorMessage() {

		@Override
		public String getFormattedHtmlMessage() {
			return "Invalid credentials!";
		}

		public ErrorLevel getErrorLevel() {
			return null;
		}
	};

	@PostConstruct
	private void init() {
		setSizeFull();
		VerticalLayout authFields = new VerticalLayout();
		HorizontalLayout buttonFields = new HorizontalLayout();
		/*
		 * Label label = new Label("This is Thomas!");
		 * label.setPrimaryStyleName(ValoTheme.LABEL_H1); addComponent(label);
		 */
		TextField emailField = new TextField("Email address:");
		PasswordField passwordField = new PasswordField("Password:");
		TextField lastnameField = new TextField("Last name");
		TextField firstnameField = new TextField("First name");
		lastnameField.setVisible(false);
		firstnameField.setVisible(false);
		login = new Button("Login");
		register = new Button("Register");
		sendregister = new Button("Send");
		back = new Button("Back");
		back.setVisible(false);
		sendregister.setVisible(false);
		name = new Label();
		name.setValue("");
		name.setVisible(true);
		Binder<UserAccount> registerBinder = new Binder<>();
		Binder<UserAccount> loginBinder;
		buttonFields.addComponents(login, register, back, sendregister);
		authFields.addComponents(firstnameField, lastnameField, emailField, passwordField, buttonFields, name);
		addComponent(authFields);

		register.addClickListener(listener -> {
			firstnameField.setVisible(true);
			lastnameField.setVisible(true);
			login.setVisible(false);
			register.setVisible(false);
			back.setVisible(true);
			sendregister.setVisible(true);
			UserAccount newUser = new UserAccount();
			registerBinder.setBean(newUser);
			registerBinder.forField(firstnameField).withValidator(new StringLengthValidator("Too short!", 3, 50))
					.bind(UserAccount::getFirstname, UserAccount::setFirstname);
			registerBinder.forField(lastnameField).withValidator(new StringLengthValidator("Too short!", 4, 50))
					.bind(UserAccount::getLastname, UserAccount::setLastname);
			registerBinder.forField(emailField)
					.withValidator(new EmailValidator("This doesn't look like a valid email address"))
					.bind(UserAccount::getEmailAddress, UserAccount::setEmailAddress);
			registerBinder.forField(passwordField).withValidator(new StringLengthValidator("Too short!", 7, 50))
					.bind(UserAccount::getPassword, UserAccount::setPassword);
		});

		sendregister.addClickListener(listener -> {
			try {
				String firstnameValue = firstnameField.getValue();
				String lastnameValue = lastnameField.getValue();
				String passwordValue = passwordField.getValue();
				String emailValue = emailField.getValue();
				digest = MessageDigest.getInstance("SHA-256");
				byte[] hash = digest.digest(passwordValue.getBytes(StandardCharsets.UTF_8));
				String encoded = Base64.getEncoder().encodeToString(hash);
				if (firstnameValue == null || "".equals(firstnameValue)) {
					sendregister.setComponentError(firstnameMessage);
				} else if (lastnameValue == null || "".equals(lastnameValue)) {
					sendregister.setComponentError(lastnameMessage);
				} else if (emailValue == null || "".equals(emailValue)) {
					sendregister.setComponentError(emailMessage);
				} else if (passwordValue == null || "".equals(passwordValue)) {
					sendregister.setComponentError(passwordMessage);
				} else {
					UserAccount newUser = new UserAccount();
					newUser.setFirstname(firstnameValue);
					newUser.setLastname(lastnameValue);
					newUser.setEmailAddress(emailValue);
					newUser.setPassword(encoded);
					boolean validEmail = auth.validateEmail(newUser);
					if (registerBinder.validate().isOk() && validEmail) {	
						auth.registration(newUser);
						Notification.show("Success registration!");
					}
					else if (!validEmail){
						Notification.show("Unsucces registration, because this email is already used!");
					}
					else {
						Notification.show("Unsucces registration!");
					}
				}
			} catch (NoSuchAlgorithmException e) {
				System.err.println("I'm sorry, but SHA-256 is not a valid message digest algorithm");
			}

		});

		login.addClickListener(listener -> {
			try {
				String emailValue = emailField.getValue();
				String passwordValue = passwordField.getValue();
				digest = MessageDigest.getInstance("SHA-256");
				byte[] hash = digest.digest(passwordValue.getBytes(StandardCharsets.UTF_8));
				String encoded = Base64.getEncoder().encodeToString(hash);
				if (emailValue == null || "".equals(emailValue)) {
					login.setComponentError(emailMessage);
				} else if (passwordValue == null || "".equals(passwordValue)) {
					login.setComponentError(passwordMessage);
					// }else if(auth.findUser(emailValue, passwordValue) == null){
					// login.setComponentError(loginMessage);
				} else {
					// Integer szam = 4;
					// Long id = new Long(szam);
					// UserAccount user = auth.fetch(id);
					if (auth.findUser(emailValue, passwordValue) == null) {
						name.setValue("User is null!");
					} else {
						UserAccount user = auth.findUser(emailValue, passwordValue);
						// name.setValue(user.getFirstname() + " " + user.getLastname());
						name.setValue(encoded);
						// ((DojoUI)getUI()).sessionData.setEmail(user.getEmailAddress());
						// ((DojoUI)getUI()).sessionData.setFirstName(user.getFirstname());
						// ((DojoUI)getUI()).sessionData.setLastName(user.getLastname());
					}
					// name.setValue(user.getFirstname());
					/*
					 * if(user == null) { name.setValue("User is null!"); }else {
					 * name.setValue(user.getFirstname()); }
					 */
					// ((DojoUI)getUI()).sessionData.setEmail(user.getEmailAddress());
					// ((DojoUI)getUI()).sessionData.setFirstName(user.getFirstname());
					// ((DojoUI)getUI()).sessionData.setLastName(user.getLastname());

				}
			} catch (NoSuchAlgorithmException e) {
				System.err.println("I'm sorry, but SHA-256 is not a valid message digest algorithm");
			}
		});

		back.addClickListener(listener -> {
			lastnameField.setVisible(false);
			firstnameField.setVisible(false);
			sendregister.setVisible(false);
			back.setVisible(false);
			login.setVisible(true);
			register.setVisible(true);
		});
	}
}

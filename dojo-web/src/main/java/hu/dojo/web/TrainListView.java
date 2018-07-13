package hu.dojo.web;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.UserError;
import com.vaadin.server.ErrorMessage.ErrorLevel;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import hu.dojo.backend.Adder;
import hu.dojo.backend.Authentication;
import hu.dojo.backend.Editor;
import hu.dojo.backend.IEntityDAO;
import hu.dojo.backend.Remover;
import hu.dojo.backend.TrainDAO;
import hu.dojo.jpa.AbstractEntity;
import hu.dojo.jpa.Colour;
import hu.dojo.jpa.Train;
import hu.dojo.jpa.TrainType;
import hu.dojo.jpa.UserAccount;

@CDIView("trainList")
public class TrainListView extends VerticalLayout implements View {

	@EJB(beanName = "TrainDAO")
	private IEntityDAO<Train> dao;

	@Inject
	private TrainGrid grid;
	private Button addBtn;
	private Button removeBtn;
	private Remover remover;
	private Editor editor;
	private Window subWindow;
	private VerticalLayout subContent;
	private Button saveBtn;
	private boolean hide;
	private TextField serialEdit;
	private NativeSelect<TrainType> typeEdit;
	private NativeSelect<Colour> colourEdit;
	private ErrorMessage serialMessage = new ErrorMessage() {

		@Override
		public String getFormattedHtmlMessage() {
			// TODO Auto-generated method stub
			return "Serial code is empty!";
		}

		@Override
		public ErrorLevel getErrorLevel() {
			// TODO Auto-generated method stub
			return null;
		}
	};
	private ErrorMessage typeMessage = new ErrorMessage() {

		@Override
		public String getFormattedHtmlMessage() {
			// TODO Auto-generated method stub
			return "Type is empty!";
		}

		@Override
		public ErrorLevel getErrorLevel() {
			// TODO Auto-generated method stub
			return null;
		}
	};
	private ErrorMessage colourMessage = new ErrorMessage() {

		@Override
		public String getFormattedHtmlMessage() {
			// TODO Auto-generated method stub
			return "Colour is empty!";
		}

		@Override
		public ErrorLevel getErrorLevel() {
			// TODO Auto-generated method stub
			return null;
		}
	};

	@PostConstruct
	private void init() {
		setSizeFull();
		HorizontalLayout buttons = new HorizontalLayout();
		subWindow = new Window("Add");
		subContent = new VerticalLayout();
		subWindow.setContent(subContent);
		hide = true;
		Binder<Train> trainBinder = new Binder<Train>();
		initEditor();
		TextField serialField = new TextField();
		NativeSelect<TrainType> selectType = new NativeSelect<>("Type select");
		selectType.setItems(TrainType.values());
		selectType.setValue(TrainType.values()[0]);
		selectType.setEmptySelectionAllowed(false);
		NativeSelect<Colour> colour = new NativeSelect<>("Colour");
		colour.setItems(Colour.values());
		colour.setValue(Colour.values()[0]);
		colour.setEmptySelectionAllowed(false);
		saveBtn = new Button("Save");
		saveBtn.setSizeFull();
		subContent.addComponents(new Label("Serial code"), serialField, selectType, colour, saveBtn);
		subWindow.center();
		addBtn = new Button("Add");
		removeBtn = new Button("Remove");
		remover = new Remover();
		editor = new Editor();
		buttons.addComponents(addBtn, removeBtn);
		grid.setSelectionMode(SelectionMode.NONE);
		addComponents(buttons);
		addComponentsAndExpand(grid);
		
		grid.getEditor().addSaveListener(listener -> {
			Train t = listener.getBean();
			editor.editTrain(t);
			Notification.show("The changes have been saved.");
		});

		removeBtn.addClickListener(listener -> {
			if (hide) {
				grid.setSelectionMode(SelectionMode.MULTI);
				hide = false;
			} else {
				Set<Train> selectedItems = grid.getSelectedItems();
				List<AbstractEntity> trains = selectedItems.stream().collect(Collectors.toList());
				if (remover.entityRemove(trains, "train")) {
					grid.deselectAll();
					Notification.show("Success delete!");
					grid.getDataProvider().refreshAll();
					grid.setSelectionMode(SelectionMode.NONE);
					hide = true;
				} else
					Notification.show("Failed delete!");
			}
		});

		addBtn.addClickListener(listener -> {
			UI.getCurrent().addWindow(subWindow);
			Train newTrain = new Train();
			trainBinder.setBean(newTrain);
			trainBinder.forField(serialField).withValidator(new StringLengthValidator("Too short!", 3, 50))
					.bind(Train::getSerialCode, Train::setSerialCode);

		});

		saveBtn.addClickListener(listener -> {
			String serialValue = serialField.getValue();
			TrainType typeValue = selectType.getValue();
			Colour colourValue = colour.getValue();
			if (serialValue == null || "".equals(serialValue)) {
				saveBtn.setComponentError(serialMessage);
			} else if (typeValue == null) {
				saveBtn.setComponentError(typeMessage);
			} else if (colourValue == null) {
				saveBtn.setComponentError(colourMessage);
			} else {
				Train newTrain = new Train();
				newTrain.setSerialCode(serialValue);
				newTrain.setType(typeValue);
				newTrain.setColour(colourValue);
				if (trainBinder.validate().isOk()) {
					dao.persist(newTrain);
					UI.getCurrent().removeWindow(subWindow);
					Notification.show("Success addition!");
					grid.getDataProvider().refreshAll();
				} else {
					Notification.show("Unsucces addition!");
				}
			}
		});
	}
	private void initEditor() {
		serialEdit = new TextField();
		typeEdit = new NativeSelect<>();
		typeEdit.setItems(TrainType.values());
		typeEdit.setEmptySelectionAllowed(false);
		colourEdit = new NativeSelect<>();
		colourEdit.setItems(Colour.values());
		colourEdit.setEmptySelectionAllowed(false);
		serialEdit.addValueChangeListener(listener -> {
			if(listener.getValue().length() < 3 || listener.getValue().length() > 50) {
				serialEdit.setComponentError(new UserError("The serial code must be between 3 and 50 character!"));
				grid.getEditor().setSaveCaption("");
			}else {
				serialEdit.setComponentError(null);
				grid.getEditor().setSaveCaption("Save");
			}
		});	
		grid.getEditor().setEnabled(true);
		grid.getColumn("serialCode").setEditorComponent(serialEdit);
		grid.getColumn("type").setEditorComponent(typeEdit);
		grid.getColumn("colour").setEditorComponent(colourEdit);
	}
}

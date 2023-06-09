package cmps251;

import cmps251.models.Service;
import cmps251.repos.ServiceRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Controller class for the add_service.fxml scene
 * 
 * <p> <i>Created on 20/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.22
 * @since		1.20
 */
public class AddServiceController {



/* --------------------------- Constant Attributes -------------------------- */
//region

	public static AddServiceController scene;
	public boolean editing = false;
	public Service data;

//endregion



/* -------------------------- Component Attributes -------------------------- */
//region

	@FXML
	public TextField serviceTitle;

	@FXML
	public TextField serviceMaxSlots;

	@FXML
	public TextField servicePricePerSlot;

	@FXML
	private Button cancelButton;

	@FXML
	private Button submitButton;

//endregion


	
/* ---------------------------- Initialize Method --------------------------- */
//region

	/**
	 * This function is called when the scene is first loaded.
     * Here we should assert for component injection, proper factories, and any other necessary procedures
	 */
	@FXML
	public void initialize() {
		assertComponents();
		setFactories();
		setObservables();
		scene = this;
	}

//endregion



/* --------------------------------- Getters -------------------------------- */
//region

//endregion



/* ----------------------------- Utility Methods ---------------------------- */
//region

	/**
	 * Makes sure all components are initialized properly and with the correct names
	 */
	private void assertComponents() {
		assert serviceTitle != null : "fx:id=\"serviceTitle\" was not injected: check your FXML file 'add_service.fxml'.";
        assert serviceMaxSlots != null : "fx:id=\"serviceMaxSlots\" was not injected: check your FXML file 'add_service.fxml'.";
        assert servicePricePerSlot != null : "fx:id=\"servicePricePerSlot\" was not injected: check your FXML file 'add_service.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'add_service.fxml'.";
        assert submitButton != null : "fx:id=\"submitButton\" was not injected: check your FXML file 'add_service.fxml'.";
	}

	/**
	 * Here is where factories should be set up
	 */
	private void setFactories() {
	}

	/**
	 * Assigns variables to observables in the scene
	 */
	private void setObservables() {
		serviceMaxSlots.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
				String newValue) {
				if (!newValue.matches("\\d*")) {
					serviceMaxSlots.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		servicePricePerSlot.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
				String newValue) {
				if (!newValue.matches("^\\d*\\.\\d+|\\d+\\.\\d*$")) {
					servicePricePerSlot.setText(newValue.replaceAll("[^\\d.]", ""));
				}
			}
		});
	}

//endregion



/* ----------------------------- Handler Methods ---------------------------- */
//region

	/**
	 * Function called when the user clicks on the cancel button
	 */
	@FXML
    void handleCancelForm(Event event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
    }

	/**
	 * Function called when the user clicks on the add button
	 */
    @FXML
    void handleSubmitForm(Event event) {
		try {
			if (serviceTitle.getText() == null || serviceTitle.getText().isBlank() || serviceMaxSlots.getText() == null || serviceMaxSlots.getText().isBlank() || servicePricePerSlot.getText() == null || servicePricePerSlot.getText().isBlank()) throw new Exception("Please enter all the required data");
			String title = serviceTitle.getText();
			int maxSlots = Integer.parseInt(serviceMaxSlots.getText());
			double pricePerSlot = Double.parseDouble(servicePricePerSlot.getText());
			if (!editing) ServiceRepository.addService(new Service(title, maxSlots, pricePerSlot));
			else ServiceRepository.updateService(data.getId(), new Service(title, maxSlots, pricePerSlot));
			MainController.scene.refreshAll();
			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();
		}
		catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.show();
		}
    }

//endregion
	
}

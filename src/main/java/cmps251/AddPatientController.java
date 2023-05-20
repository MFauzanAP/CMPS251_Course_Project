package cmps251;

import cmps251.models.Patient;
import cmps251.models.Patient.ResidencyType;
import cmps251.repos.PatientRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Controller class for the add_patient.fxml scene
 * 
 * <p> <i>Created on 20/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.22
 * @since		1.21
 */
public class AddPatientController {



/* --------------------------- Constant Attributes -------------------------- */
//region

	public static AddPatientController scene;
	public boolean editing = false;
	public Patient data;

//endregion



/* -------------------------- Component Attributes -------------------------- */
//region

	@FXML
	public TextField patientId;

	@FXML
	public TextField patientName;

	@FXML
	public ComboBox<String> patientResidency;

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
		assert patientId != null : "fx:id=\"patientId\" was not injected: check your FXML file 'add_patient.fxml'.";
        assert patientName != null : "fx:id=\"patientName\" was not injected: check your FXML file 'add_patient.fxml'.";
        assert patientResidency != null : "fx:id=\"patientResidency\" was not injected: check your FXML file 'add_patient.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'add_patient.fxml'.";
        assert submitButton != null : "fx:id=\"submitButton\" was not injected: check your FXML file 'add_patient.fxml'.";
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
		ObservableList<String> residencyTypes = FXCollections.observableArrayList();
		for (ResidencyType value : ResidencyType.values()) residencyTypes.add(value.toString());
		patientResidency.getItems().setAll(residencyTypes);
		patientId.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
				String newValue) {
				if (!newValue.matches("\\d*")) {
					patientId.setText(newValue.replaceAll("[^\\d]", ""));
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
			if (patientId.getText() == null || patientId.getText().isBlank() || patientName.getText() == null || patientName.getText().isBlank() || patientResidency.getValue() == null) throw new Exception("Please enter all the required data");
			String id = patientId.getText();
			String name = patientName.getText();
			ResidencyType residency = ResidencyType.valueOf(patientResidency.getValue());
			if (!editing) PatientRepository.addPatient(new Patient(id, name, residency));
			else PatientRepository.updatePatient(data.getId(), new Patient(id, name, residency));
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

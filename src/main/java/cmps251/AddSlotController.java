package cmps251;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

import cmps251.models.Patient;
import cmps251.models.Service;
import cmps251.models.Slot;
import cmps251.repos.PatientRepository;
import cmps251.repos.ServiceRepository;
import cmps251.repos.SlotRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Controller class for the add_slot.fxml scene
 * 
 * <p> <i>Created on 20/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.22
 * @since		1.19
 */
public class AddSlotController {



/* --------------------------- Constant Attributes -------------------------- */
//region

	public static AddSlotController scene;
	public boolean editing = false;
	public Slot data;

	ObservableList<Slot> availableSlots = FXCollections.observableArrayList();
	ObservableList<String> times = FXCollections.observableArrayList();
	ObservableList<String> services = FXCollections.observableArrayList(getServiceTitles(ServiceRepository.getServicesAsList()));
	ObservableList<String> patients = FXCollections.observableArrayList(getPatientNames(PatientRepository.getPatientsAsList()));

//endregion



/* -------------------------- Component Attributes -------------------------- */
//region

	@FXML
	public DatePicker slotDate;

	@FXML
	public ComboBox<String> slotTime;

	@FXML
	public ComboBox<String> slotService;

	@FXML
	public ComboBox<String> slotPatient;

	@FXML
	private Button cancelButton;

	@FXML
	private Button submitButton;

	@FXML
	private ListView<String> timeContainer;

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

	/** 
	 * Transforms a list of slots to a list of strings of the slot's times
	 * 
	 * @param slots						- the list of slots
	 * 
	 * @return ArrayList<String>		- a list of times
	 */
	private static ArrayList<String> getSlotTime(ArrayList<Slot> slots) {
		ArrayList<String> times = new ArrayList<String>();
		for (Slot slot : slots) times.add(slot.getTime().toString());
		times = new ArrayList<>(new LinkedHashSet<>(times));
		Collections.sort(times);
		return times;
	}
	private static ArrayList<String> getSlotTime(ObservableList<Slot> slots) {
		return getSlotTime(new ArrayList<>(slots));
	}

	/** 
	 * Transforms a list of patients to a list of strings of the patient's names
	 * 
	 * @param patients					- the list of patients
	 * 
	 * @return ArrayList<String>	- a list of names
	 */
	private static ArrayList<String> getPatientNames(ArrayList<Patient> patients) {
		ArrayList<String> names = new ArrayList<String>();
		for (Patient patient : patients) names.add(patient.getName().toString());
		names = new ArrayList<>(new LinkedHashSet<>(names));
		Collections.sort(names);
		return names;
	}

	/** 
	 * Transforms a list of services to a list of strings of the service's titles
	 * 
	 * @param services					- the list of services
	 * 
	 * @return ArrayList<String>		- a list of titles
	 */
	private static ArrayList<String> getServiceTitles(ArrayList<Service> services) {
		ArrayList<String> titles = new ArrayList<String>();
		for (Service service : services) titles.add(service.getTitle().toString());
		titles = new ArrayList<>(new LinkedHashSet<>(titles));
		Collections.sort(titles);
		return titles;
	}

//endregion



/* ----------------------------- Utility Methods ---------------------------- */
//region

	/**
	 * Makes sure all components are initialized properly and with the correct names
	 */
	private void assertComponents() {
		assert slotDate != null : "fx:id=\"slotDate\" was not injected: check your FXML file 'add_slot.fxml'.";
        assert slotTime != null : "fx:id=\"slotTime\" was not injected: check your FXML file 'add_slot.fxml'.";
        assert slotService != null : "fx:id=\"slotService\" was not injected: check your FXML file 'add_slot.fxml'.";
        assert slotPatient != null : "fx:id=\"slotPatient\" was not injected: check your FXML file 'add_slot.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'add_slot.fxml'.";
        assert submitButton != null : "fx:id=\"submitButton\" was not injected: check your FXML file 'add_slot.fxml'.";
        assert timeContainer != null : "fx:id=\"timeContainer\" was not injected: check your FXML file 'add_slot.fxml'.";
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
		timeContainer.setItems(times);
		slotTime.setItems(times);
		slotService.setItems(services);
		slotPatient.setItems(patients);
	}

//endregion



/* ----------------------------- Handler Methods ---------------------------- */
//region

	/**
	 * Function called when the user changes the data
	 */
    @FXML
    void handleDataChange(Event event) {
		LocalDate date = slotDate.getValue();
		String time = slotTime.getValue();
		Service service = slotService.getValue() != null && !slotService.getValue().isBlank() ? ServiceRepository.getServicesByTitle(slotService.getValue()).get(0) : null;
		if (date != null && service != null) this.availableSlots.setAll(SlotRepository.getAvailableSlotsByDateService(date, service));
		else if (date != null) this.availableSlots.setAll(SlotRepository.getAvailableSlotsByDate(date));
		if (editing) this.availableSlots.add(data);
		this.times.setAll(getSlotTime(availableSlots));
		if (this.times.contains(time)) slotTime.setValue(time);
		else slotTime.setValue(null);
    }

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
			LocalDate date = slotDate.getValue();
			LocalTime time = slotTime.getValue() != null && !slotTime.getValue().isBlank() ? LocalTime.parse(slotTime.getValue()) : null;
			Service service = slotService.getValue() != null && !slotService.getValue().isBlank() ? ServiceRepository.getServicesByTitle(slotService.getValue()).get(0) : null;
			Patient patient = slotPatient.getValue() != null && !slotPatient.getValue().isBlank() ? PatientRepository.getPatientsByName(slotPatient.getValue()).get(0) : null;
			if (date == null || time == null || service == null || patient == null) throw new Exception("Please enter all the required data");
			if (!editing) SlotRepository.bookSlot(date, time, service, patient);
			else SlotRepository.updateSlot(data.getId(), new Slot(date, time, service, patient));
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

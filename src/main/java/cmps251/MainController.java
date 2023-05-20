package cmps251;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import cmps251.components.TableCellButton;
import cmps251.models.Patient;
import cmps251.models.Service;
import cmps251.models.Slot;
import cmps251.repos.PatientRepository;
import cmps251.repos.ServiceRepository;
import cmps251.repos.SlotRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * Controller class for the main.fxml scene
 * 
 * <p> <i>Created on 19/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.17
 * @since		1.14
 */
public class MainController {



/* --------------------------- Constant Attributes -------------------------- */
//region

	public static MainController scene;

	ObservableList<Slot> slots = FXCollections.observableArrayList(SlotRepository.getSlotsAsList());
	ObservableList<Service> services = FXCollections.observableArrayList(ServiceRepository.getServicesAsList());
	ObservableList<Patient> patients = FXCollections.observableArrayList(PatientRepository.getPatientsAsList());

	ObservableList<String> serviceTitles = FXCollections.observableArrayList();
	ObservableList<String> patientNames = FXCollections.observableArrayList();

//endregion



/* -------------------------- Component Attributes -------------------------- */
//region

	@FXML
    private TabPane tabContainer;

    @FXML
    private Tab tabSlots;

	@FXML
    private DatePicker slotsDateBox;

	@FXML
    private ComboBox<String> slotsServiceBox;

    @FXML
    private ComboBox<String> slotsPatientBox;

	@FXML
    private TableView<Slot> tableSlots;

    @FXML
    private TableColumn<Slot, LocalDate> colSlotsDate;

    @FXML
    private TableColumn<Slot, LocalTime> colSlotsTime;

    @FXML
    private TableColumn<Slot, String> colSlotsService;

    @FXML
    private TableColumn<Slot, String> colSlotsPatient;

    @FXML
    private TableColumn<Slot, Button> colSlotsEdit;

	@FXML
    private Button printSlotsButton;

    @FXML
    private Button addSlotButton;

    @FXML
    private Tab tabServices;

	@FXML
    private ComboBox<String> servicesSearchBox;

	@FXML
    private TableView<Service> tableServices;

    @FXML
    private TableColumn<Service, String> colServicesTitle;

    @FXML
    private TableColumn<Service, Double> colServicesPricePerSlot;

    @FXML
    private TableColumn<Service, Integer> colServicesMaxSlots;

	@FXML
    private TableColumn<Service, Button> colServicesSlots;

    @FXML
    private TableColumn<Service, Button> colServicesEdit;

	@FXML
    private Button printServicesButton;

    @FXML
    private Button addServiceButton;

    @FXML
    private Tab tabPatients;

	@FXML
    private ComboBox<String> patientsSearchBox;

	@FXML
    private TableView<Patient> tablePatients;

    @FXML
    private TableColumn<Patient, String> colPatientsId;

    @FXML
    private TableColumn<Patient, String> colPatientsName;

    @FXML
    private TableColumn<Patient, Button> colPatientsSlots;

    @FXML
    private TableColumn<Patient, Button> colPatientsEdit;

	@FXML
    private Button printPatientsButton;

    @FXML
    private Button addPatientButton;

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
	 * Gets all the services under the slots
	 * 
	 * @return ArrayList<Service>		- the list of services
	 */
	private static ArrayList<Service> getSlotServices(ArrayList<Slot> slots) {
		ArrayList<Service> services = new ArrayList<Service>();
		for (Slot slot : slots) services.add(slot.getAllocatedService());
		return new ArrayList<>(new LinkedHashSet<>(services));
	}
	private static ArrayList<Service> getSlotServices(ObservableList<Slot> slots) {
		return getSlotServices(new ArrayList<>(slots));
	}

	/** 
	 * Gets all the patients under the slots
	 * 
	 * @return ArrayList<Patient>		- the list of patients
	 */
	private static ArrayList<Patient> getSlotPatients(ArrayList<Slot> slots) {
		ArrayList<Patient> patients = new ArrayList<Patient>();
		for (Slot slot : slots) patients.add(slot.getAllocatedPatient());
		return new ArrayList<>(new LinkedHashSet<>(patients));
	}
	private static ArrayList<Patient> getSlotPatients(ObservableList<Slot> slots) {
		return getSlotPatients(new ArrayList<>(slots));
	}

	/** 
	 * Gets all the service titles
	 * 
	 * @return ArrayList<String>		- the list of service titles
	 */
	private static ArrayList<String> getServiceTitles(ArrayList<Service> services) {
		ArrayList<String> titles = new ArrayList<String>();
		for (Service service : services) titles.add(service.getTitle());
		return titles;
	}
	private static ArrayList<String> getServiceTitles(ObservableList<Service> services) {
		return getServiceTitles(new ArrayList<>(services));
	}

	/** 
	 * Gets all the patients names
	 * 
	 * @return ArrayList<String>		- the list of patient names
	 */
	private static ArrayList<String> getPatientNames(ArrayList<Patient> patients) {
		ArrayList<String> names = new ArrayList<String>();
		for (Patient patient : patients) names.add(patient.getName());
		return names;
	}
	private static ArrayList<String> getPatientNames(ObservableList<Patient> patients) {
		return getPatientNames(new ArrayList<>(patients));
	}

//endregion



/* ----------------------------- Utility Methods ---------------------------- */
//region

	/**
	 * Makes sure all components are initialized properly and with the correct names
	 */
	private void assertComponents() {
		assert tabContainer != null : "fx:id=\"tabContainer\" was not injected: check your FXML file 'main.fxml'.";
        assert tabSlots != null : "fx:id=\"tabSlots\" was not injected: check your FXML file 'main.fxml'.";
        assert slotsDateBox != null : "fx:id=\"slotsDateBox\" was not injected: check your FXML file 'main.fxml'.";
        assert slotsServiceBox != null : "fx:id=\"slotsServiceBox\" was not injected: check your FXML file 'main.fxml'.";
        assert slotsPatientBox != null : "fx:id=\"slotsPatientBox\" was not injected: check your FXML file 'main.fxml'.";
        assert tableSlots != null : "fx:id=\"tableSlots\" was not injected: check your FXML file 'main.fxml'.";
        assert colSlotsDate != null : "fx:id=\"colSlotsDate\" was not injected: check your FXML file 'main.fxml'.";
        assert colSlotsTime != null : "fx:id=\"colSlotsTime\" was not injected: check your FXML file 'main.fxml'.";
        assert colSlotsService != null : "fx:id=\"colSlotsService\" was not injected: check your FXML file 'main.fxml'.";
        assert colSlotsPatient != null : "fx:id=\"colSlotsPatient\" was not injected: check your FXML file 'main.fxml'.";
        assert colSlotsEdit != null : "fx:id=\"colSlotsEdit\" was not injected: check your FXML file 'main.fxml'.";
        assert printSlotsButton != null : "fx:id=\"printSlotsButton\" was not injected: check your FXML file 'main.fxml'.";
        assert addSlotButton != null : "fx:id=\"addSlotButton\" was not injected: check your FXML file 'main.fxml'.";
        assert tabServices != null : "fx:id=\"tabServices\" was not injected: check your FXML file 'main.fxml'.";
        assert servicesSearchBox != null : "fx:id=\"servicesSearchBox\" was not injected: check your FXML file 'main.fxml'.";
        assert tableServices != null : "fx:id=\"tableServices\" was not injected: check your FXML file 'main.fxml'.";
        assert colServicesTitle != null : "fx:id=\"colServicesTitle\" was not injected: check your FXML file 'main.fxml'.";
        assert colServicesPricePerSlot != null : "fx:id=\"colServicesPricePerSlot\" was not injected: check your FXML file 'main.fxml'.";
        assert colServicesMaxSlots != null : "fx:id=\"colServicesMaxSlots\" was not injected: check your FXML file 'main.fxml'.";
        assert colServicesSlots != null : "fx:id=\"colServicesSlots\" was not injected: check your FXML file 'main.fxml'.";
        assert colServicesEdit != null : "fx:id=\"colServicesEdit\" was not injected: check your FXML file 'main.fxml'.";
        assert printServicesButton != null : "fx:id=\"printServicesButton\" was not injected: check your FXML file 'main.fxml'.";
        assert addServiceButton != null : "fx:id=\"addServiceButton\" was not injected: check your FXML file 'main.fxml'.";
        assert tabPatients != null : "fx:id=\"tabPatients\" was not injected: check your FXML file 'main.fxml'.";
        assert patientsSearchBox != null : "fx:id=\"patientsSearchBox\" was not injected: check your FXML file 'main.fxml'.";
        assert tablePatients != null : "fx:id=\"tablePatients\" was not injected: check your FXML file 'main.fxml'.";
        assert colPatientsId != null : "fx:id=\"colPatientsId\" was not injected: check your FXML file 'main.fxml'.";
        assert colPatientsName != null : "fx:id=\"colPatientsName\" was not injected: check your FXML file 'main.fxml'.";
        assert colPatientsSlots != null : "fx:id=\"colPatientsSlots\" was not injected: check your FXML file 'main.fxml'.";
        assert colPatientsEdit != null : "fx:id=\"colPatientsEdit\" was not injected: check your FXML file 'main.fxml'.";
        assert printPatientsButton != null : "fx:id=\"printPatientsButton\" was not injected: check your FXML file 'main.fxml'.";
        assert addPatientButton != null : "fx:id=\"addPatientButton\" was not injected: check your FXML file 'main.fxml'.";
	}

	/**
	 * Here is where factories should be set up
	 */
	private void setFactories() {

		//	Set slot table factories
		colSlotsDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		colSlotsTime.setCellValueFactory(new PropertyValueFactory<>("time"));
		colSlotsService.setCellValueFactory(new Callback<CellDataFeatures<Slot, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Slot, String> slot) {
				return new SimpleStringProperty(slot.getValue().getAllocatedService().getTitle());
			}
		});
		colSlotsPatient.setCellValueFactory(new Callback<CellDataFeatures<Slot, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Slot, String> slot) {
				return new SimpleStringProperty(slot.getValue().getAllocatedPatient().getName());
			}
		});
		colSlotsEdit.setCellFactory((TableCellButton.createCellButton("Edit", null)));

		//	Set service table factories
		colServicesTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		colServicesPricePerSlot.setCellValueFactory(new PropertyValueFactory<>("pricePerSlot"));
		colServicesMaxSlots.setCellValueFactory(new PropertyValueFactory<>("maxSlots"));
		colServicesSlots.setCellFactory(TableCellButton.createCellButton("View Slots", (Service service) -> { handleViewSlotPress(service); return service; }));
		colServicesEdit.setCellFactory(TableCellButton.createCellButton("Edit", null));

		//	Set patient table factories
		colPatientsId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colPatientsName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colPatientsSlots.setCellFactory(TableCellButton.createCellButton("View Slots", (Patient patient) -> { handleViewSlotPress(patient); return patient; }));
		colPatientsEdit.setCellFactory(TableCellButton.createCellButton("Edit", null));

	}

	/**
	 * Assigns variables to observables in the scene
	 */
	private void setObservables() {
		tableSlots.setItems(slots);
		tableServices.setItems(services);
		tablePatients.setItems(patients);

		slotsServiceBox.setItems(serviceTitles);
		slotsPatientBox.setItems(patientNames);

		servicesSearchBox.setItems(serviceTitles);
		
		patientsSearchBox.setItems(patientNames);
	}

	public void refreshAll() {
		if (slotsDateBox != null) slotsDateBox.setValue(null);
		if (slotsServiceBox != null) slotsServiceBox.setValue(null);
		if (slotsPatientBox != null) slotsPatientBox.setValue(null);
		if (servicesSearchBox != null) servicesSearchBox.setValue(null);
		if (patientsSearchBox != null) patientsSearchBox.setValue(null);

		this.slots.setAll(SlotRepository.getSlotsAsList());
		this.serviceTitles.setAll(getServiceTitles(getSlotServices(this.slots)));
		this.patientNames.setAll(getPatientNames(getSlotPatients(this.slots)));
		this.services.setAll(ServiceRepository.getServicesAsList());
		this.serviceTitles.setAll(getServiceTitles(this.services));
		this.patients.setAll(PatientRepository.getPatientsAsList());
		this.patientNames.setAll(getPatientNames(this.patients));
	}

//endregion



/* ----------------------------- Handler Methods ---------------------------- */
//region

	/**
	 * Function called when the user changes the tab
	 */
	@FXML
    private void handleTabChange(Event event) {
		if (slotsDateBox != null) slotsDateBox.setValue(null);
		if (slotsServiceBox != null) slotsServiceBox.setValue(null);
		if (slotsPatientBox != null) slotsPatientBox.setValue(null);
		if (servicesSearchBox != null) servicesSearchBox.setValue(null);
		if (patientsSearchBox != null) patientsSearchBox.setValue(null);

		if (event.getTarget().equals(tabSlots)) {
			this.slots.setAll(SlotRepository.getSlotsAsList());
			this.serviceTitles.setAll(getServiceTitles(getSlotServices(this.slots)));
			this.patientNames.setAll(getPatientNames(getSlotPatients(this.slots)));
		}
		if (event.getTarget().equals(tabServices)) {
			this.services.setAll(ServiceRepository.getServicesAsList());
			this.serviceTitles.setAll(getServiceTitles(this.services));
		}
		if (event.getTarget().equals(tabPatients)) {
			this.patients.setAll(PatientRepository.getPatientsAsList());
			this.patientNames.setAll(getPatientNames(this.patients));
		}
    }
	
	/**
	 * Function called when the user applies a filter to a list
	 */
	@FXML
    private void handleFilterChange(Event event) {
		LocalDate slotsDate = slotsDateBox.getValue();
		Service slotsService = slotsServiceBox.getValue() != null && !slotsServiceBox.getValue().isBlank() ? ServiceRepository.getServicesByTitle(slotsServiceBox.getValue()).get(0) : null;
		Patient slotsPatient = slotsPatientBox.getValue() != null && !slotsPatientBox.getValue().isBlank() ? PatientRepository.getPatientsByName(slotsPatientBox.getValue()).get(0) : null;
		String servicesSearch = servicesSearchBox.getValue();
		String patientsSearch = patientsSearchBox.getValue();

		if (event.getTarget().equals(slotsDateBox) || event.getTarget().equals(slotsServiceBox) || event.getTarget().equals(slotsPatientBox)) {
			if (slotsDate != null && slotsService != null && slotsPatient != null) this.slots.setAll(SlotRepository.getSlotsByDateServicePatient(slotsDate, slotsService, slotsPatient));
			else if (slotsService != null && slotsPatient != null) this.slots.setAll(SlotRepository.getSlotsByServicePatient(slotsService, slotsPatient));
			else if (slotsDate != null && slotsPatient != null) this.slots.setAll(SlotRepository.getSlotsByDatePatient(slotsDate, slotsPatient));
			else if (slotsDate != null && slotsService != null) this.slots.setAll(SlotRepository.getSlotsByDateService(slotsDate, slotsService));
			else if (slotsPatient != null) this.slots.setAll(SlotRepository.getSlotsByPatient(slotsPatient));
			else if (slotsService != null) this.slots.setAll(SlotRepository.getSlotsByService(slotsService));
			else if (slotsDate != null) this.slots.setAll(SlotRepository.getSlotsByDate(slotsDate));
			else this.slots.setAll(SlotRepository.getSlotsAsList());
		}
		if (event.getTarget().equals(servicesSearchBox) && servicesSearch != null) {
			if (!servicesSearch.isBlank()) this.services.setAll(ServiceRepository.getServicesByTitle(servicesSearch));
			else this.services.setAll(ServiceRepository.getServicesAsList());
		}
		if (event.getTarget().equals(patientsSearchBox) && patientsSearch != null) {
			if (!patientsSearch.isBlank()) this.patients.setAll(PatientRepository.getPatientsByName(patientsSearch));
			else this.patients.setAll(PatientRepository.getPatientsAsList());
		}
    }

	/**
	 * Function called when the user clicks on the print data button
	 */
	@FXML
    private void handlePrintData(Event event) {
		
    }

	/**
	 * Function called when the user clicks on the add data button
	 */
	@FXML
    private void handleAddData(Event event) {
		try {
			App.newWindow("add_slot", 450, 291);
		}
		catch (IOException e) {
			System.err.println("Unable to find add_slot.fxml!");
		}
    }

	/**
	 * Function called when the user clicks on the view slot button
	 */
	@FXML
    private void handleViewSlotPress(Object object) {
		SingleSelectionModel<Tab> selectionModel = tabContainer.getSelectionModel();
		selectionModel.select(tabSlots);
		if (object instanceof Service) {
			Service service = (Service) object;
			slotsServiceBox.setValue(service.getTitle());
			this.slots.setAll(SlotRepository.getSlotsByService(service));
		}
		else if (object instanceof Patient) {
			Patient patient = (Patient) object;
			slotsPatientBox.setValue(patient.getName());
			this.slots.setAll(SlotRepository.getSlotsByPatient(patient));
		}
    }

//endregion
	
}

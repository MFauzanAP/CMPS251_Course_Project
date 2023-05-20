package cmps251;

import java.time.LocalDate;
import java.time.LocalTime;

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
 * @version		1.16
 * @since		1.14
 */
public class MainController {



/* --------------------------- Constant Attributes -------------------------- */
//region

	ObservableList<Slot> slots = FXCollections.observableArrayList(SlotRepository.getSlotsAsList());
	ObservableList<Service> services = FXCollections.observableArrayList(ServiceRepository.getServicesAsList());
	ObservableList<Patient> patients = FXCollections.observableArrayList(PatientRepository.getPatientsAsList());

//endregion



/* -------------------------- Component Attributes -------------------------- */
//region

	@FXML
    private TabPane tabContainer;

    @FXML
    private Tab tabSlots;

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
    private Tab tabServices;

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
    private Tab tabPatients;

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
	}

//endregion



/* ----------------------------- Utility Methods ---------------------------- */
//region

	/**
	 * Makes sure all components are initialized properly and with the correct names
	 */
	public void assertComponents() {
		assert tabContainer != null : "fx:id=\"tabContainer\" was not injected: check your FXML file 'main.fxml'.";
        assert tabSlots != null : "fx:id=\"tabSlots\" was not injected: check your FXML file 'main.fxml'.";
        assert tableSlots != null : "fx:id=\"tableSlots\" was not injected: check your FXML file 'main.fxml'.";
        assert colSlotsDate != null : "fx:id=\"colSlotsDate\" was not injected: check your FXML file 'main.fxml'.";
        assert colSlotsTime != null : "fx:id=\"colSlotsTime\" was not injected: check your FXML file 'main.fxml'.";
        assert colSlotsService != null : "fx:id=\"colSlotsService\" was not injected: check your FXML file 'main.fxml'.";
        assert colSlotsPatient != null : "fx:id=\"colSlotsPatient\" was not injected: check your FXML file 'main.fxml'.";
        assert colSlotsEdit != null : "fx:id=\"colSlotsEdit\" was not injected: check your FXML file 'main.fxml'.";
        assert tabServices != null : "fx:id=\"tabServices\" was not injected: check your FXML file 'main.fxml'.";
        assert tableServices != null : "fx:id=\"tableServices\" was not injected: check your FXML file 'main.fxml'.";
        assert colServicesTitle != null : "fx:id=\"colServicesTitle\" was not injected: check your FXML file 'main.fxml'.";
        assert colServicesPricePerSlot != null : "fx:id=\"colServicesPricePerSlot\" was not injected: check your FXML file 'main.fxml'.";
        assert colServicesMaxSlots != null : "fx:id=\"colServicesMaxSlots\" was not injected: check your FXML file 'main.fxml'.";
        assert colServicesSlots != null : "fx:id=\"colServicesSlots\" was not injected: check your FXML file 'main.fxml'.";
        assert colServicesEdit != null : "fx:id=\"colServicesEdit\" was not injected: check your FXML file 'main.fxml'.";
        assert tabPatients != null : "fx:id=\"tabPatients\" was not injected: check your FXML file 'main.fxml'.";
        assert tablePatients != null : "fx:id=\"tablePatients\" was not injected: check your FXML file 'main.fxml'.";
        assert colPatientsId != null : "fx:id=\"colPatientsId\" was not injected: check your FXML file 'main.fxml'.";
        assert colPatientsName != null : "fx:id=\"colPatientsName\" was not injected: check your FXML file 'main.fxml'.";
        assert colPatientsSlots != null : "fx:id=\"colPatientsSlots\" was not injected: check your FXML file 'main.fxml'.";
        assert colPatientsEdit != null : "fx:id=\"colPatientsEdit\" was not injected: check your FXML file 'main.fxml'.";
	}

	/**
	 * Here is where factories should be set up
	 */
	public void setFactories() {

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
		colServicesSlots.setCellFactory((TableCellButton.createCellButton("View Slots", null)));
		colServicesEdit.setCellFactory((TableCellButton.createCellButton("Edit", null)));

		//	Set patient table factories
		colPatientsId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colPatientsName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colPatientsSlots.setCellFactory((TableCellButton.createCellButton("View Slots", null)));
		colPatientsEdit.setCellFactory((TableCellButton.createCellButton("Edit", null)));

	}

	/**
	 * Assigns variables to observables in the scene
	 */
	public void setObservables() {
		tableSlots.setItems(slots);
		tableServices.setItems(services);
		tablePatients.setItems(patients);
	}

//endregion



/* ----------------------------- Handler Methods ---------------------------- */
//region

	@FXML
    void handleTabChange(Event event) {
		if (event.getTarget().equals(tabSlots)) this.slots.setAll(SlotRepository.getSlotsAsList());
		if (event.getTarget().equals(tabServices)) this.services.setAll(ServiceRepository.getServicesAsList());
		if (event.getTarget().equals(tabPatients)) this.patients.setAll(PatientRepository.getPatientsAsList());
    }

//endregion
	
}

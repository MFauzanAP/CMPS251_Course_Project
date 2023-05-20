package cmps251;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import cmps251.models.Patient;
import cmps251.models.Service;
import cmps251.models.Slot;
import cmps251.models.Patient.ResidencyType;
import cmps251.repos.AdminRepository;
import cmps251.repos.PatientRepository;
import cmps251.repos.ServiceRepository;
import cmps251.repos.SlotRepository;

/**
 * Class used to start the JavaFX application
 * 
 * <p> <i>Created on 19/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.18
 * @since		1.14
 */
public class App extends Application {



/* --------------------------- Private Attributes --------------------------- */
//region

	private static Scene scene;

//endregion



/* ------------------------------ Main Function ----------------------------- */
//region

	public static void main(String[] args) {

		Patient patient = new Patient("12345678901", "Muhammad Putra", ResidencyType.RESIDENT);
		PatientRepository.addPatient(patient);
		PatientRepository.updatePatientId("12345678901", "68475684579");

		ServiceRepository.addService(new Service("Procedure", 15, 50));
		ServiceRepository.addService(new Service("Generic", 20, 100));
		ServiceRepository.addService(new Service("Specialized", 10, 150));
		ServiceRepository.addService(new Service("Operation", 5, 1000));

		ArrayList<Slot> availableSlots1 = SlotRepository.getAvailableSlotsByDateService(LocalDate.now().plusDays(1), ServiceRepository.getServicesByTitle("Operation").get(0));
		SlotRepository.bookSlot(availableSlots1.get(0), patient);
		SlotRepository.bookSlot(availableSlots1.get(1), patient);
		SlotRepository.bookSlot(availableSlots1.get(3), patient);

		ArrayList<Slot> availableSlots2 = SlotRepository.getAvailableSlotsByDateService(LocalDate.now().plusDays(1), ServiceRepository.getServicesByTitle("Generic").get(0));
		SlotRepository.bookSlot(availableSlots2.get(4), patient);
		SlotRepository.bookSlot(availableSlots2.get(5), patient);
		SlotRepository.bookSlot(availableSlots2.get(6), patient);

		// PatientRepository.updatePatientName(patient, "New name");
		// SlotRepository.updateSlotTime(availableSlots.get(0), LocalTime.parse("20:30"));
		
		launch();
	}

//endregion



/* ---------------------------- Utility Functions --------------------------- */
//region

	/**
	 * This function is called when the application first starts.
	 * Here we should specify which fxml file to use
	 */
	@Override
	public void start(Stage stage) throws IOException {
		// AdminRepository.initializeData();
		scene = new Scene(loadFXML("main"), 600, 400);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * This function is called right before the application closes.
	 * Here we should save any data that has been moidfied
	 */
	@Override
	public void stop() throws Exception {
		AdminRepository.saveData();
		super.stop();
	}

	/**
	 * Changes the current scene to the given scene
	 * 
	 * @param fxml              - the new scene to load
	 * 
	 * @throws IOException      if the file could not be found
	 */
	static void changeScene(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	/**
	 * Opens a new window with the specified fxml file
	 * 
	 * @param fxml              - the new scene to load
	 * @param width             - the width of the new window
	 * @param height            - the height of the new window
	 * 
	 * @throws IOException      if the file could not be found
	 */
	static void newWindow(String fxml, int width, int height) throws IOException {
		Stage stage = new Stage();
		stage.setScene(new Scene(loadFXML(fxml), width, height));
		stage.show();
	}

	/**
	 * Loads the given fxml file
	 * 
	 * @param fxml              - the fxml file to load
	 * 
	 * @throws IOException      if the file could not be found
	 */
	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

//endregion



}
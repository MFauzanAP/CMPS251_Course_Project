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
 * @version		1.15
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

        ArrayList<Slot> availableSlots = SlotRepository.getAvailableSlotsByDateService(LocalDate.now(), ServiceRepository.getServicesByTitle("Operation").get(0).getId());
        SlotRepository.bookSlot(availableSlots.get(0), patient);
        SlotRepository.bookSlot(availableSlots.get(1), patient);
        SlotRepository.bookSlot(availableSlots.get(3), patient);

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
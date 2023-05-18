package repos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TreeMap;

import models.Patient;
import models.Service;
import models.Slot;
import utils.RepoUtils;
import utils.RepoUtils.DataType;

/**
 * This class contains all the data related operations and functions in the Sehha hospital reception system
 * 
 * <p> This is where all the slots, services, and patients are managed
 * From here, you can add, modify, or remove the mentioned entities, along with some extra utility functions
 * 
 * <p> <i>Created on 14/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.12
 * @since		1.1
 */
@SuppressWarnings("unchecked")
public final class AdminRepository {



/* ----------------------------- Utility Methods ---------------------------- */
//region

	/** 
	 * Initializes the data by clearing it and fetching from files if they exist
	 */
	public static void initializeData() {

		//	Initialize lists for the data to be stored in
		ArrayList<Patient> patientList = new ArrayList<Patient>(); 
		ArrayList<Service> serviceList = new ArrayList<Service>(); 
		TreeMap<Service, TreeMap<LocalDate, TreeMap<LocalTime, Slot>>> slotList = new TreeMap<>(); 

		//	Try to initialize the data
		try {

			//	Try and fetch data from files if they exist
			patientList = (ArrayList<Patient>) RepoUtils.loadDataFromFile(DataType.PATIENT);
			serviceList = (ArrayList<Service>) RepoUtils.loadDataFromFile(DataType.SERVICE);
			slotList = (TreeMap<Service, TreeMap<LocalDate, TreeMap<LocalTime, Slot>>>) RepoUtils.loadDataFromFile(DataType.SLOT);

		}
		catch (Exception e) {

			//	If an error occurs then reset all lists to avoid errors
			patientList.clear();
			serviceList.clear();
			slotList.clear();

		}

		//	Add the results to the local lists
		PatientRepository.getPatients().addAll(patientList);
		ServiceRepository.getServices().addAll(serviceList);
		SlotRepository.getSlots().putAll(slotList);

	}

	/** 
	 * Saves data to files
	 */
	public static void saveData() {

		//	Save data to files
		RepoUtils.saveDataToFile(DataType.PATIENT, PatientRepository.getPatients());
		RepoUtils.saveDataToFile(DataType.SERVICE, ServiceRepository.getServices());
		RepoUtils.saveDataToFile(DataType.SLOT, SlotRepository.getSlots());

	}

//endregion



}

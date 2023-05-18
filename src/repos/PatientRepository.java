package repos;

import java.util.ArrayList;

import models.Patient;

/**
 * This class contains all the data related operations and functions for patients in the Sehha hospital reception system
 * 
 * <p> This is where all the patients are managed
 * From here, you can add, modify, or remove patients, along with some extra utility functions
 * 
 * <p> <i>Created on 18/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.12
 * @since		1.12
 */
public class PatientRepository {



/* --------------------------- Constant Attributes -------------------------- */
//region

	private static final ArrayList<Patient> patients = new ArrayList<Patient>();

//endregion



/* --------------------------------- Getters -------------------------------- */
//region

	/** 
	 * Gets all the patients currently stored
	 * 
	 * @return ArrayList<Patient>		- the list of patients currently stored
	 */
	public static ArrayList<Patient> getPatients() {
		return patients;
	}

	/** 
	 * Gets all the patients currently stored that have the given id
	 * 
	 * @param id						- the id of the patient to fetch
	 * 
	 * @return ArrayList<Patient>		- the resulting list of patients
	 */
	public static ArrayList<Patient> getPatients(String id) {

		//	Create a list to store the output results
		ArrayList<Patient> outputList = new ArrayList<Patient>();

		//	Loop through each patient and find those with the same id
		for (Patient patient : patients) {
			if (patient.getId() == id) outputList.add(patient);
		}

		//	Return the results
		return outputList;

	}

	/** 
	 * Gets all the patients currently stored that have one of the given ids
	 * 
	 * @param ids						- a list of ids to fetch for
	 * 
	 * @return ArrayList<Patient>		- the resulting list of patients
	 */
	public static ArrayList<Patient> getPatients(ArrayList<String> ids) {

		//	Create a list to store the output results
		ArrayList<Patient> outputList = new ArrayList<Patient>();

		//	Loop through each patient and find those with the same id
		for (Patient patient : patients) {
			if (ids.contains(patient.getId())) outputList.add(patient);
		}

		//	Return the results
		return outputList;

	}

//endregion



/* --------------------------------- Setters -------------------------------- */
//region

	/** 
	 * Adds a single given patient to the list of patients
	 * 
	 * @param patient						- the patient to add to the list
	 * 
	 * @throws IllegalArgumentException		if the patient already exists in the list
	 */
	public static void addPatients(Patient patient) {

		//	If the patient already exists
		if (patients.contains(patient)) throw new IllegalArgumentException("The given patient is already in the list!");

		//	If it doesn't exist then add it
		patients.add(patient);

	}

	/** 
	 * Adds a list of patients to the list of patients
	 * 
	 * @param patients						- the list of patients to add to the list
	 * 
	 * @throws IllegalArgumentException		if a patient already exists in the list
	 */
	public static void addPatients(ArrayList<Patient> newPatients) {

		//	If the patients already exists
		ArrayList<Patient> intersection = new ArrayList<Patient>(newPatients);
		intersection.retainAll(patients);
		if (intersection.size() != 0) throw new IllegalArgumentException(String.format("The patient with ID %s is already in the list!", intersection.get(0).getId()));

		//	If it doesn't exist then add it
		patients.addAll(newPatients);
		
	}

//endregion


	
}

package repos;

import java.util.ArrayList;
import java.util.TreeMap;

import models.Patient;
import models.Patient.ResidencyType;

/**
 * This class contains all the data related operations and functions for patients in the Sehha hospital reception system
 * 
 * <p> This is where all the patients are managed
 * From here, you can add, modify, or remove patients, along with some extra utility functions
 * 
 * <p> <i>Created on 18/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.13
 * @since		1.12
 */
public class PatientRepository {



/* --------------------------- Constant Attributes -------------------------- */
//region

	private static final TreeMap<String, Patient> patients = new TreeMap<>();

//endregion



/* --------------------------------- Getters -------------------------------- */
//region

	/** 
	 * Gets all the patients currently stored
	 * 
	 * @return ArrayList<Patient>		- the list of patients currently stored
	 */
	public static TreeMap<String, Patient> getPatients() {
		return patients;
	}

	/** 
	 * Gets all the patients currently stored
	 * 
	 * @return ArrayList<Patient>		- the list of patients currently stored
	 */
	public static ArrayList<Patient> getPatientsAsList() {
		return new ArrayList<Patient>(patients.values());
	}

	/** 
	 * Gets the patient currently stored that has the given id
	 * 
	 * @param id					- the id of the patient to fetch
	 * 
	 * @return Patient				- the patient with the given id
	 */
	public static Patient getPatientById(String id) {
		return patients.get(id);
	}

	/** 
	 * Gets all the patients currently stored that have one of the given ids
	 * 
	 * @param ids						- a list of ids to fetch for
	 * 
	 * @return ArrayList<Patient>		- the resulting list of patients
	 */
	public static ArrayList<Patient> getPatientsByIds(ArrayList<String> ids) {

		//	Create a list to store the output results
		ArrayList<Patient> outputList = new ArrayList<Patient>();

		//	Loop through each patient and find those with the same id
		for (String id : ids) {
			outputList.add(patients.get(id));
		}

		//	Return the results
		return outputList;

	}

	/** 
	 * Gets all the patients currently stored that have the given name
	 * 
	 * @param name						- the name of the patient to fetch
	 * 
	 * @return ArrayList<Patient>		- the resulting list of patients
	 */
	public static ArrayList<Patient> getPatientsByName(String name) {

		//	Create a list to store the output results
		ArrayList<Patient> outputList = new ArrayList<Patient>();

		//	Loop through each patient and find those with the same name
		for (Patient patient : patients.values()) {
			if (patient.getName().equals(name)) outputList.add(patient);
		}

		//	Return the results
		return outputList;

	}

	/** 
	 * Gets all the patients currently stored that have one of the given names
	 * 
	 * @param names						- a list of names to fetch for
	 * 
	 * @return ArrayList<Patient>		- the resulting list of patients
	 */
	public static ArrayList<Patient> getPatientsByName(ArrayList<String> names) {

		//	Create a list to store the output results
		ArrayList<Patient> outputList = new ArrayList<Patient>();

		//	Loop through each patient and find those with the same name
		for (Patient patient : patients.values()) {
			if (names.contains(patient.getName())) outputList.add(patient);
		}

		//	Return the results
		return outputList;

	}

	/** 
	 * Gets all the patients currently stored that have the given residency
	 * 
	 * @param residency					- the residency of the patient to fetch
	 * 
	 * @return ArrayList<Patient>		- the resulting list of patients
	 */
	public static ArrayList<Patient> getPatientsByResidency(ResidencyType residency) {

		//	Create a list to store the output results
		ArrayList<Patient> outputList = new ArrayList<Patient>();

		//	Loop through each patient and find those with the same residency
		for (Patient patient : patients.values()) {
			if (patient.getResidency() == residency) outputList.add(patient);
		}

		//	Return the results
		return outputList;

	}

//endregion



/* --------------------------------- Adders --------------------------------- */
//region

	/** 
	 * Adds a single given patient to the list of patients
	 * 
	 * @param patient						- the patient to add to the list
	 * 
	 * @throws IllegalArgumentException		if the patient already exists in the list
	 */
	public static void addPatient(Patient patient) {

		//	If the patient already exists
		if (patients.containsKey(patient.getId())) throw new IllegalArgumentException("The given patient is already in the list!");

		//	If it doesn't exist then add it
		patients.put(patient.getId(), patient);

	}

	/** 
	 * Adds a list of patients to the list of patients
	 * 
	 * @param patients						- the list of patients to add to the list
	 * 
	 * @throws IllegalArgumentException		if a patient already exists in the list
	 */
	public static void addPatients(ArrayList<Patient> patients) {
		for (Patient patient : patients) addPatient(patient);
	}

//endregion



/* -------------------------------- Updaters -------------------------------- */
//region

	/** 
	 * Updates the info of the patient with the given ID
	 * 
	 * <p> <b>NOTE</b>: this replaces all the previously stored information!
	 * 
	 * @param id				- the ID of the patient to update
	 * @param newPatient		- the new data to replace the old patient with
	 */
	public static void updatePatient(String id, Patient newPatient) {
		patients.replace(id, newPatient);
	}
	public static void updatePatient(Patient patient, Patient newPatient) {
		updatePatient(patient.getId(), newPatient);
	}

	/** 
	 * Updates the ID of the patient with the given ID
	 * 
	 * @param id				- the ID of the patient to update
	 * @param newId				- the patient's new ID
	 */
	public static void updatePatientId(String id, String newId) {

		//	Store the old patient temporarily
		Patient patient = patients.get(id);

		//	Remove him from the list
		patients.remove(id);

		//	Update his ID and add him back
		patient.setId(newId);
		addPatient(patient);

	}
	public static void updatePatientId(Patient patient, String newId) {
		updatePatientId(patient.getId(), newId);
	}

	/** 
	 * Updates the name of the patient with the given ID
	 * 
	 * @param id				- the ID of the patient to update
	 * @param name				- the patient's new name
	 */
	public static void updatePatientName(String id, String name) {
		patients.get(id).setName(name);
	}
	public static void updatePatientName(Patient patient, String name) {
		updatePatientName(patient.getId(), name);
	}

	/** 
	 * Updates the residency of the patient with the given ID
	 * 
	 * @param id				- the ID of the patient to update
	 * @param residency				- the patient's new name
	 */
	public static void updatePatientResidency(String id, ResidencyType residency) {
		patients.get(id).setResidency(residency);
	}
	public static void updatePatientResidency(Patient patient, ResidencyType residency) {
		updatePatientResidency(patient.getId(), residency);
	}

//endregion



/* -------------------------------- Deleters -------------------------------- */
//region

	/** 
	 * Deletes the patient with the given ID from the list
	 * 
	 * @param id							- the patient with this id will be deleted
	 */
	public static void deletePatient(String id) {
		patients.remove(id);
		SlotRepository.cancelSlotsByPatient(id);
	}

	/** 
	 * Deletes the patients with the given IDs from the list
	 * 
	 * @param ids							- the patients with these ids will be deleted
	 */
	public static void deletePatient(ArrayList<String> ids) {
		for (String id : ids) deletePatient(id);
	}

//endregion


	
}

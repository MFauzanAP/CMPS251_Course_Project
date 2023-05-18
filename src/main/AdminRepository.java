package main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TreeMap;

import exceptions.IllegalSlotTimeException;
import models.Patient;
import models.Service;
import models.Slot;
import utils.RepoUtils;
import utils.TimeUtils;
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
 * @version		1.11
 * @since		1.1
 */
@SuppressWarnings("unchecked")
public final class AdminRepository {
	


/* --------------------------- Constant Attributes -------------------------- */
//region

	private static final ArrayList<Patient> patients = new ArrayList<Patient>();
	private static final ArrayList<Service> services = new ArrayList<Service>();
	private static final TreeMap<Service, TreeMap<LocalDate, TreeMap<LocalTime, Slot>>> slots = new TreeMap<>();

//endregion



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
		patients.addAll(patientList);
		services.addAll(serviceList);
		slots.putAll(slotList);

	}

	/** 
	 * Saves data to files
	 */
	public static void saveData() {

		//	Save data to files
		RepoUtils.saveDataToFile(DataType.PATIENT, patients);
		RepoUtils.saveDataToFile(DataType.SERVICE, services);
		RepoUtils.saveDataToFile(DataType.SLOT, slots);

	}

//endregion



/* --------------------------- Getters and Setters -------------------------- */
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

	/** 
	 * Gets all the services currently stored
	 * 
	 * @return ArrayList<Service>		- the list of services currently stored
	 */
	public static ArrayList<Service> getServices() {
		return services;
	}

	/** 
	 * Gets all the services currently stored that have the given title
	 * 
	 * @param title						- the title of the service to fetch
	 * 
	 * @return ArrayList<Service>		- the resulting list of services
	 */
	public static ArrayList<Service> getServices(String title) {

		//	Create a list to store the output results
		ArrayList<Service> outputList = new ArrayList<Service>();

		//	Loop through each service and find those with the same title
		for (Service service : services) {
			if (service.getTitle() == title) outputList.add(service);
		}

		//	Return the results
		return outputList;

	}

	/** 
	 * Gets all the services currently stored that have one of the given titles
	 * 
	 * @param titles					- a list of titles to fetch for
	 * 
	 * @return ArrayList<Service>		- the resulting list of services
	 */
	public static ArrayList<Service> getServices(ArrayList<String> titles) {

		//	Create a list to store the output results
		ArrayList<Service> outputList = new ArrayList<Service>();

		//	Loop through each service and find those with the same title
		for (Service service : services) {
			if (titles.contains(service.getTitle())) outputList.add(service);
		}

		//	Return the results
		return outputList;

	}

	/** 
	 * Adds a single given service to the list of services
	 * 
	 * @param service						- the service to add to the list
	 * 
	 * @throws IllegalArgumentException		if the service already exists in the list
	 */
	public static void addServices(Service service) {

		//	If the service already exists
		if (services.contains(service)) throw new IllegalArgumentException("The given service is already in the list!");

		//	If it doesn't exist then add it
		services.add(service);

	}

	/** 
	 * Adds a list of services to the list of services
	 * 
	 * @param services						- the list of services to add to the list
	 * 
	 * @throws IllegalArgumentException		if a service already exists in the list
	 */
	public static void addServices(ArrayList<Service> newServices) {

		//	If the services already exists
		ArrayList<Service> intersection = new ArrayList<Service>(newServices);
		intersection.retainAll(services);
		if (intersection.size() != 0) throw new IllegalArgumentException(String.format("The service with ID %s is already in the list!", intersection.get(0).getId()));

		//	If it doesn't exist then add it
		services.addAll(newServices);
		
	}

	/** 
	 * Returns a list of booked slots for all dates under the given service.
	 * 
	 * <p> If no service is specified, this function will return all slots under all services
	 * 
	 * @param service				- the service to fetch slots for
	 * 
	 * @return ArrayList<Slot>		- a list of booked slots for all dates under the given service
	 */
	public static ArrayList<Slot> getServiceSlots(Service service) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	If a service is given, return it's slots
		if (service != null && slots.containsKey(service)) {
			for (TreeMap<LocalTime, Slot> timeMap : slots.getOrDefault(service, new TreeMap<>()).values()) {
				outputList.addAll(timeMap.values());
			}
			return outputList;
		}

		//	If no service is given then concatenate all the slots together
		for (TreeMap<LocalDate, TreeMap<LocalTime, Slot>> dateMap : slots.values()) {
			for (TreeMap<LocalTime, Slot> timeMap : dateMap.values()) {
				outputList.addAll(timeMap.values());
			}
		}

		//	Return the given service slots
		return outputList;

	}

	/** 
	 * Returns a list of booked slots for the given date under the given service.
	 * 
	 * <p> If no service is specified, this function will return all slots under all services
	 * 
	 * @param date					- the date to check slots for
	 * @param service				- the service to fetch slots for
	 * 
	 * @return ArrayList<Slot>		- a list of booked slots under the given service
	 */
	public static ArrayList<Slot> getServiceSlots(LocalDate date, Service service) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	Check if the given date is in the past
		if (Slot.isValidDate(null, date, false) != "") return outputList;

		//	If a service is given, return it's slots
		if (service != null && slots.containsKey(service)) {
			outputList.addAll(slots.getOrDefault(service, new TreeMap<>()).getOrDefault(date, new TreeMap<>()).values());
			return outputList;
		}

		//	If no service is given then concatenate all the slots together
		for (TreeMap<LocalDate, TreeMap<LocalTime, Slot>> dateMap : slots.values()) {
			outputList.addAll(dateMap.getOrDefault(date, new TreeMap<>()).values());
		}

		//	Return the given service slots
		return outputList;

	}

	/** 
	 * Returns a list of booked slots for all dates under the given service list.
	 * 
	 * @param services				- the list of services to check slots for
	 * 
	 * @return ArrayList<Slot>		- a list of booked slots under the given service
	 */
	public static ArrayList<Slot> getServiceListSlots(ArrayList<Service> services) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	Loop through each of the services and get their slots
		for (Service service : services) {
			outputList.addAll(getServiceSlots(service));
		}

		//	Return the given service slots
		return outputList;

	}
	
	/** 
	 * Returns a list of booked slots under the given service list.
	 * 
	 * @param date					- the date to check slots for
	 * @param services				- the list of services to check slots for
	 * 
	 * @return ArrayList<Slot>		- a list of booked slots under the given service
	 */
	public static ArrayList<Slot> getServiceListSlots(LocalDate date, ArrayList<Service> services) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	Check if the given date is in the past
		if (Slot.isValidDate(null, date, false) != "") return outputList;

		//	Loop through each of the services and get their slots
		for (Service service : services) {
			outputList.addAll(getServiceSlots(date, service));
		}

		//	Return the given service slots
		return outputList;

	}

	/** 
	 * Returns a list of slots for the given service that are available for the given date.
	 * Note that this date cannot be in the past!
	 * 
	 * <p> If no service is specified, this function will return all available slots under all services
	 * 
	 * @param date					- the date to check slots for
	 * @param service				- the service to check slots for
	 * 
	 * @return ArrayList<Slot>		- a list of available slots for the given date
	 */
	public static ArrayList<Slot> getAvailableSlots(LocalDate date, Service service) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	Check if the given date is in the past
		if (Slot.isValidDate(null, date, false) != "") return outputList;

		//	If a service is given
		if (service != null) {

			//	Loop through each time interval in the day
			ArrayList<LocalTime> intervals = TimeUtils.getDateTimeIntervals(date);
			TreeMap<LocalTime, Slot> allSlots = slots.getOrDefault(service, new TreeMap<>()).getOrDefault(date, new TreeMap<>());
			for (LocalTime time : intervals) {

				//	Catch illegal slot time exceptions
				try {
					
					//	If there is no slot at this time then add it as a free slot
					if (!allSlots.containsKey(time)) outputList.add(new Slot(date, time));

					//	But if there is a slot, check if it is free
					else if (!allSlots.get(time).isBooked()) outputList.add(new Slot(date, time));

				}
				catch (IllegalSlotTimeException e) {

					//	If the given time is in the past, don't add it to the output list
					continue;

				}

			}

		}
		else {

			//	If no service is given then loop through each service
			for (Service serviceElem : services) {

				//	Loop through each time interval in the day
				ArrayList<LocalTime> intervals = TimeUtils.getDateTimeIntervals(date);
				TreeMap<LocalTime, Slot> allSlots = slots.getOrDefault(serviceElem, new TreeMap<>()).getOrDefault(date, new TreeMap<>());
				for (LocalTime time : intervals) {

					//	Catch illegal slot time exceptions
					try {
						
						//	If there is no slot at this time then add it as a free slot
						if (!allSlots.containsKey(time)) outputList.add(new Slot(date, time));

						//	But if there is a slot, check if it is free
						else if (!allSlots.get(time).isBooked()) outputList.add(new Slot(date, time));

					}
					catch (IllegalSlotTimeException e) {

						//	If the given time is in the past, don't add it to the output list
						continue;

					}

				}

			}

		}

		//	Return the filtered slots
		return outputList;

	}

//endregion



}

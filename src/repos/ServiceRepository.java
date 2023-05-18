package repos;

import java.util.ArrayList;
import java.util.TreeMap;

import models.Service;

/**
 * This class contains all the data related operations and functions for services in the Sehha hospital reception system
 * 
 * <p> This is where all the services are managed
 * From here, you can add, modify, or remove services, along with some extra utility functions
 * 
 * <p> <i>Created on 18/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.13
 * @since		1.12
 */
public class ServiceRepository {



/* --------------------------- Constant Attributes -------------------------- */
//region

	private static final TreeMap<String, Service> services = new TreeMap<>();

//endregion



/* --------------------------------- Getters -------------------------------- */
//region

	/** 
	 * Gets all the services currently stored
	 * 
	 * @return ArrayList<Service>		- the tree map of services currently stored
	 */
	public static TreeMap<String, Service> getServices() {
		return services;
	}

	/** 
	 * Gets all the services currently stored as a list
	 * 
	 * @return ArrayList<Service>		- the list of services currently stored
	 */
	public static ArrayList<Service> getServicesAsList() {
		return new ArrayList<Service>(services.values());
	}

	/** 
	 * Gets the service currently stored that has the given id
	 * 
	 * @param id						- the id of the service to fetch
	 * 
	 * @return Service					- a service with the given id
	 */
	public static Service getServiceById(String id) {
		return services.get(id);
	}

	/** 
	 * Gets all the services currently stored that have one of the given ids
	 * 
	 * @param ids						- a list of ids to fetch for
	 * 
	 * @return ArrayList<Service>		- the resulting list of services
	 */
	public static ArrayList<Service> getServicesByIds(ArrayList<String> ids) {

		//	Create a list to store the output results
		ArrayList<Service> outputList = new ArrayList<Service>();

		//	Loop through each service and find those with the same id
		for (String id : ids) {
			outputList.add(services.get(id));
		}

		//	Return the results
		return outputList;

	}

	/** 
	 * Gets all the services currently stored that have the given title
	 * 
	 * @param title						- the title of the service to fetch
	 * 
	 * @return ArrayList<Service>		- the resulting list of services
	 */
	public static ArrayList<Service> getServicesByTitle(String title) {

		//	Create a list to store the output results
		ArrayList<Service> outputList = new ArrayList<Service>();

		//	Loop through each service and find those with the same title
		for (Service service : services.values()) {
			if (service.getTitle().equals(title)) outputList.add(service);
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
	public static ArrayList<Service> getServicesByTitle(ArrayList<String> titles) {

		//	Create a list to store the output results
		ArrayList<Service> outputList = new ArrayList<Service>();

		//	Loop through each service and find those with the same title
		for (Service service : services.values()) {
			if (titles.contains(service.getTitle())) outputList.add(service);
		}

		//	Return the results
		return outputList;

	}

//endregion



/* --------------------------------- Adders --------------------------------- */
//region

	/** 
	 * Adds a single given service to the list of services
	 * 
	 * @param service						- the service to add to the list
	 * 
	 * @throws IllegalArgumentException		if the service already exists in the list
	 */
	public static void addService(Service service) {

		//	If the service already exists
		if (services.containsKey(service.getId())) throw new IllegalArgumentException("The given service is already in the list!");

		//	If it doesn't exist then add it
		services.put(service.getId(), service);

	}

	/** 
	 * Adds a list of services to the list of services
	 * 
	 * @param services						- the list of services to add to the list
	 * 
	 * @throws IllegalArgumentException		if a service already exists in the list
	 */
	public static void addServices(ArrayList<Service> services) {
		for (Service service : services) addService(service);
	}

//endregion



/* -------------------------------- Updaters -------------------------------- */
//region

	/** 
	 * Updates the info of the service with the given ID
	 * 
	 * <p> <b>NOTE</b>: this replaces all the previously stored information!
	 * 
	 * @param id				- the ID of the service to update
	 * @param newService		- the new data to replace the old service with
	 */
	public static void updateService(String id, Service newService) {
		services.replace(id, newService);
	}

	/** 
	 * Updates the ID of the service with the given ID
	 * 
	 * @param id				- the ID of the service to update
	 * @param newId				- the service's new ID
	 */
	public static void updateServiceId(String id, String newId) {

		//	Store the old service temporarily
		Service service = services.get(id);

		//	Remove it from the list
		services.remove(id);

		//	Update the ID and add it back
		service.setId(newId);
		addService(service);

	}

	/** 
	 * Updates the title of the service with the given ID
	 * 
	 * @param id				- the ID of the service to update
	 * @param title				- the service's new title
	 */
	public static void updateServiceName(String id, String title) {
		services.get(id).setTitle(title);
	}

	/** 
	 * Updates the maximum number of slots of the service with the given ID
	 * 
	 * @param id				- the ID of the service to update
	 * @param maxSlots			- the service's new maximum number of slots
	 */
	public static void updateServiceMaxSlots(String id, int maxSlots) {
		services.get(id).setMaxSlots(maxSlots);
	}

	/** 
	 * Updates the price per slot of the service with the given ID
	 * 
	 * @param id				- the ID of the service to update
	 * @param pricePerSlot		- the service's new price per slot
	 */
	public static void updateServicePricePerSlot(String id, double pricePerSlot) {
		services.get(id).setPricePerSlot(pricePerSlot);
	}

//endregion



/* -------------------------------- Deleters -------------------------------- */
//region

	/** 
	 * Deletes the service with the given ID from the list
	 * 
	 * @param id							- the service with this id will be deleted
	 */
	public static void deleteService(String id) {
		services.remove(id);
		SlotRepository.cancelSlotsByService(id);
	}

	/** 
	 * Deletes the services with the given IDs from the list
	 * 
	 * @param ids							- the services with these ids will be deleted
	 */
	public static void deleteService(ArrayList<String> ids) {
		for (String id : ids) deleteService(id);
	}

//endregion


	
}

package repos;

import java.util.ArrayList;

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
 * @version		1.12
 * @since		1.12
 */
public class ServiceRepository {



/* --------------------------- Constant Attributes -------------------------- */
//region

	private static final ArrayList<Service> services = new ArrayList<Service>();

//endregion



/* --------------------------------- Getters -------------------------------- */
//region

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

//endregion


	
}

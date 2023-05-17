import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TreeMap;

import exceptions.IllegalSlotTimeException;
import models.Patient;
import models.Service;
import models.Slot;
import utils.TimeUtils;

/**
 * This class contains all the business logic in the Sehha hospital reception system
 * 
 * <p> This is where all the slots, services, and patients are managed
 * From here, you can add, modify, or remove the mentioned entities and is where the main logic is contained
 * 
 * <p> <i>Created on 14/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.3
 * @since		1.1
 */
public final class AdminApp {
	


/* --------------------------- Static Attributes --------------------------- */
//region

	private static ArrayList<Patient> patients = new ArrayList<Patient>();
	private static ArrayList<Service> services = new ArrayList<Service>();
	private static TreeMap<Service, TreeMap<LocalDate, TreeMap<LocalTime, Slot>>> slots = new TreeMap<>();

//endregion



/* ------------------------------ Logic Methods ----------------------------- */
//region

	/** 
	 * Returns a hashmap of slots for all dates under the given service.
	 * 
	 * <p> If no service is specified, this function will return all slots under all services
	 * 
	 * @param service										- the service to fetch slots for
	 * 
	 * @return TreeMap<LocalDate, TreeMap<LocalTime, Slot>>	- a hashmap of slots for all dates under the given service
	 */
	public static TreeMap<LocalDate, TreeMap<LocalTime, Slot>> getServiceSlots(Service service) {

		//	Create a new list of slots to be returned
		TreeMap<LocalDate, TreeMap<LocalTime, Slot>> outputMap = new TreeMap<>();

		//	If a service is given, return it's slots
		if (service != null && slots.containsKey(service)) return slots.get(service);

		//	If no service is given then concatenate all the slots together
		for (TreeMap<LocalDate, TreeMap<LocalTime, Slot>> map : slots.values()) {
			outputMap.putAll(map);
		}

		//	Return the given service slots
		return outputMap;

	}

	/** 
	 * Returns a hashmap of slots for the given date under the given service.
	 * 
	 * <p> If no service is specified, this function will return all slots under all services
	 * 
	 * @param date							- the date to check slots for
	 * @param service						- the service to fetch slots for
	 * 
	 * @return TreeMap<LocalTime, Slot>		- a hashmap of slots under the given service
	 */
	public static TreeMap<LocalTime, Slot> getServiceSlots(LocalDate date, Service service) {

		//	Create a new list of slots to be returned
		TreeMap<LocalTime, Slot> outputMap = new TreeMap<>();

		//	Check if the given date is in the past
		if (Slot.isValidDate(null, date, false) != "") return outputMap;

		//	Check if a service is given
		if (service != null) {

			//	Get slots for all dates
			TreeMap<LocalDate, TreeMap<LocalTime, Slot>> serviceSlots = getServiceSlots(service);

			//	If a service is given, return it's slots
			if (service != null && serviceSlots.containsKey(date)) {
				outputMap.putAll(serviceSlots.get(date));
			}

		}
		else {

			//	If no service is given then concatenate all the slots together
			for (TreeMap<LocalDate, TreeMap<LocalTime, Slot>> dateMap : slots.values()) {
				for (TreeMap<LocalTime, Slot> timeMap : dateMap.values()) {
					outputMap.putAll(timeMap);
				}
			}

		}

		//	Return the given service slots
		return outputMap;

	}

	/** 
	 * Returns a hashmap of slots for all dates under the given service list.
	 * 
	 * @param services										- the list of services to check slots for
	 * 
	 * @return TreeMap<LocalDate, TreeMap<LocalTime, Slot>>	- a hashmap of slots under the given service
	 */
	public static TreeMap<LocalDate, TreeMap<LocalTime, Slot>> getServiceListSlots(ArrayList<Service> services) {

		//	Create a new list of slots to be returned
		TreeMap<LocalDate, TreeMap<LocalTime, Slot>> outputMap = new TreeMap<>();

		//	Loop through each of the services and get their slots
		for (Service service : services) {
			outputMap.putAll(slots.getOrDefault(service, new TreeMap<>()));
		}

		//	Return the given service slots
		return outputMap;

	}
	
	/** 
	 * Returns a hashmap of slots under the given service list.
	 * 
	 * @param date							- the date to check slots for
	 * @param services						- the list of services to check slots for
	 * 
	 * @return TreeMap<LocalTime, Slot>		- a hashmap of slots under the given service
	 */
	public static TreeMap<LocalTime, Slot> getServiceListSlots(LocalDate date, ArrayList<Service> services) {

		//	Create a new list of slots to be returned
		TreeMap<LocalTime, Slot> outputMap = new TreeMap<>();

		//	Check if the given date is in the past
		if (Slot.isValidDate(null, date, false) != "") return outputMap;

		//	Loop through each of the services and get their slots
		for (Service service : services) {
			outputMap.putAll(slots.getOrDefault(service, new TreeMap<>()).getOrDefault(date, new TreeMap<>()));
		}

		//	Return the given service slots
		return outputMap;

	}

	/** 
	 * Returns a hashmap of slots that are available for the given date.
	 * Note that this date cannot be in the past!
	 * 
	 * @param date							- the date to check slots for
	 * 
	 * @return TreeMap<LocalTime, Slot>		- a hashmap of available slots for the given date
	 */
	public static TreeMap<LocalTime, Slot> getAvailableSlots(LocalDate date) {

		//	Create a new hashmap of slots to be returned
		TreeMap<LocalTime, Slot> outputList = new TreeMap<>();

		//	Check if the given date is in the past
		if (Slot.isValidDate(null, date, false) != "") return outputList;

		//	Loop through each time interval in the day
		ArrayList<LocalTime> intervals = TimeUtils.getDateTimeIntervals(date);
		TreeMap<LocalTime, Slot> allSlots = getServiceSlots(date, null);
		for (LocalTime time : intervals) {

			//	Catch illegal slot time exceptions
			try {
				
				//	If there is no slot at this time then add it as a free slot
				if (!allSlots.containsKey(time)) outputList.put(time, new Slot(date, time));

				//	But if there is a slot, check if it is free
				else if (!allSlots.get(time).isBooked()) outputList.put(time, new Slot(date, time));

			}
			catch (IllegalSlotTimeException e) {

				//	If the given time is in the past, don't add it to the output list
				continue;

			}

		}

		//	Return the filtered slots
		return outputList;

	}

//endregion



}

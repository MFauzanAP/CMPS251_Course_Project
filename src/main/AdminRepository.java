package main;

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
 * This class contains all the data related operations and functions in the Sehha hospital reception system
 * 
 * <p> This is where all the slots, services, and patients are managed
 * From here, you can add, modify, or remove the mentioned entities, along with some extra utility functions
 * 
 * <p> <i>Created on 14/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.6
 * @since		1.1
 */
public final class AdminRepository {
	


/* --------------------------- Static Attributes --------------------------- */
//region

	private static ArrayList<Patient> patients = new ArrayList<Patient>();
	public static ArrayList<Service> services = new ArrayList<Service>();
	private static TreeMap<Service, TreeMap<LocalDate, TreeMap<LocalTime, Slot>>> slots = new TreeMap<>();

//endregion



/* --------------------------- Getters and Setters -------------------------- */
//region


//endregion



/* ------------------------------ Logic Methods ----------------------------- */
//region

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

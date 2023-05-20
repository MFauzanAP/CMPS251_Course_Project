package cmps251.repos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TreeMap;

import cmps251.exceptions.IllegalSlotTimeException;
import cmps251.models.Patient;
import cmps251.models.Service;
import cmps251.models.Slot;
import cmps251.utils.TimeUtils;

/**
 * This class contains all the data related operations and functions for slots in the Sehha hospital reception system
 * 
 * <p> This is where all the slots are managed
 * From here, you can add, modify, or remove slots, along with some extra utility functions
 * 
 * <p> <i>Created on 18/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.17
 * @since		1.12
 */
public final class SlotRepository {



/* --------------------------- Constant Attributes -------------------------- */
//region

	private static final TreeMap<String, TreeMap<LocalDate, TreeMap<LocalTime, Slot>>> slots = new TreeMap<>();

//endregion



/* --------------------------------- Getters -------------------------------- */
//region

	/** 
	 * Returns the slot with the given ID
	 * 
	 * @param id					- the id to fetch slots for
	 * 
	 * @return Slot					- a slot with the given id
	 */
	public static Slot getSlotById(String id) {
		for (Slot slot : getSlotsAsList()) {
			if (slot.getId().equals(id)) return slot;
		}
		return null;
	}

	/** 
	 * Returns a tree map of all booked slots for all dates under all services.
	 * 
	 * @return TreeMap<String, TreeMap<LocalDate, TreeMap<LocalTime, Slot>>>	- a tree map of all booked slots for all dates under all services
	 */
	public static TreeMap<String, TreeMap<LocalDate, TreeMap<LocalTime, Slot>>> getSlots() {
		return slots;
	}

	/** 
	 * Returns a list of all booked slots for all dates under all services.
	 * 
	 * @return ArrayList<Slot>		- a list of all booked slots for all dates under all services
	 */
	public static ArrayList<Slot> getSlotsAsList() {
		
		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	Loop through each service and date and return slots for each time
		for (TreeMap<LocalDate, TreeMap<LocalTime, Slot>> dateMap : slots.values()) {
			for (TreeMap<LocalTime, Slot> timeMap : dateMap.values()) {
				outputList.addAll(timeMap.values());
			}
		}

		//	Return the given service slots
		return outputList;

	}

	/** 
	 * Returns a list of all booked slots for a given date
	 * 
	 * @param date					- the date to fetch slots for
	 * 
	 * @return ArrayList<Slot>		- a list of booked slots for the given date
	 */
	public static ArrayList<Slot> getSlotsByDate(LocalDate date) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	If a date is not given, return an empty list
		if (date == null) return outputList;

		//	Loop through each service and return slots for the given date
		for (TreeMap<LocalDate, TreeMap<LocalTime, Slot>> dateMap : slots.values()) {
			outputList.addAll(dateMap.getOrDefault(date, new TreeMap<>()).values());
		}

		//	Return the given service slots
		return outputList;

	}

	/** 
	 * Returns a list of all booked slots for a given time
	 * 
	 * @param time					- the time to fetch slots for
	 * 
	 * @return ArrayList<Slot>		- a list of booked slots for the given time
	 */
	public static ArrayList<Slot> getSlotsByTime(LocalTime time) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	If a time is not given, return an empty list
		if (time == null) return outputList;

		//	Loop through each service and date and return slots for the given time
		for (TreeMap<LocalDate, TreeMap<LocalTime, Slot>> dateMap : slots.values()) {
			for (TreeMap<LocalTime, Slot> timeMap : dateMap.values()) {
				if (timeMap.containsKey(time)) outputList.add(timeMap.get(time));
			}
		}

		//	Return the given service slots
		return outputList;

	}

	/** 
	 * Returns a list of booked slots for all dates under the given service.
	 * 
	 * @param service				- the service to fetch slots for
	 * 
	 * @return ArrayList<Slot>		- a list of booked slots for all dates under the given service
	 */
	public static ArrayList<Slot> getSlotsByService(String service) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	If a service is not given, return an empty list
		if (service == null) return outputList;

		//	Check if there are slots for this service
		for (TreeMap<LocalTime, Slot> timeMap : slots.getOrDefault(service, new TreeMap<>()).values()) {
			outputList.addAll(timeMap.values());
		}

		//	Return the given service slots
		return outputList;

	}
	public static ArrayList<Slot> getSlotsByService(Service service) {
		return getSlotsByService(service.getId());
	}

	/** 
	 * Returns a list of booked slots for all dates under the given patient.
	 * 
	 * @param patient				- the patient to fetch slots for
	 * 
	 * @return ArrayList<Slot>		- a list of booked slots for all dates under the given patient
	 */
	public static ArrayList<Slot> getSlotsByPatient(String patient) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	If a patient is not given, return an empty list
		if (patient == null) return outputList;

		//	Cast the tree map into a list, iterate this list, and find the slots with the given patient
		for (Slot slot : getSlotsAsList()) {
			if (slot.getAllocatedPatient().getId().equals(patient)) outputList.add(slot);
		}

		//	Return the given patient slots
		return outputList;

	}
	public static ArrayList<Slot> getSlotsByPatient(Patient patient) {
		return getSlotsByPatient(patient.getId());
	}

	/** 
	 * Returns the booked slot for the given date and time
	 * 
	 * @param datetime				- the datetime to fetch slots for
	 * 
	 * @return ArrayList<Slot>		- the booked slot for the given date and time
	 */
	public static ArrayList<Slot> getSlotsByDateTime(LocalDateTime datetime) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	If a datetime is not given, return an empty list
		if (datetime == null) return outputList;

		//	Loop through each service and return slots for the given date
		LocalDate date = datetime.toLocalDate();
		LocalTime time = datetime.toLocalTime();
		for (TreeMap<LocalDate, TreeMap<LocalTime, Slot>> dateMap : slots.values()) {
			TreeMap<LocalTime, Slot> timeMap = dateMap.getOrDefault(date, new TreeMap<>());
			if (timeMap.containsKey(time)) outputList.add(timeMap.get(time));
		}

		//	Return the given service slots
		return outputList;

	}
	public static ArrayList<Slot> getSlotsByDateTime(LocalDate date, LocalTime time) {
		return getSlotsByDateTime(LocalDateTime.of(date, time));
	}
	public static ArrayList<Slot> getSlotsByDateTime(LocalTime time, LocalDate date) {
		return getSlotsByDateTime(LocalDateTime.of(date, time));
	}

	/** 
	 * Returns a list of booked slots for the given date under the given service.
	 * 
	 * @param date					- the date to check slots for
	 * @param service				- the service to fetch slots for
	 * 
	 * @return ArrayList<Slot>		- a list of booked slots under the given service
	 */
	public static ArrayList<Slot> getSlotsByDateService(LocalDate date, String service) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	If a date or service is not given, return an empty list
		if (date == null || service == null) return outputList;

		//	If a service is given, return it's slots
		outputList.addAll(slots.getOrDefault(service, new TreeMap<>()).getOrDefault(date, new TreeMap<>()).values());

		//	Return the given service slots
		return outputList;

	}
	public static ArrayList<Slot> getSlotsByDateService(LocalDate date, Service service) {
		return getSlotsByDateService(date, service.getId());
	}

	/** 
	 * Returns a list of booked slots for the given date under the given patient.
	 * 
	 * @param date					- the date to check slots for
	 * @param patient				- the patient to fetch slots for
	 * 
	 * @return ArrayList<Slot>		- a list of booked slots under the given patient and date
	 */
	public static ArrayList<Slot> getSlotsByDatePatient(LocalDate date, String patient) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	If a date or patient is not given, return an empty list
		if (date == null || patient == null) return outputList;

		//	Loop through each slot at this date and return those with the given patient
		for (Slot slot : getSlotsByDate(date)) {
			if (slot.getAllocatedPatient().getId().equals(patient)) outputList.add(slot);
		}

		//	Return the given service slots
		return outputList;

	}
	public static ArrayList<Slot> getSlotsByDatePatient(LocalDate date, Patient patient) {
		return getSlotsByDatePatient(date, patient.getId());
	}

	/** 
	 * Returns a list of booked slots for the given time under the given service.
	 * 
	 * @param time					- the time to check slots for
	 * @param service				- the service to fetch slots for
	 * 
	 * @return ArrayList<Slot>		- a list of booked slots under the given service and time
	 */
	public static ArrayList<Slot> getSlotsByTimeService(LocalTime time, String service) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	If a time or service is not given, return an empty list
		if (time == null || service == null) return outputList;

		//	Check if there are slots for this service and time
		for (TreeMap<LocalTime, Slot> timeMap : slots.getOrDefault(service, new TreeMap<>()).values()) {
			if (timeMap.containsKey(time)) outputList.add(timeMap.get(time));
		}

		//	Return the given service slots
		return outputList;

	}
	public static ArrayList<Slot> getSlotsByTimeService(LocalTime time, Service service) {
		return getSlotsByTimeService(time, service.getId());
	}

	/** 
	 * Returns a list of booked slots for the given time under the given patient.
	 * 
	 * @param time					- the time to check slots for
	 * @param patient				- the patient to fetch slots for
	 * 
	 * @return ArrayList<Slot>		- a list of booked slots under the given patient and time
	 */
	public static ArrayList<Slot> getSlotsByTimePatient(LocalTime time, String patient) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	If a time or patient is not given, return an empty list
		if (time == null || patient == null) return outputList;

		//	Loop through each slot at this time and return those with the given patient
		for (Slot slot : getSlotsByTime(time)) {
			if (slot.getAllocatedPatient().getId().equals(patient)) outputList.add(slot);
		}

		//	Return the given service slots
		return outputList;

	}
	public static ArrayList<Slot> getSlotsByTimePatient(LocalTime time, Patient patient) {
		return getSlotsByTimePatient(time, patient.getId());
	}

	/** 
	 * Returns the booked slot for the given date, time, and service
	 * 
	 * @param datetime				- the datetime to fetch slots for
	 * @param service				- the service to fetch slots for
	 * 
	 * @return Slot					- the booked slot for the given date, time, and service
	 */
	public static Slot getSlotByDateTimeService(LocalDateTime datetime, String service) {

		//	If a datetime or service is not given, return null
		if (datetime == null || service == null) return null;

		//	Loop through each service and return slots for the given date
		LocalDate date = datetime.toLocalDate();
		LocalTime time = datetime.toLocalTime();
		TreeMap<LocalDate, TreeMap<LocalTime, Slot>> dateMap = slots.getOrDefault(service, new TreeMap<>());
		TreeMap<LocalTime, Slot> timeMap = dateMap.getOrDefault(date, new TreeMap<>());

		//	Return the given slot
		return timeMap.get(time);

	}
	public static Slot getSlotByDateTimeService(LocalDateTime datetime, Service service) {
		return getSlotByDateTimeService(datetime, service.getId());
	}
	public static Slot getSlotByDateTimeService(LocalDate date, LocalTime time, String service) {
		if (date == null || time == null || service == null) return null;
		return getSlotByDateTimeService(LocalDateTime.of(date, time), service);
	}
	public static Slot getSlotByDateTimeService(LocalDate date, LocalTime time, Service service) {
		if (date == null || time == null || service == null) return null;
		return getSlotByDateTimeService(LocalDateTime.of(date, time), service.getId());
	}
	public static Slot getSlotByDateTimeService(LocalTime time, LocalDate date, String service) {
		if (date == null || time == null || service == null) return null;
		return getSlotByDateTimeService(LocalDateTime.of(date, time), service);
	}
	public static Slot getSlotByDateTimeService(LocalTime time, LocalDate date, Service service) {
		if (date == null || time == null || service == null) return null;
		return getSlotByDateTimeService(LocalDateTime.of(date, time), service.getId());
	}


	/** 
	 * Returns the booked slot for the given date, time, and patient
	 * 
	 * @param datetime				- the datetime to fetch slots for
	 * @param patient				- the patient to fetch slots for
	 * 
	 * @return Slot					- the booked slot for the given date, time, and patient
	 */
	public static Slot getSlotByDateTimePatient(LocalDateTime datetime, String patient) {

		//	If a datetime or patient is not given, return null
		if (datetime == null || patient == null) return null;

		//	Loop through each service and return slots for the given date
		ArrayList<Slot> datetimeSlots = getSlotsByDateTime(datetime);
		for (Slot slot : datetimeSlots) {
			if (slot.getAllocatedPatient().getId().equals(patient)) return slot;
		}

		//	If no slot was found then return null
		return null;

	}
	public static Slot getSlotByDateTimePatient(LocalDateTime datetime, Patient patient) {
		return getSlotByDateTimePatient(datetime, patient.getId());
	}
	public static Slot getSlotByDateTimePatient(LocalDate date, LocalTime time, String patient) {
		if (date == null || time == null || patient == null) return null;
		return getSlotByDateTimePatient(LocalDateTime.of(date, time), patient);
	}
	public static Slot getSlotByDateTimePatient(LocalDate date, LocalTime time, Patient patient) {
		if (date == null || time == null || patient == null) return null;
		return getSlotByDateTimePatient(LocalDateTime.of(date, time), patient.getId());
	}
	public static Slot getSlotByDateTimePatient(LocalTime time, LocalDate date, String patient) {
		if (date == null || time == null || patient == null) return null;
		return getSlotByDateTimePatient(LocalDateTime.of(date, time), patient);
	}
	public static Slot getSlotByDateTimePatient(LocalTime time, LocalDate date, Patient patient) {
		if (date == null || time == null || patient == null) return null;
		return getSlotByDateTimePatient(LocalDateTime.of(date, time), patient.getId());
	}

	/** 
	 * Returns the booked slot for the given date, service, and patient
	 * 
	 * @param date					- the date to fetch slots for
	 * @param service				- the service to fetch slots for
	 * @param patient				- the patient to fetch slots for
	 * 
	 * @return ArrayList<Slot>		- the booked slot for the given date, service, and patient
	 */
	public static ArrayList<Slot> getSlotsByDateServicePatient(LocalDate date, String service, String patient) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	If a date, service, or patient is not given, return an empty list
		if (date == null || service == null || patient == null) return outputList;

		//	Loop through each service and return slots for the given date
		TreeMap<LocalDate, TreeMap<LocalTime, Slot>> dateMap = slots.getOrDefault(service, new TreeMap<>());
		TreeMap<LocalTime, Slot> timeMap = dateMap.getOrDefault(date, new TreeMap<>());
		for (Slot slot : timeMap.values()) {
			if (slot.getAllocatedPatient().getId().equals(patient)) outputList.add(slot);
		}

		//	If no slot was found then return null
		return null;

	}
	public static ArrayList<Slot> getSlotsByDateServicePatient(LocalDate date, Service service, String patient) {
		return getSlotsByDateServicePatient(date, service.getId(), patient);
	}
	public static ArrayList<Slot> getSlotsByDateServicePatient(LocalDate date, String service, Patient patient) {
		return getSlotsByDateServicePatient(date, service, patient.getId());
	}
	public static ArrayList<Slot> getSlotsByDateServicePatient(LocalDate date, Service service, Patient patient) {
		return getSlotsByDateServicePatient(date, service.getId(), patient.getId());
	}

	/** 
	 * Returns the booked slot for the given time, service, and patient
	 * 
	 * @param time					- the time to fetch slots for
	 * @param service				- the service to fetch slots for
	 * @param patient				- the patient to fetch slots for
	 * 
	 * @return ArrayList<Slot>		- the booked slot for the given time, service, and patient
	 */
	public static ArrayList<Slot> getSlotsByTimeServicePatient(LocalTime time, String service, String patient) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	If a time, service, or patient is not given, return an empty list
		if (time == null || service == null || patient == null) return outputList;

		//	Loop through each service and return slots for the given time
		TreeMap<LocalDate, TreeMap<LocalTime, Slot>> dateMap = slots.getOrDefault(service, new TreeMap<>());
		for (TreeMap<LocalTime, Slot> timeMap : dateMap.values()) {
			Slot slot = timeMap.get(time);
			if (slot != null && slot.getAllocatedPatient().getId().equals(patient)) outputList.add(slot);
		}

		//	If no slot was found then return null
		return null;

	}
	public static ArrayList<Slot> getSlotsByTimeServicePatient(LocalTime time, Service service, String patient) {
		return getSlotsByTimeServicePatient(time, service.getId(), patient);
	}
	public static ArrayList<Slot> getSlotsByTimeServicePatient(LocalTime time, String service, Patient patient) {
		return getSlotsByTimeServicePatient(time, service, patient.getId());
	}
	public static ArrayList<Slot> getSlotsByTimeServicePatient(LocalTime time, Service service, Patient patient) {
		return getSlotsByTimeServicePatient(time, service.getId(), patient.getId());
	}


	/** 
	 * Returns a list of slots for the given date that are available.
	 * Note that this date cannot be in the past!
	 * 
	 * @param date					- the date to check slots for
	 * 
	 * @return ArrayList<Slot>		- a list of available slots for the given date
	 */
	public static ArrayList<Slot> getAvailableSlotsByDate(LocalDate date) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	Check if the given date is in the past
		if (Slot.isValidDate(null, date, false) != "") return outputList;

		//	Loop through each service
		for (Service service : ServiceRepository.getServicesAsList()) {

			//	Loop through each time interval in the day
			ArrayList<LocalTime> intervals = TimeUtils.getDateTimeIntervals(date);
			TreeMap<LocalTime, Slot> allSlots = slots.getOrDefault(service, new TreeMap<>()).getOrDefault(date, new TreeMap<>());
			for (LocalTime time : intervals) {

				//	Catch illegal slot time exceptions
				try {
					
					//	If there is no slot at this time then add it as a free slot
					if (!allSlots.containsKey(time)) outputList.add(new Slot(date, time, service));

				}
				catch (IllegalSlotTimeException e) {

					//	If the given time is in the past, don't add it to the output list
					continue;

				}

			}

		}

		//	Return the filtered slots
		return outputList;

	}

	/** 
	 * Returns a list of slots for the given service that are available for the given date.
	 * Note that this date cannot be in the past!
	 * 
	 * @param date					- the date to check slots for
	 * @param service				- the service to check slots for
	 * 
	 * @return ArrayList<Slot>		- a list of available slots for the given date and service
	 */
	public static ArrayList<Slot> getAvailableSlotsByDateService(LocalDate date, String service) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<>();

		//	Check if the given date is in the past
		if (Slot.isValidDate(null, date, false) != "") return outputList;

		//	Loop through each time interval in the day
		ArrayList<LocalTime> intervals = TimeUtils.getDateTimeIntervals(date);
		TreeMap<LocalTime, Slot> allSlots = slots.getOrDefault(service, new TreeMap<>()).getOrDefault(date, new TreeMap<>());
		for (LocalTime time : intervals) {

			//	Catch illegal slot time exceptions
			try {
				
				//	If there is no slot at this time then add it as a free slot
				if (!allSlots.containsKey(time)) outputList.add(new Slot(date, time, ServiceRepository.getServiceById(service)));

			}
			catch (IllegalSlotTimeException e) {

				//	If the given time is in the past, don't add it to the output list
				continue;

			}

		}

		//	Return the filtered slots
		return outputList;

	}
	public static ArrayList<Slot> getAvailableSlotsByDateService(LocalDate date, Service service) {
		return getAvailableSlotsByDateService(date, service.getId());
	}

//endregion



/* --------------------------------- Adders --------------------------------- */
//region

	/** 
	 * Books the given slot for the given patient
	 * 
	 * @param slot							- the slot to book
	 * @param patient						- the patient to book it for
	 * 
	 * @throws IllegalArgumentException		if the given slot is already booked
	 * @throws IllegalArgumentException		if the given patient already has a booking at this date and time for another service
	 * @throws IllegalArgumentException		if the given slot's service has reached the maximum number of bookings for the day
	 */
	public static void bookSlot(Slot slot, String patient) {

		//	Deconstruct slot properties
		LocalDate date = slot.getDate();
		LocalTime time = slot.getTime();
		String service = slot.getAllocatedService().getId();

		//	Validate this booking
		if (Slot.isValidBooking(slot, patient, true) != "") return;

		//	Create a new slot with the given data
		Patient allocatedPatient = PatientRepository.getPatientById(patient);
		slot.setAllocatedPatient(allocatedPatient);

		//	Add tree maps if they are not already present
		if (!slots.containsKey(service)) slots.put(service, new TreeMap<>());
		TreeMap<LocalDate, TreeMap<LocalTime, Slot>> dateMap = slots.getOrDefault(service, new TreeMap<>());
		if (!dateMap.containsKey(date)) dateMap.put(date, new TreeMap<>());
		TreeMap<LocalTime, Slot> timeMap = dateMap.getOrDefault(date, new TreeMap<>());

		//	Add the newly booked slot to the list
		timeMap.put(time, slot);

	}
	public static void bookSlot(Slot slot, Patient patient) {
		bookSlot(slot, patient.getId());
	}
	public static void bookSlot(LocalDateTime datetime, String service, String patient) {
		bookSlot(new Slot(datetime, ServiceRepository.getServiceById(service)), patient);
	}
	public static void bookSlot(LocalDateTime datetime, Service service, String patient) {
		bookSlot(datetime, service.getId(), patient);
	}
	public static void bookSlot(LocalDateTime datetime, String service, Patient patient) {
		bookSlot(datetime, service, patient.getId());
	}
	public static void bookSlot(LocalDateTime datetime, Service service, Patient patient) {
		bookSlot(datetime, service.getId(), patient.getId());
	}
	public static void bookSlot(LocalDate date, LocalTime time, String service, String patient) {
		bookSlot(LocalDateTime.of(date, time), service, patient);
	}
	public static void bookSlot(LocalDate date, LocalTime time, Service service, String patient) {
		bookSlot(LocalDateTime.of(date, time), service.getId(), patient);
	}
	public static void bookSlot(LocalDate date, LocalTime time, String service, Patient patient) {
		bookSlot(LocalDateTime.of(date, time), service, patient.getId());
	}
	public static void bookSlot(LocalDate date, LocalTime time, Service service, Patient patient) {
		bookSlot(LocalDateTime.of(date, time), service.getId(), patient.getId());
	}
	public static void bookSlot(LocalTime time, LocalDate date, String service, String patient) {
		bookSlot(LocalDateTime.of(date, time), service, patient);
	}
	public static void bookSlot(LocalTime time, LocalDate date, Service service, String patient) {
		bookSlot(LocalDateTime.of(date, time), service.getId(), patient);
	}
	public static void bookSlot(LocalTime time, LocalDate date, String service, Patient patient) {
		bookSlot(LocalDateTime.of(date, time), service, patient.getId());
	}
	public static void bookSlot(LocalTime time, LocalDate date, Service service, Patient patient) {
		bookSlot(LocalDateTime.of(date, time), service.getId(), patient.getId());
	}
	
//endregion



/* -------------------------------- Updaters -------------------------------- */
//region

	/** 
	 * Updates the info of the slot with the given ID
	 * 
	 * <p> <b>NOTE</b>: this replaces all the previously stored information!
	 * 
	 * @param id			- the ID of the slot to update
	 * @param newSlot		- the new data to replace the old slot with
	 */
	public static void updateSlot(String id, Slot newSlot) {

		//	Cancel the old slot
		cancelSlot(id);

		//	Replace it with the new slot
		bookSlot(newSlot, newSlot.getAllocatedPatient());

	}
	public static void updateSlot(Slot slot, Slot newSlot) {
		updateSlot(slot.getId(), newSlot);
	}

	/** 
	 * Updates the date of the slot with the given ID
	 * 
	 * @param id				- the ID of the slot to update
	 * @param newDate			- the slot's new date
	 */
	public static void updateSlotDate(String id, LocalDate newDate) {

		//	Temporarily store the old slot
		Slot slot = getSlotById(id);

		//	Cancel the old one
		cancelSlot(id);

		//	Modify the date of the slot
		slot.setDate(newDate);

		//	Replace with new slot
		bookSlot(slot, slot.getAllocatedPatient());

	}
	public static void updateSlotDate(Slot slot, LocalDate newDate) {
		updateSlotDate(slot.getId(), newDate);
	}

	/** 
	 * Updates the time of the slot with the given ID
	 * 
	 * @param id				- the ID of the slot to update
	 * @param newDate			- the slot's new time
	 */
	public static void updateSlotTime(String id, LocalTime newTime) {

		//	Temporarily store the old slot
		Slot slot = getSlotById(id);

		//	Cancel the old one
		cancelSlot(id);

		//	Modify the time of the slot
		slot.setTime(newTime);

		//	Replace with new slot
		bookSlot(slot, slot.getAllocatedPatient());

	}
	public static void updateSlotTime(Slot slot, LocalTime newTime) {
		updateSlotTime(slot.getId(), newTime);
	}

	/** 
	 * Updates the service of the slot with the given ID
	 * 
	 * @param id				- the ID of the slot to update
	 * @param newService		- the slot's new service
	 */
	public static void updateSlotService(String id, Service newService) {

		//	Temporarily store the old slot
		Slot slot = getSlotById(id);

		//	Cancel the old one
		cancelSlot(id);

		//	Modify the service of the slot
		slot.setAllocatedService(newService);

		//	Replace with new slot
		bookSlot(slot, slot.getAllocatedPatient());

	}
	public static void updateSlotService(Slot slot, Service newService) {
		updateSlotService(slot.getId(), newService);
	}

	/** 
	 * Updates the patient of the slot with the given ID
	 * 
	 * @param id				- the ID of the slot to update
	 * @param newPatient		- the slot's new patient
	 */
	public static void updateSlotPatient(String id, Patient newPatient) {

		//	Temporarily store the old slot
		Slot slot = getSlotById(id);

		//	Cancel the old one
		cancelSlot(id);

		//	Modify the patient of the slot
		slot.setAllocatedPatient(newPatient);

		//	Replace with new slot
		bookSlot(slot, newPatient);

	}
	public static void updateSlotPatient(Slot slot, Patient newPatient) {
		updateSlotPatient(slot.getId(), newPatient);
	}

//endregion



/* -------------------------------- Deleters -------------------------------- */
//region

	/** 
	 * Cancels the slot with the given ID
	 * 
	 * @param id							- the slot with this id will be cancelled
	 * 
	 * @throws IllegalArgumentException		if a slot with the given ID cannot be found
	 */
	public static void cancelSlot(String id) {

		//	Try to get a slot with this ID
		Slot slot = getSlotById(id);

		//	If it doesn't exist then throw an error
		if (slot == null) throw new IllegalArgumentException("The slot with the given ID cannot be found!");

		//	Else try to delete it from the list
		slots.getOrDefault(slot.getAllocatedService().getId(), new TreeMap<>())
			.getOrDefault(slot.getDate(), new TreeMap<>())
			.remove(slot.getTime());

	}

	/** 
	 * Cancels the slots with the given IDs from the list
	 * 
	 * @param ids							- the slots with these ids will be deleted
	 * 
	 * @throws IllegalArgumentException		if a slot with the given IDs cannot be found
	 */
	public static void cancelSlots(ArrayList<String> ids) {
		for (String id : ids) cancelSlot(id);
	}

	/** 
	 * Cancels slots at the given date
	 * 
	 * @param date							- the slots at this date will be cancelled
	 */
	public static void cancelSlotsByDate(LocalDate date) {
		for (TreeMap<LocalDate, TreeMap<LocalTime, Slot>> dateMap : slots.values()) {
			if (dateMap.containsKey(date)) dateMap.remove(date);
		}
	}

	/** 
	 * Cancels slots at the given time
	 * 
	 * @param time							- the slots at this time will be cancelled
	 */
	public static void cancelSlotsByTime(LocalTime time) {
		for (TreeMap<LocalDate, TreeMap<LocalTime, Slot>> dateMap : slots.values()) {
			for (TreeMap<LocalTime, Slot> timeMap : dateMap.values()) {
				if (timeMap.containsKey(time)) timeMap.remove(time);
			}
		}
	}

	/** 
	 * Cancels slots with the given service
	 * 
	 * @param service						- the slots with this given service will be cancelled
	 */
	public static void cancelSlotsByService(String service) {
		slots.remove(service);
	}
	public static void cancelSlotsByService(Service service) {
		cancelSlotsByService(service.getId());
	}

	/** 
	 * Cancels slots with the given patient
	 * 
	 * @param patient						- the slots with this given patient will be cancelled
	 */
	public static void cancelSlotsByPatient(String patient) {

		//	Try to get a slot with this ID
		ArrayList<Slot> patientSlots = getSlotsByPatient(patient);

		//	If it doesn't exist then exit
		if (patientSlots.isEmpty()) return;

		//	Else try to delete it from the list
		for (Slot slot : patientSlots) {
			slots.getOrDefault(slot.getAllocatedService(), new TreeMap<>())
				.getOrDefault(slot.getDate(), new TreeMap<>())
				.remove(slot.getTime());
		}

	}
	public static void cancelSlotsByPatient(Patient patient) {
		cancelSlotsByPatient(patient.getId());
	}

	/** 
	 * Cancels slots at the given date and time
	 * 
	 * @param datetime						- the slots at this date and time will be cancelled
	 */
	public static void cancelSlotsByDateTime(LocalDateTime datetime) {
		LocalDate date = datetime.toLocalDate();
		LocalTime time = datetime.toLocalTime();
		for (TreeMap<LocalDate, TreeMap<LocalTime, Slot>> dateMap : slots.values()) {
			TreeMap<LocalTime, Slot> timeMap = dateMap.get(date);
			if (timeMap.containsKey(time)) timeMap.remove(time);
		}
	}
	public static void cancelSlotsByDateTime(LocalDate date, LocalTime time) {
		cancelSlotsByDateTime(LocalDateTime.of(date, time));
	}
	public static void cancelSlotsByDateTime(LocalTime time, LocalDate date) {
		cancelSlotsByDateTime(LocalDateTime.of(date, time));
	}

	/** 
	 * Cancels the slot at the given date and time under the given service
	 * 
	 * @param datetime						- the slot at this date and time will be cancelled
	 * @param service						- the slot under this service will be cancelled
	 */
	public static void cancelSlotByDateTimeService(LocalDateTime datetime, String service) {
		
		//	Deconstruct date time into its components
		LocalDate date = datetime.toLocalDate();
		LocalTime time = datetime.toLocalTime();

		//	Remove slots at the given date, time, and service
		TreeMap<LocalDate, TreeMap<LocalTime, Slot>> dateMap = slots.get(service);
		TreeMap<LocalTime, Slot> timeMap = dateMap.get(date);
		if (timeMap.containsKey(time)) timeMap.remove(time);

	}
	public static void cancelSlotByDateTimeService(LocalDateTime datetime, Service service) {
		cancelSlotByDateTimeService(datetime, service.getId());
	}
	public static void cancelSlotByDateTimeService(LocalDate date, LocalTime time, String service) {
		cancelSlotByDateTimeService(LocalDateTime.of(date, time), service);
	}
	public static void cancelSlotByDateTimeService(LocalDate date, LocalTime time, Service service) {
		cancelSlotByDateTimeService(LocalDateTime.of(date, time), service.getId());
	}
	public static void cancelSlotByDateTimeService(LocalTime time, LocalDate date, String service) {
		cancelSlotByDateTimeService(LocalDateTime.of(date, time), service);
	}
	public static void cancelSlotByDateTimeService(LocalTime time, LocalDate date, Service service) {
		cancelSlotByDateTimeService(LocalDateTime.of(date, time), service.getId());
	}

	/** 
	 * Cancels the slot at the given date and time under the given patient
	 * 
	 * @param datetime						- the slot at this date and time will be cancelled
	 * @param patient						- the slot under this patient will be cancelled
	 */
	public static void cancelSlotByDateTimePatient(LocalDateTime datetime, String patient) {

		//	Deconstruct date time into its components
		LocalDate date = datetime.toLocalDate();
		LocalTime time = datetime.toLocalTime();

		//	Try to get a slot with this allocated patient
		ArrayList<Slot> patientSlots = getSlotsByPatient(patient);

		//	If it doesn't exist then exit
		if (patientSlots.isEmpty()) return;

		//	Else try to delete it from the list
		for (Slot slot : patientSlots) {
			slots.getOrDefault(slot.getAllocatedService(), new TreeMap<>())
				.getOrDefault(date, new TreeMap<>())
				.remove(time);
		}

	}
	public static void cancelSlotByDateTimePatient(LocalDateTime datetime, Patient patient) {
		cancelSlotByDateTimePatient(datetime, patient.getId());
	}
	public static void cancelSlotByDateTimePatient(LocalDate date, LocalTime time, String patient) {
		cancelSlotByDateTimePatient(LocalDateTime.of(date, time), patient);
	}
	public static void cancelSlotByDateTimePatient(LocalDate date, LocalTime time, Patient patient) {
		cancelSlotByDateTimePatient(LocalDateTime.of(date, time), patient.getId());
	}
	public static void cancelSlotByDateTimePatient(LocalTime time, LocalDate date, String patient) {
		cancelSlotByDateTimePatient(LocalDateTime.of(date, time), patient);
	}
	public static void cancelSlotByDateTimePatient(LocalTime time, LocalDate date, Patient patient) {
		cancelSlotByDateTimePatient(LocalDateTime.of(date, time), patient.getId());
	}

//endregion


	
}

package cmps251.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import cmps251.exceptions.IllegalSlotDateException;
import cmps251.exceptions.IllegalSlotTimeException;
import cmps251.repos.ServiceRepository;
import cmps251.repos.SlotRepository;
import cmps251.utils.TimeUtils;


/**
 * This class serves as the main class for Slot objects in the Sehha hospital reception system
 * 
 * <p> It represents 30 minute time slots where patients can book the services offered by the hospital
 * Time slots with a starting time not within the hospital operating hours is not allowed.
 * Patients can only book open slots after 7:00AM and before 9:00PM in 30 minute intervals.
 * 
 * <p> <i>Created on 14/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.17
 * @since		1.0
 */
public class Slot extends Identifiable {



/* --------------------------- Constant Attributes -------------------------- */
//region

	public static final LocalTime MIN_TIME = LocalTime.parse("07:00:00");
	public static final LocalTime MAX_TIME = LocalTime.parse("20:30:00");
	public static final int SLOT_DURATION = 30;
	public static final int MAX_SLOTS_PER_DAY = 28;

//endregion



/* --------------------------- Private Attributes --------------------------- */
//region

	private LocalTime time;
	private LocalDate date;
	private boolean isBooked;
	private Service allocatedService;
	private Patient allocatedPatient;

//endregion



/* ------------------------------ Constructors ------------------------------ */
//region

	/**
	 * This constructor takes in a starting date and time for a slot.
	 * It is meant to be used to create empty slots
	 * 
	 * @param datetime						- the slot's date and time
	 */
	public Slot(LocalDateTime datetime) {

		//	Set object attributes
		this.setDate(datetime.toLocalDate());
		this.setTime(datetime.toLocalTime());

		//	Generate a random id for this slot
		this.setId(generateId());

	}
	public Slot(LocalDate date, LocalTime time) {
		this(LocalDateTime.of(date, time));
	}
	public Slot(LocalTime time, LocalDate date) {
		this(LocalDateTime.of(date, time));
	}

	/**
	 * This constructor takes in a starting date and time, as well as an allocated service for a slot.
	 * It is meant to be used to create available service slots
	 * 
	 * @param datetime						- the slot's date and time
	 * @param allocatedService				- the slot's allocated service
	 * 
	 * @throws IllegalArgumentException		if given service is null
	 * @throws IllegalSlotDateException		if given date is in the past
	 * @throws IllegalSlotDateException		if given date at the current slot time is in the past
	 * @throws IllegalSlotTimeException		if given time starts before 7:00AM or after 8:30PM
	 * @throws IllegalSlotTimeException		if given time is not within 30 minute time intervals
	 * @throws IllegalSlotTimeException		if given time at the current slot date is in the past
	 */
	public Slot(LocalDateTime datetime, Service allocatedService) {

		//	Call parent constructor to generate ID
		this(datetime);

		//	If the given service or patient is null
		if (allocatedService == null) throw new IllegalArgumentException("Given service cannot be null!");

		//	Set the slot's service
		this.setAllocatedService(allocatedService);

		//	Generate a random id for this slot
		this.setId(generateId());

	}
	public Slot(LocalDate date, LocalTime time, Service allocatedService) {
		this(LocalDateTime.of(date, time), allocatedService);
	}
	public Slot(LocalTime time, LocalDate date, Service allocatedService) {
		this(LocalDateTime.of(date, time), allocatedService);
	}

	/**
	 * This constructor takes in a starting date and time, as well as an allocated service and patient for a slot.
	 * It is meant to be used to create booked slots
	 * 
	 * @param datetime						- the slot's date and time
	 * @param allocatedService				- the slot's allocated service
	 * @param allocatedPatient				- the slot's allocated patient
	 * 
	 * @throws IllegalArgumentException		if given service or patient is null
	 * @throws IllegalSlotDateException		if given date is in the past
	 * @throws IllegalSlotDateException		if given date at the current slot time is in the past
	 * @throws IllegalSlotTimeException		if given time starts before 7:00AM or after 8:30PM
	 * @throws IllegalSlotTimeException		if given time is not within 30 minute time intervals
	 * @throws IllegalSlotTimeException		if given time at the current slot date is in the past
	 */
	public Slot(LocalDateTime datetime, Service allocatedService, Patient allocatedPatient) {

		//	Call parent constructor to generate ID
		this(datetime);

		//	If the given service or patient is null
		if (allocatedService == null || allocatedPatient == null) throw new IllegalArgumentException("Given service or patient cannot be null!");

		//	Reserve this slot for the given service and patient
		this.setBooked(true);
		this.setAllocatedService(allocatedService);
		this.setAllocatedPatient(allocatedPatient);

		//	Generate a random id for this slot
		this.setId(generateId());

	}
	public Slot(LocalDate date, LocalTime time, Service allocatedService, Patient allocatedPatient) {
		this(LocalDateTime.of(date, time), allocatedService, allocatedPatient);
	}
	public Slot(LocalTime time, LocalDate date, Service allocatedService, Patient allocatedPatient) {
		this(LocalDateTime.of(date, time), allocatedService, allocatedPatient);
	}

//endregion



/* --------------------------- Getters and Setters -------------------------- */
//region

	/**
	 * Returns the starting time of this slot
	 * 
	 * @return LocalTime	- the slot's starting time
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 * Sets the starting time of this slot
	 * 
	 * <p> <b>NOTE</b>: this method should never be called directly!
	 * It should be called only inside it's repository class or constructor 
	 * 
	 * @param time							- new starting time of the slot
	 * 
	 * @throws IllegalSlotTimeException		if given time starts before 7:00AM or after 8:30PM
	 * @throws IllegalSlotTimeException		if given time is not within 30 minute time intervals
	 * @throws IllegalSlotTimeException		if given time at the current slot date is in the past
	 */
	public void setTime(LocalTime time) {

		//	Validate starting time
		if (isValidTime(time, true) != "") return;

		//	If all is well set the new starting time
		this.time = time;
	
	}

	/**
	 * Returns the date of this slot
	 * 
	 * @return LocalDate		- the slot's date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Sets the date of this slot
	 * 
	 * <p> <b>NOTE</b>: this method should never be called directly!
	 * It should be called only inside it's repository class or constructor 
	 * 
	 * @param time							- new date of the slot
	 * 
	 * @throws IllegalSlotDateException		if given date is in the past
	 * @throws IllegalSlotDateException		if given date at the current slot time is in the past
	 */
	public void setDate(LocalDate date) {

		//	Validate slot date
		if (isValidDate(date, true) != "") return;

		//	If given date passes all checks then set the new date
		this.date = date;

	}

	/**
	 * Returns booking status of the slot
	 * 
	 * @return boolean		- the booking status of the slot
	 */
	public boolean isBooked() {
		return isBooked;
	}

	/**
	 * Sets the booking status of the slot
	 * 
	 * <p> <b>NOTE</b>: this method should never be called directly!
	 * It should be called only inside it's repository class or constructor 
	 * 
	 * @param isBooked		
	 */
	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	/**
	 * Returns the slot's allocated service
	 * 
	 * @return Service		- the allocated service for this slot
	 */
	public Service getAllocatedService() {
		return allocatedService;
	}
	
	/**
	 * Sets the slot's allocated service
	 * 
	 * <p> <b>NOTE</b>: this method should never be called directly!
	 * It should be called only inside it's repository class or constructor 
	 * 
	 * @param allocatedService		- the service to allocate for this slot		
	 */
	public void setAllocatedService(Service allocatedService) {
		this.allocatedService = allocatedService;
	}

	/**
	 * Returns the slot's allocated patient
	 * 
	 * @return Patient		- the allocated patient for this slot
	 */
	public Patient getAllocatedPatient() {
		return allocatedPatient;
	}

	/**
	 * Sets the slot's allocated patient
	 * 
	 * <p> <b>NOTE</b>: this method should never be called directly!
	 * It should be called only inside it's repository class or constructor 
	 * 
	 * @param allocatedPatient		- the patient to allocate for this slot		
	 */
	public void setAllocatedPatient(Patient allocatedPatient) {
		this.allocatedPatient = allocatedPatient;
	}

//endregion



/* ----------------------------- Utility Methods ---------------------------- */
//region

	/** 
	 * Returns an array of slots for all time intervals for the given date
	 * 
	 * @param date						- the date to get slots for
	 * 
	 * @return ArrayList<Slot>			- an array of slots for the given date
	 */
	public static ArrayList<Slot> getTimeIntervalSlots(LocalDate date) {

		//	Create a new list of time intervals to be returned
		ArrayList<Slot> outputList = new ArrayList<Slot>();

		//	Loop through each time interval in the day
		ArrayList<LocalTime> intervals = TimeUtils.getDateTimeIntervals(date);
		for (LocalTime time : intervals) {
			
			//	Create a slot and add it to the output list
			outputList.add(new Slot(date, time));

		}

		//	Return the list of slots
		return outputList;

	}

	/** 
	 * Checks if the given time is valid for the given slot
	 * 
	 * @param slot							- the slot to check the time validity for
	 * @param time							- the time to check the validity of
	 * @param throwError					- should we throw an error here?
	 * 
	 * @return String						- the error message
	 * 
	 * @throws IllegalSlotTimeException		if given time starts before 7:00AM or after 8:30PM
	 * @throws IllegalSlotTimeException		if given time is not within 30 minute time intervals
	 * @throws IllegalSlotTimeException		if given time at the current slot date is in the past
	 */
	public static String isValidTime(Slot slot, LocalTime time, boolean throwError) {

		//	If the given time is before the opening time
		if (time.isBefore(MIN_TIME)) {
			String errorMessage = "Starting time cannot be before " + MIN_TIME.toString() + "!";
			if (throwError) throw new IllegalSlotTimeException(errorMessage);
			return errorMessage;
		}

		//	If the given time is after the closing time
		if (time.isAfter(MAX_TIME)) {
			String errorMessage = "Starting time cannot be after " + MAX_TIME.toString() + "!";
			if (throwError) throw new IllegalSlotTimeException(errorMessage);
			return errorMessage;
		}

		//	If the given time is not in intervals of 30 minutes
		if (time.getMinute() != 0 && time.getMinute() != 30) {
			String errorMessage = "Starting time must be within 30 minute intervals!";
			if (throwError) throw new IllegalSlotTimeException(errorMessage);
			return errorMessage;
		}

		//	If this combination of time and the current slot date is in the past
		if (slot != null && slot.getDate() != null && LocalDateTime.of(slot.getDate(), time).isBefore(LocalDateTime.now())) {
			String errorMessage = "Starting time must not be in the past!";
			if (throwError) throw new IllegalSlotTimeException(errorMessage);
			return errorMessage;
		}

		//	Else return nothing since the given time is valid
		return "";

	}

	/** 
	 * Checks if the given date is valid for the given slot
	 * 
	 * @param slot							- the slot to check the time validity for
	 * @param date							- the date to check the validity of
	 * @param throwError					- should we throw an error here?
	 * 
	 * @return String						- the error message
	 * 
	 * @throws IllegalSlotDateException		if given date is in the past
	 * @throws IllegalSlotDateException		if given date at the current slot time is in the past
	 */
	public static String isValidDate(Slot slot, LocalDate date, boolean throwError) {

		//	If the given date is in the past
		if (date.isBefore(LocalDate.now().minusDays(1))) {
			String errorMessage = "Starting date must not be in the past!";
			if (throwError) throw new IllegalSlotDateException(errorMessage);
			return errorMessage;
		}

		//	If this combination of date and the current slot time is in the past
		if (slot != null && slot.getTime() != null && LocalDateTime.of(date, slot.getTime()).isBefore(LocalDateTime.now())) {
			String errorMessage = "Starting date and time must not be in the past!";
			if (throwError) throw new IllegalSlotDateException(errorMessage);
			return errorMessage;
		}

		//	Else return nothing since the given date is valid
		return "";

	}

	/** 
	 * Checks if the given date time combination is valid
	 * 
	 * @param date							- the date to check the validity of
	 * @param time							- the time to check the validity of
	 * @param throwError					- should we throw an error here?
	 * 
	 * @return String						- the error message
	 * 
	 * @throws IllegalSlotDateException		if given date at the current slot time is in the past
	 * @throws IllegalSlotTimeException		if given time starts before 7:00AM or after 8:30PM
	 * @throws IllegalSlotTimeException		if given time is not within 30 minute time intervals
	 * @throws IllegalSlotTimeException		if given time at the current slot date is in the past
	 */
	public static String isValidDateTime(LocalDate date, LocalTime time, boolean throwError) {

		//	Check validity of date
		String dateError = isValidDate(null, date, throwError);
		if (dateError != "") return dateError;

		//	Check validity of time
		String timeError = isValidTime(null, time, throwError);
		if (timeError != "") return timeError;

		//	Else return nothing since the given date is valid
		return "";

	}
	public static String isValidDateTime(LocalTime time, LocalDate date, boolean throwError) {
		return isValidDateTime(date, time, throwError);
	}

	/** 
	 * Checks if the given booking is valid
	 * 
	 * @param slot							- the slot to check the booking validity for
	 * @param patient						- the patient who will book the slot
	 * @param throwError					- should we throw an error here?
	 * 
	 * @return String						- the error message
	 * 
	 * @throws IllegalArgumentException		if the given slot is already booked
	 * @throws IllegalArgumentException		if the given patient already has a booking at this date and time for another service
	 * @throws IllegalArgumentException		if the given slot's service has reached the maximum number of bookings for the day
	 */
	public static String isValidBooking(Slot slot, String patient, boolean throwError) {

		//	Deconstruct slot properties
		LocalDate date = slot.getDate();
		LocalTime time = slot.getTime();
		String service = slot.getAllocatedService().getId();

		//	If the given slot is already booked
		if (SlotRepository.getSlotByDateTimeService(date, time, service) != null) {
			String errorMessage = "This slot is unavailable!";
			if (throwError) throw new IllegalSlotDateException(errorMessage);
			return errorMessage;
		}

		//	If the given patient already has a booking at this date and time for another service
		if (SlotRepository.getSlotByDateTimePatient(date, time, patient) != null) {
			String errorMessage = "You cannot book 2 slots at the same date and time!";
			if (throwError) throw new IllegalSlotDateException(errorMessage);
			return errorMessage;
		}

		//	If the given slot's service has reached the maximum number of bookings for the day
		if (SlotRepository.getSlotsByDateService(date, service).size() >= slot.getAllocatedService().getMaxSlots()) {
			String errorMessage = "This slot has reached the maximum number of bookings for the day!";
			if (throwError) throw new IllegalSlotDateException(errorMessage);
			return errorMessage;
		}

		//	Else return nothing since the given booking is valid
		return "";

	}
	public static String isValidBooking(Slot slot, Patient patient, boolean throwError) {
		return isValidBooking(slot, patient.getId(), throwError);
	}
	public static String isValidBooking(LocalDateTime datetime, String service, String patient, boolean throwError) {
		return isValidBooking(new Slot(datetime, ServiceRepository.getServiceById(service)), patient, throwError);
	}
	public static String isValidBooking(LocalDateTime datetime, Service service, String patient, boolean throwError) {
		return isValidBooking(datetime, service.getId(), patient, throwError);
	}
	public static String isValidBooking(LocalDateTime datetime, String service, Patient patient, boolean throwError) {
		return isValidBooking(datetime, service, patient.getId(), throwError);
	}
	public static String isValidBooking(LocalDateTime datetime, Service service, Patient patient, boolean throwError) {
		return isValidBooking(datetime, service.getId(), patient.getId(), throwError);
	}
	public static String isValidBooking(LocalDate date, LocalTime time, String service, String patient, boolean throwError) {
		return isValidBooking(LocalDateTime.of(date, time), service, patient, throwError);
	}
	public static String isValidBooking(LocalDate date, LocalTime time, Service service, String patient, boolean throwError) {
		return isValidBooking(LocalDateTime.of(date, time), service.getId(), patient, throwError);
	}
	public static String isValidBooking(LocalDate date, LocalTime time, String service, Patient patient, boolean throwError) {
		return isValidBooking(LocalDateTime.of(date, time), service, patient.getId(), throwError);
	}
	public static String isValidBooking(LocalDate date, LocalTime time, Service service, Patient patient, boolean throwError) {
		return isValidBooking(LocalDateTime.of(date, time), service.getId(), patient.getId(), throwError);
	}
	public static String isValidBooking(LocalTime time, LocalDate date, String service, String patient, boolean throwError) {
		return isValidBooking(LocalDateTime.of(date, time), service, patient, throwError);
	}
	public static String isValidBooking(LocalTime time, LocalDate date, Service service, String patient, boolean throwError) {
		return isValidBooking(LocalDateTime.of(date, time), service.getId(), patient, throwError);
	}
	public static String isValidBooking(LocalTime time, LocalDate date, String service, Patient patient, boolean throwError) {
		return isValidBooking(LocalDateTime.of(date, time), service, patient.getId(), throwError);
	}
	public static String isValidBooking(LocalTime time, LocalDate date, Service service, Patient patient, boolean throwError) {
		return isValidBooking(LocalDateTime.of(date, time), service.getId(), patient.getId(), throwError);
	}
	
	/** 
	 * Generates an ID for this slot
	 * 
	 * <p> This is calculated by doing the following:
	 * <ol>
	 * 		<li> Get the initial ID from {@code Identifiable}
	 * 		<li> Add date, time, service ID, and patient ID to the slot ID
	 * </ol>
	 */	
	protected String generateId() {

		//	Get the super class id
		String idConcat = super.generateId();

		//	Fetch this object's properties and convert them into strings
		String date = this.getDate().toString();
		String time = this.getTime().toString();
		String service = this.getAllocatedService() != null ? this.getAllocatedService().getId() : "None";
		String patient = this.getAllocatedPatient() != null ? this.getAllocatedPatient().getId() : "None";

		//	Concatenate the properties together
		idConcat += String.format("D%sT%sS%sP%s", date, time, service, patient);

		//	Return the new ID
		return idConcat;

	}

	/** 
	 * Returns this object as a string representation
	 * 
	 * <p> This method formats the properties in the following way:
	 * <p> {@code ID: <id>, Time Slot: <date> <time>, Status: <isBooked>, Service: <allocatedService>, Patient <allocatedPatient>}
	 */	
	@Override
	public String toString() {

		//	Get id string from super class
		String idString = super.toString();

		//	Merge slot date and time into one object
		String dateTime = LocalDateTime.of(this.date, this.time).toString();

		//	Concatenate with this object's properties
		String status = this.isBooked() ? "Booked" : "Available";
		String service = this.getAllocatedService() != null ? this.getAllocatedService().getTitle() : "None";
		String patient = this.getAllocatedPatient() != null ? this.getAllocatedPatient().getName() : "None";
		return String.format(
			"%s, Time Slot: %s, Status: %s, Service: %s, Patient: %s %n",
			idString, dateTime, status, service, patient
		);

	}

	/** 
	 * Checks if the given time is valid for this slot
	 * 
	 * @param time							- the time to check the validity of
	 * @param throwError					- should we throw an error here?
	 * 
	 * @return String						- the error message
	 * 
	 * @throws IllegalSlotTimeException		if given time starts before 7:00AM or after 8:30PM
	 * @throws IllegalSlotTimeException		if given time is not within 30 minute time intervals
	 * @throws IllegalSlotTimeException		if given time at the current slot date is in the past
	 */
	public String isValidTime(LocalTime time, boolean throwError) {
		return isValidTime(this, time, throwError);
	}

	/** 
	 * Checks if the given time is valid for this slot
	 * 
	 * @param time			- the time to check the validity of
	 * 
	 * @return boolean		- is the given time valid
	 */
	public boolean isValidTime(LocalTime time) {
		return this.isValidTime(time, false) == "";
	}

	/** 
	 * Checks if the given date is valid for this slot
	 * 
	 * @param date							- the date to check the validity of
	 * @param throwError					- should we throw an error here?
	 * 
	 * @return String						- the error message
	 * 
	 * @throws IllegalSlotDateException		if given date is in the past
	 * @throws IllegalSlotDateException		if given date at the current slot time is in the past
	 */
	public String isValidDate(LocalDate date, boolean throwError) {
		return isValidDate(this, date, throwError);
	}

	/** 
	 * Checks if the given date is valid for this slot
	 * 
	 * @param date			- the date to check the validity of
	 * 
	 * @return boolean		- is the given date valid
	 */
	public boolean isValidDate(LocalDate date) {
		return this.isValidDate(date, false) == "";
	}

//endregion



}

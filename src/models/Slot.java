package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import exceptions.IllegalSlotDateException;
import exceptions.IllegalSlotTimeException;
import utils.TimeUtils;


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
 * @version		1.6
 * @since		1.0
 */
public class Slot extends Identifiable {



/* ---------------------------- Static Attributes --------------------------- */
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
	 * This is the default constructor for the {@code Slot} class which randomly generates the ID.
	 * This constructor is not meant to be used outside of the class itself 
	 */
	private Slot() {

		//	Generate a random id for this slot
		this.generateId();

	}	

	/**
	 * This constructor takes in a starting date and time for a slot.
	 * It is meant to be used to create empty slots
	 * 
	 * @param time		- the slot's starting time
	 * @param date		- the slot's date
	 */
	public Slot(LocalTime time, LocalDate date) {

		//	Call parent constructor to generate ID
		this();

		//	Set object attributes
		this.setDate(date);
		this.setTime(time);

	}

	/**
	 * This constructor takes in a starting date and time for a slot.
	 * It is meant to be used to create empty slots
	 * 
	 * @param date		- the slot's date
	 * @param time		- the slot's starting time
	 */
	public Slot(LocalDate date, LocalTime time) {

		//	Call parent constructor to generate ID
		this();

		//	Set object attributes
		this.setDate(date);
		this.setTime(time);

	}

	/**
	 * This constructor takes in a starting date and time, as well as an allocated service and patient for a slot.
	 * It is meant to be used to create booked slots
	 * 
	 * @param time							- the slot's starting time
	 * @param date							- the slot's date
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
	public Slot(LocalTime time, LocalDate date, Service allocatedService, Patient allocatedPatient) {

		//	Call parent constructor to generate ID
		this(time, date);

		//	If the given service or patient is null
		if (allocatedService == null || allocatedPatient == null) throw new IllegalArgumentException("Given service or patient cannot be null!");

		//	Reserve this slot for the given service and patient
		this.reserve(allocatedService, allocatedPatient);

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
	 * <p> <b>NOTE</b>: this method should only be called from within the constructor.
	 * Usage outside may break certain features!
	 * 
	 * @param time							- new starting time of the slot
	 * 
	 * @throws IllegalSlotTimeException		if given time starts before 7:00AM or after 8:30PM
	 * @throws IllegalSlotTimeException		if given time is not within 30 minute time intervals
	 * @throws IllegalSlotTimeException		if given time at the current slot date is in the past
	 */
	private void setTime(LocalTime time) {

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
	 * <p> <b>NOTE</b>: this method should only be called from within the constructor.
	 * Usage outside may break certain features!
	 * 
	 * @param time							- new date of the slot
	 * 
	 * @throws IllegalSlotDateException		if given date is in the past
	 * @throws IllegalSlotDateException		if given date at the current slot time is in the past
	 */
	private void setDate(LocalDate date) {

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
	 * <p> <b>NOTE</b>: this method should only be called from within the constructor.
	 * Usage outside may break certain features!
	 * 
	 * @param allocatedService		- the service to allocate for this slot		
	 */
	private void setAllocatedService(Service allocatedService) {
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
	 * <p> <b>NOTE</b>: this method should only be called from within the constructor.
	 * Usage outside may break certain features!
	 * 
	 * @param allocatedPatient		- the patient to allocate for this slot		
	 */
	private void setAllocatedPatient(Patient allocatedPatient) {
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

	/** 
	 * Checks if the given date time combination is valid
	 * 
	 * @param time							- the time to check the validity of
	 * @param date							- the date to check the validity of
	 * @param throwError					- should we throw an error here?
	 * 
	 * @return String						- the error message
	 * 
	 * @throws IllegalSlotDateException		if given date at the current slot time is in the past
	 * @throws IllegalSlotTimeException		if given time starts before 7:00AM or after 8:30PM
	 * @throws IllegalSlotTimeException		if given time is not within 30 minute time intervals
	 * @throws IllegalSlotTimeException		if given time at the current slot date is in the past
	 */
	public static String isValidDateTime(LocalTime time, LocalDate date, boolean throwError) {
		return isValidDateTime(date, time, throwError);
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
		String service = this.isBooked() ? this.getAllocatedService().toString() : "None";
		String patient = this.isBooked() ? this.getAllocatedPatient().toString() : "None";
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



/* ------------------------------ Logic Methods ----------------------------- */
//region

	/**
	 * Reserves this slot for the given service and patient.
	 * 
	 * <p> <b>NOTE</b>: this method does not check whether for accurate business logic.
	 * This should be implemented in the {@code AdminApp} class!
	 * 
	 * @param service					- the service to allocate for this slot
	 * @param patient					- the patient to allocate for this slot
	 * 
	 * @throws IllegalStateException	if the slot has been previously booked
	 */
	public void reserve(Service service, Patient patient) {

		//	If this service has already been booked
		if (this.isBooked()) throw new IllegalStateException("This service has already been booked!");

		//	Reserve slot for the given service and patient
		this.setBooked(true);
		this.setAllocatedService(allocatedService);
		this.setAllocatedPatient(allocatedPatient);

	}

	/**
	 * Cancels this slot's reservation
	 * 
	 * <p> <b>NOTE</b>: this method does not check whether for accurate business logic.
	 * This should be implemented in the {@code AdminApp} class!
	 * 
	 * @throws IllegalStateException	if the slot has not been previously booked
	 */
	public void cancel() {

		//	If this service has not been booked
		if (!this.isBooked()) throw new IllegalStateException("This service has not yet been booked!");

		//	Cancel slot reservation
		this.setBooked(false);

	}

//endregion


}

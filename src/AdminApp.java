import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

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
 * @version		1.2
 * @since		1.1
 */
public final class AdminApp {
	


/* --------------------------- Static Attributes --------------------------- */
//region

	private static ArrayList<Patient> patients = new ArrayList<Patient>();
	private static ArrayList<Service> services = new ArrayList<Service>();
	private static HashMap<LocalDateTime, Slot> slots = new HashMap<>();

//endregion



/* ------------------------------ Logic Methods ----------------------------- */
//region

	/** 
	 * Returns an array of slots that are available for the given date.
	 * Note that this date cannot be in the past!
	 * 
	 * @param date							- the date to check slots for
	 * 
	 * @return ArrayList<Slot>				- an array of available slots for the given date
	 * 
	 * @throws IllegalSlotDateException		if given date is in the past
	 */
	public static ArrayList<Slot> getAvailableSlots(LocalDate date) {

		//	Create a new list of slots to be returned
		ArrayList<Slot> outputList = new ArrayList<Slot>();

		//	Check if the given date is in the past
		if (Slot.isValidDate(null, date, false) != "") return outputList;

		//	Loop through each time interval in the day
		ArrayList<LocalTime> intervals = TimeUtils.getDateTimeIntervals(date);
		for (LocalTime time : intervals) {

			//	Catch illegal slot time exceptions
			try {

				//	Create date time object
				LocalDateTime dateTime = LocalDateTime.of(date, time);
				
				//	If there is no slot at this time then add it as a free slot
				if (slots.get(dateTime) == null) outputList.add(new Slot(date, time));

				//	But if there is a slot, check if it is free
				else if (!slots.get(dateTime).isBooked()) outputList.add(new Slot(date, time));

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

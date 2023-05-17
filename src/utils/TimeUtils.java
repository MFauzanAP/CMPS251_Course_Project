package utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import models.Slot;

/**
 * This class offers some utility methods related to time
 * 
 * <p> <i>Created on 14/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.1
 * @since		1.1
 */
public final class TimeUtils {



/* ----------------------------- Utility Methods ---------------------------- */
//region

	/** 
	 * Returns an array of time intervals for the given date
	 * 
	 * @param date							- the date to get time intervals for
	 * 
	 * @return ArrayList<LocalTime>			- an array of time intervals for the given date
	 */
	public static ArrayList<LocalTime> getDateTimeIntervals(LocalDate date) {

		//	Create a new list of time intervals to be returned
		ArrayList<LocalTime> outputList = new ArrayList<LocalTime>();

		//	Loop through each time interval in the day
		long numMinutes = Duration.between(Slot.MIN_TIME, Slot.MAX_TIME).toMinutes();
		for (int i = 0; i <= numMinutes; i += 30) {
			
			//	Add the current time interval to the output list
			outputList.add(Slot.MIN_TIME.plusMinutes(i));

		}

		//	Return the list of time intervals
		return outputList;

	}

//endregion
	


}

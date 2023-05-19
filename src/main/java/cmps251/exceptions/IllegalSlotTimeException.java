package cmps251.exceptions;

/**
 * This is an exception that occurs when a slot constructor is given an incorrect time.
 * 
 * <p> Conditions for an incorrect time are:
 * 
 * <p> <ul>
 * 		<li> the given time starts before 7:00AM or after 8:30PM
 * 		<li> the given time is not within 30 minute time intervals
 * 		<li> the given time at the current slot date is in the past
 * </ul>
 * 
 * <p> <i>Created on 17/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.2
 * @since		1.2
 */
public class IllegalSlotTimeException extends RuntimeException {



/* ------------------------------- Constructor ------------------------------ */
//region

	/**
     * Constructs an {@code IllegalSlotTimeException} with the specified error message.
     *
     * @param   errorMessage		the error message.
     */
	public IllegalSlotTimeException(String errorMessage) {
		super(errorMessage);
	}

//endregion
	


}

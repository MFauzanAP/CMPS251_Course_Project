package cmps251.exceptions;

/**
 * This is an exception that occurs when a slot constructor is given an incorrect date.
 * 
 * <p> Conditions for an incorrect date are:
 * 
 * <p> <ul>
 * 		<li> the given date is in the past
 * 		<li> the given date with the current time is in the past
 * </ul>
 * 
 * <p> <i>Created on 17/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.2
 * @since		1.2
 */
public class IllegalSlotDateException extends RuntimeException {



/* ------------------------------- Constructor ------------------------------ */
//region

	/**
     * Constructs an {@code IllegalSlotDateException} with the specified error message.
     *
     * @param   errorMessage		the error message.
     */
	public IllegalSlotDateException(String errorMessage) {
		super(errorMessage);
	}

//endregion
	


}

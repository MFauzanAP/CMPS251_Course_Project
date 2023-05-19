package cmps251.exceptions;

/**
 * This is an exception that occurs when a service constructor is given an incorrect maximum number of slots.
 * 
 * <p> Conditions for an incorrect value are:
 * 
 * <p> <ul>
 * 		<li> the given value is negative
 * 		<li> the given value exceeds the hospital's maximum number of slots per day
 * </ul>
 * 
 * <p> <i>Created on 17/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.6
 * @since		1.6
 */
public class IllegalServiceMaxSlotsException extends RuntimeException {



/* ------------------------------- Constructor ------------------------------ */
//region

	/**
     * Constructs an {@code IllegalServiceMaxSlotsException} with the specified error message.
     *
     * @param   errorMessage		the error message.
     */
	public IllegalServiceMaxSlotsException(String errorMessage) {
		super(errorMessage);
	}

//endregion
	


}

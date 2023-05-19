package cmps251.exceptions;

/**
 * This is an exception that occurs when a patient is given an invalid id.
 * 
 * <p> Conditions for an incorrect value are:
 * 
 * <p> <ul>
 * 		<li> the given value is empty
 * 		<li> the given value contains letters
 * 		<li> the given value is not a valid QID if patient is a resident (11 digits)
 * 		<li> the given value is not a valid Visa number if patient is a visitor (12 digits)
 * </ul>
 * 
 * <p> <i>Created on 18/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.10
 * @since		1.10
 */
public class IllegalPatientIdException extends RuntimeException {



/* ------------------------------- Constructor ------------------------------ */
//region

	/**
     * Constructs an {@code IllegalPatientIdException} with the specified error message.
     *
     * @param   errorMessage		the error message.
     */
	public IllegalPatientIdException(String errorMessage) {
		super(errorMessage);
	}

//endregion
	


}

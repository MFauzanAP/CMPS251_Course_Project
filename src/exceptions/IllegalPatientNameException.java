package exceptions;

/**
 * This is an exception that occurs when a patient is given an invalid name.
 * 
 * <p> Conditions for an incorrect value are:
 * 
 * <p> <ul>
 * 		<li> the given value is empty
 * 		<li> the given value is outside the range of 3 to 255 characters
 * 		<li> the given value contains numbers
 * </ul>
 * 
 * <p> <i>Created on 18/05/2023 by Muhammad Putra</i>
 * 
 * @author		Muhammad Putra
 * @version		1.10
 * @since		1.10
 */
public class IllegalPatientNameException extends RuntimeException {



/* ------------------------------- Constructor ------------------------------ */
//region

	/**
     * Constructs an {@code IllegalPatientNameException} with the specified error message.
     *
     * @param   errorMessage		the error message.
     */
	public IllegalPatientNameException(String errorMessage) {
		super(errorMessage);
	}

//endregion
	


}

package cmps251.models;

import cmps251.exceptions.IllegalPatientIdException;
import cmps251.exceptions.IllegalPatientNameException;

/**
 * This class serves as the main class for Patient objects in the Sehha hospital reception system
 * 
 * <p> It represents a patient that can be registered in the management system.
 * A patient is someone that can book services in desired slots, and has a unique id.
 * 
 * <p> <i>Created on 16/05/2023 by Ahmad Chowdhury</i>
 * 
 * @author		Ahmad Chowdhury
 * @version		1.13
 * @since		1.9
 */
public class Patient extends Identifiable {



/* ---------------------------------- Enums --------------------------------- */
//region

	public enum ResidencyType {
		VISITOR,
		RESIDENT
	}

//endregion



/* --------------------------- Private Attributes --------------------------- */
//region 

	private String name;
	private ResidencyType residency;

//endregion



/* ------------------------------ Constructors ------------------------------ */
//region

	/**
	 * This constructor takes in an id (either QID or Visa number), name, and a residency type.
	 * This is meant to be the default constructor to be used
	 *
	 * @param id	   								- the QID or Visa number of the patient
	 * @param name 		   	   						- the patient's name
	 * @param residency	   							- the patient's residency type
	 * 
	 * @throws IllegalServiceMaxSlotsException		if given max slots is negative
	 * @throws IllegalServiceMaxSlotsException		if given max slots exceeds the maximum number of slots per day of the hospital
	 * @throws IllegalServicePriceException			if given price per slot is negative
	 */
	public Patient(String id, String name, ResidencyType residency) {

		//	Set the other properties
		this.setName(name);
		this.setResidency(residency);

		//	Assign id instead of generating it randomly
		this.setId(id);
		
	}

//endregion



/* --------------------------- Getters and Setters -------------------------- */
//region

	/**
	 * Sets the patient's ID using either their QID if they are a resident or their Visa number if they are visitors
	 * 
	 * <p> <b>NOTE</b>: this method should only be called from within the constructor.
	 * Usage outside may break certain features!
	 * 
	 * @param id					- new ID of the patient
	 */
	@Override
	public void setId(String id) {

		//	Validate ID
		if (isValidId(this, id, true) != "") return;

		//	If value is legal, set the new value
		this.id = id;

	}

	/**
	 * Returns the name of the patient
	 * 
	 * @return String				- the patient's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the patient's name
	 * 
	 * <p> <b>NOTE</b>: this method should never be called directly!
	 * It should be called only inside it's repository class or constructor 
	 * 
	 * @param name					- new name of the patient
	 */
	public void setName(String name) {

		//	Validate name
		if (isValidName(name, true) != "") return;

		//	If value is legal, set the new value
		this.name = name;

	}

	/**
	 * Returns the residency type of the patient
	 * 
	 * @return ResidencyType		- the patient's residency type
	 */
	public ResidencyType getResidency() {
		return residency;
	}

	/**
	 * Sets the patient's residency type
	 * 
	 * <p> <b>NOTE</b>: this method should never be called directly!
	 * It should be called only inside it's repository class or constructor 
	 * 
	 * @param residency				- new residency type of the patient
	 */
	public void setResidency(ResidencyType residency) {
		this.residency = residency;
	}
	
//endregion



/* ----------------------------- Utility Methods ---------------------------- */
//region

	/** 
	 * Checks if the given ID is valid
	 * 
	 * @param id								- the value to check the validity for
	 * @param throwError						- should we throw an error here?
	 * 
	 * @return String							- the error message
	 * 
	 * @throws IllegalPatientIdException		the given value is empty
	 * @throws IllegalPatientIdException		the given value contains letters
	 * @throws IllegalPatientIdException		the given value is not a valid QID if patient is a resident (11 digits)
	 * @throws IllegalPatientIdException		the given value is not a valid Visa number if patient is a visitor (12 digits)
	 */
	public static String isValidId(Patient patient, String id, boolean throwError) {

		//	If the given value is empty
		if (id.isBlank()) {
			String errorMessage = "Patient ID cannot be empty!";
			if (throwError) throw new IllegalPatientIdException(errorMessage);
			return errorMessage;
		}

		//	If the given value contains letters
		if (!id.matches("\\d+")) {
			String errorMessage = "Patient ID should only contain numbers!";
			if (throwError) throw new IllegalPatientIdException(errorMessage);
			return errorMessage;
		}

		//	If the given value is not a valid QID if patient is a resident (11 digits)
		if (patient != null && patient.residency == ResidencyType.RESIDENT && id.length() != 11) {
			String errorMessage = "Patient ID is not a valid QID, it should only have 11 digits!";
			if (throwError) throw new IllegalPatientIdException(errorMessage);
			return errorMessage;
		}

		//	If the given value is not a valid Visa number if patient is a visitor (12 digits)
		if (patient != null && patient.residency == ResidencyType.VISITOR && id.length() != 12) {
			String errorMessage = "Patient ID is not a valid Visa number, it should only have 12 digits!";
			if (throwError) throw new IllegalPatientIdException(errorMessage);
			return errorMessage;
		}

		//	Else return nothing since the given value is valid
		return "";

	}

	/** 
	 * Checks if the given name is valid
	 * 
	 * @param name								- the value to check the validity for
	 * @param throwError						- should we throw an error here?
	 * 
	 * @return String							- the error message
	 * 
	 * @throws IllegalPatientNameException		the given value is empty
	 * @throws IllegalPatientNameException		the given value is outside the range of 3 to 255 characters
	 * @throws IllegalPatientNameException		the given value contains numbers
	 */
	public static String isValidName(String name, boolean throwError) {

		//	If the given value is empty
		if (name.isBlank()) {
			String errorMessage = "Patient name cannot be empty!";
			if (throwError) throw new IllegalPatientNameException(errorMessage);
			return errorMessage;
		}

		//	If the given value is outside the range of 3 to 255 characters
		if (name.length() < 3 || name.length() > 255) {
			String errorMessage = "Patient name is either too short or too long, please keep it between 3 and 255 characters long!";
			if (throwError) throw new IllegalPatientNameException(errorMessage);
			return errorMessage;
		}

		//	If the given value contains numbers
		if (name.matches(".*[0-9].*")) {
			String errorMessage = "Patient name cannot contain numbers!";
			if (throwError) throw new IllegalPatientNameException(errorMessage);
			return errorMessage;
		}

		//	Else return nothing since the given value is valid
		return "";

	}

	/** 
	 * Returns this object as a string representation
	 * 
	 * <p> This method formats the properties in the following way:
	 * <p> {@code ID: <residency>, Name: <name>, Residency Status: <residency>
	 * 
	 * @return String		- the string representation of the object
	 */	
	@Override
	public String toString() {

		//	Concatenate this object's properties
		String idLabel = residency == ResidencyType.RESIDENT ? "QID" : "Visa Number";
		String residencyText = residency == ResidencyType.RESIDENT ? "Resident" : "Visitor";
		return String.format(
			"%s: %s, Name: %s, Residency Status: %s %n",
			idLabel, id, name, residencyText
		);

	}

	/** 
	 * Compares this object to another patient object and returns true if they are equal
	 * 
	 * @param obj			- the object to compare with
	 * 
	 * @return boolean			- are these two objects equal?
	 */	
	@Override
	public boolean equals(Object obj) {

		//	Check for general object equality
		if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;

		//	Cast parameter as a patient
		Patient patient = (Patient) obj; 

		//	Check the equality of each property
		if (!this.id.equals(patient.id)) return false;
		if (!this.name.equals(patient.name)) return false;
		if (this.residency != patient.residency) return false;

		//	Else return true since they have the same property values
		return true;
		
	}

//endregion



}

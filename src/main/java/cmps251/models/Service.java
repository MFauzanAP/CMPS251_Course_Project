package cmps251.models;

import java.util.Comparator;

import cmps251.exceptions.IllegalServiceMaxSlotsException;
import cmps251.exceptions.IllegalServicePriceException;

/**
 * This class serves as the main class for Service objects in the Sehha hospital reception system
 * 
 * <p> It represents different services offered by the hospital that can be purchased by patients.
 * Each service has a title, price, description, and a maximum number of slots per day
 * 
 * <p> <i>Created on 16/05/2023 by Grafael Karilwurara</i>
 * 
 * @author		Grafael Karilwurara
 * @version		1.16
 * @since		1.5
 */
public class Service extends Identifiable implements Comparable<Service> {



/* --------------------------- Private Attributes --------------------------- */
//region

	private String title;
	private int maxSlots;
	private double pricePerSlot;

//endregion



/* ------------------------------ Constructors ------------------------------ */
//region

	/**
	 * This constructor takes in a title, maximum number of slots, and a price per slot.
	 * This is meant to be the default constructor to be used
	 *
	 * @param title	   								- the title of the service
	 * @param maxSlots    	   						- the maximum slots per day for the service
	 * @param pricePerSlot	   						- the price per slot of the service
	 * 
	 * @throws IllegalServiceMaxSlotsException		if given max slots is negative
	 * @throws IllegalServiceMaxSlotsException		if given max slots exceeds the maximum number of slots per day of the hospital
	 * @throws IllegalServicePriceException			if given price per slot is negative
	 */
	public Service(String title, int maxSlots, double pricePerSlot) {

		//	Set properties
		this.setTitle(title);
		this.setMaxSlots(maxSlots);
		this.setPricePerSlot(pricePerSlot);

		//	Generate a random id for this slot
		this.setId(generateId());

	}

//endregion
 


/* --------------------------- Getters and Setters -------------------------- */
//region

	/**
	 * Returns the title of the service
	 * 
	 * @return String		- the service's title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of this service
	 * 
	 * <p> <b>NOTE</b>: this method should never be called directly!
	 * It should be called only inside it's repository class or constructor 
	 * 
	 * @param title				- new title of the service
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the number of maximum slots per day of the service
	 * 
	 * @return int				- the service's max number of slots per day
	 */
	public int getMaxSlots() {
		return maxSlots;
	}

	/**
	 * Sets the maximum number of slots per day for this service
	 * 
	 * <p> <b>NOTE</b>: this method should never be called directly!
	 * It should be called only inside it's repository class or constructor 
	 * 
	 * @param maxSlots			- new max slots
	 */
	public void setMaxSlots(int maxSlots) {

		//	Validate max slots
		if (isValidMaxSlots(maxSlots, true) != "") return;

		//	If value is legal, set the new value
		this.maxSlots = maxSlots;

	}

	/**
	 * Returns the price of each slot of the service
	 * 
	 * @return double		- the service's price per slot
	 */
	public double getPricePerSlot() {
		return pricePerSlot;
	}

	/**
	 * Sets the price of each slot for this service
	 * 
	 * <p> <b>NOTE</b>: this method should never be called directly!
	 * It should be called only inside it's repository class or constructor 
	 * 
	 * @param pricePerSlot			- new price per slot
	 */
	public void setPricePerSlot(double pricePerSlot) {

		//	Validate price per slot
		if (isValidPricePerSlot(pricePerSlot, true) != "") return;

		//	If value is legal, set the new value
		this.pricePerSlot = pricePerSlot;
	
	}
	
//endregion



/* ----------------------------- Utility Methods ---------------------------- */
//region

	/** 
	 * Checks if the given max slots is valid for the given service
	 * 
	 * @param maxSlots								- the value to check the validity for
	 * @param throwError							- should we throw an error here?
	 * 
	 * @return String								- the error message
	 * 
	 * @throws IllegalServiceMaxSlotsException		if given value is negative
	 * @throws IllegalServiceMaxSlotsException		if given value exceeds the maximum number of slots per day of the hospital
	 */
	public static String isValidMaxSlots(int maxSlots, boolean throwError) {

		//	If the given value is negative
		if (maxSlots < 0) {
			String errorMessage = "Maximum number of slots cannot be negative!";
			if (throwError) throw new IllegalServiceMaxSlotsException(errorMessage);
			return errorMessage;
		}

		//	If the given value exceeds the hospital's maximum number of slots per day
		if (maxSlots > Slot.MAX_SLOTS_PER_DAY) {
			String errorMessage = "Maximum number of slots cannot be above the hospital's limit!";
			if (throwError) throw new IllegalServiceMaxSlotsException(errorMessage);
			return errorMessage;
		}

		//	Else return nothing since the given value is valid
		return "";

	}

	/** 
	 * Checks if the given price per slot is valid for the given service
	 * 
	 * @param pricePerSlot						- the value to check the validity for
	 * @param throwError						- should we throw an error here?
	 * 
	 * @return String							- the error message
	 * 
	 * @throws IllegalServicePriceException		if given value is negative
	 */
	public static String isValidPricePerSlot(double pricePerSlot, boolean throwError) {

		//	If the given value is negative
		if (pricePerSlot < 0) {
			String errorMessage = "Price per slot cannot be negative!";
			if (throwError) throw new IllegalServicePriceException(errorMessage);
			return errorMessage;
		}

		//	Else return nothing since the given value is valid
		return "";

	}
 
	/** 
	 * Generates an ID for this service
	 * 
	 * <p> This is calculated by doing the following:
	 * <ol>
	 * 		<li> Get the initial ID from {@code Identifiable}
	 * 		<li> Add title, max slots, and price per slot to this service ID
	 * </ol>
	 */	
	protected String generateId() {

		//	Get the super class id
		String idConcat = super.generateId();

		//	Fetch this object's properties and convert them into strings
		String title = this.getTitle();
		String maxSlots = String.valueOf(this.getMaxSlots());
		String pricePerSlot = String.valueOf(this.getPricePerSlot());

		//	Concatenate the properties together
		idConcat += String.format("T%sMS%sPPS%s", title, maxSlots, pricePerSlot);

		//	Return the new ID
		return idConcat;

	}

	/** 
	 * Returns this object as a string representation
	 * 
	 * <p> This method formats the properties in the following way:
	 * <p> {@code ID: <id>, Title: <title>, Maximum Slots: <maxSlots>, Price per Slot: <pricePerSlot>
	 * 
	 * @return String		- the string representation of the object
	 */	
	@Override
	public String toString() {

		//	Get id string from super class
		String idString = super.toString();

		//	Concatenate with this object's properties
		return String.format(
			"%s, Title: %s, Maximum Slots: %d, Price per Slot: QAR %.2f %n",
			idString, title, maxSlots, pricePerSlot
		);

	}

	/** 
	 * Compares this object to another service object and returns true if they are equal
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

		//	Cast parameter as a service
		Service service = (Service) obj; 

		//	Check the equality of each property
		if (!this.title.equals(service.title)) return false;
		if (this.maxSlots != service.maxSlots) return false;
		if (this.pricePerSlot != service.pricePerSlot) return false;

		//	Else return true since they have the same property values
		return true;
		
	}

	/** 
	 * Generate a unique integer representation of this object to support usage in a hashmap
	 * 
	 * <p> This method formats the properties in the following way:
	 * <p> {@code ID: <id>, Title: <title>, Maximum Slots: <maxSlots>, Price per Slot: <pricePerSlot>
	 */	
	@Override
	public int hashCode() {

		//	Get the hashcode for the title and concatenate the property values
		int titleHash = this.title.hashCode();
		int classHash = titleHash + this.maxSlots + (int)this.pricePerSlot;

		//	Return the class hash value
		return classHash;

	}

	/** 
	 * Compares a service object to another
	 * 
	 * @return int		- negative, zero, or positive if this service is less than, equal to, or greater than the given service
	 */	
	@Override
	public int compareTo(Service service) {
		return Comparator
			.comparing((Service s) -> s.title)
			.thenComparing(s -> s.pricePerSlot)
			.compare(this, service);
	}

//endregion



 }

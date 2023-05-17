package models;

public class Service {
	private String serviceTitle;
	private int serviceId;
	private int maxSlots;
	private int pricePerSlot;
 
	/**
	 * 
	 *
	 * @param serviceTitle	   				-the title of the service
	 * @param serviceId   	   				-the ID of the service
	 * @param maxSlots    	   				-the maximum slots per day for the service
	 * @param pricePerSlot	   				-the price per slot of the service
	 * 
	 * @throws IllegalArgumentException 		-if the maxSlots or pricePerSlot is negative
	 */
	public Service(String serviceTitle, int serviceId, int maxSlots, int pricePerSlot) {
		validateMaxSlots(maxSlots);
		validatePricePerSlot(pricePerSlot);
		this.serviceTitle = serviceTitle;
		this.serviceId = serviceId;
		this.maxSlots = maxSlots;
		this.pricePerSlot = pricePerSlot;
	}
 
	// Getters and setters
 
	public String getServiceTitle() {
		return serviceTitle;
	}
 
	public void setServiceTitle(String serviceTitle) {
		this.serviceTitle = serviceTitle;
	}
 
	public int getServiceId() {
		return serviceId;
	}
 
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
 
	public int getMaxSlots() {
		return maxSlots;
	}
 
	public void setMaxSlots(int maxSlots) {
		validateMaxSlots(maxSlots);
		this.maxSlots = maxSlots;
	}
 
	public int getPricePerSlot() {
		return pricePerSlot;
	}
 
	public void setPricePerSlot(int pricePerSlot) {
		validatePricePerSlot(pricePerSlot);
		this.pricePerSlot = pricePerSlot;
	}
	
	
	private void validateMaxSlots(int maxSlots) {
		if (maxSlots < 0) {
			throw new IllegalArgumentException("Max slots cannot be negative");
		}
	}
 
	
	private void validatePricePerSlot(int pricePerSlot) {
		if (pricePerSlot < 0) {
			throw new IllegalArgumentException("Price per slot cannot be negative");
		}
	}
 
	/**
	 * Returns a string representation of the service.
	 *
	 * @return a string representation of the service
	 */
	@Override
	public String toString() {
		return "Service ID: " + serviceId +
				"\nService Title: " + serviceTitle +
				"\nMax Slots: " + maxSlots +
				"\nPrice Per Slot: " + pricePerSlot;
	}
 }

package models;

public class Patient extends Slot {
	private int qid;
	private String name;
	private ResidencyType residency;
	public Patient(int qid, String name, ResidencyType residency) {
		super();
		this.qid = qid;
		this.name = name;
		this.residency = residency;
	}
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ResidencyType getResidency() {
		return residency;
	}
	public void setResidency(ResidencyType residency) {
		this.residency = residency;
	}
	
	
	

}

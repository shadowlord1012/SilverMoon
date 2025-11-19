package application;

import com.google.gson.annotations.SerializedName;

public class StatusEffects {
	
	@SerializedName("Name")
	private String name;
	
	@SerializedName("Target")
	private String targetStatus;
	
	@SerializedName("amount")
	private int amount;
	
	@SerializedName("Duration")
	private long duration;
	
	public String getName() {return name;}
	public int getAmount() {return amount;}
	public long getDuration() {return duration;}
	public String getTarget() {return targetStatus;}
	
	public void setName(String value) {name = value;}
	public void setTargetStatus(String value) {targetStatus = value;}
	
	public void setAmount(int value) {
		if (value < 0)
			amount = 0;
		else
			amount = value;}
	public void setDuration(long value) { 
		if(value <0)
			duration = 0;
		else 
			duration = value;
	}
	public void setEffect(String name, String target, int amount, long duration) {
		this.name = name;
		this.targetStatus = target;
		this.amount = amount;
		this.duration = duration;
	}
}

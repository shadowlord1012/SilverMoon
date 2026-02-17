package gameEngine;

import com.google.gson.annotations.SerializedName;

public class Player extends Entity{
	
	
	@SerializedName("Seconds")
	private byte seconds;
	
	@SerializedName("Minutes")
	private byte minutes;
	
	@SerializedName("Hours")
	private int hours;
	
	private transient HeadsUpDisplay hud;
	
	//current Ability is use
	private transient Ability currentAbility;
	
	public HeadsUpDisplay getHUD() { return hud;}
	
	public transient StatusScreenController statusScreenController;
	
	public void setHUD(HeadsUpDisplay value) { hud = value;}
	public void setCurrentAbility(Ability value) { currentAbility = value;}
	public Ability getCurrentAbility() { return currentAbility;}
	public StatusScreenController getStatusScreenController() { return statusScreenController;}
	public HeadsUpDisplay getHeadUpDisplay() { return hud;}
	
	public Player() {
		super();
		this.SetRow(6);
		statusScreenController = new StatusScreenController(this);
		String [] abilityNames = {"FireballL1","Ice Shard","Heal"};
		this.setAbilityNames(abilityNames);
	}
	
	
	@Override
	public void Update(World w) {
		super.Update(w);
	}
	
	@Override
	public void Draw(javafx.scene.canvas.GraphicsContext gc) {
		super.Draw(gc);
		
		//Draws the Status Screen Controller
		statusScreenController.Draw(gc);
	}


	/**
	 * @return the seconds
	 */
	public byte getSeconds() {
		return seconds;
	}


	/**
	 * @param seconds the seconds to set
	 */
	public void setSeconds(byte seconds) {
		this.seconds = seconds;
	}


	/**
	 * @return the minutes
	 */
	public byte getMinutes() {
		return minutes;
	}


	/**
	 * @param minutes the minutes to set
	 */
	public void setMinutes(byte minutes) {
		this.minutes = minutes;
	}


	/**
	 * @return the hours
	 */
	public int getHours() {
		return hours;
	}


	/**
	 * @param hours the hours to set
	 */
	public void setHours(int hours) {
		this.hours = hours;
	}
}
;
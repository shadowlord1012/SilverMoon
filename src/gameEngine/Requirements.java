package gameEngine;

import com.google.gson.annotations.SerializedName;

public class Requirements {

	@SerializedName("level")
	private int level;
	
	@SerializedName("magicPower")
	private int magicPower;
	
	@SerializedName("Strength")
	private int strength;
	
	@SerializedName("physicalDefence")
	private int physicalDefence;
	
	@SerializedName("magicalDefence")
	private int magicalDefence;
	
	@SerializedName("elementalAlignment")
	private String elementalAlignment;
	
	
	/**
	 * Constructor for Requirements
	 * @param level
	 * @param magicPower
	 * @param strength
	 * @param physicalDefense
	 * @param magicalDefence
	 * @param elementalAlignment
	 */
	public Requirements(int level, int magicPower, int strength, int physicalDefense, int magicalDefence, String elementalAlignment) {
		this.setLevel(level);
		this.setMagicPower(magicPower);
		this.setStrength(strength);
		this.setPhysicalDefense(physicalDefense);
		this.setMagicalDefence(magicalDefence);
		this.setElementalAlignment(elementalAlignment);
	}

	
	//setters and getters
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMagicPower() {
		return magicPower;
	}

	public void setMagicPower(int magicPower) {
		this.magicPower = magicPower;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getPhysicalDefense() {
		return physicalDefence;
	}

	public void setPhysicalDefense(int physicalDefense) {
		this.physicalDefence = physicalDefense;
	}

	public int getMagicalDefence() {
		return magicalDefence;
	}

	public void setMagicalDefence(int magicalDefence) {
		this.magicalDefence = magicalDefence;
	}

	public String getElementalAlignment() {
		return elementalAlignment;
	}

	public void setElementalAlignment(String elementalAlignment) {
		this.elementalAlignment = elementalAlignment;
	}
	
}

package gameEngine;

import com.google.gson.annotations.SerializedName;

public class Item {
	
	@SerializedName("Name")
	private String name;  // Name of the item
	
	@SerializedName("Quantity")
	private int quantity;  // Quantity of the item
	
	public String getName() {return name;}
	public int getQuantity() {return quantity;}
	
	public Item() {
	}
	
	public void Initialize(String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
	}
	
}

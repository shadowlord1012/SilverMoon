package gameEngine;

import java.io.File;

import javax.imageio.ImageIO;

import com.google.gson.annotations.SerializedName;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Item {
	
	@SerializedName("Name")
	private String name;  // Name of the item
	
	@SerializedName("Quantity")
	private int quantity;  // Quantity of the item
	
	@SerializedName("Effects")
	private StatusEffects statusEffect;
	
	private transient Image img;
	
	public String getName() {return name;}
	public int getQuantity() {return quantity;}
	public StatusEffects getEffects() {return statusEffect;}
	
	public void setName(String value) {name = value;}
	public void setQuantity(int value) {quantity = value;}
	public void setEffects(String name, String target, int amount, long duration) {
		statusEffect.setEffect(name,target,amount,duration);
	}
	
	public Item() {
	}
	
	public void Initialize(String name, int quantity, StatusEffects targetEffect) {
		this.name = name;
		this.quantity = quantity;
		this.statusEffect = targetEffect;
	}

	public void LoadImage() {

		File file = new File("Resources/Images/Items/"+name+".png");
		
		try {
			img = SwingFXUtils.toFXImage(ImageIO.read(file),null);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

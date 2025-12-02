package gameEngine;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.google.gson.annotations.SerializedName;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Item {

	@SerializedName("Name")
	private String name;  // Name of the item
	
	@SerializedName("Quantity")
	private int quantity;  // Quantity of the item
	
	@SerializedName("Effects")
	private StatusEffects statusEffect;
	
	@SerializedName("requirements")
	private Requirements requirements;
	
	@SerializedName("Type")
	private String type;
	
	private transient Image img;
	private transient Vector2 position;
	
	public String getName() {return name;}
	public int getQuantity() {return quantity;}
	public StatusEffects getEffects() {return statusEffect;}
	public Requirements getRequirements() { return requirements;}
	public Image getImage() {return img;}
	public String getType() {return type;}
	
	public void setName(String value) {name = value;}
	public void setQuantity(int value) {quantity = value;}
	public void addQuantity(int value) {quantity += value;}
	public void setEffects(String name, String target, int amount, long duration) {
		statusEffect.setEffect(name,target,amount,duration);
	}
	public void setType(String value) {type = value;}
	
	public Item() {
	}

	public void LoadImage() {
		
		try {
			img = SwingFXUtils.toFXImage((BufferedImage)ImageIO.read(new File("Resources/Images/Items/"+name+".png")),null);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean useItem(Entity entity) {
		
		if(!type.equals("Consumable")) {
			System.out.println("Item is not consumable");
			return false;
		}
		
		//Checks if there is any quantity left
		if(quantity <= 0) {
			return false;
		}
		
		//Checks if the item can be used on the entity
		if(statusEffect.getTarget().equals("Health")) {
			if(entity.getStatusByName("healthcurrent") >= entity.getStatusByName("Health")) {
				return false;
			}
		}
		
		if(statusEffect.getTarget().equals("Magic")) {
			if(entity.getStatusByName("magiccurrent") >= entity.getStatusByName("Magic")) {
				return false;
			}
		}
		
		//Applies the status effect to the player
		statusEffect.ApplyEffect(entity);
		
		//Reduces the quantity by 1
		quantity -= 1;
		
		return true;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public void DrawItem(GraphicsContext gc) {
		gc.drawImage(img, position.X, position.Y);
	}
}

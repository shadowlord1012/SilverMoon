package gameEngine;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class InventoryScreen extends StatusScreen {

	private BufferedImage inventoryBackground;
	private int counter;
	
	public InventoryScreen(Entity entityRef) {
		super(entityRef);
		
		//Sets the offsets for the Equipment Screen
		super.setWidthOffset(40);
		super.setHeightOffset(40);
		//Sets the Background Image
		try {
			inventoryBackground = ImageIO.read(new File("Resources/Images/UI/InventoryScreen.png"));
		}catch(Exception e) {e.printStackTrace();}
		
		//Sets all the values for the Inventory Screen
		super.setBackgroundImage(SwingFXUtils.toFXImage(inventoryBackground,null));
		super.setMaxSelected(new Vector2(10,5));
		super.setItemSlots(new Item[(int) super.getMaxSelected().X][(int) super.getMaxSelected().Y]);
	}
	
	public void AddItemToInventory(Item item) {
		boolean added = false;
		
		//Go though the whole inventory and make sure there is not an item already there
		for(int x = 0; x < super.getMaxSelected().X; x++) {
				for(int y = 0; y < super.getMaxSelected().Y; y++) {
					
					//If there is an item in the slot
					if(super.getItemSlots()[x][y] != null && !added) {
						
						//Check if the item names match
						if(super.getItemSlots()[x][y].getName().toLowerCase().equals(item.getName().toLowerCase()) &&
								super.getItemSlots()[x][y].getType().equals("Consumable")) {
							
							//If they do, add the quantity to the existing item
							super.getItemSlots()[x][y].addQuantity(item.getQuantity());
					
							//Makes sure not to add it again in a different Slot
							added = true;
							x= (int) super.getMaxSelected().X; //Breaks the outer loop
							y= (int) super.getMaxSelected().Y; //Breaks the inner loop
							return;
						}
						else if(super.getItemSlots()[x][y].getName().toLowerCase().equals(item.getName().toLowerCase())) {
							
							//If they do, add the quantity to the existing item
							super.getItemSlots()[x][y].setQuantity(item.getQuantity());
					
							//Makes sure not to add it again in a different Slot
							added = true;
							x= (int) super.getMaxSelected().X; //Breaks the outer loop
							y= (int) super.getMaxSelected().Y; //Breaks the inner loop
							return;
						}
					}
				}
		}
		
		//If it remains un-added, then add it to an empty slot
		if(!added) {
			for(int x = 0; x < super.getMaxSelected().X; x++) {
				for(int y = 0; y < super.getMaxSelected().Y; y++) {
					
					//Finds the first empty slot and adds the item there
					if(super.getItemSlots()[x][y] == null) {
						super.setItemAtSetSlot(item, new Vector2(x,y));
						added = true;
						x= (int) super.getMaxSelected().X; //Breaks the outer loop
						y= (int) super.getMaxSelected().Y; //Breaks the inner loop
						return;
					}
				}
			}
		}
		
		//TODO: Add in a message that the inventory is full and the item could not be added
	}
	
	@Override
	public void Update() {
		super.Update();
	}
	
	@Override
	public void Draw(GraphicsContext gc) {
		super.Draw(gc);
		for(int x = 0; x < super.getMaxSelected().X; x++) {
			for(int y = 0; y < super.getMaxSelected().Y; y++) {
				//If there is an item in the slot, draw it
				if(super.getItemSlots()[x][y] != null) {
					
					//draw the item image
					gc.drawImage(super.getItemSlots()[x][y].getImage(),super.getBackgroundPosition().X+30+(x*40),
							super.getBackgroundPosition().Y+60+(y*40),30,30);
					
					//gets the default font
					Font defaultFont = Font.getDefault();
					
					//sets the font to Adventure Request
					gc.setFont(Global.ADVENTURE_REQUEST);
					
					//set item name on screen
					gc.setFill(Color.WHITE);
					
					
					if(super.getItemSlots()[x][y].getType().equals("Consumable")) 
						//draw the quantity
						gc.fillText(String.valueOf(super.getItemSlots()[x][y].getQuantity()),
							super.getBackgroundPosition().X+60+(x*40),
							super.getBackgroundPosition().Y+100+(y*40));
					
					gc.setFill(Color.BLACK);
					gc.setFont(defaultFont);
				}
			}
		}
		
	}
}

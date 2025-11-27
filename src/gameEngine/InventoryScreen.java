package gameEngine;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;

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
		super.setMaxSelected(new Vector2(9,4));
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
					if(super.getItemSlots()[x][y].getName().toLowerCase().equals(item.getName().toLowerCase())) {
						
						//If they do, add the quantity to the existing item
						super.getItemSlots()[x][y].addQuantity(item.getQuantity());
						
						//Makes sure not to add it again in a different Slot
						added = true;
						return;
					}
				}
			}
		}
		
		//If it remains un-added, then add it to an empty slot
		if(!added) {
			for(int x = 0; x < super.getMaxSelected().X; x++) {
				for(int y = 0; y < super.getMaxSelected().Y; y++) {
					
					//Finds the first emtpy slot and adds the item there
					if(super.getItemSlots()[x][y] == null) {
						super.setItemAtSetSlot(item, new Vector2(x,y));
						added = true;
						System.out.println(String.format("Item: %s added to Inventory at: %d , %d", item.getName(),x,y));
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
					gc.drawImage(super.getItemSlots()[x][y].getImage(),super.getBackgroundPosition().X+30+(x*40),
							super.getBackgroundPosition().Y+60+(y*40),30,30);
				}
			}
		}
		
	}
}

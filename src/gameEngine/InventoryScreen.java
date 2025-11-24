package gameEngine;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;

public class InventoryScreen extends StatusScreen {

	private BufferedImage inventoryBackground;
	private int counter;
	
	public InventoryScreen(Entity entityRef) {
		super(entityRef);
		super.setBackgroundImage(SwingFXUtils.toFXImage(inventoryBackground,null));
		super.setMaxSelected(new Vector2(6,6));
		super.setItemSlots(new Item[(int) super.getMaxSelected().X][(int) super.getMaxSelected().Y]);
	}
	
	public void AddItemToInventory(Item item) {
		boolean added = false;
		
		//Go though the whole inventory and make sure there is not an item already there
		for(int x = 0; x < super.getMaxSelected().X; x++) {
			for(int y = 0; y < super.getMaxSelected().Y; y++) {
				if(super.getItemSlots()[x][y] != null) {
					if(super.getItemSlots()[x][y].getName().toLowerCase().equals(item.getName().toLowerCase())) {
						super.getItemSlots()[x][y].addQuantity(item.getQuantity());
						added = true;
						return;
					}
				}
			}
		}
		
		//If it remains unadded, then add it to an empty slot
		if(!added) {
			for(int x = 0; x < super.getMaxSelected().X; x++) {
				for(int y = 0; y < super.getMaxSelected().Y; y++) {
					if(super.getItemSlots()[x][y] == null) {
						super.setItemAtSetSlot(item, new Vector2(x,y));
						return;
					}
				}
			}
		}
	}
	
	@Override
	public void Update() {
		super.Update();
		
		if(super.isOpen()) {
			
			counter++;
			if(counter >= 50)
				counter = 50;
			
			if(KeyHandlerController.ScreenMovement[0] && counter == 50) {
				//TODO: Add in the ref for the Quest Screen and implement OnChange
				counter = 0;
			}
			
			if(KeyHandlerController.ScreenMovement[1] && counter == 50) {
				//TODO: add in the ref for the Equipment Screen and implement OnCHange
				counter = 0;
			}
		}
	}
	
	@Override
	public void Draw(GraphicsContext gc) {
		super.Draw(gc);
	}
}

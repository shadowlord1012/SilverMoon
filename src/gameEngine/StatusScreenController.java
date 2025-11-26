package gameEngine;

import javafx.scene.canvas.GraphicsContext;

public class StatusScreenController {

	private InventoryScreen inventoryScreen;
	private EquipmentScreen equipmentScreen;
	private QuestScreen questScreen;
	private int lastKnownScreen = 0; // 0 = Inventory, 1 = Equipment, 2 = Quest
	private int counter = 0;
	private boolean open = false;
	
	public StatusScreenController(Player entity) {
		inventoryScreen = new InventoryScreen(entity);
		equipmentScreen = new EquipmentScreen(entity);
		questScreen = new QuestScreen(entity);
	}
	
	private void changeScreen() {
		switch(lastKnownScreen) {
		case 0:
			//Opens the inventory Screen
			inventoryScreen.setOpen(true);
			
			//makes sure the other screens are closed
			equipmentScreen.setOpen(false);
			questScreen.setOpen(false);
			break;
		case 1:			
			//opens the equipment screen
			equipmentScreen.setOpen(true);
			
			//makes sure the other screens are closed
			inventoryScreen.setOpen(false);
			questScreen.setOpen(false);
			break;
		case 2:
			//opens the quest screen
			questScreen.setOpen(true);
			
			//makes sure the other screens are closed
			inventoryScreen.setOpen(false);
			equipmentScreen.setOpen(false);
			break;
		default:
			break;
		}
	}
	
	public void Update() {
		
		//Simple counter to add delay to screen switching
		counter++;
		if(counter >=50)
			counter = 50;

		if(inventoryScreen.isOpen() || equipmentScreen.isOpen() || questScreen.isOpen()) {
			if(KeyHandlerController.ScreenMovement[0] && counter == 50) {
				//Moves to the previous screen
				lastKnownScreen--;
				
				//Make sure it doesn't go out of bounds
				if(lastKnownScreen < 0)
					lastKnownScreen = 2;
				
				//resets the counter
				counter = 0;
				
				//Changes the screen
				changeScreen();
			}
			
			if(KeyHandlerController.ScreenMovement[1] && counter == 50) {
				//Moves to the next screen
				lastKnownScreen++;
				
				//Make sure it doesn't go out of bounds
				if(lastKnownScreen > 2)
					lastKnownScreen = 0;
				
				//resets the counter
				counter = 0;
				
				//Changes the screen
				changeScreen();
			}
		}
		
		
		if(KeyHandlerController.OpenStatusScreens && counter == 50 && isOpen())
		{
			//Closes all screens
			inventoryScreen.setOpen(false);
			equipmentScreen.setOpen(false);
			questScreen.setOpen(false);
			setOpen(false);
			counter = 0;
		} 
		else if(KeyHandlerController.OpenStatusScreens && counter == 50 && !isOpen()) {
			
			//Opens the last known screen
			changeScreen();
			
			//Resets the counter
			counter = 0;
			
			//Sets open to true
			setOpen(true);
		}
		
			
		
		if(inventoryScreen.isOpen() && !equipmentScreen.isOpen() && !questScreen.isOpen())
			inventoryScreen.Update();
		if(!inventoryScreen.isOpen() && equipmentScreen.isOpen() && !questScreen.isOpen())
			equipmentScreen.Update();
		if(!inventoryScreen.isOpen() && !equipmentScreen.isOpen() && questScreen.isOpen())
			questScreen.Update();
	}
	
	public void Draw(GraphicsContext gc) {
		if(inventoryScreen.isOpen() && !equipmentScreen.isOpen() && !questScreen.isOpen())
			inventoryScreen.Draw(gc);
		if(!inventoryScreen.isOpen() && equipmentScreen.isOpen() && !questScreen.isOpen())
			equipmentScreen.Draw(gc);
		if(!inventoryScreen.isOpen() && !equipmentScreen.isOpen() && questScreen.isOpen())
			questScreen.Draw(gc);
	}

	/**
	 * @return the open
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * @param open the open to set
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}
	
}

package gameEngine;

import javafx.scene.canvas.GraphicsContext;

public class StatusScreenController {

	private InventoryScreen inventoryScreen;
	private EquipmentScreen equipmentScreen;
	private QuestScreen questScreen;
	private int currentScreen = 0; // 0 = Inventory, 1 = Equipment, 2 = Quest
	private int lastKnownScreen = 0;
	private int counter = 0;
	private boolean open = false;
	
	public StatusScreenController(Player entity) {
		setInventoryScreen(new InventoryScreen(entity));
		equipmentScreen = new EquipmentScreen(entity);
		questScreen = new QuestScreen(entity);
	}
	
	private void changeScreen() {
		switch(currentScreen) {
		case 0:
			//Opens the inventory Screen
			getInventoryScreen().setOpen(true);
			
			//makes sure the other screens are closed
			equipmentScreen.setOpen(false);
			questScreen.setOpen(false);
			break;
		case 1:			
			//opens the equipment screen
			equipmentScreen.setOpen(true);
			
			//makes sure the other screens are closed
			getInventoryScreen().setOpen(false);
			questScreen.setOpen(false);
			break;
		case 2:
			//opens the quest screen
			questScreen.setOpen(true);
			
			//makes sure the other screens are closed
			getInventoryScreen().setOpen(false);
			equipmentScreen.setOpen(false);
			break;
		default:
			break;
		}
	}
	
	//TODO: Item is not carrying over correctly when switching screens
	//Need to fix that
	private void changeWithItemScreen() {
		switch(currentScreen) {
		case 0:
			switch(lastKnownScreen) {
			case 1:
				equipmentScreen.OnChange(inventoryScreen, equipmentScreen);
				break;
			case 2:
				questScreen.OnChange(inventoryScreen, questScreen);
				break;
			}
		case 1:
			switch(lastKnownScreen) {
			case 0:
				inventoryScreen.OnChange(equipmentScreen, inventoryScreen);
				break;
			case 2:
				questScreen.OnChange(equipmentScreen, questScreen);
				break;
			}
		case 2:
			switch(lastKnownScreen) {
			case 0:
				inventoryScreen.OnChange(questScreen, inventoryScreen);
				break;
			case 1:
				equipmentScreen.OnChange(questScreen, equipmentScreen);
				break;
			}
		}
	}
	
	public void Update() {
		
		//Simple counter to add delay to screen switching
		counter++;
		if(counter >=50)
			counter = 50;

		if(getInventoryScreen().isOpen() || equipmentScreen.isOpen() || questScreen.isOpen()) {
			
			lastKnownScreen = currentScreen;
			if(KeyHandlerController.ScreenMovement[0] && counter == 50) {
				//Moves to the previous screen
				currentScreen--;
				
				//Make sure it doesn't go out of bounds
				if(currentScreen < 0)
					currentScreen = 2;
				
				//resets the counter
				counter = 0;
				
				//Changes the screen
				changeScreen();
				changeWithItemScreen();
			}
			
			if(KeyHandlerController.ScreenMovement[1] && counter == 50) {
				//Moves to the next screen
				currentScreen++;
				
				//Make sure it doesn't go out of bounds
				if(currentScreen > 2)
					currentScreen = 0;
				
				//resets the counter
				counter = 0;
				
				//Changes the screen
				changeScreen();
				changeWithItemScreen();
			}
		}
		
		
		if(KeyHandlerController.OpenStatusScreens && counter == 50 && isOpen())
		{
			//Closes all screens
			getInventoryScreen().setOpen(false);
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
		
			
		
		if(getInventoryScreen().isOpen() && !equipmentScreen.isOpen() && !questScreen.isOpen())
			getInventoryScreen().Update();
		if(!getInventoryScreen().isOpen() && equipmentScreen.isOpen() && !questScreen.isOpen())
			equipmentScreen.Update();
		if(!getInventoryScreen().isOpen() && !equipmentScreen.isOpen() && questScreen.isOpen())
			questScreen.Update();
	}
	
	public void Draw(GraphicsContext gc) {
		if(getInventoryScreen().isOpen() && !equipmentScreen.isOpen() && !questScreen.isOpen())
			getInventoryScreen().Draw(gc);
		if(!getInventoryScreen().isOpen() && equipmentScreen.isOpen() && !questScreen.isOpen())
			equipmentScreen.Draw(gc);
		if(!getInventoryScreen().isOpen() && !equipmentScreen.isOpen() && questScreen.isOpen())
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

	/**
	 * @return the inventoryScreen
	 */
	public InventoryScreen getInventoryScreen() {
		return inventoryScreen;
	}

	/**
	 * @param inventoryScreen the inventoryScreen to set
	 */
	public void setInventoryScreen(InventoryScreen inventoryScreen) {
		this.inventoryScreen = inventoryScreen;
	}
	
}

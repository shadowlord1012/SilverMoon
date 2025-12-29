package gameEngine;

import javafx.scene.canvas.GraphicsContext;

//TODO: Add in ability screen to the rotation

public class StatusScreenController {

	private InventoryScreen inventoryScreen;
	private EquipmentScreen equipmentScreen;
	private QuestScreen questScreen;
	private int currentScreen = 0; // 0 = Inventory, 1 = Equipment, 2 = Quest
	private int lastKnownScreen = 0;
	private int[] counter = {0,20};
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
			getInventoryScreen().setScreenName("Inventory");
			//makes sure the other screens are closed
			equipmentScreen.setOpen(false);
			questScreen.setOpen(false);
			
		    if(lastKnownScreen == 1) {
		    	//Transfers selected item from equipment to inventory
		    	getInventoryScreen().OnChange(equipmentScreen, getInventoryScreen());
		    } else if(lastKnownScreen == 2) {
		    	//Transfers selected item from quest to inventory
		    	getInventoryScreen().OnChange(questScreen, getInventoryScreen());
		    }
			
			break;
		case 1:			
			//opens the equipment screen
			equipmentScreen.setOpen(true);
			equipmentScreen.setScreenName("Equipment");
			//makes sure the other screens are closed
			getInventoryScreen().setOpen(false);
			questScreen.setOpen(false);
			
			if(lastKnownScreen == 0) {
		    	//Transfers selected item from inventory to equipment
		    	equipmentScreen.OnChange(getInventoryScreen(), equipmentScreen);
		    } else if(lastKnownScreen == 2) {
		    	//Transfers selected item from quest to equipment
		    	equipmentScreen.OnChange(questScreen, equipmentScreen);
		    }
			break;
		case 2:
			//opens the quest screen
			questScreen.setOpen(true);
			questScreen.setScreenName("Quest");
			//makes sure the other screens are closed
			getInventoryScreen().setOpen(false);
			equipmentScreen.setOpen(false);
			
			if(lastKnownScreen == 0) {
		    	//Transfers selected item from inventory to quest
		    	questScreen.OnChange(getInventoryScreen(), questScreen);
		    } else if(lastKnownScreen == 1) {
		    	//Transfers selected item from equipment to quest
		    	questScreen.OnChange(equipmentScreen, questScreen);
		    }
			break;
		default:
			break;
		}
	}
	
	public void Update() {
		
		//Simple counter to add delay to screen switching
		counter[0]++;
		if(counter[0] >=counter[1])
			counter[0] = counter[1];

		if(getInventoryScreen().isOpen() || equipmentScreen.isOpen() || questScreen.isOpen()) {
			
			lastKnownScreen = currentScreen;
			if(KeyHandlerController.ScreenMovement[0] && counter[0] == counter[1]) {
				//Moves to the previous screen
				currentScreen--;
				
				//Make sure it doesn't go out of bounds
				if(currentScreen < 0)
					currentScreen = 2;
				
				//resets the counter
				counter[0] = 0;
				
				//Changes the screen
				changeScreen();
			}
			
			if(KeyHandlerController.ScreenMovement[1] && counter[0] == counter[1]) {
				//Moves to the next screen
				currentScreen++;
				
				//Make sure it doesn't go out of bounds
				if(currentScreen > 2)
					currentScreen = 0;
				
				//resets the counter
				counter[0] = 0;
				
				//Changes the screen
				changeScreen();
			}
		}
		
		
		if(KeyHandlerController.OpenStatusScreens && counter[0] == counter[1] && isOpen())
		{
			//Closes all screens
			getInventoryScreen().setOpen(false);
			equipmentScreen.setOpen(false);
			questScreen.setOpen(false);
			setOpen(false);
			counter[0] = 0;
		} 
		else if(KeyHandlerController.OpenStatusScreens && counter[0] == counter[1] && !isOpen()) {
			
			//Opens the last known screen
			changeScreen();
			
			//Resets the counter
			counter[0] = 0;
			
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

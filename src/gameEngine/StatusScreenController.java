package gameEngine;

import javafx.scene.canvas.GraphicsContext;


public class StatusScreenController {

	private InventoryScreen inventoryScreen;
	private EquipmentScreen equipmentScreen;
	private QuestScreen questScreen;
	private AbilityScreen abilityScreen;
	private int maxNumberOfScreens = 3; // Has to be one less then the total number of screens
	private int currentScreen = 0; // 0 = Inventory, 1 = Equipment, 2 = Quest, 3 = Ability
	private int lastKnownScreen = 0;
	private int[] counter = {0,20};
	private boolean open = false;
	
	public StatusScreenController(Player entity) {
		setInventoryScreen(new InventoryScreen(entity));
		equipmentScreen = new EquipmentScreen(entity);
		questScreen = new QuestScreen(entity);
		abilityScreen = new AbilityScreen(entity);
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
			abilityScreen.setOpen(false);
			
		    if(lastKnownScreen == 1) {
		    	//Transfers selected item from equipment to inventory
		    	getInventoryScreen().OnChange(equipmentScreen, getInventoryScreen());
		    } 
		    else if(lastKnownScreen == 3) {
		    	//Transfers selected item from ability to inventory
		    	getInventoryScreen().OnChange(abilityScreen, getInventoryScreen());
		    }
			
			break;
		case 1:			
			//opens the equipment screen
			equipmentScreen.setOpen(true);
			equipmentScreen.setScreenName("Equipment");
			//makes sure the other screens are closed
			getInventoryScreen().setOpen(false);
			questScreen.setOpen(false);
			abilityScreen.setOpen(false);
			
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
			abilityScreen.setOpen(false);
			
			if(lastKnownScreen == 3) {
		    	//Transfers selected item from ability to quest
		    	questScreen.OnChange(abilityScreen, questScreen);
		    } else if(lastKnownScreen == 1) {
		    	//Transfers selected item from equipment to quest
		    	questScreen.OnChange(equipmentScreen, questScreen);
		    }
			break;
		case 3:
			//opens the ability screen
			abilityScreen.setOpen(true);
			abilityScreen.setScreenName("Abilities");
			//makes sure the other screens are closed
			getInventoryScreen().setOpen(false);
			equipmentScreen.setOpen(false);
			questScreen.setOpen(false);
			
			if(lastKnownScreen == 0) {
		    	//Transfers selected item from inventory to ability
		    	abilityScreen.OnChange(getInventoryScreen(), abilityScreen);
		    } else if(lastKnownScreen == 2) {
		    	//Transfers selected item from quest to ability
		    	abilityScreen.OnChange(questScreen, abilityScreen);
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

		if(getInventoryScreen().isOpen() || equipmentScreen.isOpen() || questScreen.isOpen() || abilityScreen.isOpen()) {
			
			lastKnownScreen = currentScreen;
			if(KeyHandlerController.ScreenMovement[0] && counter[0] == counter[1]) {
				//Moves to the previous screen
				currentScreen--;
				
				//Make sure it doesn't go out of bounds
				if(currentScreen < 0)
					currentScreen = maxNumberOfScreens;
				
				//resets the counter
				counter[0] = 0;
				
				//Changes the screen
				changeScreen();
			}
			
			if(KeyHandlerController.ScreenMovement[1] && counter[0] == counter[1]) {
				//Moves to the next screen
				currentScreen++;
				
				//Make sure it doesn't go out of bounds
				if(currentScreen > maxNumberOfScreens)
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
			abilityScreen.setOpen(false);
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
		
			
		//Updates the currently open screen
		if(getInventoryScreen().isOpen() && !equipmentScreen.isOpen() && !questScreen.isOpen() && !abilityScreen.isOpen())
			getInventoryScreen().Update();
		if(!getInventoryScreen().isOpen() && equipmentScreen.isOpen() && !questScreen.isOpen() && !abilityScreen.isOpen())
			equipmentScreen.Update();
		if(!getInventoryScreen().isOpen() && !equipmentScreen.isOpen() && questScreen.isOpen() && !abilityScreen.isOpen())
			questScreen.Update();
		if(!getInventoryScreen().isOpen() && !equipmentScreen.isOpen() && !questScreen.isOpen() && abilityScreen.isOpen())
			abilityScreen.Update();
	}
	
	public void Draw(GraphicsContext gc) {
		
		//Draws the currently open screen
		if(getInventoryScreen().isOpen() && !equipmentScreen.isOpen() && !questScreen.isOpen() && !abilityScreen.isOpen())
			getInventoryScreen().Draw(gc);
		if(!getInventoryScreen().isOpen() && equipmentScreen.isOpen() && !questScreen.isOpen() && !abilityScreen.isOpen())
			equipmentScreen.Draw(gc);
		if(!getInventoryScreen().isOpen() && !equipmentScreen.isOpen() && questScreen.isOpen() && !abilityScreen.isOpen())
			questScreen.Draw(gc);
		if(!getInventoryScreen().isOpen() && !equipmentScreen.isOpen() && !questScreen.isOpen() && abilityScreen.isOpen())
			abilityScreen.Draw(gc);
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

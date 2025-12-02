package gameEngine;

import java.io.File;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class StatusScreen {
	private Image backgroundImage;
	private Image cursorImage;
	private Entity entity;
	private Vector2[] position;
	private Vector2 startingPosition;
	private boolean isOpen = false;
	private boolean isSelecting = false;
	private boolean isSelectingAllowed = true;
	private Item selectedItem;
	private Item[][] itemSlots;
	private Vector2 currentSelected = new Vector2(0,0);
	private Vector2 delayCounter= new Vector2(0, 20);;
	private Vector2 maxSelected;
	private int widthOffset;
	private int heightOffset;
	private String[] slotType = {"Helmet","Chest","Legs","LeftHand","RightHand"};
	private String screenName;
	
	public StatusScreen(Entity entityRef) {
		setEntity(entityRef);
		
		//Sets the position array
		position = new Vector2[3]; //0 = Background, 1 = Cursor, 2 = Selected Item
		position[0] = new Vector2(Global.RENDER_X/2-200,Global.RENDER_Y/2-150); 
		position[1] = new Vector2(position[0].X+20,position[0].Y+50); 
		position[2] = new Vector2(0,0); //Selected Item Position
		
		//Sets the starting position of the cursor
		startingPosition = new Vector2(position[0].X+20,position[0].Y+50);
		
		try {
			//Sets the cursor image
			cursorImage = SwingFXUtils.toFXImage(
				ImageIO.read(new File("Resources/Images/UI/cursor.png")), null);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void OnChange(StatusScreen current, StatusScreen target) {
		if(!current.isSelecting() )
			return;
		if(current.getSelectedItem() == null)
			return;
		
		Item item = current.getSelectedItem();
		current.setSelectedItem(null);
		current.setSelecting(false);
		target.setSelectedItem(item);
		target.setSelecting(true);
		
	}	
	
	private void equipmentItems(int i) {
		
		switch(i) {
		case 0:
			//Picks up the item from the slot that its currently on.
			setSelectedItem(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y]);

			entity.changeStatusByPair(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getTarget(),
					-getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getAmount());
			//Empty the slot that the item was in
			getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] = null;
				
			//Sets the selecting boolean to true
			setSelecting(true);
			
			break;
		case 1:
			if(getSelectedItem() == null)
				return;
			switch(slotType[(int)getCurrentSelected().Y])
			{
				case "Chest":
					if(getSelectedItem().getType().equals("Chest"))
					{
						//Places the selected item into the current slot selected.
						getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] = getSelectedItem();
						
						//Sets the selected item to null
						setSelectedItem(null);
						
						//Sets the selecting boolean to false
						setSelecting(false);						
						
						entity.changeStatusByPair(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getTarget(),
								getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getAmount());
					}
				break;
				case "Helmet":
					if(getSelectedItem().getType().equals("Helmet"))
					{
						//Places the selected item into the current slot selected.
						getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] = getSelectedItem();
						
						//Sets the selected item to null
						setSelectedItem(null);
						
						//Sets the selecting boolean to false
						setSelecting(false);
						entity.changeStatusByPair(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getTarget(),
								getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getAmount());
					}
					break;
				case "Legs":
					if(getSelectedItem().getType().equals("Legs"))
					{
						//Places the selected item into the current slot selected.
						getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] = getSelectedItem();
							
						//Sets the selected item to null
						setSelectedItem(null);
							
						//Sets the selecting boolean to false
						setSelecting(false);
						entity.changeStatusByPair(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getTarget(),
								getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getAmount());
					}
					break;
				case "LeftHand":
					if(getSelectedItem().getType().equals("LeftHand"))
					{
						//Places the selected item into the current slot selected.
						getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] = getSelectedItem();
								
						//Sets the selected item to null
						setSelectedItem(null);
								
						//Sets the selecting boolean to false
						setSelecting(false);
						entity.changeStatusByPair(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getTarget(),
								getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getAmount());
					}
					break;
			case "RightHand":
					if(getSelectedItem().getType().equals("RightHand"))
					{
					//Places the selected item into the current slot selected.
					getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] = getSelectedItem();
									
					//Sets the selected item to null
					setSelectedItem(null);
									
					//Sets the selecting boolean to false
					setSelecting(false);
					entity.changeStatusByPair(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getTarget(),
							getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getAmount());
				}
				break;
								
			}
			break;
			case 2:
				switch(slotType[(int)getCurrentSelected().Y])
				{
					case "Chest":
						if(getSelectedItem().getType().equals("Chest"))
						{
							//Swaps the items
							Item temp = getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y];
							

							entity.changeStatusByPair(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getTarget(),
									-getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getAmount());
							
							//Places the selected item into the current slot selected.
							getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] = getSelectedItem();
							

							entity.changeStatusByPair(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getTarget(),
									getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getAmount());
							
							//Sets the selected item to the temp item
							setSelectedItem(temp);

							setSelecting(true);
						}
					break;
					case "Helmet":
						if(getSelectedItem().getType().equals("Helmet"))
						{
							//Swaps the items
							Item temp = getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y];
							entity.changeStatusByPair(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getTarget(),
									-getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getAmount());
							//Places the selected item into the current slot selected.
							getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] = getSelectedItem();
							
							entity.changeStatusByPair(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getTarget(),
									getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getAmount());
							//Sets the selected item to the temp item
							setSelectedItem(temp);

							setSelecting(true);
						}
						break;
					case "Legs":
						if(getSelectedItem().getType().equals("Legs"))
						{
							//Swaps the items
							Item temp = getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y];
							entity.changeStatusByPair(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getTarget(),
									-getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getAmount());
							//Places the selected item into the current slot selected.
							getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] = getSelectedItem();
							
							entity.changeStatusByPair(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getTarget(),
									getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getAmount());
							//Sets the selected item to the temp item
							setSelectedItem(temp);

							setSelecting(true);
						}
						break;
					case "LeftHand":
						if(getSelectedItem().getType().equals("LeftHand"))
						{
							//Swaps the items
							Item temp = getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y];
							entity.changeStatusByPair(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getTarget(),
									-getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getAmount());
							//Places the selected item into the current slot selected.
							getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] = getSelectedItem();
							entity.changeStatusByPair(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getTarget(),
									getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getAmount());
							//Sets the selected item to the temp item
							setSelectedItem(temp);

							setSelecting(true);
						}
						break;
				case "RightHand":
						if(getSelectedItem().getType().equals("RightHand"))
						{
							//Swaps the items
							Item temp = getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y];
							
							//Places the selected item into the current slot selected.
							getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] = getSelectedItem();
							entity.changeStatusByPair(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getTarget(),
									getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y].getEffects().getAmount());
							//Sets the selected item to the temp item
							setSelectedItem(temp);

							setSelecting(true);
						}
					break;			
				}
				break;
		}
		
	}
	
	public void Update() {		
		if(isOpen()) {
			
			delayCounter.X++;
			if(delayCounter.X >= delayCounter.Y)
				delayCounter.X = delayCounter.Y;
			
			
			//Moves the cursor UP, LEFT, DOWN, RIGHT
			if(KeyHandlerController.Movement[0] && delayCounter.X == delayCounter.Y)
			{
				delayCounter.X = 0;
				getCurrentSelected().Y--;
				if(getCurrentSelected().Y < 0)
					getCurrentSelected().Y = getMaxSelected().Y-1;
			}
			if(KeyHandlerController.Movement[1] && delayCounter.X == delayCounter.Y)
			{
				delayCounter.X = 0;
				getCurrentSelected().X--;
				if(getCurrentSelected().X < 0)
					getCurrentSelected().X = getMaxSelected().X-1;
			}
			if(KeyHandlerController.Movement[2] && delayCounter.X == delayCounter.Y)
			{
				delayCounter.X = 0;
				getCurrentSelected().Y++;
				if(getCurrentSelected().Y > getMaxSelected().Y-1)
					getCurrentSelected().Y = 0;
			}
			if(KeyHandlerController.Movement[3] && delayCounter.X == delayCounter.Y)
			{
				delayCounter.X = 0;
				getCurrentSelected().X++;
				if(getCurrentSelected().X > getMaxSelected().X-1)
					getCurrentSelected().X = 0;
			}
			if(KeyHandlerController.Action && delayCounter.X == delayCounter.Y && isSelectingAllowed && delayCounter.X == delayCounter.Y) {
				if(getItemSlots() != null)
					if(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] != null && !isSelecting()) {
						
						if(screenName.equals("Equipment"))
						{
							equipmentItems(0);
						}
						else if(screenName.equals("Inventory"))
						{
							//Picks up the item from the slot that its currently on.
							setSelectedItem(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y]);
								
							//Empty the slot that the item was in
							getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] = null;
								
							//Sets the selecting boolean to true
							setSelecting(true);
						}
	
					}
					else if(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] == null && isSelecting()) {
						if(screenName.equals("Equipment"))
						{
							equipmentItems(1);
						}
						else if(screenName.equals("Inventory"))
						{
							//Places the selected item into the current slot selected.
							getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] = getSelectedItem();
							
							//Sets the selected item to null
							setSelectedItem(null);
							
							//Sets the selecting boolean to false
							setSelecting(false);
						}
					}
					else if (getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] != null && isSelecting()) {
						
						if(screenName.equals("Equipment"))
						{
							equipmentItems(2);
						}
						else if(screenName.equals("Inventory"))
						{
							//Swaps the items
							Item temp = getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y];
							
							//Places the selected item into the current slot selected.
							getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] = getSelectedItem();
							
							//Sets the selected item to the temp item
							setSelectedItem(temp);

							setSelecting(true);
						}
						
					}
					else {
						
					}
				delayCounter.X = 0;
			}
			
			if(isSelecting && KeyHandlerController.UseItem && delayCounter.X == delayCounter.Y) {
				if(getSelectedItem() != null) {
					//Uses the selected item on the entity
					if(getSelectedItem().useItem(getEntity()))
						System.out.println("Used "+getSelectedItem().getName()+" on "+getEntity().getName());
					else
						System.out.println("Failed to use "+getSelectedItem().getName()+" on "+getEntity().getName());
					
					//If the quantity is 0 after using the item, it removes it from the selection
					if(getSelectedItem().getQuantity() <= 0) {
						setSelectedItem(null);
						setSelecting(false);
					}
				}
				delayCounter.X = 0;
			}
			
			//Updates the position of the cursor on the screen
			if(currentSelected != null)
				position[1] = new Vector2(getCurrentSelected().X*getWidthOffset()+startingPosition.X,
					getCurrentSelected().Y*getHeightOffset()+startingPosition.Y);
			
			//Updates the position of the selected item if there is one
			if(isSelecting())
				position[2] = new Vector2(position[1].X+30,	position[1].Y+30);
		}
	}
	
	public void Draw(GraphicsContext gc) {
		
		//Draws the Screens background first
		if(backgroundImage != null)
			gc.drawImage(backgroundImage, position[0].X, position[0].Y
				,450,300);
		
		//Draws the cursor on top of the background
		if(cursorImage != null)
			gc.drawImage(cursorImage, position[1].X, position[1].Y,
				50, 50);
		
		//Draws the selected Item if there is one to the bottom right of the cursor.
		if(isSelecting()) {
			if(selectedItem != null)
				gc.drawImage(selectedItem.getImage(), position[2].X, position[2].Y,
					20,20);
		}
	}

	/**
	 * @return the backgroundImage
	 */
	public Image getBackgroundImage() {
		return backgroundImage;
	}

	/**
	 * @param backgroundImage the backgroundImage to set
	 */
	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	/**
	 * @return the cursorImage
	 */
	public Image getCursorImage() {
		return cursorImage;
	}

	/**
	 * @param cursorImage the cursorImage to set
	 */
	public void setCursorImage(Image cursorImage) {
		this.cursorImage = cursorImage;
	}

	/**
	 * @return the isOpen
	 */
	public boolean isOpen() {
		return isOpen;
	}

	/**
	 * @param isOpen the isOpen to set
	 */
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	/**
	 * @return the maxSelected
	 */
	public Vector2 getMaxSelected() {
		return maxSelected;
	}

	/**
	 * @param maxSelected the maxSelected to set
	 */
	public void setMaxSelected(Vector2 maxSelected) {
		this.maxSelected = maxSelected;
	}

	/**
	 * @return the itemSlots
	 */
	public Item[][] getItemSlots() {
		return itemSlots;
	}

	/**
	 * @param itemSlots the itemSlots to set
	 */
	public void setItemSlots(Item[][] itemSlots) {
		this.itemSlots = itemSlots;
	}
	
	public void setItemAtSetSlot(Item item, Vector2 slot) {
		itemSlots[(int) slot.X][(int) slot.Y] = item;
	}

	public boolean isSelecting() {
		return isSelecting;
	}

	public void setSelecting(boolean isSelecting) {
		this.isSelecting = isSelecting;
	}

	public Vector2 getCurrentSelected() {
		return currentSelected;
	}

	public void setCurrentSelected(Vector2 currentSelected) {
		this.currentSelected = currentSelected;
	}

	public Item getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Item selectedItem) {
		this.selectedItem = selectedItem;
	}

	/**
	 * @return the isSelectingAllowed
	 */
	public boolean isSelectingAllowed() {
		return isSelectingAllowed;
	}

	/**
	 * @param isSelectingAllowed the isSelectingAllowed to set
	 */
	public void setSelectingAllowed(boolean isSelectingAllowed) {
		this.isSelectingAllowed = isSelectingAllowed;
	}

	/**
	 * @return the entity
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public Vector2 getBackgroundPosition() {
		return position[0];
	}

	/**
	 * @return the widthOffset
	 */
	public int getWidthOffset() {
		return widthOffset;
	}

	/**
	 * @param widthOffset the widthOffset to set
	 */
	public void setWidthOffset(int widthOffset) {
		this.widthOffset = widthOffset;
	}

	/**
	 * @return the heightOffset
	 */
	public int getHeightOffset() {
		return heightOffset;
	}

	/**
	 * @param heightOffset the heightOffset to set
	 */
	public void setHeightOffset(int heightOffset) {
		this.heightOffset = heightOffset;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @param screenName the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
}

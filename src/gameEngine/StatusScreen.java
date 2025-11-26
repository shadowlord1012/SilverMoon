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
	private Vector2 currentSelected;
	private Vector2 delayCounter= new Vector2(0,50);
	private Vector2 maxSelected;
	private int widthOffset;
	private int heightOffset;
	
	public StatusScreen(Entity entityRef) {
		setEntity(entityRef);
		position = new Vector2[3];
		position[0] = new Vector2(Global.RENDER_X/2-200,Global.RENDER_Y/2-150); //Background Position
		position[1] = new Vector2(position[0].X+20,position[0].Y+35); //Cursor Position
		position[2] = new Vector2(0,0); //Selected Item Position
		try {
			cursorImage = SwingFXUtils.toFXImage(
				ImageIO.read(new File("Resources/Images/UI/cursor.png")), null);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void OnChange(StatusScreen current, StatusScreen target) {
		target.setOpen(true);
		current.setOpen(false);
		if(current.isSelecting()) {
			target.setSelectedItem(current.getSelectedItem());
			current.setSelectedItem(null);
			target.setSelecting(true);
		}
	}
	
	public void Update() {		
		if(isOpen()) {
			
			delayCounter.X++;
			if(delayCounter.X >= delayCounter.Y)
				delayCounter.X = delayCounter.Y;
			
			/*
			//Moves the cursor UP, LEFT, DOWN, RIGHT
			if(KeyHandlerController.Movement[0] && delayCounter.X == delayCounter.Y)
			{
				delayCounter.X = 0;
				getCurrentSelected().Y--;
				if(getCurrentSelected().Y < 0)
					getCurrentSelected().Y = getMaxSelected().Y;
			}
			if(KeyHandlerController.Movement[1] && delayCounter.X == delayCounter.Y)
			{
				delayCounter.X = 0;
				getCurrentSelected().X--;
				if(getCurrentSelected().X < 0)
					getCurrentSelected().X = getMaxSelected().X;
			}
			if(KeyHandlerController.Movement[2] && delayCounter.X == delayCounter.Y)
			{
				delayCounter.X = 0;
				getCurrentSelected().Y++;
				if(getCurrentSelected().Y > getMaxSelected().Y)
					getCurrentSelected().Y = 0;
			}
			if(KeyHandlerController.Movement[3] && delayCounter.X == delayCounter.Y)
			{
				delayCounter.X = 0;
				getCurrentSelected().X++;
				if(getCurrentSelected().X > getMaxSelected().X)
					getCurrentSelected().X = 0;
			}
			if(KeyHandlerController.Action && delayCounter.X == delayCounter.Y && isSelectingAllowed) {
				if(!isSelecting()) {
					//Picks up the item from the slot that its currently on.
					setSelectedItem(getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y]);
					
					//Empty the slot that the item was in
					getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] = null;
					
					//Sets the selecting boolean to true
					setSelecting(true);
				}
				else {
					
					//Places the selected item into the current slot selected.
					getItemSlots()[(int) getCurrentSelected().X][(int) getCurrentSelected().Y] = getSelectedItem();
					
					//Sets the selected item to null
					setSelectedItem(null);
					
					//Sets the selecting boolean to false
					setSelecting(false);
				}
				delayCounter.X = 0;
			}
			*/
			//Updates the position of the cursor on the screen
			if(currentSelected != null)
				position[1] = new Vector2(getCurrentSelected().X*widthOffset+startingPosition.X,
					getCurrentSelected().Y*heightOffset+startingPosition.Y);
			
			//Updates the position of the selected item if there is one
			if(isSelecting())
				position[2] = new Vector2(position[1].X+getCursorImage().getWidth()+5,
					position[1].Y+getCursorImage().getHeight()+5);
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
}

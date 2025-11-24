package gameEngine;

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
	private Item selectedItem;
	private Item[][] itemSlots;
	private Vector2 currentSelected;
	private Vector2 delayCounter;
	private Vector2 maxSelected;
	private int widthOffset;
	private int heightOffset;
	
	public StatusScreen(Entity entityRef) {
		entity = entityRef;
		position = new Vector2[3];
	}
	
	public void OnChange(StatusScreen current, StatusScreen target) {
		target.setOpen(true);
		current.setOpen(false);
		if(current.isSelecting) {
			target.selectedItem = current.selectedItem;
			current.selectedItem = null;
			target.isSelecting = true;
		}
	}
	
	public void Update() {		
		if(isOpen()) {
			
			delayCounter.X++;
			if(delayCounter.X >= delayCounter.Y)
				delayCounter.X = delayCounter.Y;
			
			if(KeyHandlerController.Movement[0] && delayCounter.X == delayCounter.Y)
			{
				delayCounter.X = 0;
				currentSelected.Y--;
				if(currentSelected.Y < 0)
					currentSelected.Y = getMaxSelected().Y;
			}
			if(KeyHandlerController.Movement[1] && delayCounter.X == delayCounter.Y)
			{
				delayCounter.X = 0;
				currentSelected.X--;
				if(currentSelected.X < 0)
					currentSelected.X = getMaxSelected().X;
			}
			if(KeyHandlerController.Movement[2] && delayCounter.X == delayCounter.Y)
			{
				delayCounter.X = 0;
				currentSelected.Y++;
				if(currentSelected.Y > getMaxSelected().Y)
					currentSelected.Y = 0;
			}
			if(KeyHandlerController.Movement[3] && delayCounter.X == delayCounter.Y)
			{
				delayCounter.X = 0;
				currentSelected.X++;
				if(currentSelected.X > getMaxSelected().X)
					currentSelected.X = 0;
			}
			if(KeyHandlerController.Action && delayCounter.X == delayCounter.Y) {
				if(!isSelecting) {
					selectedItem = getItemSlots()[(int) currentSelected.X][(int) currentSelected.Y];
					getItemSlots()[(int) currentSelected.X][(int) currentSelected.Y] = null;
					isSelecting = true;
				}
				if(isSelecting) {
					getItemSlots()[(int) currentSelected.X][(int) currentSelected.Y] = selectedItem;
					selectedItem = null;
					isSelecting = false;
				}
				delayCounter.X = 0;
			}
			
			position[1] = new Vector2(currentSelected.X*widthOffset+startingPosition.X,
					currentSelected.Y*heightOffset+startingPosition.Y);
			if(isSelecting)
				position[2] = new Vector2(position[1].X+getCursorImage().getWidth()+5,
					position[1].Y+getCursorImage().getHeight()+5);
		}
	}
	
	public void Draw(GraphicsContext gc) {
		
		//Draws the Screens background first
		if(getBackgroundImage() != null)
			gc.drawImage(getBackgroundImage(), position[0].X, position[0].Y
				,getBackgroundImage().getWidth(), getBackgroundImage().getHeight());
		if(getCursorImage() != null)
			gc.drawImage(getCursorImage(), position[1].X, position[1].Y,
				getCursorImage().getWidth(), getCursorImage().getHeight());
		if(isSelecting) {
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
}

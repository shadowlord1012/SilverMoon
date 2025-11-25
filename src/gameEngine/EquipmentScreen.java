package gameEngine;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;

public class EquipmentScreen extends StatusScreen{

	private BufferedImage equipmentScreen;
	private String[] slotType = {"Helmet","Chest","Legs","LeftHand","RightHand"};
	
	
	public EquipmentScreen(Entity entityRef) {
		super(entityRef);
		super.setMaxSelected(new Vector2(1,6));
		super.setBackgroundImage(SwingFXUtils.toFXImage(null, null));
		super.setItemSlots(new Item[(int)super.getMaxSelected().X][(int)super.getMaxSelected().Y]);
	}
	
	/**
	 * Adds an Item to a set slot 
	 * @param item
	 */
	public void AddToSlot(Item item) {
		if(super.isSelecting())
		{
			switch((int)(super.getCurrentSelected().Y))
			{
			case 1:
				if(item.getType() == slotType[0])
				{
					//If there is no item in the current slot
					if(super.getItemSlots()[(int)super.getCurrentSelected().X][(int)super.getCurrentSelected().Y]
							 == null)
					{
						//Set the item to the slot
						super.setItemAtSetSlot(item, super.getCurrentSelected());
						break;
					}
					
					//If there is an item in the slot
					else {
						super.setSelectedItem(super.getItemSlots()[(int)super.getCurrentSelected().X][(int)super.getCurrentSelected().Y]);
						super.setItemAtSetSlot(item, getCurrentSelected());
						break;
					}
				}
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6: 
				break;
			}
		}
	}

	@Override
	public void Update() {
		super.Update();
	}
	
	@Override
	public void Draw(GraphicsContext gc) {
		super.Draw(gc);
	}
}

package gameEngine;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EquipmentScreen extends StatusScreen{

	private BufferedImage equipmentScreen;
	private String[] slotType = {"Helmet","Chest","Legs","LeftHand","RightHand"};
	private int counter;
	
	public EquipmentScreen(Entity entityRef) {
		super(entityRef);

		//Sets the offsets for the Equipment Screen
		super.setWidthOffset(0);
		super.setHeightOffset(40);
		//Sets the Background Image
		try {
			equipmentScreen = ImageIO.read(new File("Resources/Images/UI/EquipmentScreen.png"));
		}catch(Exception e) {e.printStackTrace();}
		
		//sets all the values for the Equipment Screen
		super.setMaxSelected(new Vector2(0,4));
		super.setBackgroundImage(SwingFXUtils.toFXImage(equipmentScreen, null));
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
				SlotNumber(item, 0);
				break;
			case 2:
				SlotNumber(item, 1);
				break;
			case 3:
				SlotNumber(item, 2);
				break;
			case 4:
				SlotNumber(item, 3);
				break;
			case 5:
				SlotNumber(item, 4);
				break;
			}
		}
	}

	/**
	 * Adds an item to a specific slot number
	 * @param item
	 * @param slotNumber
	 */
	private void SlotNumber(Item item, int slotNumber) {
		if(item.getType() == slotType[slotNumber])
		{
			//If there is no item in the current slot
			if(super.getItemSlots()[(int)super.getCurrentSelected().X][(int)super.getCurrentSelected().Y]
					 == null)
			{
				//Set the item to the slot
				super.setItemAtSetSlot(item, super.getCurrentSelected());
			}
			
			//If there is an item in the slot
			else {
				super.setSelectedItem(super.getItemSlots()[(int)super.getCurrentSelected().X][(int)super.getCurrentSelected().Y]);
				super.setItemAtSetSlot(item, getCurrentSelected());
			}
		}
	}
	
	private void drawArmorSlotInformation(GraphicsContext gc) {
		
		Font defaultFont = Font.getDefault();
		
		//sets the font to Adventure Request
		gc.setFont(Global.ADVENTURE_REQUEST);
		
		//set item name on screen
		gc.setFill(Color.WHITE);
		
		//Displays the names and buff of armor pieces
		gc.fillText("Berserker Warhelm", super.getBackgroundPosition().X+75, super.getBackgroundPosition().Y+70);
		gc.fillText("+5 Attack", super.getBackgroundPosition().X+75, super.getBackgroundPosition().Y+90);
		gc.fillText("Berserker WarPlate", super.getBackgroundPosition().X+75, super.getBackgroundPosition().Y+110);
		gc.fillText("+5 Attack", super.getBackgroundPosition().X+75, super.getBackgroundPosition().Y+130);
		gc.fillText("Berserker WarLegs", super.getBackgroundPosition().X+75, super.getBackgroundPosition().Y+150);
		gc.fillText("+5 Attack", super.getBackgroundPosition().X+75, super.getBackgroundPosition().Y+170);
		gc.fillText("Berserker Sword", super.getBackgroundPosition().X+75, super.getBackgroundPosition().Y+190);
		gc.fillText("+5 Attack", super.getBackgroundPosition().X+75, super.getBackgroundPosition().Y+210);
		gc.fillText("Berserker Shield", super.getBackgroundPosition().X+75, super.getBackgroundPosition().Y+230);
		gc.fillText("+5 Attack", super.getBackgroundPosition().X+75, super.getBackgroundPosition().Y+250);
		
		gc.setFill(Color.BLACK);
		gc.setFont(defaultFont);
	}
	
	private void drawInformation(GraphicsContext gc) {
		//TODO: Use fill text to draw in any and all information about the entities equipment
		
		//Changes the font back to default after drawing
		Font defaultFont = Font.getDefault();
		
		//sets the font to Adventure Request
		gc.setFont(Global.ADVENTURE_REQUEST);
		
		//Sets the title color to gold
		gc.setFill(Color.GOLD);
		
		//draws the title
		gc.fillText("Equipment",super.getBackgroundPosition().X+175, super.getBackgroundPosition().Y+25);
		
		//Sets the information color to white
		gc.setFill(Color.WHITE);
		
		//Writes all the information about the entity
		gc.fillText(String.format("Name: %s", super.getEntity().getName()),
				super.getBackgroundPosition().X+250, super.getBackgroundPosition().Y+55);
		gc.fillText(String.format("Level: %d", (int)super.getEntity().getStatusByName("Level")),
				super.getBackgroundPosition().X+250, super.getBackgroundPosition().Y+80);
		gc.fillText(String.format("Health: %d / %d", (int)super.getEntity().getStatusByName("healthcurrent"), (int)super.getEntity().getStatusByName("Health")),
				super.getBackgroundPosition().X+250, super.getBackgroundPosition().Y+105);
		gc.fillText(String.format("Magic: %d / %d", (int)super.getEntity().getStatusByName("magiccurrent"), (int)super.getEntity().getStatusByName("Magic")),
				super.getBackgroundPosition().X+250, super.getBackgroundPosition().Y+130);
		gc.fillText(String.format("Attack: %d", (int)super.getEntity().getStatusByName("Attack")),
				super.getBackgroundPosition().X+250, super.getBackgroundPosition().Y+155);
		gc.fillText(String.format("Defence: %d", (int)super.getEntity().getStatusByName("Defence")),
				super.getBackgroundPosition().X+250, super.getBackgroundPosition().Y+180);
		gc.fillText(String.format("Elemental: \n %s", super.getEntity().getAlignment()),
				super.getBackgroundPosition().X+250, super.getBackgroundPosition().Y+205);
		gc.fillText(String.format("Gold: \n %d", super.getEntity().getGold()),
				super.getBackgroundPosition().X+250, super.getBackgroundPosition().Y+250);
		
		//Turns the font back to black
		gc.setFill(Color.BLACK);
		
		//Sets the font back to default
		gc.setFont(defaultFont);
	}
	
	
	@Override
	public void Update() {
		super.Update();
	}
	
	@Override
	public void Draw(GraphicsContext gc) {
		super.Draw(gc);
		drawInformation(gc);
		drawArmorSlotInformation(gc);
	}
}

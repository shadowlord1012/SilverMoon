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
		super.setMaxSelected(new Vector2(1,5));
		super.setBackgroundImage(SwingFXUtils.toFXImage(equipmentScreen, null));
		super.setItemSlots(new Item[(int)super.getMaxSelected().X][(int)super.getMaxSelected().Y]);
	}
	

	private void drawArmorSlotInformation(GraphicsContext gc) {
		
		Font defaultFont = Font.getDefault();
		
		//sets the font to Adventure Request
		gc.setFont(Global.ADVENTURE_REQUEST);
		
		//set item name on screen
		gc.setFill(Color.WHITE);
		
		//Displays the names and buff of armor pieces		
		for(int y = 0; y < super.getMaxSelected().Y; y++) {
			//If there is an item in the slot, draw its name and buff
			if(super.getItemSlots()[0][y] != null) {
				gc.fillText(super.getItemSlots()[0][y].getName(), super.getBackgroundPosition().X+75, super.getBackgroundPosition().Y+70+(y*40));
				gc.fillText(String.format("+ %d %s",(int)super.getItemSlots()[0][y].getEffects().getAmount(), 
						super.getItemSlots()[0][y].getEffects().getTarget()), super.getBackgroundPosition().X+75, super.getBackgroundPosition().Y+90+(y*40));
			}
		}
		
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
		
		for(int x = 0; x < super.getMaxSelected().X; x++) {
			for(int y = 0; y < super.getMaxSelected().Y; y++) {
				//If there is an item in the slot, draw it
				if(super.getItemSlots()[x][y] != null) {
					
					//draw the item image
					gc.drawImage(super.getItemSlots()[x][y].getImage(),super.getBackgroundPosition().X+30,
							super.getBackgroundPosition().Y+60+(y*40),30,30);
				}
			}
		}
		
		drawInformation(gc);
		drawArmorSlotInformation(gc);
	}
}

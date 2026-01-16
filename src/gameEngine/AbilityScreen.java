package gameEngine;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;

public class AbilityScreen extends StatusScreen {
	
	/**
	 * TODO: Note Display all the abilities the player has learned
	 * and allow them to equip/unequip abilities to their
	 * ability hotbar.
	 * Include ability descriptions and stats.
	 */
	
	private BufferedImage abilityScreen;
	private int counter;

	public AbilityScreen(Entity entityRef) {
		super(entityRef);
		
		//Sets the offsets for the Ability Screen
		super.setWidthOffset(0);
		super.setHeightOffset(40);
		
		//Sets the Background Image
		try {
			abilityScreen = ImageIO.read(new File("Resources/Images/UI/AbilityScreen.png"));
		}catch(Exception e) {e.printStackTrace();}
		
		//Sets all the values for the Ability Screen
		super.setBackgroundImage(SwingFXUtils.toFXImage(abilityScreen, null));
		
		
		super.setMaxSelected(new Vector2(10,5));
		super.setItemSlots(new Item[(int) super.getMaxSelected().X][(int) super.getMaxSelected().Y]);
	}
	
	public void Update() {
		super.Update();
		
	}
	
	public void Draw(GraphicsContext gc) {
		super.Draw(gc);
	}
}

package gameEngine;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class QuestScreen extends StatusScreen {

	private BufferedImage backgroundImage;
	private int counter;
	private Player playRef;
	
	public QuestScreen(Player playerRef) {
		super(playerRef);
		this.playRef = playerRef;
		
		
		//Sets the Background Image
		try {
			backgroundImage = ImageIO.read(new File("Resources/Images/UI/QuestScreen.png"));
		}catch(Exception e) {e.printStackTrace();}
		
		//Sets all the values for the Quest Screen
		super.setBackgroundImage(SwingFXUtils.toFXImage(backgroundImage, null));
		
		//TODO: Adjust max selected based on design only temp so no error out of bounds if moved
		super.setMaxSelected(new Vector2(10,5));
		super.setItemSlots(new Item[(int) super.getMaxSelected().X][(int) super.getMaxSelected().Y]);
	}

	private void DrawTime(GraphicsContext gc) {
		
		//Saves the default font
		Font defaultFont = Font.getDefault();
		
		//sets the font to Adventure Request
		gc.setFont(Global.ADVENTURE_REQUEST);
				
		//set item name on screen
		gc.setFill(Color.WHITE);
		
		//Draws the Game Time played
		gc.fillText(String.format("Game Time: %s", Global.GAME_TIME.TackTime(playRef)), 
				super.getBackgroundPosition().X+25, super.getBackgroundPosition().Y+270);
		
		//Resets the font and color
		gc.setFont(defaultFont);
		gc.setFill(Color.BLACK);
	}
		
	
	@Override
	public void Update() {
		super.Update();
	}
	
	@Override
	public void Draw(GraphicsContext gc) {
		super.Draw(gc);
		DrawTime(gc);
	}
}

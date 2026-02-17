package gameEngine;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.google.gson.annotations.SerializedName;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;


public class Tile {
	
	@SerializedName("Width")
	private int width; //width of the tile
	@SerializedName("Height")
	private int height; //Height of the tile
	@SerializedName("StoppableSides")
	private int stoppableDirection; // which sides are not passable
	@SerializedName("IsDamage")
	private boolean isDamage; // does it cause damage
	@SerializedName("IsActive")
	private boolean isActive; //Is it a moving tile
	@SerializedName("NumberOfFrames")
	private int numberOfFrames; //If it is a moving tile, how many frames does it have
	private transient Vector2 currentFrame = Vector2.Zero(); //The current frame of the tile, used for animation
	
	private transient Image img; //The image of the tile
	private transient BufferedImage activeImage; //The image of the tile when it is active, used for animation
	
	//Which tile set the tile belongs to and where on the tileset it is
	@SerializedName("TileSetName")
	private String tileSetName; 
	@SerializedName("LocationOnTileSet")
	private Vector2 location; 
	@SerializedName("FilePath")
	private String filePath;
	 
	
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public int getStoppableSides() {return stoppableDirection;}
	public boolean IsDamage() {return isDamage;}
	public boolean IsActive() {return isActive;}
	public int getNumberOfFrames() {return numberOfFrames;}
	public Image getImg() {return img;}
	
	//Sets the image if the tileset is not needed
	public void setImage(Image value) {img = value;}
	public String getTileSetName() {return tileSetName;}
	public Vector2 getLocation() {return location;}
	public Vector2 getCurrentFrame() {return currentFrame;}
	
	public void Initialize() {
		width = 0;
		height = 0;
		stoppableDirection = 0;
		isDamage = false;
		isActive = false;
	}
	
	/**
	 * Sets the values of the tile
	 * @param width
	 * @param height
	 * @param direction
	 * @param damage
	 * @param active
	 * @param tileSetName
	 * @param locationOnTileset
	 * @param numberOfFrames
	 */
	public void set(int width, int height, int direction, boolean damage,
			boolean active, String tileSetName, Vector2 locationOnTileset, int numberOfFrames) {
		this.width = width;
		this.height = height;
		this.stoppableDirection = direction;
		this.isDamage = damage;
		this.isActive = active;
		this.tileSetName = tileSetName;
		this.location = locationOnTileset;
		this.numberOfFrames = numberOfFrames;
	}
	
	/**
	 * Sets the Image of the Tile.
	 * Requires to be initialized
	 */
	public void LoadImage() {
		
		File file = new File("Resources/TileSheets/"+tileSetName);
		
		//If the tile is active, it needs to load the entire image for animation
		if(this.isActive)
		{
			try {
				BufferedImage temp = ImageIO.read(file);
				this.activeImage = temp.getSubimage(
						(int) location.X * width,
						(int) location.Y* height, 
						(int) width+(width * numberOfFrames),
						height);
			} catch (Exception e) { e.printStackTrace(); }
		}
		//If the tile is not active, it only needs to load the image of the tile
		else {
			try {
				this.img = SwingFXUtils.toFXImage(javax.imageio.ImageIO.read(file)
					.getSubimage(
							(int) location.X * width,
							(int) location.Y* height, 
							(int) width,
							height),null);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void ChangeImage() {
		
		if(currentFrame.X > numberOfFrames) {
			currentFrame.X = 0;
		}
		
		if(isActive) {
			img = SwingFXUtils.toFXImage(activeImage
					.getSubimage(
							(int) (location.X * width + (currentFrame.Y * width)),
							(int) location.Y* height, 
							width,
							height),null);
			currentFrame.X++;
			if(currentFrame.X >= numberOfFrames) {
				currentFrame.X = 0;
			}
		}
	}
}

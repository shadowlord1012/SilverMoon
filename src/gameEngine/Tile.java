package gameEngine;

import java.io.File;

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
	
	private transient Image img; //The image of the tile
	
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
	public Image getImg() {return img;}
	
	//Sets the image if the tileset is not needed
	public void setImage(Image value) {img = value;}
	public String getTileSetName() {return tileSetName;}
	public Vector2 getLocation() {return location;}
	
	public void Initialize() {
		width = 0;
		height = 0;
		stoppableDirection = 0;
		isDamage = false;
		isActive = false;
	}
	
	public void set(int width, int height, int direction, boolean damage,
			boolean active, String tileSetName, Vector2 locationOnTileset) {
		this.width = width;
		this.height = height;
		this.stoppableDirection = direction;
		this.isDamage = damage;
		this.isActive = active;
		this.tileSetName = tileSetName;
		this.location = locationOnTileset;
	}
	
	/**
	 * Sets the Image of the Tile.
	 * Requires to be initialized
	 */
	public void LoadImage() {
		
		File file = new File("Resources/TileSheets/"+tileSetName);
		
		try {
			this.img = SwingFXUtils.toFXImage(javax.imageio.ImageIO.read(file)
					.getSubimage(
							(int) location.X * width,
							(int) location.Y* height, 
							width, height),null);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}

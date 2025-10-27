package gameEngine;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.gson.annotations.SerializedName;

import javafx.embed.swing.SwingFXUtils;

public class Entity {

	//Variables
		@SerializedName("Name")
		private String name;  // Name of the entity

		@SerializedName("Type")
		private String type;  // Type of the entity
		
		@SerializedName("Status")
		private Map<String,Double> status;  // all the status for the entity
		
		@SerializedName("Items")
		private Map<String, Item> items;
		
		@SerializedName("ElementalAlignment")
		private String elementalAlignment;
		
		private transient BufferedImage img;  // Image of the entity
		private transient int renderingCounter[] = {0,0,0}; // rendering counters
		private transient int renderSpeed = 3;  // How fast they render
		
		@SerializedName("NumberOfImages")
		private Vector2 numberOfImages;
		
		@SerializedName("MovementSpeed")
		private int movementSpeed;  // How fast the entity moves
		private transient int directionFacing = 0; // Which direction the entity is facing
		
		@SerializedName("ImageRectangle")
		private Rectangle imgRect;  // how big the image is to be
		
		@SerializedName("SpriteSheetPath")
		private String spriteSheetPath;
		
		private transient Vector2 Position = new Vector2(200,200); // Position of the entity
		public transient boolean isMoving;  // is the entity Moving 
		public transient boolean IsActive;  // is the entity alive 
		
		//Getter Methods
		public String getName() {return name;} 
		public double getStatusByName(String value) {return status.get(value);}
		public Map<String,Double> getStatusMap() {return status;}
		public Map<String, Item> getItems() {return items;}
		public String getAlignment() {return elementalAlignment;}
		public Rectangle getImgRect() {return imgRect;}
		public double getWidth() {return imgRect.getWidth();}
		public double getHeight() {return imgRect.getHeight();}
		public int MovementSpeed() {return movementSpeed;}
		public int getDirectionFacing() {return directionFacing;}
		public Vector2 getPosition() {return Position;}
		public int getMovementSpeed() {return movementSpeed;}
		public Vector2 getNumberOfImages() {return numberOfImages;}
		
		//Setter Methods
		public void setDirectionFacing(int value) {directionFacing = value;}
		public void setName(String value) {name = value;}
		public void setMovementSpeed(int value) {movementSpeed = value;}
		public void setStatusMap(Map<String,Double> value) {status = value;}
		public void setItems(Map<String, Item> value) {items = value;}
		public void setElementalAlignment(String value) { elementalAlignment = value;}
		public void setNumberOfImages(Vector2 value) {numberOfImages = value;}
		public void SetRow(int value) {renderingCounter[2] = value;}
		public void setPosition(Vector2 value) {Position = value;}
		public void setSpriteSheetPath(String value ) { spriteSheetPath = value;}
		
		
		/**
		 * Creates an entity. Must be initialized 
		 */
		public Entity() {
			status = new HashMap<>();
			items = new HashMap<>();
			imgRect = new Rectangle();
		}
		
		/**
		 * Enters in all the data needed for the entity
		 * @param name
		 * @param width
		 * @param height
		 * @param movementSpeed
		 */
		public void Initialize(String name, int width, int height, int movementSpeed, int xImg, int yImg) {
			
			//Sets some of the basic values
			this.name = name;
			this.imgRect.x = 0;
			this.imgRect.y = 0;
			this.imgRect.width = width;
			this.imgRect.height = height;
			this.movementSpeed = movementSpeed;
			this.numberOfImages = new Vector2(xImg,yImg);
		}
		
		/**
		 * set the status values by key eg. attack is key, 5 is value
		 * @param key
		 * @param value
		 * @return if it was completed correctly
		 */
		
		public void LoadImage(String path) {
			
			File file = new File("Resources/Player/"+path+".png");
			
			try {
				this.img = ImageIO.read(file);
				this.imgRect = new Rectangle(0,0,
						(int)(this.img.getWidth()/this.numberOfImages.X),
						(int)(this.img.getHeight()/this.numberOfImages.Y));
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		public boolean setStatusByPair(String key, double value) {
			
			// makes sure the value is getter then 0
			if(value >= 0) {
				// makes sure the status name is not already there
				if(!status.containsKey(key.toLowerCase())) {
					status.put(key, value);
					return true;
				}
			}
			return false;
		}
		
		/**
		 * Changes a status by key 
		 * @param key
		 * @param value
		 * @return if it was completed correctly
		 */
		public void changeStatusByPair(String key, double value) {
			if(key == "magiccurrent") {
				if(status.get(key)+value > status.get("Magic"))
				{
					status.put(key, status.get("Magic"));
				}
				else if(status.get(key)+value < 0)
				{
					status.put(key,0.0);
				}
				else {
					status.put(key, status.get(key)+value);
				}
			}
			else if(key == "healthcurrent")
			{
				if(status.get(key)+value > status.get("Health"))
				{
					status.put(key, status.get("Health"));
				}
				else if(status.get(key)+value < 0)
				{
					status.put(key,0.0);
				}
				else {
					status.put(key, status.get(key)+value);
				}
			}
			else {
				if(status.containsKey(key)) {
					status.put(key, status.get(key)+value);
				}
			}
		}
	/**
	 * Updates the entity
	 */
	public void Update(World w) {
		
		if(this.isMoving) {
			this.renderingCounter[0]++;
			if(this.renderingCounter[0] >= renderSpeed) {
				this.renderingCounter[0] = 0;
				this.renderingCounter[1]++;
			}
			if(this.renderingCounter[1] >= this.numberOfImages.X-1)
				this.renderingCounter[1] = 0;
		}
		else {
			this.renderingCounter[1] = 0;
		}
	}
	
	/**
	 * Draws the entity 
	 * @param gc
	 */
	public void Draw(javafx.scene.canvas.GraphicsContext gc) {
		
		if(this.IsActive)
		{
			//Changes the javax.swing buffered image to a javafx image
			javafx.scene.image.Image entityImg = SwingFXUtils.toFXImage(
					this.img.getSubimage(this.imgRect.width*this.renderingCounter[1],
							this.imgRect.height*this.renderingCounter[2],
							this.imgRect.width,
							this.imgRect.height),
							null);
			// Draws the new image on the screen
			gc.drawImage(entityImg,
					this.Position.X,
					this.Position.Y,
					this.imgRect.width*Global.SCALE,
					this.imgRect.height*Global.SCALE);
			
		}
	}
}

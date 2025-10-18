package gameEngine;


public class Tile {
	
	private int width; //width of the tile
	private int height; //Height of the tile
	private int stoppableDirection; // which sides are not passable
	private boolean isDamage; // does it cause damage
	private boolean isActive; //Is it a moving tile
	private java.awt.image.BufferedImage img; //The image of the tile
	
	//Which tile set the tile belongs to and where on the tileset it is
	private String tileSetName; 
	private Vector2 location; 
	
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public int getStoppableSides() {return stoppableDirection;}
	public boolean IsDamage() {return isDamage;}
	public boolean IsActive() {return isActive;}
	public java.awt.image.BufferedImage getImg() {return img;}
	
	//Sets the image if the tileset is not needed
	public void setImage(java.awt.image.BufferedImage value) {img = value;}
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
		setImage();
	}
	
	/**
	 * Sets the Image of the Tile.
	 * Requires to be initialized
	 */
	private void setImage() {
		//Tries to set the image of the tile as long as the tile set name is set and its location on that set
		try {			
			for(TileSet e : Global.MASTER_TILESET_LIST){
				if(e.getName().equals(this.tileSetName)) {
					this.img = e.getImg().getSubimage(
							(int)location.X, 
							(int)location.Y, width, height);	
				}
			}
		}catch(Exception e) {e.printStackTrace();}
	}
}

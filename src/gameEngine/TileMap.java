package gameEngine;

import com.google.gson.annotations.SerializedName;

import javafx.embed.swing.SwingFXUtils;



public class TileMap {
	
	@SerializedName("MapName")
	private String name;
	@SerializedName("TilesOnMap")
	private Tile[][] tilesOnMap;
	@SerializedName("XTiles")
	private int xTiles;
	@SerializedName("YTiles")
	private int yTiles;
	
	public String getName() {return name;}
	public int getYTiles() {return yTiles;}
	public int getXTiles() {return xTiles;}
	
	public TileMap() {
		xTiles = 0;
		yTiles = 0;
		name = "DefaultTileMap";
		tilesOnMap = new Tile[xTiles][yTiles];
	}
	
	public void setSize(int x, int y)
	{
		xTiles = x;
		yTiles = y;
	}
	
	public void LoadTileMapData(Tile tile, Vector2 position) {
		if(tilesOnMap != null)
			tilesOnMap[(int)position.X][(int)position.Y] = tile;
		/*
		 * TODO: write to error log on the computer 
		 */
	}
	
	public void LoadTileSetImage() {
		for(int x = 0; x < xTiles; x++) {
			for(int y = 0; y < yTiles; y++) {
				if(tilesOnMap[x][y] != null) {
					tilesOnMap[x][y].LoadImage();
				}
			}
		}
	}
	
	public void Update() {
		
	}
	
	/**
	 * Draws the Tiles on the screen creating a map background 
	 * of tiles
	 * @param g
	 */
	public void Draw(javafx.scene.canvas.GraphicsContext g) {
		for(int x = 0;x < xTiles;x++)
		{
			for(int y = 0; y < yTiles;y++) {
				
				//TODO Add in camera positioning once Camera Class is completed and updated
				
				g.drawImage(SwingFXUtils.toFXImage(tilesOnMap[x][y].getImg(), null),
						(x*tilesOnMap[x][y].getWidth()*Global.SCALE)+Global.CAMERA.Position.X,
						(y*tilesOnMap[x][y].getHeight()*Global.SCALE)+Global.CAMERA.Position.Y,
						tilesOnMap[x][y].getWidth()*Global.SCALE,
						tilesOnMap[x][y].getHeight()*Global.SCALE);
			}
		}
		
	}
}

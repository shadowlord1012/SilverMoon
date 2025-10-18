package gameEngine;


public class TileMap {
	
	private String name;
	private Tile[][] tilesOnMap;
	private int xTiles;
	private int yTiles;
	
	public String getName() {return name;}
	public int getYTiles() {return yTiles;}
	public int getXTiles() {return xTiles;}
	
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
				
				//TODO Add in counter if the tile image has movement
				
				javafx.scene.image.Image tileImage = 
						javafx.embed.swing.SwingFXUtils.toFXImage(
								tilesOnMap[x][y].getImg().getSubimage(0+(tilesOnMap[x][y].getWidth()),
										0,
										tilesOnMap[x][y].getWidth(),
										tilesOnMap[x][y].getHeight()),null);
				
				//TODO Add in camera positioning once Camera Class is completed and updated
				
				g.drawImage(tileImage,
						(x*tilesOnMap[x][y].getWidth()*Global.SCALE)+Global.CAMERA.Position.X,
						(y*tilesOnMap[x][y].getHeight()*Global.SCALE)+Global.CAMERA.Position.Y,
						tilesOnMap[x][y].getWidth()*Global.SCALE,
						tilesOnMap[x][y].getHeight()*Global.SCALE);
			}
		}
		
	}
}

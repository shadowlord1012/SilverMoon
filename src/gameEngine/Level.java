package gameEngine;

public class Level {

	private String name;
	private java.util.Map<String,TileMap> tileMaps;
	
	public String getLevelName() {return name;}
	public TileMap getTileMap(String value) {return tileMaps.get(value);}
	
	/**
	 * Creates the Level
	 * @param name
	 */
	public Level(String name) {
		this.name = name;
		tileMaps = new java.util.HashMap<>();
	}
	
	/**
	 * Adds a map to the level
	 * @param map
	 */
	public void LoadMap(TileMap map) {
		if(!this.tileMaps.containsKey(map.getName()))
			this.tileMaps.put(map.getName(), map);
	}
	
	/**
	 * Updates the Levels maps
	 * @param currentMap
	 */
	public void Update(String currentMap) {
		this.tileMaps.get(currentMap).Update();
	}
	
	/**
	 * Draws the Levels Maps
	 * @param gc
	 * @param currentMap
	 */
	public void DrawTileMap(javafx.scene.canvas.GraphicsContext gc, String currentMap) {
		if(this.tileMaps.containsKey(currentMap))
			this.tileMaps.get(currentMap).Draw(gc);
	}
}

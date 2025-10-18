package gameEngine;

import java.util.ArrayList;
import java.util.List;

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
		
		//creates a list to hold all tile maps
		List<TileMap> maps = new ArrayList<>();
		
		//gets all tile map data from the data loader
		maps = Global.DATA_LOADER.LoadTileData(name);
		
		//checks if the map list is not empty
		if(!maps.isEmpty()) {
			
			//loads each map into the level
			for(TileMap map : maps) {
				map.LoadTileSetImage();
				LoadMap(map);
			}
		}
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

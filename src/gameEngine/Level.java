package gameEngine;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

public class Level {

	private String name;
	private Map<String,TileMap> tileMaps;
	
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

	
	public void LoadLevelData() {
		
		Future<List<TileMap>> mapFuture = Global.DATA_LOADER.loadingTileMapData(Global.CURRENT_LEVEL);
		
		try {
			List<TileMap> maps = mapFuture.get();

			//checks if the map list is not empty
			if(!maps.isEmpty()) {			

				//loads each map into the level
				for(TileMap map : maps) {
					map.LoadTileSetImage();
					LoadMap(map);
					System.out.printf("Tile Map %s loaded \n", map.getName());
				}
			}
			System.out.printf("%s Loaded \n", name);
		} catch(Exception e) {e.printStackTrace();}
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
	
}

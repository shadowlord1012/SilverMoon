package gameEngine;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javafx.scene.canvas.GraphicsContext;

public class Level {

	private String name;
	private Map<String,TileMap> tileMaps;
	private List<Item> itemList;
	
	public String getLevelName() {return name;}
	public TileMap getTileMap(String value) {return tileMaps.get(value);}
	
	/**
	 * Creates the Level
	 * @param name
	 */
	public Level(String name) {
		this.name = name;
		tileMaps = new java.util.HashMap<>();

		setItemList(Global.ITEM_DIRECTORY);
		
		getItemList().forEach(e -> {
			e.setPosition(new Vector2((Math.random()*100)+50, (Math.random()*100)+50));
		});
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
	
	public void DrawItems(GraphicsContext gc) {
		getItemList().forEach(e -> {
			if(e.getImage() != null)
				gc.drawImage(e.getImage(), 
					e.getPosition().X + Global.CAMERA.Position.X, 
					e.getPosition().Y + Global.CAMERA.Position.Y,
					e.getImage().getWidth()*Global.SCALE,
					e.getImage().getHeight()*Global.SCALE);
		});
	}
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
		
	}
	
}

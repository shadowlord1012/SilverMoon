package gameEngine;

import java.util.ArrayList;
import java.util.List;

public class World {
	private List<Level> levelList;
	
	public Level currentLevel(String value){
		for(Level e : levelList) {
			if(e.getLevelName().equals(value))
				return e;
		}
		return null;
	}
	/**
	 * Creates the world 
	 */
	public World() {
		levelList = new ArrayList<Level>();
		Initialize();
	}
	
	private void Initialize() {
		//TODO: Add in dataLoader information here
	}
	
	/**
	 * Adds a new level to the list of Levels
	 * @param level
	 */
	public void AddLevel(Level level) {
		if(!levelList.contains(level))
			levelList.add(level);
	}
	
	/**
	 * Updates the Level in the world 
	 * @param currentMap
	 */
	public void Update(String currentMap) {
		levelList.forEach(e -> e.Update(currentMap));
	}
	
	/**
	 * Draws the level on the screen
	 * @param gc
	 * @param currentMap
	 */
	public void Draw(javafx.scene.canvas.GraphicsContext gc, String currentMap) {
		levelList.forEach(e -> e.DrawTileMap(gc, currentMap));
	}
}

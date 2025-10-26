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
		//TODO: Change to a file that has all the levels to upload with.
		Level levelOne = new Level("levelOne");
		
		//Adds the level to the world
		AddLevel(levelOne);
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
	
}

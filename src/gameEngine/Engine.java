package gameEngine;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;

public class Engine {

	private GraphicsContext gc;
	private Map<String,World> gameWorldDirectory = new HashMap<>();
	private String worldName = "Map1";
	public static Audio audio;
	private Player player;
	private GraphicsController graphics;
	
	public Engine(GraphicsContext gc) {
		audio = new Audio();
		this.gc = gc;
		player = Global.DATA_LOADER.LoadPlayerData("Link");
		player.IsActive = true;
		Initialize();
	}
	
	private void Initialize() {
		//TODO : Add in information from the data loader
		World mainWorld = new World();
		gameWorldDirectory.put("Map1", mainWorld);
		graphics = new GraphicsController(gameWorldDirectory,player);
	}
	
	public void Update() {
		
		if(gameWorldDirectory.containsKey(worldName)) 
			gameWorldDirectory.get(worldName).Update(worldName);
		
		player.Update(gameWorldDirectory.get(worldName));
		
		Collision.tileCollision(gameWorldDirectory.get(worldName).currentLevel("levelOne").getTileMap(worldName), player);
		
		graphics.Update(gameWorldDirectory, player);
	}
	
	public void Draw() {
		gc.clearRect(0, 0, Global.RENDER_X, Global.RENDER_Y);
		
		graphics.Draw(gc);
	}
}

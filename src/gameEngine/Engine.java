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
	
	public Engine(GraphicsContext gc) {
		audio = new Audio();
		this.gc = gc;
		player = new Player();
		Initialize();
	}
	
	private void Initialize() {
		//TODO : Add in information from the data loader
		World mainWorld = new World();
		gameWorldDirectory.put("Map1", mainWorld);
	}
	
	public void Update() {
		
		if(gameWorldDirectory.containsKey(worldName)) 
			gameWorldDirectory.get(worldName).Update(worldName);
		
		//player.Update(gameWorldDirectory.get(worldName));
	}
	
	public void Draw() {
		gc.clearRect(0, 0, Global.RENDER_X, Global.RENDER_Y);
		
		if(gameWorldDirectory.containsKey(worldName))
			gameWorldDirectory.get(worldName).Draw(gc, worldName);
		
		//player.Draw(gc);
	}
}

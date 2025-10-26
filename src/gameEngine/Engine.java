package gameEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;


import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Engine implements Runnable{

	private GraphicsContext gc;
	private Map<String,World> gameWorldDirectory = new HashMap<>();
	private String worldName = "Map1";
	public static Audio audio;
	private Player player;
	private GraphicsController graphics;
	//private Map<String,Item> itemDirectory = new HashMap<>();
	
	private volatile boolean running = true;
	private long lastTime = System.nanoTime();
	private double nsPerTick = 1000000000/60D;
	private double delta = 0;
	
	public GraphicsContext getGraphics() {return gc;}
	public void setGraphics(GraphicsContext value) { gc = value;}
	
	public Engine(GraphicsContext gc) {

		this.gc = gc;
		
		//Sets all the values need for the Engine
		Initialize();
	}
	
	private void Initialize() {
		//TODO : Add in information from the data loader
		
		//creates the Audio object
		audio = new Audio();
				
		//Loads in the player data
		player = Global.DATA_LOADER.LoadPlayerData("Link");
		player.IsActive = true;
		player.setHUD(new HeadsUpDisplay(player));
		player.getStatusMap().put("magiccurrent", 4.0);
		player.getStatusMap().put("healthcurrent",  10.0);
				
		Future<World> worldLoading = Global.DATA_LOADER.loadingWorld();
		
		try {
			World world = worldLoading.get();
			world.currentLevel(Global.CURRENT_LEVEL).LoadLevelData();
			//adds it to the directory
			gameWorldDirectory.put("Map1", world);
			
			//creates the graphics controller
			graphics = new GraphicsController(gameWorldDirectory,player);

		}catch(Exception e) {e.printStackTrace();}

		//plays the start music
		audio.playBGM("Theme.wav");
		
	}
	
	public void setGraphicsContext(Canvas canvas)
	{
		//sets the graphics context
		gc = canvas.getGraphicsContext2D();
		
		//clears the screen
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		//resets the render values
		Global.RENDER_X = (int) canvas.getWidth();
		Global.RENDER_Y = (int) canvas.getHeight();
	}
	
	public void Update() {
		
		
		//updates the world
		if(gameWorldDirectory.containsKey(worldName)) 
			gameWorldDirectory.get(worldName).Update(worldName);
		
		//updates the player
		player.Update(gameWorldDirectory.get(worldName));
		
		//updates the collision in relevance to the player
		Collision.tileCollision(gameWorldDirectory.get(worldName).currentLevel(Global.CURRENT_LEVEL).getTileMap(Global.TILE_MAP_NAME), player);
		Collision.teleportLocationCollision(gameWorldDirectory.get(worldName).currentLevel(Global.CURRENT_LEVEL).getTileMap(Global.TILE_MAP_NAME), player);
		
		//updates the graphic controller
		graphics.Update(gameWorldDirectory, player);
		
	}
	
	public void Draw() {
		
		gc.clearRect(0, 0, Global.RENDER_X, Global.RENDER_Y);
		
		graphics.Draw(gc);
	}
	
	public void stop(){
		running = false;
	}
	
	@Override
	public void run() {
		
		// TODO Auto-generated method stub
		while(running) {
			long now = System.nanoTime();
			
			delta+=(now-lastTime)/nsPerTick;
			lastTime = now;
			while(delta >= 1)
			{
				Update();
				
				delta-=1;
				
			}
			
			Platform.runLater(this::Draw);
			
			try {
				Thread.sleep(1);
			}catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

}

package gameEngine;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.scene.canvas.Canvas;

public class DataLoader {
	
	private final ExecutorService executor = Executors.newFixedThreadPool(3);
	
	public DataLoader() {
		
	}
	
	public Future<Engine> loadingEngine(Canvas canvas){
		Callable<Engine> loadingEngine = () ->{
			Engine game = new Engine(canvas.getGraphicsContext2D());
			canvas.widthProperty().addListener(obs -> {
				game.setGraphicsContext(canvas);
			});
			
			canvas.heightProperty().addListener(obs -> {
				game.setGraphicsContext(canvas);
			});
			return game;
		};
		
		return executor.submit(loadingEngine);
	}
	
	public Future<World> loadingWorld(){
		
		Callable<World> loadingWorld = () -> {
			System.out.println("Loading World");
			World world = new World();
			return world;
		};
		
		return executor.submit(loadingWorld);
	}
	
	public Future<List<TileMap>> loadingTileMapData(String levelName)
	{
		
		Callable<List<TileMap>> loadingMap = () -> {
			System.out.println("Loading Tile Maps...");
			//creates a list to hold all tile maps
			List<TileMap> tileMap = new ArrayList<>();
			
			//gets the directory of tile maps
			File directory = new File("Resources/TileMaps/" + levelName+"/");
			
			//lists all files in the directory that end with .tilemap
			File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".tilemap"));
			
			//loops through all files and loads them into the tile map list
			if(files != null ) {
				for(File mapfiles : files) {
					try (FileReader reader = new FileReader(mapfiles)) {
						Gson gson = new GsonBuilder().create();
						tileMap.add(gson.fromJson(reader, TileMap.class));
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			System.out.println("Tile Maps loaded!");
			return tileMap;
		};
		
		return executor.submit(loadingMap);
	}
	
	
	public Player LoadPlayerData(String playerName) {
		
		//creates a player object to hold the data
		Player player = new Player();
		
		//gets the player data file
		File playerFile = new File("Resources/Player/" + playerName + ".player");
		
		//loads the player data from the file
		try (FileReader reader = new FileReader(playerFile)) {
			Gson gson = new GsonBuilder().create();
			player = gson.fromJson(reader, Player.class);
			
			player.LoadImage(playerName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return player;
	}
	
	public void shutdown() {
		executor.shutdown();
	}
}

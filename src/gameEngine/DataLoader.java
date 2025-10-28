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
	
	public Future<List<Item>> loadItems(){
		
		//creates a call function to be executed at a later time
		Callable<List<Item>> loadingItems = () -> {
			
			//creates an list of all the items
			List<Item> itemList = new ArrayList<>();
			
			//gets all the files in the Item directory
			File directory = new File("Resources/Items/");
			
			//gets all the files in that directory
			File[] files = directory.listFiles((dir,name) -> name.toLowerCase().endsWith(".item"));
			
			//Makes sure the list is not empty or null
			if(files != null)
				
				//for every file in the directory
				for(File itemFile : files)
				{
					// read in the file
					try (FileReader reader = new FileReader(itemFile)) {
						
						//create the Gson object 
						Gson gson = new GsonBuilder().create();
						
						//add to the item to the list though the converting of a json file to item class through gson
						itemList.add(gson.fromJson(reader, Item.class));
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			
			//returns the item list
			return itemList;
		};
		
		//returns the call function
		return executor.submit(loadingItems);
		
	}
	
	public Future<Engine> loadingEngine(Canvas canvas){
		
		//creates a call function to be executed at a later time
		Callable<Engine> loadingEngine = () ->{
			
			//creates the game engine
			Engine game = new Engine(canvas.getGraphicsContext2D());
			
			//Adds the appropriate listeners that are needed
			canvas.widthProperty().addListener(obs -> {
				game.setGraphicsContext(canvas);
			});
			
			canvas.heightProperty().addListener(obs -> {
				game.setGraphicsContext(canvas);
			});
			
			//returns the game
			return game;
		};
		
		//returns the call function
		return executor.submit(loadingEngine);
	}
	
	public Future<World> loadingWorld(){
		
		//creates a call function to be executed at a later time
		Callable<World> loadingWorld = () -> {
			
			//creates the world object
			World world = new World();
			
			//returns the world object
			return world;
		};
		
		//returns the call function
		return executor.submit(loadingWorld);
	}
	
	public Future<List<TileMap>> loadingTileMapData(String levelName)
	{
		
		Callable<List<TileMap>> loadingMap = () -> {
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

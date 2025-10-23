package gameEngine;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataLoader {
	
	public DataLoader() {
		
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
	
	public List<TileMap> LoadTileData(String levelName) {
		
		//creates a list to hold all tile maps
		List<TileMap> tileMap = new ArrayList<>();
		
		//gets the directory of tile maps
		File directory = new File("Resources/TileMaps/" + levelName 	+"/");
		
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
	}
}

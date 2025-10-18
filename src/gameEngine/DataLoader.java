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

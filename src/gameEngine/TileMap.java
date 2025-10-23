package gameEngine;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;




public class TileMap {
	
	@SerializedName("MapName")
	private String name;
	
	@SerializedName("BottomLayer")
	private Tile[][] bottomTileLayer;

	@SerializedName("MiddleLayer")
	private Tile[][] middleTileLayer;

	@SerializedName("TopLayer")
	private Tile[][] topTileLayer;

	@SerializedName("TransportLayer")
	private Tile[][] transportLayer;
	
	@SerializedName("Layers")
	private Map<String, Tile[][]> tileLayers = new HashMap<>();
	
	@SerializedName("XTiles")
	private int xTiles;
	
	@SerializedName("YTiles")
	private int yTiles;
	
	public String getName() {return name;}
	public int getYTiles() {return yTiles;}
	public int getXTiles() {return xTiles;}
	
	public Map<String,Tile[][]> getTileLayers() {return tileLayers;}
	
	public Tile[][] getTilesOnMapnbylayer(String layer) {
		switch(layer.toLowerCase()) 
		{
			case "bottom":
				return bottomTileLayer;
			case "middle":
				return middleTileLayer;
			case "top":
				return topTileLayer;
			case "transport":
				return transportLayer;
			default:
				return null;
		}
	}
	
	public TileMap() {
		xTiles = 0;
		yTiles = 0;
		name = "DefaultTileMap";
		bottomTileLayer = new Tile[0][0];
		middleTileLayer = new Tile[0][0];
		topTileLayer = new Tile[0][0];
		transportLayer = new Tile[0][0];
	}
	
	public TileMap(int x, int y)	{
		bottomTileLayer = new Tile[x][y];
		middleTileLayer = new Tile[x][y];
		topTileLayer = new Tile[x][y];
		transportLayer = new Tile[x][y];
		xTiles = x;
		yTiles = y;
	}
	
	public void setSize(int x, int y)
	{
		xTiles = x;
		yTiles = y;
	}
	
	public void LoadTileMapData(Tile tile, Vector2 position, String layerName) {
		tileLayers.remove(layerName);
		
		switch(layerName.toLowerCase())
		{
		case "bottom":
			bottomTileLayer[(int)position.X][(int)position.Y] = tile;
				tileLayers.put(layerName, bottomTileLayer);
			break;
		case "middle":
			middleTileLayer[(int)position.X][(int)position.Y] = tile;
				tileLayers.put(layerName, middleTileLayer);
			break;
		case "top":
			topTileLayer[(int)position.X][(int)position.Y] = tile;
				tileLayers.put(layerName, topTileLayer);
			break;
		case "transport":
			transportLayer[(int)position.X][(int)position.Y] = tile;
				tileLayers.put(layerName, transportLayer);
			break;
		}
	}
	
	public void LoadTileSetImage() {
		
		tileLayers.forEach((layer,e) ->
		{
			for(int x = 0; x < xTiles; x++) {
				for(int y = 0; y < yTiles; y++) {
					if(e[x][y] != null)
						e[x][y].LoadImage();					
				}
			}
		});
	}
	
	public void Update() {
		
	}
}

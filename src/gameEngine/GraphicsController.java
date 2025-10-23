package gameEngine;


import java.util.Map;

import javafx.scene.canvas.GraphicsContext;

public class GraphicsController {
	
	private Map<String,World> world;
	private Player player;
	//private List<Npc> npc;
	//private List<Monster> monster;
	
	public GraphicsController(Map<String,World> worldRef, Player playerRef) {
		world = worldRef;
		player = playerRef;
	}
	
	public void Update(Map<String,World> worldRef, Player playerRef) {
		world = worldRef;
		player = playerRef;
	}
	
	public void Draw(GraphicsContext gc) {
		world.forEach((name,level) ->{
			
			int xTiles = level.currentLevel("levelOne").getTileMap("Map1").getXTiles();
			int yTiles = level.currentLevel("levelOne").getTileMap("Map1").getYTiles();
			
			level.currentLevel("levelOne") //Gets the level of the world
			.getTileMap("Map1") //gets the map of the level
				.getTileLayers() //gets all the layers in that map on the level
				.forEach((layer,map) -> {
					if(layer.toLowerCase().equals("bottom"))
					{
						for(int i = 0; i < xTiles; i++) {
							for(int j = 0; j < yTiles; j++) {
								if(map[i][j] != null) {
									gc.drawImage(map[i][j].getImg(), 
											(i*map[i][j].getWidth()*Global.SCALE)+Global.CAMERA.Position.X,
											(j*map[i][j].getHeight()*Global.SCALE)+Global.CAMERA.Position.Y,
											map[i][j].getWidth()*Global.SCALE,
											map[i][j].getHeight()*Global.SCALE);
								}
							}
						}
					}
					if(layer.toLowerCase().equals("middle"))
					{
						for(int i = 0; i < xTiles; i++) {
							for(int j = 0; j < yTiles; j++) {
								if(map[i][j] != null) {
									gc.drawImage(map[i][j].getImg(), 
											(i*map[i][j].getWidth()*Global.SCALE)+Global.CAMERA.Position.X,
											(j*map[i][j].getHeight()*Global.SCALE)+Global.CAMERA.Position.Y,
											map[i][j].getWidth()*Global.SCALE,
											map[i][j].getHeight()*Global.SCALE);
								}
							}
						}
					}
					//Draw the player on the screen
					player.Draw(gc);
					
					//TODO: once monsters are to be added in.
					/*
					monster.forEach(e -> {
						if(e.IsActive)
						{
							e.Draw(gc);
						}
					});
					npc.forEach(e -> {
						if(e.IsActive)
						{
							e.Draw(gc);
						}
					});
					*/
					
					if(layer.toLowerCase().equals("top"))
					{
						for(int i = 0; i < xTiles; i++) {
							for(int j = 0; j < yTiles; j++) {
								if(map[i][j] != null) {
									gc.drawImage(map[i][j].getImg(), 
											(i*map[i][j].getWidth()*Global.SCALE)+Global.CAMERA.Position.X,
											(j*map[i][j].getHeight()*Global.SCALE)+Global.CAMERA.Position.Y,
											map[i][j].getWidth()*Global.SCALE,
											map[i][j].getHeight()*Global.SCALE);
								}
							}
						}
					}
					if(layer.toLowerCase().equals("transport"))
					{
						for(int i = 0; i < xTiles; i++) {
							for(int j = 0; j < yTiles; j++) {
								if(map[i][j] != null) {
									gc.drawImage(map[i][j].getImg(), 
											(i*map[i][j].getWidth()*Global.SCALE)+Global.CAMERA.Position.X,
											(j*map[i][j].getHeight()*Global.SCALE)+Global.CAMERA.Position.Y,
											map[i][j].getWidth()*Global.SCALE,
											map[i][j].getHeight()*Global.SCALE);
								}
							}
						}
					}
				});
		});
	}
}

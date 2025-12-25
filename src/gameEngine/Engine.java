package gameEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;


import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Engine implements Runnable{

	private GraphicsContext gc;
	private Map<String,World> gameWorldDirectory = new HashMap<>();
	private List<Ability> abilityDirectory = new ArrayList<Ability>();
	private String worldName = "Map1";
	private Player player;
	private GraphicsController graphics;
	private Audio audio;
	//private Map<String,Item> itemDirectory = new HashMap<>();
	private int counter;
	
	private volatile boolean running = true;
	private long lastTime = System.nanoTime();
	private double nsPerTick = 1000000000/60D;
	private double delta = 0;
	
	public Audio getAudio() {return audio;}
	public Player getPlayer() {return player;}
	public void setAudio(Audio value) {audio = value;}
	public GraphicsContext getGraphics() {return gc;}
	public void setGraphics(GraphicsContext value) { gc = value;}
	
	public Engine(GraphicsContext gc) {

		this.gc = gc;
		
		//Sets all the values need for the Engine
		Initialize();
	}
	
	private void Initialize() {
		//TODO : Add in information from the data loader
		
		Future<List<Ability>> abilitiesListLoading = Global.DATA_LOADER.loadAbilities();
		Future<List<Item>> itemListLoading = Global.DATA_LOADER.loadItems();
		
		
		//Loads in the player data
		player = Global.DATA_LOADER.LoadPlayerData("Link");
		player.IsActive = true;
		player.setHUD(new HeadsUpDisplay(player));
		player.getStatusMap().put("magiccurrent", 10.0);
		player.getStatusMap().put("healthcurrent",  2.0);
			
		//Starts the game timer to keep track of time played
		Global.GAME_TIME = new gameEngine.GameTime();
		
		Future<World> worldLoading = Global.DATA_LOADER.loadingWorld();
		
		try {
			abilityDirectory = abilitiesListLoading.get();
			System.out.println("Abilities Loaded: " + abilityDirectory.size());
		}catch(Exception e) {e.printStackTrace();}
		
		try {
			Global.ITEM_DIRECTORY = itemListLoading.get();
			System.out.println("Items Loaded: " + Global.ITEM_DIRECTORY.size());
			
		}catch(Exception e) {e.printStackTrace();}
		
		try {
			World world = worldLoading.get();
			world.currentLevel(Global.CURRENT_LEVEL).LoadLevelData();
			//adds it to the directory
			gameWorldDirectory.put("Map1", world);
			//adds starting items to the player inventory
			player.statusScreenController.getInventoryScreen()
					.AddItemToInventory(Global.ITEM_DIRECTORY.stream().filter(item -> item.getName().equals("HealthPotionOne")).findFirst().orElse(null));
			System.out.println("loading player abilities");
			player.loadAbilities(abilityDirectory);
			for(Ability ability : player.getUsableAbilities()) {
				if(ability != null) {
					System.out.println("Loaded Ability: " + ability.getName());
					ability.getCooldown()[1] = ability.getMaxCooldown();
					ability.getDuration()[1] = ability.getMaxDuration();
					ability.LoadImage();
				}
			}
			player.setCurrentAbility(player.getUsableAbilities()[0]);
			//creates the graphics controller
			graphics = new GraphicsController(gameWorldDirectory,player);

		}catch(Exception e) {e.printStackTrace();}

		
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
		gameWorldDirectory.get(worldName).currentLevel(Global.CURRENT_LEVEL).getItemList().forEach(item -> {
			Collision.ItemCollision(player, item);
		});
		
		counter++;
		if(counter >= 50)
			counter = 50;
		
		if(KeyHandlerController.UpDown[0] && counter == 50) {
			player.changeStatusByPair("magiccurrent", -0.5);
			counter = 0;
		}
		if(KeyHandlerController.UpDown[1]&& counter == 50) {
			player.changeStatusByPair("magiccurrent", 0.5);
			counter = 0;
		}
			
		
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
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		//Game Loop
		while(running) {
			
			//Calculates delta time
			long now = System.nanoTime();
			delta+=(now-lastTime)/nsPerTick;
			lastTime = now;
			
			//Updates the game logic based on delta time
			while(delta >= 1)
			{
				Update();
				
				delta-=1;
				
			}
			
			//Draws the game
			Platform.runLater(this::Draw);
			
			
			//Sleeps the thread to prevent overuse of CPU
			try {
				Thread.sleep(1);
			}catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

}

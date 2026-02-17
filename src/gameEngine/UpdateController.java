package gameEngine;

import java.util.Map;

public class UpdateController {
	
	public void Update(Map<String,World> gameWorldDirectory,Player player, String worldName,
			GraphicsController graphics, Audio audio) {
		
		
		//updates the player
		UpdatePlayer(gameWorldDirectory.get(worldName), player, audio);
		
		//updates the collision in relevance to the player
		Collision.tileCollision(gameWorldDirectory.get(worldName).currentLevel(Global.CURRENT_LEVEL).getTileMap(Global.TILE_MAP_NAME), player);
		Collision.teleportLocationCollision(gameWorldDirectory.get(worldName).currentLevel(Global.CURRENT_LEVEL).getTileMap(Global.TILE_MAP_NAME), player);
		gameWorldDirectory.get(worldName).currentLevel(Global.CURRENT_LEVEL).getItemList().forEach(item -> {
			Collision.ItemCollision(player, item);
		});
		
		//updates the graphic controller
		graphics.Update(gameWorldDirectory, player);			 
	}

	private void UpdatePlayer(World world, Player player, Audio audio) {
		//Updates the player movement
		if(!player.getStatusScreenController().isOpen())
		{
		PlayerMovement(world, player);
		
		//Updates the player camera movement
		PlayerCameraMovement(world, player);
		player.Update(world);
		
		}
		
		player.getStatusScreenController().Update();
		player.getHeadUpDisplay().Update(player);
		
		//Updates the player using an ability
		PlayerUseAbility(world, player, audio);
	}
	
	private void PlayerMovement(World world, Player player) {
		//Depending on the input of the User
				if(KeyHandlerController.Movement[0]|| KeyHandlerController.Movement[1]
						|| KeyHandlerController.Movement[2] || KeyHandlerController.Movement[3])
				{
					player.isMoving = true;
					
					/*
					 * When the character is moving up on the screen
					 */
					if(KeyHandlerController.Movement[0])
					{
						//Keeps the player on the screen and adjusts if needed
						if(player.getPosition().Y+(player.getImgRect().height/2) < 0 && Global.CAMERA.Position.Y <=0)
							player.setPosition( new Vector2(player.getPosition().X, player.getPosition().Y));
						else
							player.setPosition( new Vector2(player.getPosition().X, player.getPosition().Y-player.MovementSpeed()));
						
						//Sets the row in which the Image is located on the sprite sheet
						player.SetRow(4);
						
						player.setLastKnownDirection(1);
					}
					
					/*
					 * When the character is moving to the left on the screen
					 */
					else if(KeyHandlerController.Movement[1])
					{
						//Keeps the player on the screen and adjusts if needed
						if(player.getPosition().X+(player.getImgRect().width/2) < 0 && Global.CAMERA.Position.X <=0)
							player.setPosition( new Vector2(player.getPosition().X, player.getPosition().Y));
						else
							player.setPosition( new Vector2(player.getPosition().X-player.MovementSpeed(), player.getPosition().Y));

						//Sets the row in which the Image is located on the sprite sheet
						player.SetRow(7);
						player.setLastKnownDirection(4);
					}
					/*
					 * When the character is moving to the down on the screen
					 */
					else if(KeyHandlerController.Movement[2])
					{
						//Keeps the player on the screen and adjusts if needed
						if(player.getPosition().Y + player.MovementSpeed()+(-Global.CAMERA.Position.Y) > 
						(world.currentLevel(Global.CURRENT_LEVEL).getTileMap(Global.TILE_MAP_NAME).getYTiles()*Global.SCALE*Global.TILE_SIZE)-(player.getImgRect().height*2)-82 &&
						Global.CAMERA.Position.Y < 0)
							player.setPosition( new Vector2(player.getPosition().X, player.getPosition().Y));
						else 
							player.setPosition( new Vector2(player.getPosition().X, player.getPosition().Y+player.MovementSpeed()));

						//Sets the row in which the Image is located on the sprite sheet
						player.SetRow(6);
						player.setLastKnownDirection(3);
					}
					/*
					 * WHen the character is moving right on the screen
					 */
					else if(KeyHandlerController.Movement[3])
					{
						//Keeps the player on the screen and adjusts if needed
						if(player.getPosition().X + player.MovementSpeed()+(-Global.CAMERA.Position.X) >
						(world.currentLevel(Global.CURRENT_LEVEL).getTileMap(Global.TILE_MAP_NAME).getXTiles()*Global.SCALE*Global.TILE_SIZE)-(player.getImgRect().width*2)-50 &&
						Global.CAMERA.Position.X < 0)
							player.setPosition( new Vector2(player.getPosition().X, player.getPosition().Y));
						else 
							player.setPosition( new Vector2(player.getPosition().X+player.MovementSpeed(), player.getPosition().Y));

						//Sets the row in which the Image is located on the sprite sheet
						player.SetRow(5);
						player.setLastKnownDirection(2);
					}
					else {
					}
				}
				else
				{
					player.isMoving = false;
				}
	}
	
	private void PlayerCameraMovement(World world, Player player) {
		//Camera Movement to the right 
				if(player.getPosition().X + (player.getImgRect().width) > (Global.RENDER_X-player.getImgRect().width - 10) || 
						(player.getPosition().X + (player.getImgRect().width) > 
							(world.currentLevel(Global.CURRENT_LEVEL)
									.getTileMap(Global.TILE_MAP_NAME)
									.getXTiles()*Global.SCALE*Global.TILE_SIZE)-(player.getImgRect().width)))
				{
					player.setPosition( new Vector2 (player.getPosition().X-player.MovementSpeed(), player.getPosition().Y));
					Global.CAMERA.Position.X -= player.MovementSpeed();
				}
				
				//camera movement to the left
				if(player.getPosition().X < 0 && Global.CAMERA.Position.X < 0)
				{
					player.setPosition( new Vector2(0,player.getPosition().Y));
					Global.CAMERA.Position.X += player.MovementSpeed();
				}
				
				//camera movement down
				if(player.getPosition().Y +(player.getImgRect().height) > (Global.RENDER_Y-player.getImgRect().height - 80) || 
						(player.getPosition().X + (player.getImgRect().width) >
						(world.currentLevel(Global.CURRENT_LEVEL)
								.getTileMap(Global.TILE_MAP_NAME)
								.getXTiles()*Global.SCALE*Global.TILE_SIZE)-(player.getImgRect().height))) {
					player.setPosition( new Vector2(player.getPosition().X, player.getPosition().Y-player.MovementSpeed()));
					Global.CAMERA.Position.Y -= player.MovementSpeed();
				}
				
				//camera movement up
				if(player.getPosition().Y < 0 && Global.CAMERA.Position.Y < 0)
				{
					player.setPosition( new Vector2 (player.getPosition().X,0));
					Global.CAMERA.Position.Y += player.MovementSpeed();
				}
	}
	
	private void PlayerUseAbility(World world, Player player, Audio audio) {
		// gets the current ability the player is using
				if(KeyHandlerController.UseAbility && !player.getCurrentAbility().IsOnCoolDown()) {
					
					// checks if the player has enough magic current to use the ability
					if(player.getStatusByName("magiccurrent")-player.getCurrentAbility().getMagicCost() < 0) {
						return;
					}
					
					// reduces the magic current by the cost of the ability
					player.changeStatusByPair("magiccurrent",-player.getCurrentAbility().getMagicCost());
					
					// resets the cooldown counter to 0 to use the ability
					player.getCurrentAbility().getCooldown()[0] = 0; 
					
					// sets the ability to be on cooldown
					player.getCurrentAbility().setOnCoolDown(true); 
					
					// sets the ability to be moving
					player.getCurrentAbility().setIsActive(true);
					
					// sets the ability to be moving
					player.getCurrentAbility().setIsMoving(true); 
					
					// plays the sound effect of the ability
					audio.playSE(player.getUsableAbilities()[0].getAudioFile());
				}
	}
	
	private void UpdateTileMap(World world, String worldName) {
		world.currentLevel(worldName).getTileMap(worldName).getTileLayers().forEach((name,layer) -> {
			for(int i = 0; i < world.currentLevel(worldName).getTileMap(worldName).getXTiles(); i++) {
				for(int j = 0; j < world.currentLevel(worldName).getTileMap(worldName).getYTiles(); j++) {
					if(layer[i][j] != null) {
						if(layer[i][j].IsActive())
						{
						}
					}
				}
			}
		});
	}
}

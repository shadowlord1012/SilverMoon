package gameEngine;

public class Player extends Entity{
	
	private transient HeadsUpDisplay hud;
	
	public HeadsUpDisplay getHUD() { return hud;}
	public void setHUD(HeadsUpDisplay value) { hud = value;}
	public Player() {
		super();
		this.SetRow(6);
	}
	
	/**
	 * Sets the new position of the Player Character
	 * @param world
	 */
	private void movement(World world)
	{
		//Depending on the input of the User
		if(KeyHandlerController.Movement[0]|| KeyHandlerController.Movement[1]
				|| KeyHandlerController.Movement[2] || KeyHandlerController.Movement[3])
		{
			this.isMoving = true;
			
			/*
			 * When the character is moving up on the screen
			 */
			if(KeyHandlerController.Movement[0])
			{
				//Keeps the player on the screen and adjusts if needed
				if(this.getPosition().Y+(this.getImgRect().height/2) < 0 && Global.CAMERA.Position.Y <=0)
					this.setPosition( new Vector2(this.getPosition().X, this.getPosition().Y));
				else
					this.setPosition( new Vector2(this.getPosition().X, this.getPosition().Y-this.MovementSpeed()));
				
				//Sets the row in which the Image is located on the sprite sheet
				this.SetRow(4);
			}
			
			/*
			 * When the character is moving to the left on the screen
			 */
			else if(KeyHandlerController.Movement[1])
			{
				//Keeps the player on the screen and adjusts if needed
				if(this.getPosition().X+(this.getImgRect().width/2) < 0 && Global.CAMERA.Position.X <=0)
					this.setPosition( new Vector2(this.getPosition().X, this.getPosition().Y));
				else
					this.setPosition( new Vector2(this.getPosition().X-this.MovementSpeed(), this.getPosition().Y));

				//Sets the row in which the Image is located on the sprite sheet
				this.SetRow(7);
			}
			/*
			 * When the character is moving to the down on the screen
			 */
			else if(KeyHandlerController.Movement[2])
			{
				//Keeps the player on the screen and adjusts if needed
				if(this.getPosition().Y + this.MovementSpeed()+(-Global.CAMERA.Position.Y) > 
				(world.currentLevel(Global.CURRENT_LEVEL).getTileMap(Global.TILE_MAP_NAME).getYTiles()*Global.SCALE*Global.TILE_SIZE)-(this.getImgRect().height*2)-82 &&
				Global.CAMERA.Position.Y < 0)
					this.setPosition( new Vector2(this.getPosition().X, this.getPosition().Y));
				else 
					this.setPosition( new Vector2(this.getPosition().X, this.getPosition().Y+this.MovementSpeed()));

				//Sets the row in which the Image is located on the sprite sheet
				this.SetRow(6);
			}
			/*
			 * WHen the character is moving right on the screen
			 */
			else if(KeyHandlerController.Movement[3])
			{
				//Keeps the player on the screen and adjusts if needed
				if(this.getPosition().X + this.MovementSpeed()+(-Global.CAMERA.Position.X) >
				(world.currentLevel(Global.CURRENT_LEVEL).getTileMap(Global.TILE_MAP_NAME).getXTiles()*Global.SCALE*Global.TILE_SIZE)-(this.getImgRect().width*2)-50 &&
				Global.CAMERA.Position.X < 0)
					this.setPosition( new Vector2(this.getPosition().X, this.getPosition().Y));
				else 
					this.setPosition( new Vector2(this.getPosition().X+this.MovementSpeed(), this.getPosition().Y));

				//Sets the row in which the Image is located on the sprite sheet
				this.SetRow(5);
			}
			else {
			}
		}
		else
		{
			this.isMoving = false;
		}
	}
	
	/**
	 * Movement of the camera for the player
	 */
	private void cameraMovement(World world) {
		
		//Camera Movement to the right 
		if(this.getPosition().X + (this.getImgRect().width) > (Global.RENDER_X-this.getImgRect().width - 10) || 
				(this.getPosition().X + (this.getImgRect().width) > 
					(world.currentLevel(Global.CURRENT_LEVEL)
							.getTileMap(Global.TILE_MAP_NAME)
							.getXTiles()*Global.SCALE*Global.TILE_SIZE)-(this.getImgRect().width)))
		{
			this.setPosition( new Vector2 (this.getPosition().X-this.MovementSpeed(), this.getPosition().Y));
			Global.CAMERA.Position.X -= this.MovementSpeed();
		}
		
		//camera movement to the left
		if(this.getPosition().X < 0 && Global.CAMERA.Position.X < 0)
		{
			this.setPosition( new Vector2(0,this.getPosition().Y));
			Global.CAMERA.Position.X += this.MovementSpeed();
		}
		
		//camera movement down
		if(this.getPosition().Y +(this.getImgRect().height) > (Global.RENDER_Y-this.getImgRect().height - 80) || 
				(this.getPosition().X + (this.getImgRect().width) >
				(world.currentLevel(Global.CURRENT_LEVEL)
						.getTileMap(Global.TILE_MAP_NAME)
						.getXTiles()*Global.SCALE*Global.TILE_SIZE)-(this.getImgRect().height))) {
			this.setPosition( new Vector2(this.getPosition().X, this.getPosition().Y-this.MovementSpeed()));
			Global.CAMERA.Position.Y -= this.MovementSpeed();
		}
		
		//camera movement up
		if(this.getPosition().Y < 0 && Global.CAMERA.Position.Y < 0)
		{
			this.setPosition( new Vector2 (this.getPosition().X,0));
			Global.CAMERA.Position.Y += this.MovementSpeed();
		}
	}
	
	@Override
	public void Update(World w) {
		
		movement(w);
		cameraMovement(w);
		super.Update(w);
		hud.Update(this);
	}
	
	@Override
	public void Draw(javafx.scene.canvas.GraphicsContext gc) {
		
		super.Draw(gc);
	}
}
;
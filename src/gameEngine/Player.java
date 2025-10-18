package gameEngine;

public class Player extends Entity{
	
	public Player() {
		super();
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
				if(this.Position.Y+this.imgRect.height < 0 && Global.CAMERA.Position.Y <=0)
					this.Position = new Vector2(this.Position.X, this.Position.Y+this.MovementSpeed());
				else
					this.Position = new Vector2(this.Position.X, this.Position.Y-this.MovementSpeed());
				
				//Sets the row in which the Image is located on the sprite sheet
				this.SetRow(4);
			}
			
			/*
			 * When the character is moving to the left on the screen
			 */
			else if(KeyHandlerController.Movement[1])
			{
				//Keeps the player on the screen and adjusts if needed
				if(this.Position.X+this.MovementSpeed() < -this.imgRect.width && Global.CAMERA.Position.X <=0)
					this.Position = new Vector2(this.Position.X+this.MovementSpeed(), this.Position.Y);
				else
					this.Position = new Vector2(this.Position.X-this.MovementSpeed(), this.Position.Y);

				//Sets the row in which the Image is located on the sprite sheet
				this.SetRow(7);
			}
			/*
			 * When the character is moving to the down on the screen
			 */
			else if(KeyHandlerController.Movement[2])
			{
				//Keeps the player on the screen and adjusts if needed
				if(this.Position.Y + this.MovementSpeed()+(-Global.CAMERA.Position.Y) > 
				(world.currentLevel("").getTileMap("").getYTiles()*Global.SCALE*Global.TILE_SIZE)-(this.imgRect.height*2) &&
				Global.CAMERA.Position.Y < 0)
					this.Position = new Vector2(this.Position.X, this.Position.Y-this.MovementSpeed());
				else 
					this.Position = new Vector2(this.Position.X, this.Position.Y+this.MovementSpeed());

				//Sets the row in which the Image is located on the sprite sheet
				this.SetRow(6);
			}
			/*
			 * WHen the character is moving right on the screen
			 */
			else if(KeyHandlerController.Movement[3])
			{
				//Keeps the player on the screen and adjusts if needed
				if(this.Position.X + this.MovementSpeed()+(-Global.CAMERA.Position.X) >
				(world.currentLevel("").getTileMap("").getXTiles()*Global.SCALE*Global.TILE_SIZE)-(this.imgRect.width*2) &&
				Global.CAMERA.Position.X < 0)
					this.Position = new Vector2(this.Position.X-this.MovementSpeed(), this.Position.Y);
				else 
					this.Position = new Vector2(this.Position.X+this.MovementSpeed(), this.Position.Y);

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
	private void cameraMovement() {
		
		if(this.Position.X + (this.imgRect.width) > Global.RENDER_X-(this.imgRect.width))
		{
			this.Position = new Vector2 (this.Position.X-this.MovementSpeed(), this.Position.Y);
			Global.CAMERA.Position.X -= this.MovementSpeed();
		}
		if(this.Position.X < 0 && Global.CAMERA.Position.X < 0)
		{
			this.Position = new Vector2(0,this.Position.Y);
			Global.CAMERA.Position.X += this.MovementSpeed();
		}
		if(this.Position.Y +(this.imgRect.height) > Global.RENDER_Y-this.imgRect.height) {
			this.Position = new Vector2(this.Position.X, this.Position.Y-this.MovementSpeed());
			Global.CAMERA.Position.Y -= this.MovementSpeed();
		}
		if(this.Position.Y < 0 && Global.CAMERA.Position.Y < 0)
		{
			this.Position = new Vector2 (this.Position.X,0);
			Global.CAMERA.Position.Y += this.MovementSpeed();
		}
	}
	
	@Override
	public void Update(World w) {
		
		movement(w);
		cameraMovement();
		super.Update(w);
	}
	
	@Override
	public void Draw(javafx.scene.canvas.GraphicsContext gc) {
		
		super.Draw(gc);
	}
}
;
package gameEngine;

import com.google.gson.annotations.SerializedName;

public class Player extends Entity{
	
	
	@SerializedName("Seconds")
	private byte seconds;
	
	@SerializedName("Minutes")
	private byte minutes;
	
	@SerializedName("Hours")
	private int hours;
	
	private transient HeadsUpDisplay hud;
	private transient Audio audioRef;
	
	//current Ability is use
	private transient Ability currentAbility;
	
	public HeadsUpDisplay getHUD() { return hud;}
	
	public transient StatusScreenController statusScreenController;
	
	public void setHUD(HeadsUpDisplay value) { hud = value;}
	public void setAudioRef(Audio value) { audioRef = value;}
	public void setCurrentAbility(Ability value) { currentAbility = value;}
	public Ability getCurrentAbility() { return currentAbility;}
	
	public Player() {
		super();
		this.SetRow(6);
		statusScreenController = new StatusScreenController(this);
		String [] abilityNames = {"FireballL1","Ice Shard","Heal"};
		this.setAbilityNames(abilityNames);
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
				
				setLastKnownDirection(1);
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
				setLastKnownDirection(4);
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
				setLastKnownDirection(3);
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
				setLastKnownDirection(2);
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
	
	private void useAbility(World w) {
		
		// gets the current ability the player is using
		if(KeyHandlerController.UseAbility && !currentAbility.IsOnCoolDown()) {
			
			// checks if the player has enough magic current to use the ability
			if(this.getStatusByName("magiccurrent")-currentAbility.getMagicCost() < 0) {
				return;
			}
			
			// reduces the magic current by the cost of the ability
			this.changeStatusByPair("magiccurrent",-currentAbility.getMagicCost());
			
			// resets the cooldown counter to 0 to use the ability
			currentAbility.getCooldown()[0] = 0; 
			
			// sets the ability to be on cooldown
			currentAbility.setOnCoolDown(true); 
			
			// sets the ability to be moving
			currentAbility.setIsActive(true);
			
			// sets the ability to be moving
			currentAbility.setIsMoving(true); 
			
			// plays the sound effect of the ability
			audioRef.playSE(this.getUsableAbilities()[0].getAudioFile());
		}
	}
	
	@Override
	public void Update(World w) {
		
		//Will only update the player if the status screen is not open
		if(!statusScreenController.isOpen()) {
			//Updates the movement of the player
			movement(w);
		
			//updates the camera movement
			cameraMovement(w);
			
			//Updates the player as an entity
			super.Update(w);
		}
		//updates the Status Screen Controller
		statusScreenController.Update();
		
		//Uses an ability if the key is pressed
		useAbility(w);
		
		//Updates the Heads Up Display
		hud.Update(this);
	}
	
	@Override
	public void Draw(javafx.scene.canvas.GraphicsContext gc) {
		super.Draw(gc);
		
		//Draws the Status Screen Controller
		statusScreenController.Draw(gc);
	}


	/**
	 * @return the seconds
	 */
	public byte getSeconds() {
		return seconds;
	}


	/**
	 * @param seconds the seconds to set
	 */
	public void setSeconds(byte seconds) {
		this.seconds = seconds;
	}


	/**
	 * @return the minutes
	 */
	public byte getMinutes() {
		return minutes;
	}


	/**
	 * @param minutes the minutes to set
	 */
	public void setMinutes(byte minutes) {
		this.minutes = minutes;
	}


	/**
	 * @return the hours
	 */
	public int getHours() {
		return hours;
	}


	/**
	 * @param hours the hours to set
	 */
	public void setHours(int hours) {
		this.hours = hours;
	}
}
;
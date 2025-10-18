package gameEngine;

import javafx.scene.input.KeyCode;

public class Global {
	public static String INSTALL_LOCATION = "/home/mike/RPG/Sliver_Moon";  // Default Save Location
	public static int SCALE = 3;  // Scale for the graphics rendering
	public static int SCREEN_ROW = 16;  // Default Number of Tiles on y axis
	public static int SCREEN_COLUMN = 12;  // Default Number of Tiles on x axis
	public static int TILE_SIZE = 16;  // Default size of the tiles
	public static final int RENDER_X = (TILE_SIZE*SCALE)*SCREEN_COLUMN;  //default size of screen x
	public static final int RENDER_Y = (TILE_SIZE*SCALE)*SCREEN_ROW;  // default size of screen y
	public static Camera CAMERA = new Camera(); // The Camera that will be defaulted to the world
	public static float MASTER_VOLUME = 0; // Master Volume amount
	public static float SOUND_EFFECT_VOLUME= 0; // Sound effect volumes
	public static KeyCode MOVEMENT_UP = KeyCode.UP;  // default Up key
	public static KeyCode MOVEMENT_LEFT = KeyCode.LEFT;  // Default Left key
	public static KeyCode MOVEMENT_RIGHT= KeyCode.RIGHT;  // Default right key
	public static KeyCode MOVEMENT_DOWN = KeyCode.DOWN;  // Default Down Key
	public static KeyCode ACTION = KeyCode.SPACE;  // Default action key
	public static final DataLoader DATA_LOADER = new DataLoader(); // Global Data Loader
}

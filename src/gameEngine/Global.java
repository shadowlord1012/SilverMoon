package gameEngine;

import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;

public class Global {
	public static int SCALE = 3;  // Scale for the graphics rendering
	public static int SCREEN_ROW = 12;  // Default Number of Tiles on y axis
	public static int SCREEN_COLUMN = 16;  // Default Number of Tiles on x axis
	public static int TILE_SIZE = 16;  // Default size of the tiles
	public static int RENDER_X = (TILE_SIZE*SCALE)*SCREEN_COLUMN;  //default size of screen x
	public static int RENDER_Y = (TILE_SIZE*SCALE)*SCREEN_ROW;  // default size of screen y
	public static Camera CAMERA = new Camera(); // The Camera that will be defaulted to the world
	public static float MASTER_VOLUME = 0; // Master Volume amount
	public static float SOUND_EFFECT_VOLUME= 0.75f; // Sound effect volumes
	public static KeyCode MOVEMENT_UP = KeyCode.UP;  // default Up key
	public static KeyCode MOVEMENT_LEFT = KeyCode.LEFT;  // Default Left key
	public static KeyCode MOVEMENT_RIGHT= KeyCode.RIGHT;  // Default right key
	public static KeyCode MOVEMENT_DOWN = KeyCode.DOWN;  // Default Down Key
	public static KeyCode ACTION = KeyCode.SPACE;  // Default action key
	public static KeyCode DEBUG_UP = KeyCode.I;
	public static KeyCode DEBUG_DOWN = KeyCode.O;
	public static KeyCode SCREEN_MOVE_LEFT = KeyCode.Q; // Screen move left key
	public static KeyCode SCREEN_MOVE_RIGHT = KeyCode.E; // Screen move right key
	public static KeyCode OPEN_STATUS_SCREENS = KeyCode.TAB; // Open Status Screens key
	public static final DataLoader DATA_LOADER = new DataLoader(); // Global Data Loader
	public static String CURRENT_LEVEL = "levelOne";
	public static String TILE_MAP_NAME	 = "Map1";
	public static boolean TILE_MAP_LOADED = false;
	public static Font LADY_RADICAL = Font.loadFont("file:Resources/Fonts/LadyRadical.ttf", 14);
	public static Font ADVENTURE_REQUEST = Font.loadFont("file:Resources/Fonts/AdventureRequest.ttf", 14);
	public static Font DEFAULT_FONT = Font.getDefault();
	public static GameTime GAME_TIME;
}

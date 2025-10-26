package application;
	
import java.io.File;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import gameEngine.Engine;
import gameEngine.Global;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;


/*
 * java --module-path C:\javafx-sdk-21.0.6\lib --add-modules=javafx.controls,javafx.swing,javafx.graphics,javafx.fxml -jar C:\Users\mikek\Documents\SilverMoon\game.jar
 */


public class Main extends Application {
	
	private gameEngine.KeyHandlerController keyBind = new gameEngine.KeyHandlerController();
	private gameEngine.Engine mainGameEngine;
	private Thread gameThread;
	private Canvas gameCanvas;
	private long lastFrameTime = 0;
    private int frameCount = 0;
    private double fps = 0.0;
    Future<Engine> gameEngineLoading;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//Sets the pane in which everything rests on
			BorderPane root = new BorderPane();			
			
			//New Canvas Object
			gameCanvas = new Canvas();
			
			//Adds it to the center of the root
			root.setCenter(gameCanvas);
			
			//Binds the width of the canvas to the width of the parent
			gameCanvas.widthProperty().bind(root.widthProperty());
			gameCanvas.heightProperty().bind(root.heightProperty());
			
			
			
			
			//Creates the scene in where everything takes place
			Scene scene = new Scene(root,gameEngine.Global.RENDER_X, 
					gameEngine.Global.RENDER_Y);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			//Adds Listeners to the scene 
			scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent  -> {
				keyBind.keyPressed(keyEvent);
			});
			scene.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
				keyBind.keyRealesed(keyEvent);
			});
			
			//Sets the primary stage and displays it
			primaryStage.setTitle("Silver Moon");
			primaryStage.setScene(scene);
			primaryStage.show();

			gameEngineLoading = Global.DATA_LOADER.loadingEngine(gameCanvas);
			
			System.out.println("Main stage Complete");
			
			gameCanvas.getGraphicsContext2D().drawImage(
					SwingFXUtils.toFXImage(ImageIO.read(new File("Resources/Images/Title Screen Image EN.jpg")),null),
					0, 0, Global.RENDER_X, Global.RENDER_Y);
			
			try {
				mainGameEngine = gameEngineLoading.get();
				System.out.println("Engine Loaded");
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				Global.DATA_LOADER.shutdown();
			}
			
			gameThread = new Thread(mainGameEngine,"GameEngineThread");
			gameThread.setDaemon(true);
			gameThread.start();
			//Starts the game
			startGame(primaryStage);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * Show the FPS of the Javafx thread
	 * @param now
	 */
	private void ShowFPS(long now) {
		
		if (lastFrameTime == 0) {
            lastFrameTime = now;
            return;
        }

		frameCount++;
        long elapsedNanos = now - lastFrameTime;

        // Update FPS every second (or adjust as needed)
        if (elapsedNanos >= 1_000_000_000) { // 1 second in nanoseconds
            fps = (double) frameCount / (elapsedNanos / 1_000_000_000.0);
            frameCount = 0;
            lastFrameTime = now;
        }
		
	}
	
	private void startGame(Stage stage) {
		
		AnimationTimer gameLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				
				ShowFPS(now);
				
                stage.setTitle(String.format("Silver Moon : %.2f FPS", fps));
			}
		};
		
		gameLoop.start();
	}
	
	@Override
	public void stop() throws Exception{
		if(mainGameEngine != null)
			mainGameEngine.stop();
		super.stop();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

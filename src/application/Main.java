package application;
	
import java.io.File;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import gameEngine.Audio;
import gameEngine.Engine;
import gameEngine.FadeInOut;
import gameEngine.Global;
import gameEngine.KeyHandlerController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	
	private gameEngine.KeyHandlerController keyBind = new gameEngine.KeyHandlerController();
	private gameEngine.Engine mainGameEngine;
	private Thread gameThread;
	private Canvas gameCanvas;
	private long lastFrameTime = 0;
    private int frameCount = 0;
    private double fps = 0.0;
    Future<Engine> gameEngineLoading;
    private boolean onlyOnce = false;
    private boolean engineLoaded = false;
    private boolean runGame = false;
    private int[] selectedValue = {0,0};
    private int[] delayCounter = {0,0};
    private Audio audio = new Audio();
	private Text text = new Text("Start");
	private Text quitText = new Text("Quit");
	private FadeInOut fadeInOut = new FadeInOut();
	private BorderPane root;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//Sets the pane in which everything rests on
			root = new BorderPane();			
			
			root.setStyle("-fx-background-color: black;");
			
			//New Canvas Object
			gameCanvas = new Canvas();
			
			//Adds it to the center of the root
			root.setCenter(gameCanvas);
			
			//Binds the width of the canvas to the width of the parent
			gameCanvas.widthProperty().bind(root.widthProperty());
			gameCanvas.heightProperty().bind(root.heightProperty());
			
			SetTextInformation();
			
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
			
			gameCanvas.getGraphicsContext2D().drawImage(
					SwingFXUtils.toFXImage(ImageIO.read(new File("Resources/Images/Title Screen Image EN.jpg")),null),
					0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
			
			root.getChildren().add(text);
			root.getChildren().add(quitText);
			
			primaryStage.setOnCloseRequest(this::OnClose);
			//Sets the primary stage and displays it
			primaryStage.setTitle("Silver Moon");
			primaryStage.setScene(scene);
			primaryStage.show();
			audio.playBGM("Theme.wav");
			startGame(primaryStage);
			//Starts the game
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void SetTextInformation() {
		//Sets the Start text information
		text.setX((gameEngine.Global.RENDER_X/2)-25);
		text.setY(gameEngine.Global.RENDER_Y-100);
		text.setFill(Color.WHITE);
		text.setFont(Font.font("Times New Roman",FontWeight.BOLD,30));
		
		//Sets the Start text information
		quitText.setX((gameEngine.Global.RENDER_X/2)-25);
		quitText.setY(gameEngine.Global.RENDER_Y-75);
		quitText.setFill(Color.WHITE);
		quitText.setFont(Font.font("Times New Roman",FontWeight.BOLD,30));
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

				
				if(!onlyOnce) {						
					
					handling();
				}
				
                stage.setTitle(String.format("Silver Moon : %.2f FPS", fps));
			}
		};
		
		gameLoop.start();

		gameEngineLoading = Global.DATA_LOADER.loadingEngine(gameCanvas);

		//starts the fade in and out on text
		fadeInOut.fadeTextInOut(text, 1.5);
	}
	
	private void changeText() {
		
		//Just so the user can not spam the action button allow a count delay to happen
		delayCounter[0]++;
		if(delayCounter[0] >=50)
			delayCounter[0] = 50;
		
		//what is to be changed
		selectedValue[0]++;
		switch(selectedValue[0])
		{
		case 0:
			fadeInOut.stop();
			fadeInOut.fadeTextInOut(text, 1.5);
			break;
		case 1:
			fadeInOut.stop();
			fadeInOut.fadeTextInOut(quitText, 1.5);;
			break;
		}
		
		//changes what is being selected on the screen for fade in or out
		if(selectedValue[0] >= selectedValue[1])
			selectedValue[0] = 0;
		
		if(selectedValue[0] < 0)
			selectedValue[0] = selectedValue[1];
	}
	
	private void handling() {
		
		if(KeyHandlerController.Action && !runGame && delayCounter[0] == 50) {
			text.setText("Load");
		}
		
		if(gameEngineLoading.isDone()&& !engineLoaded)
		{
			engineLoaded=true;
			System.out.println("Engine Loaded");
		}
		
		
		if(gameEngineLoading.isDone() && KeyHandlerController.Action && runGame)
		{
			
			fadeInOut.fadeOutIn(gameCanvas, 1000,500);
			
			try {
				mainGameEngine = gameEngineLoading.get();
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				Global.DATA_LOADER.shutdown();
			}
			
			audio.stop();
			audio.shutdown();
			
			root.getChildren().remove(text);
			
			
			mainGameEngine.setAudio(audio);
			
			mainGameEngine.getAudio().playBGM("FieldOne.wav");
			onlyOnce = true;
			gameThread = new Thread(mainGameEngine,"GameEngineThread");
			gameThread.setDaemon(true);
			gameThread.start();
			
		}
	}
	@Override
	public void stop() throws Exception{
		audio.stop();
		audio.shutdown();
		Global.DATA_LOADER.shutdown();		
		if(mainGameEngine != null) {
			mainGameEngine.stop();
			mainGameEngine.getAudio().stop();
		}
		super.stop();
	}
	
	private void OnClose(WindowEvent event) {
		Platform.exit();
		System.exit(0);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

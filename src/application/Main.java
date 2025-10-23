package application;
	
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class Main extends Application {
	
	private gameEngine.KeyHandlerController keyBind = new gameEngine.KeyHandlerController();
	private gameEngine.Engine mainGameEngine;
	private Canvas gameCanvas = new Canvas(gameEngine.Global.RENDER_X,gameEngine.Global.RENDER_Y);
	private long lastTime = System.nanoTime();
	private double nsPerTick = 1000000000/60D;
	private long lastTimer = System.currentTimeMillis();
	private double delta = 0;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//Sets the pane in which everything rests on
			BorderPane root = new BorderPane();
			
			//Creates a new Plane
			Pane layerPane = new Pane();
			
			layerPane.getChildren().addAll(
					new gameEngine.Layer(gameEngine.Global.RENDER_X, 
							gameEngine.Global.RENDER_Y));
			
			root.setCenter(layerPane);
			
			Scene scene = new Scene(root,gameEngine.Global.RENDER_X, 
					gameEngine.Global.RENDER_Y);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent  -> {
				keyBind.keyPressed(keyEvent);
			});
			scene.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
				keyBind.keyRealesed(keyEvent);
			});
			
			mainGameEngine = new gameEngine.Engine(gameCanvas.getGraphicsContext2D());
			
			root.getChildren().add(gameCanvas);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			startGame();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void startGame() {
		
		AnimationTimer gameLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				delta+=(now-lastTime)/nsPerTick;
				lastTime = now;
				while(delta >= 1)
				{
					mainGameEngine.Update();
					
					mainGameEngine.Draw();
					
					delta-=1;
					
				}
			}
		};
		
		gameLoop.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

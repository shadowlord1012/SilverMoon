package gameEngine;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class FadeInOut {

	private FadeTransition fadeTransition;
	
	public FadeTransition getFadeTransition() {return fadeTransition;}
	
	public void stop() {
		fadeTransition.stop();
	}
	
	/**
	 * Creates and starts a fade-in and fade-out loop for a JavaFX Text node.
	 * * @param textNode The Text object to animate.
	 * @param durationInSeconds The time for one direction of the fade (e.g., fade-in only).
	 */
	public void fadeTextInOut(Text textNode, double durationInSeconds) {
		
	    // Create a FadeTransition instance
	    fadeTransition = new FadeTransition(Duration.seconds(durationInSeconds), textNode);

	    // Set the starting opacity value (fully visible)
	    fadeTransition.setFromValue(1.0); 

	    // Set the ending opacity value (fully transparent/invisible)
	    fadeTransition.setToValue(0.0);

	    // Set the number of times the animation should run (Timeline.INDEFINITE runs forever)
	    fadeTransition.setCycleCount(Timeline.INDEFINITE);

	    // Tell the transition to reverse the animation automatically after each cycle
	    // This makes it fade out and then immediately fade back in.
	    fadeTransition.setAutoReverse(true); 

	    // Start the animation
	    fadeTransition.play();
	}
	
	public void fadeOutIn(Node node, long durationMillis, long holdTimeMillis) {
	    // 1. Fade In Transition (from invisible to fully opaque)
	    FadeTransition fadeIn = new FadeTransition(Duration.millis(durationMillis), node);
	    fadeIn.setFromValue(1.0); // Start at fully transparent
	    fadeIn.setToValue(0.0);   // End at fully opaque

	    // 2. Pause Transition (a simple pause is implicitly created by SequentialTransition)
	    // We don't need a separate PauseTransition if we only chain transitions, 
	    // but the hold time is often applied to the fade-in's duration 
	    // before the fade-out begins, or we can use a separate transition if needed.
	    // For a cleaner fade-out *right* after a hold, we'll implement it manually:

	    // 3. Fade Out Transition (from fully opaque to invisible)
	    FadeTransition fadeOut = new FadeTransition(Duration.millis(durationMillis), node);
	    fadeOut.setFromValue(0.0); // Start at fully opaque
	    fadeOut.setToValue(1.0);   // End at fully transparent
	    
	    // Set up a sequential transition to run the animations one after the other.
	    // We use a small 'hack' with the fade-in's onFinished to introduce the hold time.
	    fadeIn.setOnFinished(event -> {
	        // After fade-in finishes, wait for the hold time, then start fade-out
	        new Thread(() -> {
	            try {
	                Thread.sleep(holdTimeMillis);
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	            }
	            // Important: All JavaFX scene graph modifications must be done on the FX thread
	            javafx.application.Platform.runLater(fadeOut::play);
	        }).start();
	    });

	    // Start the fade-in
	    fadeIn.play();
	}
	
}

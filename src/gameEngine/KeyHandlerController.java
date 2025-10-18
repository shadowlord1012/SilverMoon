package gameEngine;

import javafx.scene.input.KeyEvent;

public class KeyHandlerController {
	
	public static boolean[] Movement = {false,false,false,false};
	public static boolean Action = false;
	
	
	public void keyPressed(KeyEvent e) {
		if(e.getCode() == Global.MOVEMENT_UP)
			Movement[0] = true;
		if(e.getCode() == Global.MOVEMENT_LEFT)
			Movement[1] = true;
		if(e.getCode() == Global.MOVEMENT_DOWN)
			Movement[2] = true;
		if(e.getCode() == Global.MOVEMENT_RIGHT)
			Movement[3] = true;
		if(e.getCode() == Global.ACTION)
			Action = true;
	}
	public void keyRealesed(KeyEvent e) {
		if(e.getCode() == Global.MOVEMENT_UP)
			Movement[0] = false;
		if(e.getCode() == Global.MOVEMENT_LEFT)
			Movement[1] = false;
		if(e.getCode() == Global.MOVEMENT_DOWN)
			Movement[2] = false;
		if(e.getCode() == Global.MOVEMENT_RIGHT)
			Movement[3] = false;
		if(e.getCode() == Global.ACTION)
			Action = false;
	}
}

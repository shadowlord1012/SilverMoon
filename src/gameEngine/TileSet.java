package gameEngine;

import java.io.IOException;

public class TileSet {
	
	private String name;
	private java.awt.image.BufferedImage img;
	
	public String getName() {return name;}
	public java.awt.image.BufferedImage getImg(){return img;}
	
	public TileSet(String name) {
		this.name = name;
		
		try {
			this.img = javax.imageio.ImageIO.read(
					new java.io.File(Global.INSTALL_LOCATION+"/Assets/TileSets/"+name+".jpg"));
		}catch(IOException e) { e.printStackTrace();}
	}
	
}

package gameEngine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class HeadsUpDisplay {
	
	@SuppressWarnings("unused")
	private Player playerRef;
	private Image healthBar;
	private Image manaBar;
	private BufferedImage manaBarCenter;
	private BufferedImage healthBarCenter;
	private Image[] manaBarCenterFX = new Image[3];
	private Image[] healthBarCenterFX = new Image[3];
	private int[] rendering = new int[33];
	private double[] manaDif = new double[2];
	private double[] healthDif = new double[2];
	
	public HeadsUpDisplay(Player player) {
		playerRef = player;
		Initialize();
	}
	
	private void Initialize() {
		try {
			BufferedImage bufferedTempImage = ImageIO.read(new File("Resources/Images/HUD/healthBarEmpty.png"));
			healthBar = SwingFXUtils.toFXImage(bufferedTempImage,null);
			bufferedTempImage = ImageIO.read(new File("Resources/Images/HUD/manaBarEmpty.png"));
			manaBar = SwingFXUtils.toFXImage(bufferedTempImage,null);
			bufferedTempImage = ImageIO.read(new File("Resources/Images/HUD/manaBarCenter.png"));
			manaBarCenter = bufferedTempImage;
			bufferedTempImage = ImageIO.read(new File("Resources/Images/HUD/healthBarCenter.png"));
			healthBarCenter = bufferedTempImage;
			
			//adds all the images to the arrays
			for(int x = 0; x < 3; x++)
			{
				manaBarCenterFX[x] = SwingFXUtils.toFXImage(manaBarCenter.getSubimage(24*x, 0, 24, 44),null);
				healthBarCenterFX[x] = SwingFXUtils.toFXImage(healthBarCenter.getSubimage(24*x, 0, 24, 44),null);
			}
		}
		catch (Exception e) { e.printStackTrace();}
	}
	
	public void Update(Player player) {
		
		manaDif[0] = player.getStatusByName("magiccurrent");
		manaDif[1] = player.getStatusByName("Magic");
		healthDif[0] = player.getStatusByName("healthcurrent");
		healthDif[1] = player.getStatusByName("Health");
		
		rendering[0]++;
		if(rendering[0] >= 8) {
			rendering[0] = 0;
			for(int x = 0; x < 30;x++)
			{
				rendering[1+x] = (int)(Math.random()*3);
				
			}
		}
		
		IntStream.of(rendering).forEach(e ->{
			if(e >= 3)
				e=0;
		});
	}
	
	public void Draw(GraphicsContext gc) {
		
		for(double x = 0; x < (30*(healthDif[0]/healthDif[1])); x++)
		{
			gc.drawImage(healthBarCenterFX[rendering[(int)(1+x)]],
					55 +(x*8), Global.RENDER_Y-52,
					((healthBarCenter.getWidth()/3)/3)+1,
					(healthBarCenter.getHeight()/3+3));
		}
		
		//health bar and center
		gc.drawImage(healthBar, 11, Global.RENDER_Y-75
						,healthBar.getWidth()/3,
						healthBar.getHeight()/3);
		
		//Mana bar and center
		for(double x = 0; x < (30*(manaDif[0]/manaDif[1])); x++)
		{
			gc.drawImage(manaBarCenterFX[rendering[(int)(1+x)]],
					(Global.RENDER_X-(manaBarCenter.getWidth()/3)-270)+(x*8), Global.RENDER_Y-52,
					(manaBarCenter.getWidth()/3)/3,
					(manaBarCenter.getHeight()/3+3));
		}
			
		gc.drawImage(manaBar, Global.RENDER_X-(manaBar.getWidth()/3)-10, Global.RENDER_Y-75,
				manaBar.getWidth()/3,
				manaBar.getHeight()/3);
	}
}

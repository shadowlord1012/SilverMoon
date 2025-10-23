package gameEngine;

import java.awt.Rectangle;

public class Collision {
	
	public static void tileCollision(TileMap map, Entity entity) {
		
		//creates the entities
		Rectangle entityRec = new Rectangle(
				(int)entity.getPosition().X,
				(int)entity.getPosition().Y,
				(int)entity.getWidth()-5,
				(int)entity.getHeight()-5);
		
		for(int x = 0; x < map.getXTiles(); x++)
			for(int y=0; y < map.getYTiles(); y++)
			{
				if(map.getTilesOnMap()[x][y].getStoppableSides() > 0)
				{
					Rectangle tileRec = new Rectangle(
							(int)((map.getTilesOnMap()[x][y].getWidth()*x*Global.SCALE)+Global.CAMERA.Position.X)-(map.getTilesOnMap()[x][y].getWidth()*Global.SCALE),
							(int)((map.getTilesOnMap()[x][y].getHeight()*y*Global.SCALE)+Global.CAMERA.Position.Y)-(map.getTilesOnMap()[x][y].getHeight()*Global.SCALE),
							(int)map.getTilesOnMap()[x][y].getWidth()*Global.SCALE,
							(int)map.getTilesOnMap()[x][y].getHeight()*Global.SCALE);
					
					switch(map.getTilesOnMap()[x][y].getStoppableSides())
					{
					
					//if the tile is a complete not entry
					case 10:						
						if(KeyHandlerController.Movement[3] && entityRec.intersects(tileRec))
							entity.setPosition(new Vector2(entity.getPosition().X-entity.getMovementSpeed(), entity.getPosition().Y));
						else if(KeyHandlerController.Movement[2] && entityRec.intersects(tileRec))
							entity.setPosition(new Vector2(entity.getPosition().X, entity.getPosition().Y-entity.getMovementSpeed()));
						else if(KeyHandlerController.Movement[1] && entityRec.intersects(tileRec))
							entity.setPosition(new Vector2(entity.getPosition().X+entity.getMovementSpeed(), entity.getPosition().Y));
						else if( KeyHandlerController.Movement[0] && entityRec.intersects(tileRec))
							entity.setPosition(new Vector2(entity.getPosition().X, entity.getPosition().Y+entity.getMovementSpeed()));
						else
							break;
					//TODO: add in collision if only a single or multiple side is blocked
					}
				}
			}
		
	}
}

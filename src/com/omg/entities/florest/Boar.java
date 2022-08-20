package com.omg.entities.florest;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.omg.entities.Entity;
import com.omg.main.Game;
import com.omg.world.Camera;
import com.omg.world.World;

public class Boar extends Entity{
	
	private boolean isCharging,warning,attacking;
	private int distanceView = 200, frames = 0, maxFrames = 10,indexAnimation = 0,side = Game.rand.nextInt(2),wantedX = 0, coolDown = 10, waitTime = 0, sec = 30;
	private BufferedImage spriteBoar[];

	public Boar(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		spriteBoar = new BufferedImage[4];
		int ySprite = 128+(16*side);
		//If side = 0 then it loads the left sprite, else it loads the right sprite
		
		for(int i = 0; i < 4;i++) {
			spriteBoar[i] = Game.spritesheet.getSprite(96+(i*16),ySprite, 16,16);
		}
	}
	public boolean isSeeing() {
		if(Game.player.getY() < this.getY()) {
			if(side == 1) {
				if(Game.player.getX() - getX() < 200 && Game.player.getX() - getX() > 0) {
					return true;
				}
				return false;
			}else {
				if(getX() - Game.player.getX() < 200 && getX() - Game.player.getX() > 0) {
					return true;
				}else {
					return false;
				}
			}
		}
		return false;
	}
	public void tick() {
		if(isSeeing() && !isCharging) {
			waitTime++;
			indexAnimation = 3;
			if(waitTime == coolDown) {
				waitTime = 0;
				isCharging = true;
			}
		}else if(isSeeing() && isCharging) {
			
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				if(indexAnimation < 2) {
					indexAnimation++;
				}else {
					indexAnimation = 0;
				}
			}
			
			if(side == 1) {
				wantedX = getX() + 200;
				if(World.isFree(getX()+6, getY()))
					setX(getX()+6);
				if(wantedX == getX() + 200) {
					waitTime++;
					if(waitTime == coolDown) {
						isCharging = false;
					}
				}
			}else {
				wantedX = getX() - 200;
				if(World.isFree(getX()-6, getY())) {
					setX(getX()-6);
				}else {
					isCharging = false;
				}
				if(wantedX == getX() + 200) {
					waitTime++;
					if(waitTime == coolDown) {
						isCharging = false;
					}
				}
			}
		}else {
			indexAnimation = 0;
		}
		sec--;
		if(World.isFree(getX(), getY()+2))
			setY(getY()+2);
		if(collided() && sec < 1){
			Game.player.life--;
			sec = 30;
		}
	}
	
	public void render(Graphics g) {
		 g.drawImage(spriteBoar[indexAnimation],getX() - Camera.x,getY() - Camera.y,World.tile_size,World.tile_size,null);
	}

}

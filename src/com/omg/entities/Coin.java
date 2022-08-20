package com.omg.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.omg.main.Game;
import com.omg.world.Camera;
import com.omg.world.World;

public class Coin extends Entity {
	
	private BufferedImage coinSprite[];
	private int frames = 0, maxFrames = 7,indexAnimation = 0,animationSide = 0,indexAnimation1 = 0,frames1 = getY();

	public Coin(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		coinSprite = new BufferedImage[12];
		coinSprite[0] = Game.spritesheet.getSprite(0, 80, 16, 16);
		coinSprite[1] = Game.spritesheet.getSprite(16, 80, 16, 16);
		coinSprite[2] = Game.spritesheet.getSprite(32, 80, 16, 16);
		coinSprite[3] = Game.spritesheet.getSprite(48, 80, 16, 16);
		coinSprite[4] = Game.spritesheet.getSprite(64, 80, 16, 16);
		coinSprite[5] = Game.spritesheet.getSprite(80, 80, 16, 16);
		coinSprite[6] = Game.spritesheet.getSprite(0, 96, 16, 16);
		coinSprite[7] = Game.spritesheet.getSprite(16, 96, 16, 16);
		coinSprite[8] = Game.spritesheet.getSprite(32, 96, 16, 16);
		coinSprite[9] = Game.spritesheet.getSprite(48, 96, 16, 16);
		coinSprite[10] = Game.spritesheet.getSprite(64, 96, 16, 16);
		coinSprite[11] = Game.spritesheet.getSprite(80, 96, 16, 16);
		
	}
	
	public void tick() {
		
		if(collided()) {
			Game.score++;
			Game.entities.remove(this);
		}
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			if(animationSide == 0) {
					indexAnimation++;
					if(indexAnimation == 11) {
						animationSide = 1;
					}
			}else {
				indexAnimation--;
				if(indexAnimation == 0) {
					animationSide = 0;
				}
			}
		}
		if(indexAnimation1 == 0) {
			if(getY() != (frames1+10)) {
				setY(getY()+1);
			}else {
				indexAnimation1 = 1;
			}
		}
		if(indexAnimation1 == 1) {
			if(getY() != (frames1-10)) {
				setY(getY()-1);
			}else {
				indexAnimation1 = 0;
			}
		}

	}
	
	public void render(Graphics g) {
		g.drawImage(coinSprite[indexAnimation],getX()-Camera.x,getY()-Camera.y,World.tile_size,World.tile_size,null);
	}

}

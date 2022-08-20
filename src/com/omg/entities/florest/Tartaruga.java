package com.omg.entities.florest;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.omg.entities.Entity;
import com.omg.main.Game;
import com.omg.world.Camera;
import com.omg.world.World;

public class Tartaruga extends Entity{
	
	private int posIni,speed;
	private int frames = 0, maxFrames = 15, dir = 0,indexAnimation = 0,sec = 15;
	private boolean right, left = true;
	private BufferedImage turtleSpriteRight[], turtleSpriteLeft[],deadTurtle;
	public  boolean dead = false;
	
	public Tartaruga(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		posIni = x;
		speed = 1;
		turtleSpriteRight = new BufferedImage[3];
		turtleSpriteLeft = new BufferedImage[3];
		
		deadTurtle = Game.spritesheet.getSprite(144, 16, 16, 16);
		
		turtleSpriteRight[0] = Game.spritesheet.getSprite(96, 16, 16, 16);
		turtleSpriteRight[1] = Game.spritesheet.getSprite(112, 16, 16, 16);
		turtleSpriteRight[2] = Game.spritesheet.getSprite(128, 16, 16, 16);
		
		turtleSpriteLeft[0] = Game.spritesheet.getSprite(96, 32, 16, 16);
		turtleSpriteLeft[1] = Game.spritesheet.getSprite(112, 32, 16, 16);
		turtleSpriteLeft[2] = Game.spritesheet.getSprite(128, 32, 16, 16);
	}
	
	public void tick() {
		if(!dead) {
			if(left && World.isFree(getX()-speed, getY())) {
				setX(getX()-speed);
				dir = -1;
				if(World.isFree(getX()-20, getY()+1)) {
					left = false;
					right = true;
				}
			}else {
				left = false;
				right = true;
			}
			if(right && World.isFree(getX()+speed, getY())) {
				setX(getX()+speed);
				dir = 1;
				if(World.isFree(getX()+20, getY()+1)) {
					left = true;
					right = false;
				}
			}else {
				left = true;
				right = false;
			}
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				if(indexAnimation == 2) {
					indexAnimation = 0;
				}else {
					indexAnimation++;
				}
			}
		}
		sec--;
		if(collided() && !dead) {
			if(Game.player.getY() < getY()) {
				this.dead = true;
				Game.player.isJumping = true;
			}else {
				if(sec < 1) {
					Game.player.life--;
					sec = 30;
				}
			}
		}
		
	}
	
	
	public void render(Graphics g) {
		if(dead) {
			g.drawImage(deadTurtle,getX() - Camera.x,getY() - Camera.y,30,30,null);
		}else if(dir == 1) {
			g.drawImage(turtleSpriteRight[indexAnimation],getX() - Camera.x,getY() - Camera.y,World.tile_size,World.tile_size,null);
		}else {
			g.drawImage(turtleSpriteLeft[indexAnimation],getX() - Camera.x,getY() - Camera.y,World.tile_size,World.tile_size,null);
		}
	}

}

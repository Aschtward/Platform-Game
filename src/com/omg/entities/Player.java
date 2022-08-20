package com.omg.entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.omg.main.Game;
import com.omg.world.Camera;
import com.omg.world.World;

public class Player extends Entity{
	
	//Vari�veis de render
	private BufferedImage playerSpriteRight[], playerSpriteLeft[],playerSpriteJump[],playerSpriteDamge[];
	private int frames = 0, maxFrames = 20,indexAnimation = 0;
	private int speed = 2;
	private int jumpHeight = 20,jumpFrames = 0;
	public int dir = 1,life = 3,beforelife = 3;
	public boolean left,right,jump,isJumping;
	public boolean interaction,tookdamage = false;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		//Carregando sprites do spritesheet do game
		playerSpriteRight = new BufferedImage[3];
		playerSpriteLeft = new BufferedImage[3];
		playerSpriteJump = new BufferedImage[2];
		playerSpriteDamge = new BufferedImage[2];
		playerSpriteRight[0] = Game.spritesheet.getSprite(96, 0, 16, 16);
		playerSpriteRight[1] = Game.spritesheet.getSprite(112, 0, 16, 16);
		playerSpriteRight[2] = Game.spritesheet.getSprite(128, 0, 16, 16);
		playerSpriteLeft[0] = Game.spritesheet.getSprite(48, 16, 16, 16);
		playerSpriteLeft[1] = Game.spritesheet.getSprite(64, 16, 16, 16);
		playerSpriteLeft[2] = Game.spritesheet.getSprite(80, 16, 16, 16);
		playerSpriteJump[0] = Game.spritesheet.getSprite(48, 32, 16, 16);
		playerSpriteJump[1] = Game.spritesheet.getSprite(64, 32, 16, 16);
		playerSpriteDamge[0] = Game.spritesheet.getSprite(0, 32, 16, 16);
		playerSpriteDamge[1] = Game.spritesheet.getSprite(16, 32, 16, 16);
	}
	
	public void tick() {
		if(jump) {
			if(!World.isFree(getX(), getY()+4)) {
				isJumping = true;
			}else {
				jump = false;
			}
		}
		if(isJumping) {
			if(World.isFree(getX(), getY()-2)) {
				setY(getY()-6);
				jumpFrames++;
				if(jumpFrames == jumpHeight) {
					isJumping = false;
					jump = false;
					jumpFrames = 0;
				}
			}else {
				isJumping = false;
				jump = false;
				jumpFrames = 0;
			}
		}
		if(right && World.isFree(getX()+speed, getY())) {
			//direita
			setX(getX()+speed);
			dir = 1;
		}else if(left && World.isFree(getX()-speed, getY())) {
			//esquerda
			setX(getX()-speed);
			dir = 2;
		}
		if(World.isFree(getX(), getY()+4) && !isJumping) {
			setY(getY()+4);
		}
		frames++;
		if(frames == maxFrames) {
			frames = 0 ;
			indexAnimation++;
			if(indexAnimation == 2) {
				indexAnimation = 0;
			}
		}
		if(beforelife > life) {
			tookdamage = true;
			beforelife = life;
		}
		Camera.x = Camera.clamp(getX() - (Game.WIDTH/2),0,World.width*World.tile_size - Game.WIDTH);
		Camera.y = Camera.clamp(getY() - (Game.HEIGHT/4),0,World.height*World.tile_size - Game.HEIGHT);
	}
	
	public void render(Graphics g) {
		if(isJumping) {
			//Pulando
			if(dir == 1) {
				g.drawImage(playerSpriteJump[1], getX() - Camera.x,getY() - Camera.y, World.tile_size, World.tile_size, null);
			}
			if(dir == 2)  {
				g.drawImage(playerSpriteJump[0], getX() - Camera.x,getY() - Camera.y, World.tile_size, World.tile_size, null);
			}
		}else if(dir == 1 && right) {
			g.drawImage(playerSpriteRight[indexAnimation], getX() - Camera.x,getY() - Camera.y, World.tile_size, World.tile_size, null);
		}else if(dir == 2 && left){
			g.drawImage(playerSpriteLeft[indexAnimation], getX() - Camera.x,getY() - Camera.y, World.tile_size, World.tile_size, null);
		}else if(dir == 1){
			g.drawImage(playerSpriteRight[0], getX() - Camera.x,getY() - Camera.y, World.tile_size, World.tile_size, null);
		}else {
			g.drawImage(playerSpriteLeft[2], getX() - Camera.x,getY() - Camera.y, World.tile_size, World.tile_size, null);
		}
		if(tookdamage) {
			if(dir == 1)
				g.drawImage(playerSpriteDamge[0], getX() - Camera.x,getY() - Camera.y, World.tile_size, World.tile_size, null);
			if(dir == 2)
				g.drawImage(playerSpriteDamge[1], getX() - Camera.x,getY() - Camera.y, World.tile_size, World.tile_size, null);
			tookdamage = false;
		}
	}
}

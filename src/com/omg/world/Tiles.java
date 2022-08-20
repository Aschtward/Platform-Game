package com.omg.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.omg.main.Game;

public class Tiles {

	private BufferedImage sprite;
	
	public static BufferedImage groundSprite = Game.spriteground.getSprite(16, 0, 16, 16);
	public static BufferedImage groundLeftTop = Game.spriteground.getSprite(32, 0, 16, 16);
	public static BufferedImage groundLeftDown = Game.spriteground.getSprite(32, 16, 16, 16);
	public static BufferedImage groundRightTop = Game.spriteground.getSprite(0, 0, 16, 16);
	public static BufferedImage groundRightDown = Game.spriteground.getSprite(0, 16, 16, 16);
	public static BufferedImage groundMiddle = Game.spriteground.getSprite(16, 16, 16, 16);
	public static BufferedImage groundMiddleDown = Game.spriteground.getSprite(16, 32, 16, 16);
	
	public static BufferedImage plataformaSprite = Game.spritesheet.getSprite(64, 0, 16, 16);
	public static BufferedImage plataformaSpriteLeft = Game.spritesheet.getSprite(16, 0, 16, 16);
	public static BufferedImage plataformaSpriteRight = Game.spritesheet.getSprite(32, 0, 16, 16);

	private int x,y;
	
	public Tiles(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,x - Camera.x,y - Camera.y,World.tile_size,World.tile_size,null);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	

}

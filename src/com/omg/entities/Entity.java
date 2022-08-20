package com.omg.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import com.omg.main.Game;
import com.omg.world.Camera;
import com.omg.world.World;

public class Entity {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private BufferedImage sprite;
	public BufferedImage playerSprite[];
	public static BufferedImage waterSprite = Game.spritesheet.getSprite(144, 0, 16, 16);
	public static BufferedImage waterSpriteSub = Game.spritesheet.getSprite(144, 32, 16, 16);
	public static BufferedImage waterSpriteSub1 = Game.spritesheet.getSprite(144, 48, 16, 16);
	public static BufferedImage waterSpriteSub2 = Game.spritesheet.getSprite(144, 64, 16, 16);

	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	protected double distanceBetwen(int x1, int y1, int x2, int y2) {
		return Math.sqrt(((x1-x2)*(x1-x2)) + ((y1-y2)*(y1-y2)));
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX()-Camera.x, this.getY() - Camera.y,32,32,null);
	}
	
	public boolean collided() {
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),32,32);
		Rectangle ent = new Rectangle(this.getX(),this.getY(),this.getWidth()-15,this.getHeight()-15);
		return player.intersects(ent);
	}
	
	public static boolean notPlayerCollided(Entity a, Entity b) {
		Rectangle ent_a = new Rectangle(a.getX(),a.getY(),a.getWidth(),a.getHeight());
		Rectangle ent_b = new Rectangle(b.getX(),b.getY(),b.getWidth(),b.getHeight());
		return ent_a.intersects(ent_b);
	}

	
	public void tick(){
		
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	
		
}

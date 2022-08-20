package com.omg.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.omg.main.Game;
import com.omg.world.Camera;
import com.omg.world.World;

public class Chest extends Entity{
	
	private boolean showMessage;
	private BufferedImage chestSpriteClosed,chestSpriteOpen;
	private boolean isInteracted, isOpen = false;

	public Chest(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		chestSpriteClosed = Game.spritesheet.getSprite(16, 16, 16, 16);
		chestSpriteOpen = Game.spritesheet.getSprite(32, 16, 16, 16);
	}
	
	public void tick() {
		if(this.getX() == Game.player.getX() && this.getY() == Game.player.getY()) {
			showMessage = true;
			if(Game.player.interaction) {
				isInteracted = true;
			}
		}else {
			showMessage = false;
		}
		if(isInteracted && !isOpen) {
			Game.score += 100;
		}
		if(isInteracted) {
			isOpen = true;
		}
	}
	
	public void itemDrop() {
		
	}
	
	public void render(Graphics g) {
		if(showMessage && !isInteracted) {
			g.setColor(Color.white);
			g.fillRect(getX()-Camera.x-45, getY()-Camera.y-37,130,30);
			g.setColor(Color.black);
			g.drawRect(getX()-Camera.x-45, getY()-Camera.y-37,130,30);
			g.setFont(new Font("Arial",Font.BOLD,16));
			g.setColor(Color.black);
			g.drawString("Press E to open", getX()-Camera.x-40, getY()-Camera.y-16);
		}
		if(isInteracted) {
			g.drawImage(chestSpriteOpen,  getX()-Camera.x, getY()-Camera.y, getHeight(), getHeight(), null);
		}else {
			g.drawImage(chestSpriteClosed,  getX()-Camera.x, getY()-Camera.y, getHeight(), getHeight(), null);
		}
	}

}

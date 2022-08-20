package com.omg.entities.florest;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.omg.entities.Entity;
import com.omg.main.Game;
import com.omg.world.Camera;
import com.omg.world.World;

public class Snake extends Entity {
	
	private BufferedImage snakeSpriteRight[],snakeSpriteLeft[];
	private int side = 0,frames = 0, maxFrames = 10,indexAnimation = 0,sec = 30;
	private boolean isAttacking = false;

	public Snake(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		snakeSpriteRight = new BufferedImage[6];
		snakeSpriteLeft = new BufferedImage[6];
		
		for(int i = 0; i < 6; i++) {
			snakeSpriteRight[i] = Game.spritesheet.getSprite(i*16,128, 16, 16);
			snakeSpriteLeft[i] = Game.spritesheet.getSprite(i*16,144, 16, 16);
		}
	}
	
	public void tick() {
		if(Game.player.getX() < getX()) {
			//Jogador est� a esquerda
			side = 1;
		}else {
			//Jogador est� a direita
			side = 0;
		}
		if(this.distanceBetwen(getX(), getY(), Game.player.getX(), Game.player.getY()) < 70) {
			isAttacking = true;
		}
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			if(isAttacking && indexAnimation < 5) {
				indexAnimation++;
			}else if(indexAnimation == 5) {
				isAttacking = false;
				indexAnimation = 0;
			}else if(indexAnimation < 2) {
				indexAnimation++;
			}else {
				indexAnimation = 0;
			}
		}
		sec--;
		if(collided() && sec < 1){
			Game.player.life--;
			sec = 30;
		}
	}

	public void render(Graphics g) {
		if(side == 1) {
			g.drawImage(snakeSpriteLeft[indexAnimation],getX()-Camera.x,getY()-Camera.y,World.tile_size,World.tile_size,null);
		}else {
			g.drawImage(snakeSpriteRight[indexAnimation],getX()-Camera.x,getY()-Camera.y,World.tile_size,World.tile_size,null);
		}

	}
}

package com.omg.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.omg.main.Game;
import com.omg.world.World;

public class Water extends Entity{

	public Water(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void tick() {
		if(collided()) {
			Game.player.life = 0;
		}
	}
}

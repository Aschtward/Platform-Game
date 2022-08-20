package com.omg.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.omg.entities.Chest;
import com.omg.entities.Coin;
import com.omg.entities.Entity;
import com.omg.entities.Player;
import com.omg.entities.Water;
import com.omg.entities.florest.Boar;
import com.omg.entities.florest.Snake;
import com.omg.entities.florest.Tartaruga;
import com.omg.graph.Spritesheet;
import com.omg.graph.UI;
import com.omg.main.Game;

public class World {
	
	public static int width, height;
	public static int tile_size = 32;
	public static Tiles[] tiles;

	public World(String path) {
		try {
		BufferedImage image = ImageIO.read(getClass().getResource(path));
		int[] pixels = new int[image.getWidth()*image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels ,0, image.getWidth());
		width = image.getWidth();
		height = image.getHeight();
		tiles = new Tiles[image.getWidth()*image.getHeight()];

		for(int i = 0; i < image.getWidth();i++) {
			for(int j = 0; j < image.getHeight();j++) {
				int pxAtual = pixels[i + (j*image.getWidth())];
				if(pxAtual == 0xFFFE4D47) {
					//Plataforma SpriteCheck
					tiles[i + (j*image.getWidth())] = new Ground(i*tile_size,j*tile_size,Tiles.plataformaSprite);
					if(i-1 >= 0 && pixels[i-1+(j*image.getWidth())] != 0xFFFE4D47) {
						tiles[i + (j*image.getWidth())] = new Ground(i*tile_size,j*tile_size,Tiles.plataformaSpriteLeft);
					}else if(pixels[i+1+(j*image.getWidth())] != 0xFFFE4D47) {
						tiles[i + (j*image.getWidth())] = new Ground(i*tile_size,j*tile_size,Tiles.plataformaSpriteRight);
					}
				}else if(pxAtual == 0xFF7F3300) {
					//Chao Sprite
					tiles[i + (j*image.getWidth())] = new Ground(i*tile_size,j*tile_size,Tiles.groundSprite);
					if(j-1 > 0 &&  pixels[(i+((j-1)*image.getWidth()))] == 0xFF7F3300 ) {
						tiles[i + (j*image.getWidth())] = new Ground(i*tile_size,j*tile_size,Tiles.groundMiddle);
					}
					if(i-1 > 0 && pixels[(i-1+(j*image.getWidth()))] != 0xFF7F3300 ) {
						tiles[(i + (j*image.getWidth()))] = new Ground(i*tile_size,j*tile_size,Tiles.groundRightTop);
						if(j-1 > 0 &&  pixels[(i+((j-1)*image.getWidth()))] == 0xFF7F3300) {
							if(j+1 < height &&  pixels[(i+((j+1)*image.getWidth()))] != 0xFF7F3300) {
								tiles[i + (j*image.getWidth())] = new Ground(i*tile_size,j*tile_size,Tiles.groundRightDown);
							}else {
								tiles[i + (j*image.getWidth())] = new Ground(i*tile_size,j*tile_size,Tiles.groundMiddleDown);
							}
						}
					}else if(i+1 < width && pixels[(i+1+(j*image.getWidth()))] != 0xFF7F3300 ) {
						tiles[(i + (j*image.getWidth()))] = new Ground(i*tile_size,j*tile_size,Tiles.groundLeftTop);
						if(j-1 > 0 &&  pixels[(i+((j-1)*image.getWidth()))] == 0xFF7F3300 && j+1 < height &&  pixels[(i+((j+1)*image.getWidth()))] != 0xFF7F3300) {
							if(j+1 < height &&  pixels[(i+((j+1)*image.getWidth()))] != 0xFF7F3300) {
								tiles[i + (j*image.getWidth())] = new Ground(i*tile_size,j*tile_size,Tiles.groundLeftDown);
							}else {
								tiles[i + (j*image.getWidth())] = new Ground(i*tile_size,j*tile_size,Tiles.groundMiddleDown);
							}
						}
					}

				}else if(pxAtual == 0xFFFF9D00) {

				}else if(pxAtual == 0xFF252F33) {
					//Tartaruga
					Tartaruga turtle = new Tartaruga(i*tile_size,j*tile_size,32,32,null);
					Game.entities.add(turtle);
				}else if(pxAtual == 0xFF44444) {
					//Cogumelo
				}else if(pxAtual == 0xFF0E2800) {
					//Cano SpriteCheck
				}else if(pxAtual == 0xFFD7CAC8) {
					//Bandeira SpriteCheck
				}else if(pxAtual == 0xFF2E7C59) {
					//Agua
					Water water;
					water = new Water(i*tile_size,j*tile_size,tile_size,tile_size,Entity.waterSprite);
					if(j-1 >= 0 && pixels[(i + ((j-1)*image.getWidth()))] == 0xFF2E7C59){
						water = new Water(i*tile_size,j*tile_size,tile_size,tile_size,Entity.waterSpriteSub);
					}
					if(j-2 >= 0 && pixels[(i + (j-2)*image.getWidth())] == 0xFF2E7C59){
						water = new Water(i*tile_size,j*tile_size,tile_size,tile_size,Entity.waterSpriteSub1);
					}
					if(j-3 >= 0 && pixels[(i + (j-3)*image.getWidth())] == 0xFF2E7C59){
						water = new Water(i*tile_size,j*tile_size,tile_size,tile_size,Entity.waterSpriteSub2);
					}
					Game.entities.add(water);
				}else if(pxAtual == 0xFFFF009D) {
					//Player
					Game.player = new Player(i*tile_size,j*tile_size,32,32,null);
					Game.entities.add(Game.player);
				}else if(pxAtual == 0xFFFAFF00) {
					//Moeda
					Coin coin = new Coin(i*tile_size,j*tile_size,32,32,null);
					Game.entities.add(coin);
				}else if(pxAtual == 0xFF7C4724) {
					Chest chest = new Chest(i*tile_size,j*tile_size,tile_size,tile_size,null);
					Game.entities.add(chest);
				}else if(pxAtual == 0xFFB0C510) {
					Snake snake = new Snake(i*tile_size,j*tile_size,tile_size,tile_size,null);
					Game.entities.add(snake);
				}else if(pxAtual == 0xFFA12BC0) {
					Boar boar = new Boar(i*tile_size,j*tile_size,tile_size,tile_size,null);
					Game.entities.add(boar);
				}
			}
		}
		
		}catch(IOException e) {
			return;
		}
	}
	public static boolean isFree(int xnext, int ynext) {
		
		int x1 = xnext / tile_size;
		int y1 = ynext/ tile_size;
		
		int x2 = (xnext+tile_size-1) / tile_size;
		int y2 = ynext/ tile_size;
		
		int x3 = xnext / tile_size;
		int y3 = (ynext+tile_size-1)/ tile_size;
		
		int x4 = (xnext+tile_size-1) / tile_size;
		int y4 = (ynext+tile_size-1)/ tile_size;
		
		if(xnext > width* 32 || ynext > height*32 || xnext < 2 || ynext < 2)
			return false;
		
		return !(tiles[x1+(y1*World.width)] instanceof Ground ||
				tiles[x2+(y2*World.width)] instanceof Ground ||
				tiles[x3+(y3*World.width)] instanceof Ground ||
				tiles[x4+(y4*World.width)] instanceof Ground );
	}
	public static void worldRestart(String path) {
		Game.entities.clear();
		Game.image = new BufferedImage(Game.WIDTH,Game.HEIGHT,BufferedImage.TYPE_INT_RGB);
		Game.spritesheet = new Spritesheet("/text.png");
		Game.score = 0;
		Game.pscore = 0;
		Game.world = new World(path);
		Game.ui = new UI();
	}
	
	
	public void render(Graphics g) {
		
		int xstart = Camera.x / tile_size ;
		int ystart = Camera.y / tile_size ;
		int xfinal = xstart  + (Game.WIDTH / tile_size) ;
		int yfinal = ystart + (Game.HEIGHT/ tile_size) ;
		for(int i = xstart; i <= xfinal + 1; i++) {
			for(int j = ystart; j <= yfinal + 1;  j++) {
				if(i < 0 || j < 0 || i >= width|| j >= height) {
					continue;
				}
				Tiles tile = tiles[i+ (j*width)];
				if(tile != null)
					tile.render(g);
			}
		}
	}

}

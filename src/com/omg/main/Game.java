package com.omg.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.omg.entities.Entity;
import com.omg.entities.Player;
import com.omg.graph.Spritesheet;
import com.omg.graph.UI;
import com.omg.world.World;

public class Game extends Canvas implements Runnable, KeyListener{
	
	
	private static final long serialVersionUID = 1227254042505466843L;
	
	///Definindo parametros para janela
	public static JFrame frame;
	public static int WIDTH = 900;
	public static int HEIGHT = 500;
	public static int SCALE = 1;
	///Fim parametros para janela
	
	public static BufferedImage image;
	public static List<Entity> entities;
	public static Spritesheet spritesheet;
	public static Spritesheet spriteground;
	public static BufferedImage back;
	public static Player player;
	public static Random rand;
	public static UI ui;
	public static World world;
	private Thread thread;
	private boolean isRunning = true;
	public String path = "/map1.png";
	public static double score = 0;
	public static int pscore = 0;

	public Game() {
		
		///Cria��o da Janela
		setPreferredSize(new Dimension(SCALE*WIDTH,SCALE*HEIGHT));
		inicia_frame();
		///
		addKeyListener(this);
		entities = new ArrayList<Entity>();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);
		try {
			back = ImageIO.read(getClass().getResource("/bgclouds.png"));
		}catch(IOException e) {
			
		}
		spritesheet = new Spritesheet("/text.png");
		spriteground = new Spritesheet("/groundFlorest.png");
		rand = new Random();
		world = new World("/map1.png");
		entities.add(player);
		ui = new UI();
		
	}
	
	public void inicia_frame() {///Inicializa janela
		frame = new JFrame("Jungle");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}
		
	public void tick() {
		player.tick();
		for(int i = 0; i <  entities.size();i++) {
			entities.get(i).tick();
		}
		if(player.life == 0) {
			//World.worldRestart(path);
		}
	}
	
	public void  render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(122,102,255));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		
		/*Renderiza��o do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		g.drawImage(back,0,0,this.WIDTH*SCALE,HEIGHT*SCALE,null);
		//bg.render(g);
		world.render(g);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		ui.render(g);
		bs.show();
	}
	
	public void run() {
		
		//Implementa��o game looping
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		double timer = System.currentTimeMillis();
		int frames = 0;
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta+=(now - lastTime)/ns;
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				frames = 0;
				timer += 1000;
			}
		}//Fim implementa��o game looping
		
		stop();
}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true; //Direita
			player.dir = 1;
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;//Esquerda
			player.dir = 2;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.jump = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_E)
			player.interaction = true;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false; //Direita
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;//Esquerda
		}
		if(e.getKeyCode() == KeyEvent.VK_E)
			player.interaction = false;
	}
}

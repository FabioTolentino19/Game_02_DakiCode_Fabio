package com.tolentsgames.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;

import com.tolentsgames.entities.Enemy;
import com.tolentsgames.entities.Entity;
import com.tolentsgames.entities.Player;
import com.tolentsgames.graficos.MapConverter;
import com.tolentsgames.graficos.Spritesheet;
import com.tolentsgames.graficos.UI;
import com.tolentsgames.world.FloorTile;
import com.tolentsgames.world.Tile;
import com.tolentsgames.world.WallTile;
import com.tolentsgames.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {
		
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static int WIDTH = 448;
	public static int HEIGHT = 576; 
	public static int SCALE = 1;
		
	private BufferedImage image;
	public BufferedImage teste, spriteFull;
	
	public static List<Entity> entities;
	public static Spritesheet spritesheet, spritesheet2, spritesheet2_b;
	public static Spritesheet originalLevel, buildMap;
	public static World world;
	public static Player player;
	public static MapConverter mapConverter;
	private int ghostsOut = 0; 
	
	public UI ui;
	
	public static int frutas_atual = 0;
	public static int frutas_contagem = 0;
	
	public static int vitaminas_atual = 0;
	public static int vitaminas_contagem = 0;
	
	
	public Game() {
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		
		//Inicializando objetos.		
		buildMap = new Spritesheet("/buildMap.png");
		spritesheet = new Spritesheet("/spritesheet.png");
		spritesheet2 = new Spritesheet("/spritesheet2.png");
		spritesheet2_b = new Spritesheet("/spritesheet2_branco.png");
		originalLevel = new Spritesheet("/originalLevel.png");
		entities = new ArrayList<Entity>();
		player = new Player(0, 0, 32, 32, 2, spritesheet.getSprite(64, 544, 32, 32));
		world = new World("/level1.png");
		ui = new UI();							
		
		entities.add(player);
//		spriteFull = spritesheet.getSprite(0, 0, 160, 160);
//		teste = originalLevel.getSprite(16, 8*16, 16, 16);
//		spriteFull.getGraphics().drawImage(teste, 0, 48, null);
//		spritesheet.setSprite(spriteFull); //substitui imagem inteira
//		spritesheet.spriteSave("png", "C:\\Users\\FabioDesktop\\eclipse-workspace\\Game_02_Fabio\\res\\spritesheet.png");
//		try {
//		Thread.sleep(10000);
//	} catch (InterruptedException e) {
//		e.printStackTrace();
//	}
		//mapConverter = new MapConverter("/originalLevel.png");

	}
	
	public void initFrame() {
		frame = new JFrame("Pac-Man");
		frame.add(this);
		//frame.setUndecorated(true); //Retira a barra de comandos para minimizar ou fechar o app.
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
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
		ghostsOut = 0;
		//System.out.println("=================================");
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
			if(e instanceof Enemy) {
				//System.out.println(" x: " + e.getX()/16 + " y: " + e.getY()/16);
				if((e.getX()/16 <= 11 || e.getX()/16 >= 16) || (e.getY()/16 <= 15 || e.getY()/16 >= 18))
					ghostsOut++;
			}
		}
		if(ghostsOut == 4) {
			//System.out.println("Todos fora....");
			World.tiles[13 + (15 * World.WIDTH)] = new WallTile(13*16, 15*16, Tile.TILE_HORIZONTAL_6);
			World.tiles[14 + (15 * World.WIDTH)] = new WallTile(14*16, 15*16, Tile.TILE_HORIZONTAL_6);
		} else {			
				//System.out.println("Pelo Menos um dentro....");
				World.tiles[13 + (15 * World.WIDTH)] = new FloorTile(13*16, 15*16, Tile.TILE_DOOR_1);
				World.tiles[14 + (15 * World.WIDTH)] = new FloorTile(14*16, 15*16, Tile.TILE_DOOR_1);		
		}
	}
		
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		/* Rederização do jogo */
		//Graphics2D g2 = (Graphics2D) g;
		
		world.render(g);
		Collections.sort(entities, Entity.nodeSorter);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);					
		}
		
		//mapConverter.render(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		ui.render(g);
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1 ) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}
		
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
			player.left = false;
			player.down = false;
			player.up = false;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
			player.right = false;
			player.down = false;
			player.up = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = true;
			player.left = false;
			player.down = false;
			player.right = false;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = true;
			player.up = false;
			player.left = false;
			player.right = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_A) {
			if(Sound.musicVolume > 0) {
				Sound.musicVolume--;
				//Sound.musicBackground.setVolume(Sound.musicVolume);
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Q) {
			if(Sound.musicVolume < Sound.maxVolume) {
				Sound.musicVolume++;
				//Sound.musicBackground.setVolume(Sound.musicVolume);
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_S) {
			if(Sound.effectsVolume > 0)
				Sound.effectsVolume--;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_W) {
			if(Sound.effectsVolume < Sound.maxVolume)
				Sound.effectsVolume++;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
/*		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = false;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = false;
		}  */
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	
}

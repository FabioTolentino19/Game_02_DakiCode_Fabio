package com.tolentsgames.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import com.tolentsgames.main.Game;
import com.tolentsgames.world.Node;
import com.tolentsgames.world.Vector2i;

public class Entity {
	
	public boolean ghostMode = false;
	public int ghostFrames = 0;
	public int ghostFramesMax = 60*6;
	public int nextTime = Entity.rand.nextInt(60*5 - 60*3) + 60*3;
	public int xCongela = 0, yCongela = 0;
	
	public static BufferedImage MACA_SPRITE = Game.spritesheet.getSprite(16, 64, 16, 16);
	public static BufferedImage VITAMINA_SPRITE = Game.spritesheet.getSprite(16, 6*16, 16, 16);
	public static BufferedImage ENEMY1 = Game.spritesheet.getSprite(128, 512, 32, 32);
	public static BufferedImage ENEMY2 = Game.spritesheet.getSprite(128, 544, 32, 32);
	public static BufferedImage ENEMY1_ghost = Game.spritesheet.getSprite(160, 512, 32, 32);
	public static BufferedImage ENEMY2_ghost = Game.spritesheet.getSprite(160, 544, 32, 32);
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected double speed;
	
	public int depth;
	
	protected List<Node> path;
	
	public boolean debug = false;
	
	protected BufferedImage sprite;
	
	public static Random rand = new Random();
	
	public Entity(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
	}
	
	public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity n0, Entity n1) {
			if(n1.depth < n0.depth)
				return +1;
			if(n1.depth > n0.depth)
				return -1;
			return 0;
		}
		
	};
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void tick() {}
	
	public double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public void followPath(List<Node> path) {
		if(path != null) {
			if(path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				//double xprev = x;
				//double yprev = y;
				if(x < target.x * 16) {
					x++;
				} else if(x > target.x * 16 ) {
					x--;
				}
				
				if(y < target.y * 16) {
					y++;
				} else if(y > target.y * 16) {
					y--;
				}
				
				if(x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size() - 1);
				}
			}
		}
	}
	
	public static boolean isColidding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX(), e1.getY(), e1.getWidth(), e1.getHeight());
		Rectangle e2Mask = new Rectangle(e2.getX(), e2.getY(), e2.getWidth(), e2.getHeight());
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX(), this.getY(), null);
	}
}

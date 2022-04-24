package com.tolentsgames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.tolentsgames.main.Game;
import com.tolentsgames.world.AStar;
import com.tolentsgames.world.Vector2i;



public class Enemy extends Entity {
	
		
	public Enemy(int x, int y, int width, int height, int speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		depth = 1;
		if(ghostMode == false) {
			folowPlayer();			
		} else {
			escapePlayer();
			ghostFrames++;
			if(ghostFrames == ghostFramesMax) {
				ghostFrames = 0;
				ghostMode = false;
			}
		}
/*
		System.out.println("ghostFrames: " + ghostFrames + " ghostMode: " + ghostMode + " HashCode: " + this.hashCode());
		ghostFrames++;
		if(ghostFrames == nextTime)
		{
			nextTime = Entity.rand.nextInt(60*5 - 60*3) + 60*3;
			ghostFrames = 0;
			if(ghostMode == false)
			{
				ghostMode = true;
			} else {
				ghostMode = false;
			}
		}
		*/	
	}
	
	public void folowPlayer () {
		int xx = 0, yy = 0;
		xx = (int)Game.player.x/16;
		yy = (int)Game.player.y/16;
		
		//System.out.println("xx: " + xx + " yy: " + yy);
	
		if(path == null || path.size() == 0) {
			Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
			Vector2i end = new Vector2i(xx, yy);
			path = AStar.findPath(Game.world, start, end);
		}	
				
		if(new Random().nextInt(100) < 80)
			followPath(path);
		if(x % 16 == 0 && y % 16 == 0) {
			if(new Random().nextInt(100) < 10) {
			Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
			Vector2i end = new Vector2i(xx, yy);
			path = AStar.findPath(Game.world, start, end);
			}
		}
	}
	
	public void escapePlayer () {
		int xx = 1, yy = 4; 
		if(ghostFrames == 0) {
			xCongela = (int)(Game.player.x/16);
			yCongela = (int)(Game.player.y/16);
		}
			
		if(xCongela >= 0 && xCongela <= 3)
			xx = 26;
		if(yCongela >= 5 && yCongela <= 7)
			yy = 32;
		
		//System.out.println("xx: " + xx + " yy: " + yy);
		
		if(path == null || path.size() == 0) {
			Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
			Vector2i end = new Vector2i(xx, yy);
			path = AStar.findPath(Game.world, start, end);
		}	
				
		if(new Random().nextInt(100) < 80)
			followPath(path);
		if(x % 16 == 0 && y % 16 == 0) {
			if(new Random().nextInt(100) < 10) {
			Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
			Vector2i end = new Vector2i(xx, yy);
			path = AStar.findPath(Game.world, start, end);
			}
		}
	}
	
	public void killGhost() {
		this.setX(15*16);
		this.setY(17*16);
	}
	
	public void render(Graphics g) {
		
		if(!ghostMode) {
			if(this.sprite == Entity.ENEMY1_ghost)
				this.sprite = Entity.ENEMY1;
			if(this.sprite == Entity.ENEMY2_ghost)
				this.sprite = Entity.ENEMY2;
			g.drawImage(sprite, this.getX()-8, this.getY()-8, null);
		} else {
			if(this.sprite == Entity.ENEMY1)
				this.sprite = Entity.ENEMY1_ghost;
			if(this.sprite == Entity.ENEMY2)
				this.sprite = Entity.ENEMY2_ghost;
			g.drawImage(sprite, this.getX()-8, this.getY()-8, null);
		}
			
	}

}

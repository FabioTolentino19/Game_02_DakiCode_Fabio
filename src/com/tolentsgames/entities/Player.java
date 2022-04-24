package com.tolentsgames.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.tolentsgames.main.Game;
import com.tolentsgames.world.World;

public class Player extends Entity {
	
	public boolean right, up, left, down;
	
	public BufferedImage sprite_left;
	public BufferedImage sprite_up;
	public BufferedImage sprite_down;
	public BufferedImage sprite_full;
	
	public int lastDir = 1;
	
	private int frames = 0, maxFrames = 3, index = 0, maxIndex = 1;
	private boolean moved = false;
	private int tunel = 0;
	
	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		sprite_left = Game.spritesheet.getSprite(32, 544, 32, 32);
		sprite_up = Game.spritesheet.getSprite(32, 512, 32, 32);
		sprite_down = Game.spritesheet.getSprite(64, 512, 32, 32);
		sprite_full = Game.spritesheet.getSprite(96, 544, 32, 32);
	}
	
		
	public void tick() {
		moved = false;
		depth = 2;
		
		switch(tunel) {
		
		case 0:
			
			if(x == -14 && y == 272)
				tunel = 2;
			if(x == 432 && y == 272)
				tunel = 1;
		
			if(right && World.isFree((int)(x+speed), this.getY())) {
				moved = true;
				x += speed;
				lastDir = 1;
			}
			else if(left && World.isFree((int)(x-speed), this.getY())) {
				moved = true;
				x -= speed;
				lastDir = -1;
			}
			if(up && World.isFree(this.getX(), (int)(y-speed))) {
				moved = true;
				y -= speed;
				lastDir = 2;
			}
			else if(down && World.isFree(this.getX(), (int)(y+speed))) {
				moved = true;
				y += speed;
				lastDir = -2;
			}
			
			verificarPegaFruta();
			verificarPegaVitamina();
			checkMove();
				
			break;
			
		case 1:
			
			if(right && x <= 448) {
				moved = true;
				x += speed;
				lastDir = 1;
				if(x > 448) {
					tunel = 2;
					x = -32;
				}
			}
			else if(left && x >= 430) {
				moved = true;
				x -= speed;
				lastDir = -1;
				if(x < 430)
					tunel = 0;
			}
			
			checkMove();
			break;
			
		case 2:
			
			if(right && x <= -14) {
				moved = true;
				x += speed;
				lastDir = 1;
				if( x > -14)
					tunel = 0;
			}
			else if(left && x >= -30) {
				moved = true;
				x -= speed;
				lastDir = -1;
				if (x < -30) {
					tunel = 1;
					x = 448;				
				}
			}
			
			checkMove();
			break;
		}
		
			
		
//		if(Game.frutas_atual == Game.frutas_contagem) {
//			System.out.println("Ganhamos o jogo !!!");
//			World.restartGame();
//		}
	}
	
	public void verificarPegaFruta() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Fruta) {
				if(isColidding(this, current)) {
					if(this.getX() == (13*16+8) && this.getY() == (26*16)) { // se está na posição inicial não come a fruta
						return;
					} else {
						Game.frutas_atual++;
						Game.entities.remove(i);
						return;	
					}
				}
			}
		}
	}
	
	public void verificarPegaVitamina() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Vitamina) {
				if(isColidding(this, current)) {
					Game.vitaminas_atual++;
					Game.entities.remove(i);
					for(int j = 0; j < Game.entities.size(); j++) {
						Entity e = Game.entities.get(j);
						if(e instanceof Enemy) {
							e.ghostMode = true;
							((Enemy) e).killGhost();
						}
					}
					return;	
					}
				}
			}
		}
	
	public static boolean isColidding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX(), e1.getY(), e1.getWidth()-16, e1.getHeight()-16);
		Rectangle e2Mask = new Rectangle(e2.getX(), e2.getY(), e2.getWidth(), e2.getHeight());
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void checkMove() {
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		} else {
			index = 0;
		}
	}
	
	public void render(Graphics g) {
		
		switch(index) {
		
		case 0:
			switch(lastDir) {
			
			case  1: g.drawImage(sprite, 		this.getX() - 8, this.getY() - 8, null); break;
			case -1: g.drawImage(sprite_left, 	this.getX() - 8, this.getY() - 8, null); break;
			case  2: g.drawImage(sprite_up, 	this.getX() - 8, this.getY() - 8, null); break;
			case -2: g.drawImage(sprite_down, 	this.getX() - 8, this.getY() - 8, null); break;
			}
			break;
		
		case 1: g.drawImage(sprite_full, 	this.getX() - 8, this.getY() - 8, null); break;
			
			
		}
	}
}

package com.tolentsgames.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tolentsgames.main.Game;

public class Tile {
	
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 16, 16);
	public static BufferedImage TILE_BACK_GROUND = Game.spritesheet.getSprite(0, 0, 16, 16);
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(0, 48, 16, 16);
	public static BufferedImage TILE_CORNER_1 = Game.spritesheet.getSprite(0, 48, 16, 16);
	public static BufferedImage TILE_VERTICAL_1 = Game.spritesheet.getSprite(0, 64, 16, 16);
	public static BufferedImage TILE_CORNER_2 = Game.spritesheet.getSprite(0, 12*16, 16, 16);
	public static BufferedImage TILE_HORIZONTAL_1 = Game.spritesheet.getSprite(0, 16*16, 16, 16);
	public static BufferedImage TILE_GRAY = Game.spritesheet.getSprite(0, 17*16, 16, 16);
	public static BufferedImage TILE_HORIZONTAL_2 = Game.spritesheet.getSprite(0, 18*16, 16, 16);
	public static BufferedImage TILE_CORNER_3 = Game.spritesheet.getSprite(0, 27*16, 16, 16);
	public static BufferedImage TILE_CORNER_4 = Game.spritesheet.getSprite(0, 28*16, 16, 16);
	public static BufferedImage TILE_HORIZONTAL_3 = Game.spritesheet.getSprite(16, 27*16, 16, 16);
	public static BufferedImage TILE_HORIZONTAL_4 = Game.spritesheet.getSprite(16, 28*16, 16, 16);
	public static BufferedImage TILE_CORNER_5 = Game.spritesheet.getSprite(2*16, 5*16, 16, 16);
	public static BufferedImage TILE_VERTICAL_2 = Game.spritesheet.getSprite(2*16, 6*16, 16, 16);
	public static BufferedImage TILE_CORNER_6 = Game.spritesheet.getSprite(2*16, 7*16, 16, 16);
	public static BufferedImage TILE_CORNER_7 = Game.spritesheet.getSprite(2*16, 27*16, 16, 16);
	public static BufferedImage TILE_CORNER_8 = Game.spritesheet.getSprite(2*16, 28*16, 16, 16);
	public static BufferedImage TILE_CORNER_9 = Game.spritesheet.getSprite(4*16, 25*16, 16, 16);
	public static BufferedImage TILE_VERTICAL_3 = Game.spritesheet.getSprite(5*16, 6*16, 16, 16);
	public static BufferedImage TILE_VERTICAL_4 = Game.spritesheet.getSprite(5*16, 13*16, 16, 16);
	public static BufferedImage TILE_CORNER_10 = Game.spritesheet.getSprite(7*16, 30*16, 16, 16);
	public static BufferedImage TILE_CORNER_11 = Game.spritesheet.getSprite(8*16, 12*16, 16, 16);
	public static BufferedImage TILE_CORNER_12 = Game.spritesheet.getSprite(8*16, 13*16, 16, 16);
	public static BufferedImage TILE_CORNER_13 = Game.spritesheet.getSprite(10*16, 15*16, 16, 16);
	public static BufferedImage TILE_VERTICAL_5 = Game.spritesheet.getSprite(10*16, 16*16, 16, 16);
	public static BufferedImage TILE_CORNER_14 = Game.spritesheet.getSprite(10*16, 19*16, 16, 16);
	public static BufferedImage TILE_HORIZONTAL_5 = Game.spritesheet.getSprite(12*16, 15*16, 16, 16);
	public static BufferedImage TILE_CORNER_15 = Game.spritesheet.getSprite(13*16, 3*16, 16, 16);
	public static BufferedImage TILE_DOOR_1 = Game.spritesheet.getSprite(13*16, 15*16, 16, 16);
	public static BufferedImage TILE_CORNER_16 = Game.spritesheet.getSprite(14*16, 3*16, 16, 16);
	public static BufferedImage TILE_HORIZONTAL_6 = Game.spritesheet.getSprite(15*16, 15*16, 16, 16);
	public static BufferedImage TILE_CORNER_17 = Game.spritesheet.getSprite(17*16, 15*16, 16, 16);
	public static BufferedImage TILE_CORNER_18 = Game.spritesheet.getSprite(17*16, 19*16, 16, 16);
	public static BufferedImage TILE_CORNER_19 = Game.spritesheet.getSprite(27*16, 3*16, 16, 16);
	public static BufferedImage TILE_CORNER_20 = Game.spritesheet.getSprite(27*16, 12*16, 16, 16);
	public static BufferedImage TILE_CORNER_21 = Game.spritesheet.getSprite(27*16, 27*16, 16, 16);
	public static BufferedImage TILE_CORNER_22 = Game.spritesheet.getSprite(27*16, 28*16, 16, 16);
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public static BufferedImage getTileSprite(Tile tile) {
		return tile.sprite;
	}
	
	public void setTileSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x, y, null);
	}
}

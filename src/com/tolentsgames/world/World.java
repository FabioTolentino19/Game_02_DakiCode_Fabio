package com.tolentsgames.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.tolentsgames.entities.Enemy;
import com.tolentsgames.entities.Entity;
import com.tolentsgames.entities.Fruta;
import com.tolentsgames.entities.Player;
import com.tolentsgames.entities.Vitamina;
import com.tolentsgames.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	public static boolean fullMap, premioMap;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy*map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					switch(pixelAtual) {
					
					case 0xFF000000:
					case 0xFF000010:
					case 0xFF0000A0: tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_BACK_GROUND); break;
					case 0xFF000020: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_1); break;
					case 0xFF000030: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_VERTICAL_1); break;
					case 0xFF000040: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_2); break;
					case 0xFF000050: 
					case 0xFF0000B0: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_HORIZONTAL_1); break;
					case 0xFF000060: 
					case 0xFF0000F0: tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_GRAY); break;
					case 0xFF000070: 
					case 0xFF0000E0: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_HORIZONTAL_2); break;
					case 0xFF000080: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_3); break;
					case 0xFF000090: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_4); break;
					case 0xFF0000C0: 
						Fruta fruta = new Fruta(xx*16, yy*16, 16, 16, 0, Entity.MACA_SPRITE);
						Game.entities.add(fruta);
						Game.frutas_contagem++;
						break;
					case 0xFF0000D0: 
						Vitamina vitamina = new Vitamina(xx*16, yy*16, 16, 16, 0, Entity.VITAMINA_SPRITE);
						Game.entities.add(vitamina);
						Game.vitaminas_contagem++;
						break;
					case 0xFF000100: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_HORIZONTAL_3); break;
					case 0xFF000110: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_HORIZONTAL_4); break;
					case 0xFF000120: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_5); break;
					case 0xFF000130: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_VERTICAL_2); break;
					case 0xFF000140: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_6); break;
					case 0xFF000150: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_7); break;
					case 0xFF000160: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_8); break;
					//0xFF000170,180 Vida não vou utilizar ainda
					//0xFF000190 letra vou gerar depois no java
					//0xFF0001A0,1B0 Vida não vou utilizar ainda
					//0xFF0001C0 letra vou gerar depois no java
					case 0xFF0001D0: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_9); break;
					//0xFF0001E0,1F0 letra vou gerar depois no java
					case 0xFF000200: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_VERTICAL_3); break;
					case 0xFF000210: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_VERTICAL_4); break;
					case 0xFF000220: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_10); break;
					case 0xFF000230: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_11); break;
					case 0xFF000240: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_12); break;
					//0xFF000250,260 letra vou gerar depois no java
					case 0xFF000270: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_13); break;
					case 0xFF000280: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_VERTICAL_5); break;
					case 0xFF000290: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_14); break;
					//0xFF0002A0 letra vou gerar depois no java
					case 0xFF0002B0: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_HORIZONTAL_5); break;
					case 0xFF0002C0: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_15); break;
					case 0xFF0002D0: tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_DOOR_1); break;
					//0xFF0002D0 letra vou gerar depois no java
					case 0xFF0002F0: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_16); break;
					//0xFF000300 letra vou gerar depois no java
					case 0xFF000310: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_HORIZONTAL_6); break;
					//0xFF000320,330 letra vou gerar depois no java
					case 0xFF000340: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_17); break;
					case 0xFF000350: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_18); break;
					//0xFF000360 letra vou gerar depois no java
					//0xFF000370,380,390,3A0 Cereja
					case 0xFF0003B0: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_19); break;
					case 0xFF0003C0: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_20); break;
					case 0xFF0003D0: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_21); break;
					case 0xFF0003E0: tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_CORNER_22); break;
					case 0xFF008E00: 
						Game.player.setX(xx*16+8);
						Game.player.setY(yy*16);
						break;
					case 0xFF00FFFF: 
						Enemy enemy = new Enemy(xx*16, yy*16, 32, 32, 1, Entity.ENEMY1);
						Game.entities.add(enemy);
						break;
					case 0xFFFF7F7F: 
						Enemy enemy2 = new Enemy(xx*16, yy*16, 32, 32, 1, Entity.ENEMY2);
						Game.entities.add(enemy2);
						break;
					}
					
					
					
					
					
					/*
					if(pixelAtual == 0xFF0000FF) {
						// chão
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_WALL);
					} else if(pixelAtual == 0xFFFFFFFF) {
						// parede
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
					} else if(pixelAtual == 0xFFFFD800) {
						// Player
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);						
					} else if(pixelAtual == 0xFF00A700) {
						// Maçã para nosso player comer!
						Fruta fruta = new Fruta(xx*16, yy*16, 16, 16, 0, Entity.MACA_SPRITE);
						Game.entities.add(fruta);
						Game.frutas_contagem++;
					} else if(pixelAtual == 0xFF00FFFF) {
						// Inimigo 1
						Enemy enemy = new Enemy(xx*16, yy*16, 16, 16, 1, Entity.ENEMY1);
						Game.entities.add(enemy);
					} else if(pixelAtual == 0xFFFF7F7F) {
						// Inimigo 2
						Enemy enemy = new Enemy(xx*16, yy*16, 16, 16, 1, Entity.ENEMY2);
						Game.entities.add(enemy);
					}
					*/
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFreeDynamic(int xnext, int ynext, int width, int height) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+width-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+height-1) / TILE_SIZE;
		
		int x4 = (xnext+width-1) / TILE_SIZE;
		int y4 = (ynext+height-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof WallTile)
				 );
	}

	
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof WallTile)
				 );
	}
		
	public static void restartGame() {
		Game.player = new Player(0, 0, 16, 16, 2, Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.entities.clear();
		Game.entities.add(Game.player);
		Game.frutas_atual = 0;
		Game.frutas_contagem = 0;
		Game.world = new World("/level1.png");
		return;	
	}
	
	public void render(Graphics g) {
		
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				Tile tile = tiles[x + (y*WIDTH)];
				tile.render(g);
			}
		}
	}
}

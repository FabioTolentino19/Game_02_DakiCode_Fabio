package com.tolentsgames.graficos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.tolentsgames.main.Game;
import com.tolentsgames.world.*;

public class MapConverter {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	public BufferedImage[][] spriteImages; 
	public BufferedImage[][] spriteUnico;
	public BufferedImage spritesUnicos;
	public static int[][] corSprites;
	public int spritesQuantity = 0, currentColor = 0, lastColor = 0;
	public static boolean newSprite = true;
	public static boolean checkSprite = false;
	public boolean complementar = false;
	
	
	public MapConverter(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			spriteImages = new BufferedImage[(map.getWidth())/16][(map.getHeight())/16];
			corSprites = new int[(map.getWidth())/16][(map.getHeight())/16];
			spriteUnico = new BufferedImage[(map.getWidth())/16][(map.getHeight())/16];
			spritesUnicos = Game.spritesheet2_b.getSprite(0, 0, 448, 676);
			int[] position = new int[2];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[(map.getWidth()) * (map.getHeight())];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int x = 0; x < map.getWidth()/16; x++) {
				for(int y = 0; y < map.getHeight()/16; y++) {
					spriteImages[x][y] = Game.originalLevel.getSprite(x*16, y*16, 16, 16);
					//g.drawImage(spriteImages[x][y], 50 , 50, null);
					//tiles[x + y*WIDTH] = new FloorTile(x, y, spriteImages[x + y*WIDTH]);
					
					System.out.println("Capturando imagem spriteImages[" + x + "]" + "[" + y + "]");
					
					if( x > 0 || y > 0 ) {
									
						System.out.println("Procurando imagem igual em spriteImages[" + x + "]" + "[" + y + "]");
						
						int xx, yy;
						if(x == 0)
							xx = 1;
						else
							xx = x;
						if(y == 0 && !complementar)
							yy = 1;
						else
							yy = y;
						
						if(x > 0) {
							complementar = true;
						}
						

						position = vectorContainImage(spriteImages[x][y], spriteImages, xx, yy);
						
						if(position[0] == -1)
							newSprite = true;
						else {
							newSprite = false;
							}
						}
						System.out.println("Geral  inicio  - currentColor: " + currentColor + " spritesQuantity: " + 
								spritesQuantity +" Position[" + position[0] + "][" + position[1] + "]");
						if(newSprite) {
							System.out.println("Novo Sprite             - currentColor: " + currentColor + " spritesQuantity: " 
									+ spritesQuantity);
							spriteUnico[x][y] = spriteImages[x][y];
							spritesUnicos.getGraphics().drawImage(spriteImages[x][y], x*16, y*16, null);
							corSprites[x][y] = currentColor;
							spritesQuantity++;
							Game.buildMap.setspriteRGB(x, y, currentColor);
							lastColor += 16;
							currentColor += 16;
							System.out.println("Novo NEXT               - currentColor: " + currentColor + " corSprites[" + x + "]" + "[" + y + "]: " + corSprites[x][y]);
						} else {
							System.out.println("Iguais         - currentColor: " + currentColor + " corSprites[" + x + "]" + "[" + y + "]: " + corSprites[x][y]);
							currentColor = corSprites[position[0]][position[1]];
							System.out.println("Iguais cor a ser colocada lor: " + currentColor + " corSprites[" + x + "]" + "[" + y + "]: " + corSprites[x][y]);
							Game.buildMap.setspriteRGB(x, y, currentColor);
							corSprites[x][y] = currentColor;
							System.out.println("Iguais - NEXT1 - currentColor: " + currentColor + " corSprites[" + x + "]" + "[" + y + "]: " + corSprites[x][y]);
							currentColor = lastColor;
							System.out.println("Iguais - NEXT2 - currentColor: " + currentColor + " corSprites[" + x + "]" + "[" + y + "]: " + corSprites[x][y]);						
						}
						System.out.println("Geral   NEXT   - currentColor: " + currentColor + " spritesQuantity: " + spritesQuantity);
						
//							try {
//								Thread.sleep(100);
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
					}				
				}
				
					
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Salvando novo mapa em: new_map.png...");
		Game.buildMap.spriteSave("png", "C:\\Users\\FabioDesktop\\eclipse-workspace\\Game_02_Fabio\\res\\new_map.png");
		System.out.println("Mapa Salvo !!!");
		System.out.println("Número de sprites encontrados: " + spritesQuantity);
		System.out.println("Salvando cores... ");
		saveCorMap(corSprites);
		System.out.println("Cores Salvas ");
		System.out.println("Salvando srites unicos... ");
		Game.spritesheet2_b.setSprite(spritesUnicos); //substitui imagem inteira
		Game.spritesheet2_b.spriteSave("png", "C:\\Users\\FabioDesktop\\eclipse-workspace\\Game_02_Fabio\\res\\spritesheet2_branco.png");
		System.out.println("Finalizada gravação !!! ");
	}
	
	public boolean imageEqual(BufferedImage image1, BufferedImage image2) {
		for(int k = 0; k < 16; k++) {
		    for(int l = 0; l < 16; l++) {
		    	// Compare the pixels for equality.
		    	//System.out.println("k: " + k + " l: " + l);
		    	if(image1.getRGB(k, l) != image2.getRGB(k, l)) {
		    		//System.out.println("k: " + k + " l: " + l + "Cheguei aqui, imagens diferentes !!!");
			    	return false;
			    	}
			    }
		    }
		//System.out.println("Sai por aqui imagens iguais");
		return true;
	}
	
	public int[] vectorContainImage(BufferedImage image1, BufferedImage[][] image2, int xx, int yy) {
		
		if(complementar) {
			for(int x = xx; x <= xx; x++) {
				for(int y = 0; y < yy; y++) {
					System.out.println("Complementar Procurando imagem igual em spriteImages[" + x + "]" + "[" + y + "]");
					System.out.println("xx: " + xx + " yy: " + yy);
					if(imageEqual(image1, image2[x][y])) {
						int[] retorno = {x, y};
						return retorno;
					}											
				}
			}
			yy = 36;
		}
		
		for(int x = 0; x < xx; x++) {
			for(int y = 0; y < yy; y++) {
				System.out.println("Dentro do Vector Procurando imagem igual em spriteImages[" + x + "]" + "[" + y + "]");
				System.out.println("xx: " + xx + " yy: " + yy);
				if(imageEqual(image1, image2[x][y])) {
					int[] retorno = {x, y};
					return retorno;
				}											
			}
		}
		int[] retorno = {-1, -1};	
		return retorno;
	}
	
	
	public static void saveCorMap(int[][] cores) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter( new FileWriter("res/save.txt"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		for(int x = 0; x < cores.length; x++) {
			for(int y = 0; y < cores[x].length; y++) {
			String value = "[ " + Integer.toString(x) + "] " + "[ " + Integer.toString(y) + "] " + " - " + Integer.toHexString(cores[x][y]);
			try {
				write.write(value);
				write.newLine();
			} catch(IOException e) {}
		}
	}
		try {
			write.flush();
			write.close();
		} catch(IOException e) {}
	}	
	
	
	
	public void render(Graphics g) {
		for(int x = 0; x < WIDTH/16; x++) {
			for(int y = 0; y < HEIGHT/16; y++) {
				System.out.println("Agora vamos colocar na tela... x:" + x + " y:" + y);
				g.setColor(Color.black);
				g.drawImage(spriteImages[x][y], 50, 50, null);
				//g.drawImage(spritesUnicos, 70, 50, null);
				String value = "[ " + Integer.toString(x) + "] " + "[ " + Integer.toString(y) + "] " + " - " + Integer.toString(corSprites[x][y]);
				g.drawString(value, 90, 50);
//				try {
//							Thread.sleep(1000);
//					} catch (InterruptedException e) {
//					e.printStackTrace();
//				}		
			}
		}
	}
}

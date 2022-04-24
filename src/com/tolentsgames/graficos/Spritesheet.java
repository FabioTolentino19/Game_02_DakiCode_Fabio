package com.tolentsgames.graficos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	
	private BufferedImage spritesheet;
	
	public Spritesheet(String path)
	{
		try {
			spritesheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}
	
	
	public void setSubSprite
    (BufferedImage smaller, BufferedImage larger, int x, int y) {
    larger.getGraphics().drawImage(smaller, x, y, null);
}
	
	
	public void setSprite(BufferedImage image) {
		this.spritesheet = image;
	}
	
	public void setspriteRGB(int x, int y, int rgb) {
		spritesheet.setRGB(x, y, rgb);
	}
	
	public void spriteSave(String type, String path) {
		try {
			ImageIO.write(spritesheet, type, new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

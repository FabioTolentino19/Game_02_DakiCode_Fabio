package com.tolentsgames.entities;

import java.awt.image.BufferedImage;

public class Vitamina extends Entity {

	public Vitamina(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		depth = 0;
	}

}

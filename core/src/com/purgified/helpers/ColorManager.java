package com.purgified.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;

public class ColorManager {
	
	private List<Color> colors;
	private Random randomGenerator;
	
	public ColorManager() {
		this.randomGenerator = new Random();
		this.colors = new ArrayList<Color>();
		
		// Set up 5 beautiful flat colors to be used in random order whenever game resets
		this.colors.add(new Color(26/255f, 188/255f, 156/255f, 1)); // Green
		this.colors.add(new Color(230/255f, 126/255f, 34/255f, 1)); // Orange
		this.colors.add(new Color(231/255f, 76/255f, 60/255f, 1)); // Red
		this.colors.add(new Color(52/255f, 152/255f, 219/255f, 1)); // Blue
		this.colors.add(new Color(52/255f, 73/255f, 94/255f, 1)); // Dark Blue
	}
	
	public Color getRandomColor() {
		int random = randomGenerator.nextInt(colors.size());
		
		return colors.get(random);
	}
	
}

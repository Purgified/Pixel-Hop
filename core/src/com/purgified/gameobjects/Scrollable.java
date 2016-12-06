package com.purgified.gameobjects;

import java.util.Random;

import Configuration.Configuration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Scrollable {
	
	public int BAR_MIN_VELOCITY = Configuration.BAR_MIN_VELOCITY;
	public int BAR_MAX_RANDOM_VELOCITY = Configuration.BAR_MAX_RANDOM_VELOCITY; 
	private static final int BAR_OFFSET = Configuration.BAR_OFFSET;
	
	protected Vector2 barAcceleration;
	protected Vector2 position;
	protected Vector2 velocity;
	protected int width;
	protected int height;
	protected boolean isScrolledDown, isScrolledUp;
	protected boolean isRightBar;
	
	private Rectangle bounds;
	private Random randomNumber = new Random();
	private int randomSpeed;
	
	
	public Scrollable(float x, float y, int width, int height, float scrollSpeed, boolean isRightBar) {
		randomSpeed = randomNumber.nextInt(BAR_MAX_RANDOM_VELOCITY) + BAR_MIN_VELOCITY;

		this.isRightBar = isRightBar;
		
		if (isRightBar) {
			position = new Vector2(x + BAR_OFFSET, y);
		} else {
			position = new Vector2(x - BAR_OFFSET, y);
		}
		
		velocity = new Vector2(randomSpeed, scrollSpeed); // Give vertical scrolling speed & horizontal expanding & contracting speed
		this.width = width;
		this.height = height;
		
		bounds = new Rectangle();
		bounds.set(x, y, width, height);
		
		isScrolledDown = false;
		isScrolledUp = false;
	}
	
	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));
		
		if (position.y + height < 0) {
			isScrolledDown = true;
			isScrolledUp = false;
		} else if (position.y > Gdx.graphics.getHeight() && velocity.y > 0) {
			isScrolledUp = true;
			isScrolledDown = false;
		}
		
		// If the Scrollable object is no longer visible
		if (isRightBar) {
			if (position.x < Gdx.graphics.getWidth() / 2) {
				velocity.x = randomSpeed;
			}
			
			if (position.x + width> Gdx.graphics.getWidth() + BAR_OFFSET) {
				velocity.x = -randomSpeed;
			}
		} else {
			if (position.x + width > Gdx.graphics.getWidth() / 2) {
				velocity.x = -randomSpeed;
			}
			
			if (position.x < -BAR_OFFSET) {
				velocity.x = randomSpeed;
			}
		}
		
	}
	
	//Should override for more specific behavior
	public void reset(float newY) {
		position.y = newY;
		isScrolledDown = false;
		isScrolledUp = false;
		randomSpeed = randomNumber.nextInt(BAR_MAX_RANDOM_VELOCITY) + BAR_MIN_VELOCITY;
	}
	
	// GETTERS
	public boolean isScrolledDown() {
		return isScrolledDown;
	}
	
	public boolean isScrolledUp() {
		return isScrolledUp;
	}
	
	public float getTailY() {
		return position.y + height;
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY(){
		return position.y;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void updateBounds() {
		bounds.x = position.x;
		bounds.y = position.y;
	}
	
	public Rectangle getBounds() {
		updateBounds();
		return bounds;
	}
	
	public int getBarSpeed() {
		return randomSpeed;
	}
	
	public void setBarSpeed(int randomSpeed) {
		this.randomSpeed = randomSpeed;
	}
	
	public void setBarSpeedY(int speed) {
		this.velocity.y = speed;
	}
	
	public void setScrollSpeed(float scrollSpeed) {
		velocity.y = scrollSpeed;
	}
}

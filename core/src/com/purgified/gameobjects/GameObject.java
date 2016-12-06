package com.purgified.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
	private Rectangle bounds;
	
	private Vector2 position;
	private Vector2 velocity;
	
	public GameObject(int width, int height) {
		bounds = new Rectangle();
		position = new Vector2();
		velocity = new Vector2();
		
		bounds.setWidth(width);
		bounds.setHeight(height);
	}
	
	// Update bounds with current position
	public void updateBounds() {
		bounds.x = position.x;
		bounds.y = position.y;
	}
	
	public void move(float x, float y) {
		position.set(x, y);
	}
	
	// Adds velocity components to position components so that its movement updates on the screen
	public void integrate(float delta) {
		position.add(velocity.x * delta, velocity.y * delta);
	}
	
	public Rectangle getBounds() {
		updateBounds();
		return bounds;
	}
	
	public void setBounds(Rectangle r) {
		this.bounds = r;
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public float getX() {
		return position.x;
	}
	
	public void setX(float x) {
		this.position.x = x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public void setY(float y) {
		this.position.y = y;
	}
	
	public float getHeight() {
		return bounds.height;
	}
	
	public float getWidth() {
		return bounds.width;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public void setVelocity(float x, float y) {
		velocity.set(x, y);
	}
}

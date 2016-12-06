package com.purgified.gameobjects;

import Configuration.Configuration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.purgified.helpers.AssetLoader;

public class Pixel extends GameObject {
	
	private Vector2 acceleration;
	private Vector2 velocity;
	private Vector2 position;
	
	private boolean dead = false;

	private float maxVel = Gdx.graphics.getWidth() * (5 / 18f);
	
	public Pixel() {
		super(Configuration.PIXEL_SIZE, Configuration.PIXEL_SIZE);
		
		acceleration = new Vector2(0, Configuration.PIXEL_ACCELERATION);
		velocity = getVelocity();
		position = getPosition();
	}
	
	public void update(float delta) {
		velocity.add(0, acceleration.y * delta);
		
		// Max velocity
		if (getVelocity().y > maxVel) {
			velocity.y = maxVel;
		}
		
		// If object goes below screen, it dies
		if (position.y < 0) {
			position.y = 0;
			dead = true;
		}
		
		// If object goes above screen, it dies
		if (position.y + getHeight() > Gdx.graphics.getHeight() && !dead) {
			AssetLoader.hit.play();
			dead = true;
		}
		
		if (!dead) {
			position.add(velocity.cpy().scl(delta));
			setPosition(position);
			setVelocity(velocity);
		}
	}
	
	// Main method called whenever user taps/clicks on screen
	public void onClick() {
		AssetLoader.jump.play();
		setVelocity(0, 300f);
		setAcceleration(0, Configuration.PIXEL_ACCELERATION);
	}
	
	// Ready position is when you're waiting for user to tap to start the game
	public void onReady() {
		setAcceleration(0, 0);
		setVelocity(0, 0);
		dead = false;
	}
	
	public void onRestart(Rectangle field) {
		move((field.width - getWidth()) / 2, 0);
		setVelocity(0f, 150f);
		setAcceleration(0, Configuration.PIXEL_ACCELERATION);
		dead = false;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void die() {
		dead = true;
	}
	
	public void setAcceleration(float x, float y) {
		acceleration.set(x, y);
	}
	
	public Vector2 getAcceleration() {
		return acceleration;
	}
}

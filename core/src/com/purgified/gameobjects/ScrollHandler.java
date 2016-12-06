package com.purgified.gameobjects;

import Configuration.Configuration;

import com.badlogic.gdx.Gdx;
import com.purgified.gameworld.GameWorld;

public class ScrollHandler {
	private GameWorld world;
	
	private Bar bar1, bar2, bar3;
	private Bar bar4, bar5, bar6;
	
	public int SCROLL_SPEED = Configuration.SCROLL_SPEED;
	public static final int BAR_GAP = Configuration.BAR_GAP;
	public static final int BAR_WIDTH_MIN = Configuration.BAR_WIDTH_MIN;
	public static final int BAR_HEIGHT = Configuration.BAR_HEIGHT;
	
	
	// Constructor receives a float that tells us where we need to create Bar objects
	public ScrollHandler(GameWorld world) {
		this.world = world;
		
		resetBars();
	}
	
	public void update(float delta) {
		bar1.update(delta);
		bar2.update(delta);
		bar3.update(delta);

		bar4.setBarSpeed(bar1.getBarSpeed());
		bar4.update(delta);
		bar5.setBarSpeed(bar2.getBarSpeed());
		bar5.update(delta);
		bar6.setBarSpeed(bar3.getBarSpeed());
		bar6.update(delta);
		
		
        
        // Check if any of the pipes are scrolled DOWN,
        // and reset accordingly
        if (bar1.isScrolledDown()) {
        	bar1.reset(bar3.getTailY() + BAR_GAP);
        } else if (bar2.isScrolledDown()) {
        	bar2.reset(bar1.getTailY() + BAR_GAP);
        } else if (bar3.isScrolledDown()) {
        	bar3.reset(bar2.getTailY() + BAR_GAP);
        }
        
        if (bar4.isScrolledDown()) {
        	bar4.reset(bar6.getTailY() + BAR_GAP);
        	bar4.setBarSpeed(bar1.getBarSpeed());
        } else if (bar5.isScrolledDown()) {
        	bar5.reset(bar4.getTailY() + BAR_GAP);
        	bar5.setBarSpeed(bar2.getBarSpeed());
        } else if (bar6.isScrolledDown()) {
        	bar6.reset(bar5.getTailY() + BAR_GAP);
        	bar6.setBarSpeed(bar3.getBarSpeed());
        }
        
        
	}
	
	public boolean collides(Pixel ball) {

		if (world.isRunning()) {
	        if (!bar1.isScored()
	                && bar1.getY() + (bar1.getHeight() / 2) < ball.getY()
	                        + ball.getHeight()) {
	            addScore(1);
	            bar1.setScored(true);
	            bar4.setScored(true);
	            checkScore();
	        } else if (!bar2.isScored()
	                && bar2.getY() + (bar2.getHeight() / 2) < ball.getY()
	                        + ball.getHeight()) {
	            addScore(1);
	            bar2.setScored(true);
	            bar5.setScored(true);
	            checkScore();
	        } else if (!bar3.isScored()
	                && bar3.getY() + (bar3.getHeight() / 2) < ball.getY()
	                        + ball.getHeight()) {
	            addScore(1);
	            bar3.setScored(true);
	            bar6.setScored(true);
	            checkScore();
	
	        }
	        
	        
		}
		
        return doesCollide(ball);
    }
	
	public boolean doesCollide(Pixel ball) {	
		return (ball.getBounds().overlaps(bar1.getBounds()) ||
				ball.getBounds().overlaps(bar2.getBounds()) ||
				ball.getBounds().overlaps(bar3.getBounds()) ||
				ball.getBounds().overlaps(bar4.getBounds()) ||
				ball.getBounds().overlaps(bar5.getBounds()) ||
				ball.getBounds().overlaps(bar6.getBounds())); 
	}
	
	public void checkScore() {
		if (world.score == 5) {
			SCROLL_SPEED -= (int) (Gdx.graphics.getWidth() * (5 / 36f)); // 100
			setScroll(SCROLL_SPEED);
		} else if (world.score == 10) {
			SCROLL_SPEED -= (int) (Gdx.graphics.getWidth() * (5 / 36f)); // 100
			setScroll(SCROLL_SPEED);
		} else if (world.score == 15) {
			SCROLL_SPEED -= (int) (Gdx.graphics.getWidth() * (1 / 24f)); // 30
			setScroll(SCROLL_SPEED);
		} else if (world.score == 20) {
			SCROLL_SPEED -= (int) (Gdx.graphics.getWidth() * (1 / 12f)); // 60
			setScroll(SCROLL_SPEED);
		} else if (world.score == 40) {
			SCROLL_SPEED -= (int) (Gdx.graphics.getWidth() * (1 / 24f)); // 30
			setScroll(SCROLL_SPEED);
		}
	}
	
	public void addScore(int inc) {
		world.addScore(inc);
	}
	
	public void resetBars() {
		int yPos = (Gdx.graphics.getHeight() - BAR_HEIGHT) / 2;
		int xPos = Gdx.graphics.getWidth() - BAR_WIDTH_MIN;
		
		bar1 = new Bar(0, yPos, BAR_WIDTH_MIN, BAR_HEIGHT, Configuration.SCROLL_SPEED, false);
		bar2 = new Bar(0, bar1.getTailY() + BAR_GAP, BAR_WIDTH_MIN, BAR_HEIGHT, Configuration.SCROLL_SPEED, false);
		bar3 = new Bar(0, bar2.getTailY() + BAR_GAP , BAR_WIDTH_MIN, BAR_HEIGHT, Configuration.SCROLL_SPEED, false);
		
		bar4 = new Bar(xPos, yPos, BAR_WIDTH_MIN, BAR_HEIGHT, Configuration.SCROLL_SPEED, true);
		bar5 = new Bar(xPos, bar4.getTailY() + BAR_GAP, BAR_WIDTH_MIN, BAR_HEIGHT, Configuration.SCROLL_SPEED, true);
		bar6 = new Bar(xPos, bar5.getTailY() + BAR_GAP, BAR_WIDTH_MIN, BAR_HEIGHT, Configuration.SCROLL_SPEED, true);
	}
	
	public void newGame() {
		int yPos = (Gdx.graphics.getHeight() - BAR_HEIGHT) / 2;
		
		bar1.reset(yPos);
		bar2.reset(bar1.getTailY() + BAR_GAP);
		bar3.reset(bar2.getTailY() + BAR_GAP);
		
		bar4.reset(yPos);
		bar5.reset(bar4.getTailY() + BAR_GAP);
		bar6.reset(bar5.getTailY() + BAR_GAP);
	}
	
	public void resetScored() {
		bar1.setScored(false);
		bar2.setScored(false);
		bar3.setScored(false);
		bar4.setScored(false);
		bar5.setScored(false);
		bar6.setScored(false);
		
		SCROLL_SPEED = Configuration.SCROLL_SPEED;
	}
	
	public void increaseBarsSpeed(int inc) {
		bar1.setBarSpeed(bar1.getBarSpeed() + inc);
		bar2.setBarSpeed(bar2.getBarSpeed() + inc);
		bar3.setBarSpeed(bar3.getBarSpeed() + inc);
		bar4.setBarSpeed(bar4.getBarSpeed() + inc);
		bar5.setBarSpeed(bar5.getBarSpeed() + inc);
		bar6.setBarSpeed(bar6.getBarSpeed() + inc);
	}
	
	// GETTERS
	public Bar getBar1() {
		return bar1;
	}
	
	public Bar getBar2() {
		return bar2;
	}
	
	public Bar getBar3() {
		return bar3;
	}
	
	public Bar getBar4() {
		return bar4;
	}
	
	public Bar getBar5() {
		return bar5;
	}
	
	public Bar getBar6() {
		return bar6;
	}
	
	public int getScrollSpeed() {
		return SCROLL_SPEED;
	}
	
	public void setNoScroll() {
		setScroll(0);
	}
	
	public void setScroll(int speed) {
		bar1.setScrollSpeed(speed);
		bar2.setScrollSpeed(speed);
		bar3.setScrollSpeed(speed);
		bar4.setScrollSpeed(speed);
		bar5.setScrollSpeed(speed);
		bar6.setScrollSpeed(speed);
	}

}

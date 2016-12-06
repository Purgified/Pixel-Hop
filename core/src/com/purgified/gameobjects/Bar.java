package com.purgified.gameobjects;

public class Bar extends Scrollable{
	
	private boolean isScored = false;
	
	public Bar(float x, float y, int width, int height, float scrollSpeed, boolean isRightBar) {
		super(x, y, width, height, scrollSpeed, isRightBar);
	}
	
	// Change to have random bar positions
	@Override
	public void reset(float newY) {
		super.reset(newY);
		isScored = false;
	}
	
	public boolean isScored() {
		return isScored;
	}
	
	public void setScored(boolean isScored) {
		this.isScored = isScored;
	}
}

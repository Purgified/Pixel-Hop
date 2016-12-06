package com.purgified.helpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.purgified.controllers.AdsController;
import com.purgified.gameobjects.Pixel;
import com.purgified.gameworld.GameWorld;
import com.purgified.ui.SimpleButton;

public class InputHandler implements InputProcessor{

    private Rectangle rectangle;
	
    private List<SimpleButton> menuButtons;
    private SimpleButton playButton;
    private SimpleButton rankButton;
    private SimpleButton shareButton;
    private SimpleButton achieveButton;

	private Pixel ball;
	private GameWorld myWorld;
	
	AdsController adsController;


	public InputHandler(GameWorld myWorld, AdsController adsController) {
		this.myWorld = myWorld;
		ball = myWorld.getBall();
		this.adsController = adsController;
		
		this.rectangle = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		
		// DONT ALTER THESE FLOAT VARIABLES - Math to position the buttons relatively for different screen sizes
		float bottomHeight = rectangle.getHeight() * 0.3f;
		float topHeight = rectangle.getHeight() * 0.108f;
		
		float leftSpace = rectangle.getHeight() * (1 / 10f);
		float rightSpace = rectangle.getHeight() * (1 / 10f);
		
		float buttonWidth = (float) AssetLoader.playButtonDown.getWidth();
		float buttonRadius = rectangle.getHeight() * 0.1f;
		
		menuButtons = new ArrayList<SimpleButton>();
		
		
		playButton = new SimpleButton(rectangle.width / 2 - buttonRadius / 2, rectangle.height / 2 - bottomHeight, buttonRadius, buttonRadius, AssetLoader.playButtonUp, AssetLoader.playButtonDown);
		
		playButton = new SimpleButton(rectangle.width / 2 - buttonRadius / 2, rectangle.height / 2 - bottomHeight, buttonRadius, buttonRadius, AssetLoader.playButtonUp, AssetLoader.playButtonDown);
        rankButton = new SimpleButton(rectangle.width / 2 - leftSpace - buttonRadius, rectangle.height / 2 - bottomHeight, buttonRadius, buttonRadius, AssetLoader.rankButtonUp, AssetLoader.rankButtonDown);
        shareButton = new SimpleButton(rightSpace + rectangle.width / 2, rectangle.height / 2 - bottomHeight, buttonRadius, buttonRadius, AssetLoader.shareButtonUp, AssetLoader.shareButtonDown);
        achieveButton = new SimpleButton(rectangle.width / 2 - buttonRadius / 2 , (rectangle.height / 2 - bottomHeight) + topHeight  + buttonWidth / 2, buttonRadius, buttonRadius, AssetLoader.achieveButtonUp, AssetLoader.achieveButtonDown);
        
        
        menuButtons.add(playButton);
        menuButtons.add(rankButton);
        menuButtons.add(shareButton);
        menuButtons.add(achieveButton);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (myWorld.isMenu() || myWorld.isHighScore()) {
			playButton.isTouchDown(screenX, screenY);
			rankButton.isTouchDown(screenX, screenY);
			shareButton.isTouchDown(screenX, screenY);
			achieveButton.isTouchDown(screenX, screenY);
		}
		if (myWorld.isReady()) {
			myWorld.start();
			ball.onClick();
		} else if (myWorld.isRunning()) {
			ball.onClick();
		} else if (myWorld.isGameOver() || myWorld.isHighScore()) {
			myWorld.reset();
		}

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (myWorld.isMenu() || myWorld.isHighScore()) {
            if (playButton.isTouchUp(screenX, screenY)) {
            	if (myWorld.isHighScore()) {
            		myWorld.resetRendererColor();
            		myWorld.resetBarsPositions();
            	}
                myWorld.ready();
                return true;
            } else if (rankButton.isTouchUp(screenX, screenY)) {
            	adsController.getLeaderboardGPGS();
            } else if (shareButton.isTouchUp(screenX, screenY)) {
            	adsController.showShareButton();
            } else if (achieveButton.isTouchUp(screenX, screenY)) {
            	adsController.getAchievementsGPGS();
            }
        }

		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	public List<SimpleButton> getMenuButtons() {
        return menuButtons;
    }
}

package com.purgified.gameworld;

import Configuration.Configuration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.purgified.gameobjects.Pixel;
import com.purgified.gameobjects.ScrollHandler;
import com.purgified.helpers.AssetLoader;
import com.purgified.pixelhop.PixelHop;

public class GameWorld {

	private ScrollHandler scroller;
	private Pixel ball;
	private Rectangle field;
	
	// Main score variable
	public int score = 0;
	// Temporary score is added so that it can be shown when its gameover, so that it can be drawn to the screen
	public int tempScore = 0;
	private boolean timed = false;

	private GameRenderer renderer;
	public GameState currentState;
	
	private PixelHop game;

	public enum GameState {
		MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
	}
	
	// Ads variables
	private boolean adShown = false;
	// -1 indicates that the ads will be shown after 2 deaths when the user FIRST launches the game
	private int adCounter = Configuration.AD_FREQUENCY - 1;
	
    public GameWorld(PixelHop game) {
    	currentState = GameState.MENU;
    	ball = new Pixel();
    	scroller = new ScrollHandler(this);
    	this.game = game;
		field = new Rectangle(); 
		field.width = Gdx.graphics.getWidth();
		field.height = Gdx.graphics.getHeight();
    	
		reset();
    }

    public void update(float delta) {
    	if (isMenu()) {
    		ball.onReady();
    		scroller.setNoScroll();
            scroller.update(delta);
    	} else {
    		updateBall(delta);
    		scroller.update(delta);
    	}
    }
 	
 	public void updateBall(float delta) {
 		if (isReady()) {
 			ball.onReady();
 			scroller.setNoScroll();
 		} else if (isRunning()) {
 			ball.setAcceleration(0, Configuration.PIXEL_ACCELERATION);
 			scroller.setScroll(scroller.getScrollSpeed());
 		} else {
 			scroller.setNoScroll();
 		}
 		
 		// Check ball collision with bars & update scores
 		if (scroller.collides(ball) || ball.isDead()) {
 			if (!ball.isDead()) {
 				AssetLoader.hit.play();
 				ball.die();
 			}
 			over();
 		}
 		
 		
 		
 		if (isGameOver()) {
 			if (score > AssetLoader.getHighScore()) {
                 AssetLoader.setHighScore(score);
             }
 			
 			reset();
 		}
 		
 		ball.integrate(delta);
 		ball.update(delta);
 	}
 	
 	public void resetBarsPositions() {
 		scroller.newGame();
 	}
 	
 	public void reset() {
		// Position ball in ready position
		if (isReady() || isMenu()) {
			score = 0;
			tempScore = 0;
			ball.onRestart(field);
			adShown = false;
		} else if (isGameOver()) {
			scroller.setScroll(-scroller.getScrollSpeed());
			ball.setAcceleration(0, ball.getAcceleration().y - 100);
			tempScore = score;
			
			// Show game over UI after 1.2 seconds
			if (!timed) {
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
			 			highscore();
			 			score = 0;
			 			timed = false;
						scroller.resetScored();
						ball.onReady();
						
			 			AssetLoader.addGamesPlayed();
						addAchievement();
			 			game.getAdsController().submitScoreGPGS(AssetLoader.getHighScore());
			 			
			 			if (Configuration.ADS_ON) {

							if (adCounter == Configuration.AD_FREQUENCY) {
								System.out.println("adShown? " + adShown);
								if (!adShown) {
									game.getAdsController().showOrLoadInterstitialAd();
									adShown = true;
								}
								adCounter--;
							} else if (adCounter == 0) {
								adCounter = Configuration.AD_FREQUENCY;
								adShown = false;
							} else {
								adCounter--;
							}
							
			 			}
			 			
					}
				}, 1.2f);
			}
			timed = true;
			
		}
	}
 	
 	// Checks for games played to add relevant achievements. If you add in more games played achievements, add in more if-statements
 	public void addAchievement() {
 		if (AssetLoader.getGamesPlayed() >= 10)
 			game.getAdsController().unlockAchievementGPGS(Configuration.ACHIEVEMENT_10_GAMES);
 		if (AssetLoader.getGamesPlayed() >= 25)
 			game.getAdsController().unlockAchievementGPGS(Configuration.ACHIEVEMENT_25_GAMES);
 		if (AssetLoader.getGamesPlayed() >= 50)
 			game.getAdsController().unlockAchievementGPGS(Configuration.ACHIEVEMENT_50_GAMES);
 		if (AssetLoader.getGamesPlayed() >= 100)
 			game.getAdsController().unlockAchievementGPGS(Configuration.ACHIEVEMENT_100_GAMES);
 		if (AssetLoader.getGamesPlayed() >= 200)
 			game.getAdsController().unlockAchievementGPGS(Configuration.ACHIEVEMENT_200_GAMES);

		if (AssetLoader.getHighScore() >= 5)
			game.getAdsController().unlockAchievementGPGS(Configuration.ACHIEVEMENT_5_POINTS);
		if (AssetLoader.getHighScore() >= 10)
			game.getAdsController().unlockAchievementGPGS(Configuration.ACHIEVEMENT_10_POINTS);
		if (AssetLoader.getHighScore() >= 25)
			game.getAdsController().unlockAchievementGPGS(Configuration.ACHIEVEMENT_25_POINTS);
		if (AssetLoader.getHighScore() >= 50)
			game.getAdsController().unlockAchievementGPGS(Configuration.ACHIEVEMENT_50_POINTS);
		if (AssetLoader.getHighScore() >= 100)
			game.getAdsController().unlockAchievementGPGS(Configuration.ACHIEVEMENT_100_POINTS);
		if (AssetLoader.getHighScore() >= 200)
			game.getAdsController().unlockAchievementGPGS(Configuration.ACHIEVEMENT_200_POINTS);
 		
 	}
 	
 	public void start() {
		currentState = GameState.RUNNING;
	}

	public void ready() {
		currentState = GameState.READY;
	}
	
	public void highscore() {
		currentState = GameState.HIGHSCORE;
	}
	
	public void over() {
		currentState = GameState.GAMEOVER;
	}
	
	public boolean isReady() {
		return currentState == GameState.READY;
	}

	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}

	public boolean isHighScore() {
		return currentState == GameState.HIGHSCORE;
	}

	public boolean isMenu() {
		return currentState == GameState.MENU;
	}

	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}
    
	public int getScore() {
		return score;
	}
	
	public void addScore(int score) {
		this.score += score;
	}

    public Pixel getBall() {
        return ball;
    }
    
    public void setRenderer(GameRenderer renderer) {
		this.renderer = renderer;
	}

	public ScrollHandler getScrollHandler() {
		return scroller;
	}
	
	// Resets the background color to a random color
	public void resetRendererColor() {
		renderer.resetColor();
	}
}

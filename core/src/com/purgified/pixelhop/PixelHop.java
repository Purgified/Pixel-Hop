package com.purgified.pixelhop;

import Configuration.Configuration;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.purgified.controllers.AdsController;
import com.purgified.helpers.AssetLoader;
import com.purgified.screens.GameScreen;
import com.purgified.screens.SplashScreen;

public class PixelHop extends Game {

    private AdsController adsController;

    public AdsController getAdsController () {
        return adsController;
    }

    public PixelHop () {
        this(null);
    }

    public PixelHop (AdsController adsController) {
        if (adsController != null) {
            this.adsController = adsController;
        } else {
            this.adsController = new DummyAdsController();
        }
    }

    @Override
	public void create() {
		AssetLoader.load();
		if (Configuration.SHOW_SPLASH_SCREEN) {
			setScreen(new SplashScreen(this, adsController));
		} else {
			setScreen(new GameScreen(this, adsController));
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
	
	// DON'T DELETE ANY OF THESE. These are dummies, and are only used when the desktop version is launched
    private class DummyAdsController implements AdsController {

        @Override
        public void showOrLoadInterstitialAd () {
            Gdx.app.debug("DummyAdController", "showInterstitialAd");
        }

        @Override
        public void showBannerAd () {
            Gdx.app.debug("DummyAdController", "showBannerAd");
        }

        @Override
        public void hideBannerAd () {
            Gdx.app.debug("DummyAdController", "hideInterstitialAd");
        }

        
        
        
		@Override
		public boolean getSignedInGPGS() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void loginGPGS() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void submitScoreGPGS(int score) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void unlockAchievementGPGS(String achievementId) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getLeaderboardGPGS() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getAchievementsGPGS() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void showShareButton() {
			// TODO Auto-generated method stub
			
		}

    }

}

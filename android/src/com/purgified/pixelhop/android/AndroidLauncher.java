package com.purgified.pixelhop.android;

import Configuration.Configuration;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.games.Games;
import com.purgified.controllers.AdsController;
import com.purgified.pixelhop.PixelHop;

import BaseGameUtil.GameHelper;

public class AndroidLauncher extends AndroidApplication implements GameHelper.GameHelperListener, AdsController {
	
	GameHelper gameHelper;
	
	public static GoogleAnalytics analytics;
	public static Tracker tracker;
    AdView bannerAd;
    InterstitialAd interstitialAd;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//		initialize(new CircleHop(), config);
		
		// Google Analytics setup
		analytics = GoogleAnalytics.getInstance(this);
	    analytics.setLocalDispatchPeriod(1800);

	    tracker = analytics.newTracker("UA-64838803-1"); // Replace with actual tracker/property Id
	    tracker.enableExceptionReporting(true);
	    tracker.enableAdvertisingIdCollection(true);
	    tracker.enableAutoActivityTracking(true);
		
	 // All subsequent hits will be send with screen name = "main screen"
	    tracker.setScreenName("main screen");

	    tracker.send(new HitBuilders.EventBuilder()
	           .setCategory("Launch")
	           .setAction("launched")
	           .setLabel("Launched App")
	           .build());
	    
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		
        View game = initializeForView(new PixelHop(this), config);
        setupAds();

        RelativeLayout layout = new RelativeLayout(this);
        layout.addView(game, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layout.addView(bannerAd, params);
        
        setContentView(layout);
        
        
        if (gameHelper == null) {
            gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
            gameHelper.enableDebugLog(true);
          }
        gameHelper.setup(this);
	}
	
	public void setupAds () {
		bannerAd = new AdView(this);
        bannerAd.setVisibility(View.INVISIBLE);
        bannerAd.setBackgroundColor(0xff000000);
        bannerAd.setAdUnitId(Configuration.BANNER_AD_UNIT_ID);
        bannerAd.setAdSize(AdSize.SMART_BANNER);
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(Configuration.INTERSTITIAL_AD_UNIT_ID);

        AdRequest.Builder builder = new AdRequest.Builder();
        AdRequest ad = builder.build();
        interstitialAd.loadAd(ad);
    }
	
	@Override
    public void showOrLoadInterstitialAd () {
        runOnUiThread(new Runnable() {
            @Override
            public void run () {
                if (interstitialAd.isLoaded()) {
                	interstitialAd.show();
                } else {
                	AdRequest interstitialRequest = new AdRequest.Builder().build();
                	interstitialAd.loadAd(interstitialRequest);
                }
            }
        });
    }


    @Override
    public void showBannerAd () {
        runOnUiThread(new Runnable() {
            @Override
            public void run () {
                bannerAd.setVisibility(View.VISIBLE);
                AdRequest.Builder builder = new AdRequest.Builder();
                AdRequest ad = builder.build();
                bannerAd.loadAd(ad);
            }
        });
    }

    @Override
    public void hideBannerAd () {
        runOnUiThread(new Runnable() {
            @Override
            public void run () {
                bannerAd.setVisibility(View.INVISIBLE);
            }
        });
    }

    
    
    
    @Override
	public void onStart(){
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	public void onStop(){
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
	}
	
	@Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable(){
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (final Exception ex) {
		}
	}

	@Override
	public void submitScoreGPGS(int score) {
		Games.Leaderboards.submitScore(gameHelper.getApiClient(), Configuration.LEADERBOARD_ID, score);
	}
	
	@Override
	public void unlockAchievementGPGS(String achievementId) {
	  Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
	}
	
	@Override
	public void getLeaderboardGPGS() {
	  if (gameHelper.isSignedIn()) {
	    startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), Configuration.LEADERBOARD_ID), 100);
	  }
	  else if (!gameHelper.isConnecting()) {
	    loginGPGS();
	  }
	}

	@Override
	public void getAchievementsGPGS() {
	  if (gameHelper.isSignedIn()) {
	    startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
	  }
	  else if (!gameHelper.isConnecting()) {
	    loginGPGS();
	  }
	}
	
	@Override
	public void onSignInFailed() {
	}

	@Override
	public void onSignInSucceeded() {
	}

	@Override
	public void showShareButton() {
		String packageName = getApplicationContext().getPackageName();
		String appName = getApplicationContext().getApplicationInfo().loadLabel(getApplicationContext().getPackageManager()).toString();
		
		
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
		sharingIntent.setType("text/plain");
		String shareBody = "Try " + appName + "! Super addicting :D https://play.google.com/store/apps/details?id=" + packageName ;
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, appName);
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		startActivity(Intent.createChooser(sharingIntent, "Share via"));
	}

	
}

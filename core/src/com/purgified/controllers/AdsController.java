package com.purgified.controllers;

public interface AdsController {

	// Controller for Ads
    public void showOrLoadInterstitialAd ();
    public void showBannerAd ();
    public void hideBannerAd ();
    
    // Controller for Leaderboard and Achievements
    public boolean getSignedInGPGS();
    public void loginGPGS();
    public void submitScoreGPGS(int score);
    public void unlockAchievementGPGS(String achievementId);
    public void getLeaderboardGPGS();
    public void getAchievementsGPGS();
    
    // Controller for Share Button
    public void showShareButton();

}

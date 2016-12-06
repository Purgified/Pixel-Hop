package com.purgified.helpers;

import Configuration.Configuration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	
	public static Texture barTexture, ballTexture, splashLogoTexture, gameLogoTexture;
    public static Texture  playButtonUp, playButtonDown;
    public static Texture rankButtonDown, rankButtonUp;
    public static Texture shareButtonDown, shareButtonUp;
    public static Texture achieveButtonDown, achieveButtonUp;
    public static TextureRegion bar, ball;
    public static TextureRegion splashLogo;
    
    public static ParticleEffect effect;
    public static Sound jump, hit;
	
	public static BitmapFont font, shadow;
	public static Sprite gameLogo;
	private static Preferences prefs;
	
	public static void load() {
        barTexture = new Texture(Gdx.files.internal("images/bar.png"));
        ballTexture = new Texture(Gdx.files.internal("images/pixel.png"));
        
        splashLogoTexture = new Texture(Gdx.files.internal("images/splashlogo.png"));
        splashLogoTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        gameLogoTexture = new Texture(Gdx.files.internal("images/gamelogo.png"));
        gameLogoTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        playButtonUp = new Texture(Gdx.files.internal("images/playButtonUp.png"));
        playButtonDown = new Texture(Gdx.files.internal("images/playButtonDown.png"));
        rankButtonUp = new Texture(Gdx.files.internal("images/rankButtonUp.png"));
        rankButtonDown = new Texture(Gdx.files.internal("images/rankButtonDown.png"));
        shareButtonUp = new Texture(Gdx.files.internal("images/shareButtonUp.png"));
        shareButtonDown = new Texture(Gdx.files.internal("images/shareButtonDown.png"));
        achieveButtonUp = new Texture(Gdx.files.internal("images/achieveButtonUp.png"));
        achieveButtonDown = new Texture(Gdx.files.internal("images/achieveButtonDown.png"));
        
        playButtonUp.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        playButtonDown.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        rankButtonUp.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        rankButtonDown.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        shareButtonUp.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        shareButtonDown.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        achieveButtonUp.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        achieveButtonDown.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        
        bar = new TextureRegion(barTexture, 0, 0, barTexture.getWidth(), barTexture.getHeight());
        ball = new TextureRegion(ballTexture, 0, 0, ballTexture.getWidth(), ballTexture.getHeight());
        splashLogo = new TextureRegion(splashLogoTexture, 0, 0, splashLogoTexture.getWidth(), splashLogoTexture.getHeight());
        jump = Gdx.audio.newSound(Gdx.files.internal("audio/jump.wav"));
        hit = Gdx.audio.newSound(Gdx.files.internal("audio/hit.wav"));
		
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("particles/particle.p"), Gdx.files.internal("particles"));
        effect.scaleEffect(Configuration.PARTICLE_EFFECTS_SIZE);
        
		font = new BitmapFont(Gdx.files.internal("font/text.fnt"));
		font.getData().setScale(Configuration.FONT_SIZE, Configuration.FONT_SIZE);
		
		gameLogo = new Sprite(gameLogoTexture, 0, 0, gameLogoTexture.getWidth(), gameLogoTexture.getHeight());
		float scaleX = Gdx.graphics.getWidth() / 720;
		gameLogo.setScale(scaleX, 1);
		
		// Create (or retrieve existing) preferences file
        prefs = Gdx.app.getPreferences("PixelHop");
		
		if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }

		if (!prefs.contains("gamesPlayed")) {
            prefs.putInteger("gamesPlayed", 0);
        }
	}
	
	public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }
	
	public static int getHighScore() {
        return prefs.getInteger("highScore");
    }
	
	public static void addGamesPlayed() {
        prefs.putInteger("gamesPlayed", getGamesPlayed() + 1);
        prefs.flush();
    }
	
	public static int getGamesPlayed() {
        return prefs.getInteger("gamesPlayed");
    }
	
	public static void dispose() {
		barTexture.dispose();
		ballTexture.dispose();
		playButtonDown.dispose();
		playButtonDown.dispose();
		achieveButtonDown.dispose();
		achieveButtonUp.dispose();
		shareButtonDown.dispose();
		shareButtonUp.dispose();
		rankButtonDown.dispose();
		rankButtonUp.dispose();
		
		
		effect.dispose();
		font.dispose();
	}
}

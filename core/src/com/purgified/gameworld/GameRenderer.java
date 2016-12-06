package com.purgified.gameworld;

import java.util.List;

import Configuration.Configuration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.purgified.gameobjects.Pixel;
import com.purgified.gameobjects.Bar;
import com.purgified.gameobjects.ScrollHandler;
import com.purgified.helpers.AssetLoader;
import com.purgified.helpers.ColorManager;
import com.purgified.helpers.InputHandler;
import com.purgified.ui.SimpleButton;


public class GameRenderer {
	private GameWorld myWorld;
	private OrthographicCamera orthoCamera;
	private ShapeRenderer shapeRenderer; 
	
	private SpriteBatch batcher;
	
	private Pixel ball;
	private ScrollHandler scroller;
	private Bar bar1, bar2, bar3;
	private Bar bar4, bar5, bar6;

	private TextureRegion bar, ballTexture;
	

    // Buttons
    private List<SimpleButton> menuButtons;
    
    // ColorManager for random color
    private ColorManager colorManager;
    private Color color;

    
	
	public GameRenderer(GameWorld world) {
		myWorld = world;
		
		this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor())
                .getMenuButtons();
		
		orthoCamera = new OrthographicCamera();
		orthoCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(orthoCamera.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(orthoCamera.combined);
		
		ball = myWorld.getBall();
		scroller = myWorld.getScrollHandler();
		bar1 = scroller.getBar1();
		bar2 = scroller.getBar2();
		bar3 = scroller.getBar3();
		bar4 = scroller.getBar4();
		bar5 = scroller.getBar5();
		bar6 = scroller.getBar6();
		
		this.bar = AssetLoader.bar;
		this.ballTexture = AssetLoader.ball;
		
		colorManager = new ColorManager();
		this.color = colorManager.getRandomColor();
		
	}

	public void render() {
		float delta = Gdx.graphics.getRawDeltaTime();
		draw(delta);
	}
	
	
	// Set the visual side of things to the screen (draw stuff)
	public void draw(float delta) {
		Gdx.gl.glClearColor(color.r, color.g, color.b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batcher.begin();
		drawBars(delta);
		batcher.end();
		
		shapeRenderer.begin(ShapeType.Filled);
		drawBall(delta);
		shapeRenderer.end();
		
		batcher.begin();
		if (myWorld.isMenu()) {
			drawMenuUI();
			drawLogo();
		}
		if (myWorld.isRunning()) {
			AssetLoader.font.draw(batcher, myWorld.score + "", Gdx.graphics.getWidth() / 2 - 1, Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() * .05f) - 1);
		}
		if (myWorld.isHighScore()) {
			String msg = " Game Over.\n Score:" + myWorld.tempScore + " \n Highscore: " + AssetLoader.getHighScore();
			float width = (new GlyphLayout(AssetLoader.font, msg)).width;
			float height = (new GlyphLayout(AssetLoader.font, msg)).height;

			AssetLoader.font.draw(batcher, msg , Gdx.graphics.getWidth() / 2 - 1 - width * .5f, Gdx.graphics.getHeight() / 2 - 1 + height);
			drawMenuUI();
		}
		batcher.end();
	}
	
	public void drawBars(float delta) {
		batcher.draw(bar, bar1.getX(), bar1.getY(), bar1.getWidth(), bar1.getHeight());
		batcher.draw(bar, bar2.getX(), bar2.getY(), bar2.getWidth(), bar2.getHeight());
		batcher.draw(bar, bar3.getX(), bar3.getY(), bar3.getWidth(), bar3.getHeight());
		batcher.draw(bar, bar4.getX(), bar4.getY(), bar4.getWidth(), bar4.getHeight());
		batcher.draw(bar, bar5.getX(), bar5.getY(), bar5.getWidth(), bar5.getHeight());
		batcher.draw(bar, bar6.getX(), bar6.getY(), bar6.getWidth(), bar6.getHeight());
	}
	
	public void drawBall(float delta) {
//		shapeRenderer.rect(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight()); // Uncomment if you don't want to use image for square/pixel object
		batcher.begin();
		batcher.draw(ballTexture, ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
		if (Configuration.ENABLE_PARTICLE_EFFECTS) {
			drawParticle(delta);
		}
		batcher.end();
	}
	
	public void drawParticle(float delta) {
		AssetLoader.effect.setPosition(ball.getX() + (ball.getWidth() / 2), ball.getY());
		AssetLoader.effect.start();
		AssetLoader.effect.draw(batcher, delta);
	}
	
	private void drawMenuUI() {
		
		
        for (SimpleButton button : menuButtons) {
            button.draw(batcher);
        }

    }
	
	private void drawLogo() {
		// Gets width of the text/font so that it can be centered accordingly
		GlyphLayout glyphLayout = new GlyphLayout();
		String item = Configuration.APP_NAME;
		glyphLayout.setText(AssetLoader.font,item);
		float w = glyphLayout.width;
		
		if (Configuration.USE_IMAGE_FOR_GAME_LOGO) {
			AssetLoader.gameLogo.setPosition(Gdx.graphics.getWidth() / 2 - AssetLoader.gameLogo.getWidth() / 2, Gdx.graphics.getHeight() / 2 );
			AssetLoader.gameLogo.draw(batcher);
		} else {
			AssetLoader.font.draw(batcher, Configuration.APP_NAME, Gdx.graphics.getWidth() / 2 - w / 2, Gdx.graphics.getHeight() / 2 + (Gdx.graphics.getHeight() * 0.2f));
		}
	}

	public void resetColor() {
		color = colorManager.getRandomColor();
	}
	
}

package Configuration;

import com.badlogic.gdx.Gdx;

public class Configuration {
	// ADS configuration
	public static final String BANNER_AD_UNIT_ID = "ca-app-pub-7817940621390472/3835794742";
    public static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-7817940621390472/8471790746";
    public static final int AD_FREQUENCY = 1;
    
    // Name configuration
    public static final String APP_NAME = "Pixel Hop";
    
    // Boolean configurations
    public static boolean ADS_ON = true;
    public static boolean USE_IMAGE_FOR_GAME_LOGO = false;
    public static boolean SHOW_SPLASH_SCREEN = false;
    public static boolean ENABLE_PARTICLE_EFFECTS = true;
    
    // Achievements IDs - POINTS
    public static final String ACHIEVEMENT_5_POINTS = "CgkI1sqC184HEAIQAA";    
    public static final String ACHIEVEMENT_10_POINTS = "CgkI1sqC184HEAIQAg";    
    public static final String ACHIEVEMENT_25_POINTS = "CgkI1sqC184HEAIQBA";    
    public static final String ACHIEVEMENT_50_POINTS = "CgkI1sqC184HEAIQBQ";    
    public static final String ACHIEVEMENT_100_POINTS = "CgkI1sqC184HEAIQBg";    
    public static final String ACHIEVEMENT_200_POINTS = "CgkI1sqC184HEAIQCA";
    // Achievements IDs - GAMES PLAYED
    public static final String ACHIEVEMENT_10_GAMES = "CgkI1sqC184HEAIQAQ";   
    public static final String ACHIEVEMENT_25_GAMES = "CgkI1sqC184HEAIQAw";   
    public static final String ACHIEVEMENT_50_GAMES = "CgkI1sqC184HEAIQBw";   
    public static final String ACHIEVEMENT_100_GAMES = "CgkI1sqC184HEAIQCQ";
    public static final String ACHIEVEMENT_200_GAMES = "CgkI1sqC184HEAIQCg";
    
    // Leaderboards ID
    public static final String LEADERBOARD_ID = "CgkI1sqC184HEAIQCw";
    
    // Pixel (square) configurations
    public static final int PIXEL_SIZE = (int) (Gdx.graphics.getWidth()  * 0.042f); // Must be square, so all sides are of the same length
    public static final int PIXEL_ACCELERATION = - (int) (Gdx.graphics.getWidth() * (23 / 36f));
    
    // Particle effects configurations
    public static final float PARTICLE_EFFECTS_SIZE = 0.25f; // Default is 1.0
    
    // Font configurations
    public static final float FONT_SIZE = 2.5f; // Default is 1.0
    
    // Moving bar configurations
    public static final int BAR_MIN_VELOCITY = (int) (Gdx.graphics.getWidth() * 0.14f); // Minimum velocity that the bars must at least move at
	public static final int BAR_MAX_RANDOM_VELOCITY = (int) (Gdx.graphics.getWidth() * 0.15f); // Maximum random number for adding to minimum velocity
	public static final int SCROLL_SPEED = - (int) (Gdx.graphics.getWidth() * (5 / 12f)); // Negative scroll speed indicates that the bar moves downwards
	public static final int BAR_GAP = (int) (Gdx.graphics.getHeight() * 0.3f); // Gap b/w bars on the same x-axis
	public static final int BAR_WIDTH_MIN = (int) (Gdx.graphics.getWidth() * .5125f);
	public static final int BAR_HEIGHT = (int) (Gdx.graphics.getHeight() * .03f); // How tall the bars are
	public static final int BAR_OFFSET = (int) (Gdx.graphics.getWidth() * .125f); // Offset so that the bars position correctly
	
	
	
}
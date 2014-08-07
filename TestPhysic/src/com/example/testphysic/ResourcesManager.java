package com.example.testphysic;

import java.io.IOException;

import android.graphics.Color;



import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

public class ResourcesManager
{
	//---------------------------------------------
	// VARIABLES
	//---------------------------------------------
	
	private static final ResourcesManager INSTANCE = new ResourcesManager();
	
	public Engine engine;
	public GameActivity activity;
	public BoundCamera camera;
	public VertexBufferObjectManager vbom;
	
	public Font font;
	
	//---------------------------------------------
	// TEXTURES & TEXTURE REGIONS
	//---------------------------------------------
	
	public ITextureRegion splash_region;
	public ITextureRegion menu_background_region;
	public ITextureRegion play_region;
	public ITextureRegion options_region;
	
	// Game Texture
	public BuildableBitmapTextureAtlas gameTextureAtlas;
	public BuildableBitmapTextureAtlas backgroundTextureAtlas;
	
	// Game Texture Regions
	public ITextureRegion platform1_region;
	public ITextureRegion platform2_region;
	public ITextureRegion platform3_region;
	public ITextureRegion platform4_region;
	public ITextureRegion platform5_region;
	public ITextureRegion coin_region;
	public ITiledTextureRegion player_region;
	public ITextureRegion bullet;
	public ITextureRegion trapRegion;
	public ITextureRegion backgroud;
	public ITiledTextureRegion destructible_bloc;
	public ITextureRegion lineRegion;
	public ITextureRegion stickRegion;
		//perturbations
	public BuildableBitmapTextureAtlas perturbationsAtlas;
	public ITextureRegion verticalRegion;
	public ITextureRegion horizontalRegion;
	public ITextureRegion diagonalRegion;
	
	//BONUS
	public BuildableBitmapTextureAtlas bonusAtlas;
	public ITextureRegion redBallRegion;
	public ITextureRegion blueBallRegion;
	public ITextureRegion mysteryBallRegion;
	public ITextureRegion orangeBallRegion;
	public ITextureRegion blackBallRegion;
	
	
	
	//ENNEMIS TEXTURE
	public BuildableBitmapTextureAtlas ennemiTextureAtlas;
	public ITiledTextureRegion ennemi1Region;
	
	//HUD
	public BuildableBitmapTextureAtlas HUDTextureAtlas;
	public ITiledTextureRegion area;
	public ITiledTextureRegion heartsRegion;
	public ITextureRegion arrowRegion;
	public ITextureRegion balanceRegion;
	public BuildableBitmapTextureAtlas arrowTextureAtlas;
	public ITextureRegion toLeftArrow;
	public ITextureRegion toRightArrow;
	
	//SPLASH
	private BitmapTextureAtlas splashTextureAtlas;
	private BuildableBitmapTextureAtlas menuTextureAtlas;
	
	// Level Complete Window
	public ITextureRegion complete_window_region;
	public ITiledTextureRegion complete_stars_region;
	
	//option
	private BuildableBitmapTextureAtlas optionTextureAtlas;
	public ITextureRegion retour_region;
	public ITiledTextureRegion son_region;
			
	//stats
	private BuildableBitmapTextureAtlas statTextureAtlas;
	public ITextureRegion retour_region_stat;
	public TextureRegion background_stat;
		
	//SOUND
	public Music music_menu;
	public Music music_game;

	
	
	
	
	
	
	
	
	
	//---------------------------------------------
	// CLASS LOGIC
	//---------------------------------------------
	public void loadMenuSoundResources()
	{
		try
		{
			music_menu = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity,"music/music.ogg");
			music_game = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity,"music/epic_game.ogg");
		}
		catch (IOException e)
		{
		    e.printStackTrace();
		}
	}
	public void loadStatResources()
	{
		loadStatGraphics();
		loadStatFonts();
	}
	private void loadStatFonts()
	{
		FontFactory.setAssetBasePath("font/");
		final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "ARIAL.TTF", 50, true, Color.WHITE, 2, Color.BLACK);
		font.load();

	}
	private void loadStatGraphics()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		statTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 512, TextureOptions.BILINEAR);
        retour_region_stat = BitmapTextureAtlasTextureRegionFactory.createFromAsset(statTextureAtlas, activity, "retour.png");
        background_stat = BitmapTextureAtlasTextureRegionFactory.createFromAsset(statTextureAtlas, activity, "background_stat.png");
        
        try 
    	{
			this.statTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.statTextureAtlas.load();
		} 
    	catch (final TextureAtlasBuilderException e)
    	{
			Debug.e(e);
		}
	}
	
	public void loadOptionResources()
	{
		loadOptionGraphics();
	}
	
	private void loadOptionGraphics()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
        optionTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 256, TextureOptions.BILINEAR);
        retour_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(optionTextureAtlas, activity, "retour.png");
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("music/");
        son_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(optionTextureAtlas, activity, "son.png", 2, 1);
        
        try 
    	{
			this.optionTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.optionTextureAtlas.load();
		} 
    	catch (final TextureAtlasBuilderException e)
    	{
			Debug.e(e);
		}
	}
	
	public void loadMenuResources()
	{
		loadMenuGraphics();
		loadMenuAudio();
		loadMenuFonts();
	}
	
	public void loadGameResources()
	{
		loadGameGraphics();
		loadGameFonts();
		loadGameAudio();
	}
	
	private void loadPerturbations()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
        perturbationsAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 4096, 512, TextureOptions.BILINEAR);
        verticalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(perturbationsAtlas, activity, "perturbations2.png");
        horizontalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(perturbationsAtlas, activity, "perturbations1.png");
        diagonalRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(perturbationsAtlas, activity, "perturbations3.png");
	
    	try 
    	{
			perturbationsAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			perturbationsAtlas.load();
		} 
    	catch (final TextureAtlasBuilderException e)
    	{
			
		}
        
	}
	
	private void loadBonus()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
        bonusAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 256, 32, TextureOptions.BILINEAR);
        redBallRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bonusAtlas, activity, "redBall.png");
       	blueBallRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bonusAtlas, activity, "blueBall.png");
        orangeBallRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bonusAtlas, activity, "orangeBall.png");
        blackBallRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bonusAtlas, activity, "blackBall.png");
        mysteryBallRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bonusAtlas, activity, "mysteryBall.png");
    	
        try 
    	{
			bonusAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			bonusAtlas.load();
		} 
    	catch (final TextureAtlasBuilderException e)
    	{
			
		}
	}
	
	private void loadMenuGraphics()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
        menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_screen.png");
        play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.png");
        options_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "options.png");
       
    	try 
    	{
			this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.menuTextureAtlas.load();
		} 
    	catch (final TextureAtlasBuilderException e)
    	{
			Debug.e(e);
		}
	}
	
	private void loadMenuAudio()
	{
		
	}
	
	private void loadMenuFonts()
	{
		FontFactory.setAssetBasePath("font/");
		final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
		font.load();

	}

	private void loadGameGraphics()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
        gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        
       	platform1_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "green_platform.png");
       	platform2_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "red_platform.png");
       	platform3_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "yellow_platform.png");
        platform4_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "blue_platform.png");
        platform5_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "purple_platform.png");
        coin_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "coin.png");
        player_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "player.png", 3, 1);
        bullet = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "bullet.png");
        trapRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "piege1.png");
        destructible_bloc = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "destructibleBlock.png", 1, 2);
        lineRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "ligne.png");
        stickRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "bordure.png");      
        
        complete_window_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "end_level.png");
        complete_stars_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "stars.png", 2, 1);
        
        backgroundTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        backgroud = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas, activity, "background.jpg");
        
        
        

        
    	try 
    	{
			this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameTextureAtlas.load();
			this.backgroundTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.backgroundTextureAtlas.load();
    	} 
    	catch (final TextureAtlasBuilderException e)
    	{
			Debug.e(e);
		}
    	
    	
        loadEnnemiGraphics();
        loadPerturbations();
        loadBonus();
	}
	
	private void loadEnnemiGraphics() 
	{
		ennemiTextureAtlas = 
				new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		ennemi1Region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(ennemiTextureAtlas, activity, "enemy1.png", 8, 2);
		
    	try 
    	{
			ennemiTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			ennemiTextureAtlas.load();
		} 
    	catch (final TextureAtlasBuilderException e)
    	{
			Debug.e(e);
		}
	}

	public void loadHUDGraphics()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");	
		HUDTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		area = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(HUDTextureAtlas, activity, "fires.png", 2, 1);
		heartsRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(HUDTextureAtlas, activity, "coeurs.png", 2, 1);
		arrowRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(HUDTextureAtlas, activity, "fleche.png");
		balanceRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(HUDTextureAtlas, activity, "balance.png");
		
		arrowTextureAtlas =  new BuildableBitmapTextureAtlas(activity.getTextureManager(), 512, 126, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		toLeftArrow = BitmapTextureAtlasTextureRegionFactory.createFromAsset(arrowTextureAtlas, activity, "ToLeft.png");
		toRightArrow = BitmapTextureAtlasTextureRegionFactory.createFromAsset(HUDTextureAtlas, activity, "toRight.png");
	
		try
		{
			HUDTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			HUDTextureAtlas.load();
			arrowTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			arrowTextureAtlas.load();
		}
		catch(final TextureAtlasBuilderException e)
		{
			Debug.e(e);	
		}
	}
	
	private void loadGameFonts()
	{
		
	}
	
	private void loadGameAudio()
	{
		
	}
	
	public void unloadGameTextures()
	{
		gameTextureAtlas.unload();
		ennemiTextureAtlas.unload();
		unloadBonus();
		unloadPerturbations();
	}
	
	public void unloadHUDGraphics()
	{
		HUDTextureAtlas.unload();
	}
	
	public void loadSplashScreen()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
        splashTextureAtlas.load();	
	}
	
	public void unloadSplashScreen()
	{
		splashTextureAtlas.unload();
		splash_region = null;
	}
	
	public void unloadMenuTextures()
	{
		menuTextureAtlas.unload();
	}
	
	public void loadMenuTextures()
	{
		menuTextureAtlas.load();
	}

	private void unloadBonus()
	{
		bonusAtlas.unload();
	}
	
	private void unloadPerturbations()
	{
		perturbationsAtlas.unload();
	}
	
	public static void prepareManager(Engine engine, GameActivity activity, BoundCamera camera, VertexBufferObjectManager vbom)
	{
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;
	}
	
	public void write(String key, String value) throws IOException
	{
		activity.writeStats(key, value);
	}
	
	public String read(String key) throws IOException
	{
		return activity.readStats(key);
	}
	
	//---------------------------------------------
	// GETTERS AND SETTERS
	//---------------------------------------------
	
	public static ResourcesManager getInstance()
	{
		return INSTANCE;
	}
}
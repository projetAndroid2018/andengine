package com.example.testphysic;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;


public class SceneManager
{
	//---------------------------------------------
	// SCENES
	//---------------------------------------------
	
	public boolean Sound_on;
	
	private BaseScene splashScene;
	private BaseScene menuScene;
	private BaseScene gameScene;
	private BaseScene loadingScene;
	private BaseScene optionScene;
	private BaseScene statScene;
	private BaseScene highscoreScene;
	private BaseScene boutiqueScene;
	private BaseScene globalScene;
	private BaseScene transitionScene;
	
	
	//---------------------------------------------
	// VARIABLES
	//---------------------------------------------
	
	private static final SceneManager INSTANCE = new SceneManager();
	
	private SceneType currentSceneType = SceneType.SCENE_SPLASH;
	
	private BaseScene currentScene;
	
	private Engine engine = ResourcesManager.getInstance().engine;
	
	public enum SceneType
	{
		SCENE_SPLASH,
		SCENE_MENU,
		SCENE_GAME,
		SCENE_LOADING,
		SCENE_OPTION,
		SCENE_STAT,
		SCENE_HIGHSCORE,
		SCENE_BOUTIQUE,
		SCENE_GLOBAL,
		SCENE_TRANSITION,
	}
	
	//---------------------------------------------
	// CLASS LOGIC
	//---------------------------------------------
	
	public void setScene(BaseScene scene)
	{
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
	}
	
	public void setScene(SceneType sceneType)
	{
		switch (sceneType)
		{
			case SCENE_MENU:
				setScene(menuScene);
				break;
			case SCENE_GAME:
				setScene(gameScene);
				break;
			case SCENE_SPLASH:
				setScene(splashScene);
				break;
			case SCENE_LOADING:
				setScene(loadingScene);
				break;
			case SCENE_OPTION:
				setScene(optionScene);
				break;
			case SCENE_STAT:
				setScene(statScene);
				break;
			case SCENE_HIGHSCORE:
				setScene(highscoreScene);
				break;
			case SCENE_BOUTIQUE:
				setScene(boutiqueScene);
				break;
			case SCENE_GLOBAL:
				setScene(globalScene);
				break;
			case SCENE_TRANSITION:
				setScene(transitionScene);
				break;
			default:
				break;
		}
	}
	public void createGlobalScene()
	{
		ResourcesManager.getInstance().loadGlobalResources();
		globalScene = new GlobalScene();
        SceneManager.getInstance().setScene(globalScene);
	}
	public void createTransitionScene()
	{
		ResourcesManager.getInstance().loadTransitionResources();
		transitionScene = new transition();
        SceneManager.getInstance().setScene(transitionScene);
	}
	public void createBoutiqueScene()
	{
		ResourcesManager.getInstance().loadBoutiqueResources();
		boutiqueScene = new Boutique();
        SceneManager.getInstance().setScene(boutiqueScene);
	}
	public void createHighscoreScene()
	{
		ResourcesManager.getInstance().loadHightResources();
		highscoreScene = new Highscore();
        SceneManager.getInstance().setScene(highscoreScene);
	}
	
	public void createStatScene()
	{
		ResourcesManager.getInstance().loadStatResources();
		statScene = new Stats();
        SceneManager.getInstance().setScene(statScene);
	}
	
	public void createOptionScene()
	{
		ResourcesManager.getInstance().loadOptionResources();
		optionScene = new optionScene();
        SceneManager.getInstance().setScene(optionScene);
	}
	
	public void createMenuScene()
	{
		ResourcesManager.getInstance().loadMenuResources();
		menuScene = new MainMenuScene();
		loadingScene = new LoadingScene();
        SceneManager.getInstance().setScene(menuScene);
	}
	
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback)
	{
		ResourcesManager.getInstance().loadSplashScreen();
		splashScene = new SplashScene();
		currentScene = splashScene;
		ResourcesManager.getInstance().loadMenuSoundResources();
		ResourcesManager.music_menu.play();
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
	}
	
	public void disposeSplashScene()
	{
		ResourcesManager.getInstance().unloadSplashScreen();
		splashScene.disposeScene();
		splashScene = null;
	}
	
	public void loadGameScene(final Engine mEngine)
	{
		setScene(loadingScene);
		ResourcesManager.getInstance().unloadMenuTextures();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
		{
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
            	mEngine.unregisterUpdateHandler(pTimerHandler);
            	ResourcesManager.getInstance().loadGameResources();
            	
            	if (ResourcesManager.music_menu.isPlaying())
            	{ResourcesManager.music_menu.pause();}
            	ResourcesManager.music_game.play();
            	
        		gameScene = new GameScene();
        		setScene(gameScene);
            }
		}));
	}
	
	public void loadMenuScene(final Engine mEngine)
	{
		setScene(loadingScene);
		gameScene.disposeScene();
		ResourcesManager.getInstance().unloadGameTextures();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
		{
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
            	mEngine.unregisterUpdateHandler(pTimerHandler);
            	ResourcesManager.getInstance().loadMenuTextures();
        		setScene(menuScene);
            }
		}));
	}
	
	//---------------------------------------------
	// GETTERS AND SETTERS
	//---------------------------------------------
	
	public static SceneManager getInstance()
	{
		return INSTANCE;
	}
	
	public SceneType getCurrentSceneType()
	{
		return currentSceneType;
	}
	
	public BaseScene getCurrentScene()
	{
		return currentScene;
	}
}
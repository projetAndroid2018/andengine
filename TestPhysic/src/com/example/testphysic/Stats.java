package com.example.testphysic;


import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;

import com.example.testphysic.SceneManager.SceneType;

public class Stats extends BaseScene implements IOnMenuItemClickListener
{
	private final int MENU_RETOUR = 0;
	private MenuScene menuChildScene;
	
	//Stats of the last game
	private String numberEnemyKilled;
	private String numberCoinCollected;
	private String numberMeters;
	private String score;
	private String numberJumps;
	private String numberScenes;
	private String numberTrapsDestroyed;
	
	//Best stats
	private String bestNumberEnemyKilled;
	private String bestNumberCoinCollected;
	private String bestNumberMeters;
	private String bestScore;
	private String bestNumberJumps;
	private String bestNumberScenes;
	private String bestNumberTrapsDestroyed;
	
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) 
	{
		switch(pMenuItem.getID())
		{
			case MENU_RETOUR:
				if (GameActivity.sound)
		    	{
		    		ResourcesManager.getInstance().music_menu.play();
		    	}
				SceneManager.getInstance().createMenuScene();
				return true;
			default:
				return false;
		}
	}

	@Override
	public void createScene() 
	{
		//ResourcesManager.getInstance().unloadGameTextures();
		camera.setChaseEntity(null);
		createBackground();
		createMenuChildScene();

		//--------------------------------------------
		//Last Game
		//--------------------------------------------
		
		numberCoinCollected = Integer.toString(GameScene.numberCoinsCollected);
		numberEnemyKilled = Integer.toString(GameScene.numberEnemiesDestroyed);
		numberJumps = Integer.toString(GameScene.numberJumps);
		numberMeters = Float.toString(GameScene.numberMeters);
		numberScenes = Integer.toString(GameScene.numberScenesLoaded);
		numberTrapsDestroyed = Integer.toString(GameScene.numberTrapsDestroyed);
		score = Integer.toString(GameScene.score);
		
		//------------------------------------------------
		//Best Stats
		//------------------------------------------------
		
		try {bestNumberCoinCollected = resourcesManager.read(GameScene.BEST_NUMBER_COINS_COLLECTED_KEY);} 
		catch (IOException e) { bestNumberCoinCollected = "ERROR";}
		try {bestNumberEnemyKilled = resourcesManager.read(GameScene.BEST_NUMBER_ENEMIES_DESTROYED_KEY);} 
		catch (IOException e) { bestNumberEnemyKilled = "ERROR";}
		try {bestNumberJumps = resourcesManager.read(GameScene.BEST_NUMBER_JUMPS_KEY);} 
		catch (IOException e) { bestNumberJumps = "ERROR";}
		try {bestNumberMeters = resourcesManager.read(GameScene.BEST_NUMBER_KILOMETERS_KEY);} 
		catch (IOException e) { bestNumberMeters = "ERROR";}
		try {bestNumberScenes = resourcesManager.read(GameScene.BEST_NUMBER_SCENES_LOADED_KEY);} 
		catch (IOException e) { bestNumberScenes = "ERROR";}
		try {bestNumberTrapsDestroyed = resourcesManager.read(GameScene.BEST_NUMBER_TRAPS_DESTROYED_KEY);} 
		catch (IOException e) { bestNumberTrapsDestroyed = "ERROR";}
		try {bestScore = resourcesManager.read(GameScene.BEST_SCORE_KEY);} 
		catch (IOException e) { bestScore = "ERROR";}

		
		attachChild(new Text(350, engine.getCamera().getCenterY() + 200,  resourcesManager.font, "ENEMIES KILLED : " + numberEnemyKilled + " / " + bestNumberEnemyKilled, vbom));
		attachChild(new Text(350, engine.getCamera().getCenterY() + 150, resourcesManager.font, " COINS COLLECTED : " + numberCoinCollected + " / " + bestNumberCoinCollected, vbom));
		attachChild(new Text(400, engine.getCamera().getCenterY() + 100, resourcesManager.font, "METERS : " + numberMeters + " / " +bestNumberMeters, vbom));
		attachChild(new Text(350, engine.getCamera().getCenterY() + 50, resourcesManager.font, "SCORE : " + score + " / " +bestScore, vbom));
		attachChild(new Text(350, engine.getCamera().getCenterY(), resourcesManager.font, "   NUMBER OF JUMP : " + numberJumps + " / " + bestNumberJumps, vbom));
		attachChild(new Text(350, engine.getCamera().getCenterY() - 50, resourcesManager.font, "  SCENES REACHED : " + numberScenes + " / " + bestNumberScenes, vbom));
		attachChild(new Text(350, engine.getCamera().getCenterY() - 100, resourcesManager.font, "  TRAPS DESTROYED : " + numberTrapsDestroyed + " / " + bestNumberTrapsDestroyed, vbom));

	}
	
	private void createBackground()
	{
		attachChild(new Sprite(400, 240, engine.getCamera().getWidth(), engine.getCamera().getHeight(), 
				resourcesManager.background_stat, vbom)
		{
    		@Override
            protected void preDraw(GLState pGLState, Camera pCamera) 
    		{
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
		});
	}
	
	private void createMenuChildScene()
	{
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(0, 0);
		
		
		final IMenuItem retourMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_RETOUR, resourcesManager.retour_region_stat, vbom), 1.2f, 1);
		
		menuChildScene.addMenuItem(retourMenuItem);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		retourMenuItem.setPosition(engine.getCamera().getCenterX(), engine.getCamera().getCenterY() - 200);

		menuChildScene.setOnMenuItemClickListener(this);
		
		setChildScene(menuChildScene);
	}

	@Override
	public void onBackKeyPressed() 
	{
		if (GameActivity.sound)
    	{
    		ResourcesManager.getInstance().music_menu.play();
    	}
		SceneManager.getInstance().createMenuScene();
	}

	@Override
	public SceneType getSceneType() 
	{
		return SceneType.SCENE_STAT;
	}

	@Override
	public void disposeScene() 
	{
		// TODO Auto-generated method stub
		
	}

}

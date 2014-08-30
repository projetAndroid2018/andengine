package com.example.testphysic;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import android.view.MotionEvent;

import com.example.testphysic.SceneManager.SceneType;

public class Highscore extends BaseScene implements IOnMenuItemClickListener, IOnSceneTouchListener
{
	
	//Scrolling
	private float mTouchY = 0, mTouchOffsetY = 0;
	
	//Best stats
		private String bestNumberEnemyKilled;
		private String bestNumberCoinCollected;
		private String bestNumberMeters;
		private String bestScore;
		private String bestNumberJumps;
		private String bestNumberScenes;
		private String bestNumberTrapsDestroyed;
	
		private final int MENU_RETOUR = 0;
		private MenuScene menuChildScene;
		
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) 
	{
		switch(pMenuItem.getID())
		{
			case MENU_RETOUR:
				camera.setChaseEntity(null); 
				camera.setCenter(400, 240);
				SceneManager.getInstance().createTransitionScene();
				return true;
			default:
				return false;
		}
	}

	@Override
	public void createScene() 
	{
		camera.setBounds(0, -400, 800, 480); // here we set camera bounds
		camera.setBoundsEnabled(true);
		
		this.setOnSceneTouchListener(this);
		
		createBackground();
		createMenuChildScene();
		
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
		
		
		
		attachChild(new Text(engine.getCamera().getCenterX(), engine.getCamera().getCenterY() + 200,  resourcesManager.font, "BEST STATS : ", vbom));
		
		
		attachChild(new Text(engine.getCamera().getCenterX(), engine.getCamera().getCenterY() + 150,  resourcesManager.font, "Enemies Killed : " + bestNumberEnemyKilled, vbom));
		attachChild(new Text(350, engine.getCamera().getCenterY() + 100, resourcesManager.font,                              "Coins Collected : "   + bestNumberCoinCollected, vbom));
		attachChild(new Text(400, engine.getCamera().getCenterY() + 50, resourcesManager.font,                               "Meters :"  + bestNumberMeters, vbom));
		attachChild(new Text(350, engine.getCamera().getCenterY() , resourcesManager.font,                                   "Score : "  + bestScore, vbom));
		attachChild(new Text(350, engine.getCamera().getCenterY() - 50, resourcesManager.font,                               "Number Of Jump : "  + bestNumberJumps, vbom));
		attachChild(new Text(350, engine.getCamera().getCenterY() - 100, resourcesManager.font,                              "Scenes Reached : "  + bestNumberScenes, vbom));
		attachChild(new Text(350, engine.getCamera().getCenterY() - 150, resourcesManager.font,                              "Traps Destroyed : " +  bestNumberTrapsDestroyed, vbom));
	}

	
	private void createBackground()
	{
		attachChild(new Sprite(400, 100, engine.getCamera().getWidth(), engine.getCamera().getHeight()*3, 
				resourcesManager.background_hight, vbom)
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
		
		
		final IMenuItem retourMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_RETOUR, resourcesManager.retour_region_hight, vbom), 1.2f, 1);
		
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
		camera.setChaseEntity(null); 
		camera.setCenter(400, 240);
		SceneManager.getInstance().createMenuScene();
	}

	@Override
	public SceneType getSceneType() 
	{
		
		return SceneType.SCENE_HIGHSCORE;
	}

	@Override
	public void disposeScene() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pTouchEvent) 
	{
		if(pTouchEvent.getAction() == MotionEvent.ACTION_DOWN | pTouchEvent.getAction() == MotionEvent.ACTION_UP)
        {
               // mTouchX = pTouchEvent.getMotionEvent().getX();
                mTouchY = pTouchEvent.getMotionEvent().getY();
        }
        else if(pTouchEvent.getAction() == MotionEvent.ACTION_MOVE)
        {
                //float newX = pTouchEvent.getMotionEvent().getX();
                float newY = pTouchEvent.getMotionEvent().getY();
               
                //mTouchOffsetX = (newX - mTouchX);
                mTouchOffsetY = (newY - mTouchY);
               
                //float newScrollX = this.camera.getCenterX() - mTouchOffsetX;
                float newScrollY = this.camera.getCenterY() + mTouchOffsetY;
               
                this.camera.setCenter(this.camera.getCenterX(), newScrollY);
               
                //mTouchX = newX;
                mTouchY = newY;
        }
        return true;
	}
}

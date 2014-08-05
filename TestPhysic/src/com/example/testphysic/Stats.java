package com.example.testphysic;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
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
	
	private CharSequence ennemie_killed;
	private CharSequence coin_collected;
	private CharSequence killometers_done;
	private CharSequence Best_score;
	private CharSequence jump_stat;
	private CharSequence number_scene;
	private CharSequence trap_destroyed;
	
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) 
	{
		switch(pMenuItem.getID())
		{
			case MENU_RETOUR:
				SceneManager.getInstance().createMenuScene();
				return true;
			default:
				return false;
		}
	}

	@Override
	public void createScene() 
	{
		ResourcesManager.getInstance().unloadGameTextures();
		camera.setChaseEntity(null);
		
		createBackground();
		createMenuChildScene();
		
		try 
		{
			coin_collected = resourcesManager.read("BEST_NUMBER_COINS_COLLECTED_KEY");
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write("BEST_NUMBER_COINS_COLLECTED_KEY", "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try 
		{
			ennemie_killed = resourcesManager.read("BEST_NUMBER_ENEMIES_DESTROYED_KEY");
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write("BEST_NUMBER_ENEMIES_DESTROYED_KEY", "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try 
		{
			jump_stat = resourcesManager.read("BEST_NUMBER_JUMPS_KEY");
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write("BEST_NUMBER_JUMPS_KEY", "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try 
		{
			killometers_done = resourcesManager.read("BEST_NUMBER_KILOMETERS_KEY");
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write("BEST_NUMBER_KILOMETERS_KEY", "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try 
		{
			number_scene = resourcesManager.read("BEST_NUMBER_SCENES_LOADED_KEY");
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write("BEST_NUMBER_SCENES_LOADED_KEY", "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try
		{
			trap_destroyed = resourcesManager.read("BEST_NUMBER_TRAPS_DESTROYED_KEY");
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write("BEST_NUMBER_TRAPS_DESTROYED_KEY", "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try 
		{
			Best_score = resourcesManager.read("BEST_SCORE_KEY");
		} 
		catch (IOException e)
		{
			try {resourcesManager.write("BEST_SCORE_KEY", "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		
		
		
		
		attachChild(new Text(250, engine.getCamera().getCenterY() + 200, resourcesManager.font,    "ENNEMIES KILLED :" , vbom));
		attachChild(new Text(250, engine.getCamera().getCenterY() + 150, resourcesManager.font,    "COIN COLLECTED  :", vbom));
		attachChild(new Text(250, engine.getCamera().getCenterY() + 100, resourcesManager.font,     "KILOMETERS DONE :", vbom));
		attachChild(new Text(250, engine.getCamera().getCenterY() + 50, resourcesManager.font,          "BEST SCORE      :", vbom));
		attachChild(new Text(250, engine.getCamera().getCenterY(), resourcesManager.font,     "JUMP NUMBER     :" , vbom));
		attachChild(new Text(250, engine.getCamera().getCenterY() - 50, resourcesManager.font,    "SCENE REACH     :" , vbom));
		attachChild(new Text(250, engine.getCamera().getCenterY() - 100, resourcesManager.font,    "TRAP DESTROYED  :" , vbom));
		
		attachChild(new Text(600, engine.getCamera().getCenterY() + 200, resourcesManager.font,ennemie_killed, vbom));
		attachChild(new Text(600, engine.getCamera().getCenterY() + 150, resourcesManager.font,coin_collected, vbom));
		attachChild(new Text(600, engine.getCamera().getCenterY() + 100, resourcesManager.font,killometers_done,vbom));
		attachChild(new Text(600, engine.getCamera().getCenterY() + 50, resourcesManager.font,Best_score, vbom));
		attachChild(new Text(600, engine.getCamera().getCenterY(), resourcesManager.font,jump_stat, vbom));
		attachChild(new Text(600, engine.getCamera().getCenterY() - 50, resourcesManager.font,number_scene, vbom));
		attachChild(new Text(600, engine.getCamera().getCenterY() - 100, resourcesManager.font,trap_destroyed, vbom));
	}
	
	private void createBackground()
	{
		attachChild(new Sprite(400, 240, engine.getCamera().getWidth(), engine.getCamera().getHeight(), 
				resourcesManager.menu_background_region, vbom)
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

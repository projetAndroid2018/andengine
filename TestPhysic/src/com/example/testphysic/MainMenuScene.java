package com.example.testphysic;

import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.engine.camera.Camera;


import com.example.testphysic.SceneManager.SceneType;


public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener
{
	//---------------------------------------------
	// VARIABLES
	//---------------------------------------------
	
	private MenuScene menuChildScene;
	
	private final int MENU_PLAY = 0;
	private final int MENU_OPTIONS = 1;
	private final int MENU_STATS = 2;
	private final int MENU_BOUTIQUE = 3;
	
	//---------------------------------------------
	// METHODS FROM SUPERCLASS
	//---------------------------------------------

	@Override
	public void createScene()
	{
		createBackground();
		createMenuChildScene();
	}

	@Override
	public void onBackKeyPressed()
	{
		System.exit(0);
	}

	@Override
	public SceneType getSceneType()
	{
		return SceneType.SCENE_MENU;
	}
	

	@Override
	public void disposeScene()
	{
		// TODO Auto-generated method stub
	}
	
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY)
	{
		switch(pMenuItem.getID())
		{
			case MENU_PLAY:
				//Load Game Scene!
				SceneManager.getInstance().loadGameScene(engine);
				return true;
			case MENU_OPTIONS:
				SceneManager.getInstance().createOptionScene();
				return true;
			case MENU_STATS:
				SceneManager.getInstance().createHighscoreScene();
				return true;
			case MENU_BOUTIQUE:
				SceneManager.getInstance().createBoutiqueScene();
				return true;
			default:
				return false;
		}
	}
	
	//---------------------------------------------
	// CLASS LOGIC
	//---------------------------------------------
	
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
		
		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region, vbom), 1.2f, 1);
		final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, resourcesManager.options_region, vbom), 1.2f, 1);
		final IMenuItem statMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_STATS, resourcesManager.stats_region, vbom), 1.2f, 1);
		final IMenuItem boutiqueMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_BOUTIQUE, resourcesManager.boutique_region, vbom), 1.2f, 1);
		
		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(optionsMenuItem);
		menuChildScene.addMenuItem(statMenuItem);
		menuChildScene.addMenuItem(boutiqueMenuItem);
		
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		playMenuItem.setPosition(engine.getCamera().getCenterX(), engine.getCamera().getCenterY() + 150);
		optionsMenuItem.setPosition(engine.getCamera().getCenterX(), engine.getCamera().getCenterY() + 50);
		statMenuItem.setPosition(engine.getCamera().getCenterX(), engine.getCamera().getCenterY() - 50);
		boutiqueMenuItem.setPosition(engine.getCamera().getCenterX(), engine.getCamera().getCenterY() - 150);
		
		
		menuChildScene.setOnMenuItemClickListener(this);
		
		setChildScene(menuChildScene);
	}
}
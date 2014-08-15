package com.example.testphysic;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.example.testphysic.SceneManager.SceneType;

public class transition extends BaseScene implements IOnMenuItemClickListener
{

	private MenuScene menuChildScene;
	
	private final int MENU_GLOBAL = 0;
	private final int MENU_BEST = 1;
	private final int MENU_RETOUR = 2;
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) 
	{
		switch(pMenuItem.getID())
		{
			case MENU_GLOBAL:
				SceneManager.getInstance().createGlobalScene();
				return true;
			case MENU_BEST:
				SceneManager.getInstance().createHighscoreScene();
				return true;
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
		createBackground();
		createMenuChildScene();
		
		
	}
	
	private void createBackground()
	{
		attachChild(new Sprite(400, 100, engine.getCamera().getWidth(), engine.getCamera().getHeight()*3, 
				resourcesManager.background_transition, vbom)
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
		
		
		final IMenuItem bestMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_BEST, resourcesManager.region_best, vbom), 1.2f, 1);
		final IMenuItem globalMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_GLOBAL, resourcesManager.region_global, vbom), 1.2f, 1);
		final IMenuItem retourMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_RETOUR, resourcesManager.retour_region_transition, vbom), 1.2f, 1);
		
		
		menuChildScene.addMenuItem(bestMenuItem);
		menuChildScene.addMenuItem(globalMenuItem);
		menuChildScene.addMenuItem(retourMenuItem);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		bestMenuItem.setPosition(engine.getCamera().getCenterX(), engine.getCamera().getCenterY() + 150);
		globalMenuItem.setPosition(engine.getCamera().getCenterX(), engine.getCamera().getCenterY() );
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
		return SceneType.SCENE_HIGHSCORE;
	}

	@Override
	public void disposeScene() 
	{
		// TODO Auto-generated method stub
		
	}

}

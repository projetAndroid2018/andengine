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
import org.andengine.opengl.util.GLState;


import com.example.testphysic.SceneManager.SceneType;

public class optionScene extends BaseScene implements IOnMenuItemClickListener
{
	
	private MenuScene menuChildScene;
	private final int MENU_RETOUR = 0;
	private final int MENU_SON = 1;
	
	
	public AnimatedSpriteMenuItem SOUND_MenuItem;
	
	@Override
	public void createScene() 
	{
		createBackground();
		createMenuChildScene();
	}
		 
	
	private void createMenuChildScene()
	{
		
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(0, 0);
		
		final IMenuItem retourMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_RETOUR, resourcesManager.retour_region, vbom), 1.2f, 1);
		
		SOUND_MenuItem = new AnimatedSpriteMenuItem(MENU_SON, resourcesManager.son_region, vbom);
		
		menuChildScene.addMenuItem(retourMenuItem);
		
		menuChildScene.addMenuItem(SOUND_MenuItem);
		
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		retourMenuItem.setPosition(engine.getCamera().getCenterX(), engine.getCamera().getCenterY() - 200);
		
		SOUND_MenuItem.setPosition(engine.getCamera().getCenterX(), engine.getCamera().getCenterY() - 100);
		
		
		menuChildScene.setOnMenuItemClickListener(this);
		
		setChildScene(menuChildScene);
		
		if (GameActivity.sound)
		{
			SOUND_MenuItem.setCurrentTileIndex(0);
		}
		else
		{
			SOUND_MenuItem.setCurrentTileIndex(1);
		}
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
	

	@Override
	public void onBackKeyPressed() 
	{
		SceneManager.getInstance().createMenuScene();
	}

	@Override
	public SceneType getSceneType() 
	{
		return SceneType.SCENE_OPTION;
	}

	@Override
	public void disposeScene()
	{
		
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY)
	{
		switch(pMenuItem.getID())
		{
			case MENU_RETOUR:
				SceneManager.getInstance().createMenuScene();
				return true;
			case MENU_SON:
				if (GameActivity.sound)
				{
					try 
					{
						resourcesManager.write(GameScene.GLOBAL_SON, "false");
						SOUND_MenuItem.setCurrentTileIndex(1);
						GameActivity.sound = false;
						ResourcesManager.getInstance().music_game.pause();
						ResourcesManager.getInstance().music_menu.pause();
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
				else
				{
					try 
					{
						resourcesManager.write(GameScene.GLOBAL_SON, "true");
						GameActivity.sound = true;
						SOUND_MenuItem.setCurrentTileIndex(0);
						ResourcesManager.getInstance().music_game.play();
						ResourcesManager.getInstance().music_menu.play();
					} 
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				return true;
			default:
				return false;
		}
	}

}

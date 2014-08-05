package com.example.testphysic;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.animator.IMenuSceneAnimator;
import org.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.align.VerticalAlign;

import android.R.bool;

import com.example.testphysic.SceneManager.SceneType;

public class optionScene extends BaseScene implements IOnMenuItemClickListener
{
	private boolean Sound_on;
	
	private MenuScene menuChildScene;
	private final int MENU_RETOUR = 0;
	private final int MENU_SON = 1;
	
	public AnimatedSpriteMenuItem SOUND_MenuItem;
	
	@Override
	public void createScene() 
	{
		// TODO Auto-generated method stub
		createBackground();
		createMenuChildScene();
	}
		 
	
	private void createMenuChildScene()
	{
		
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(0, 0);
		
		
		
		final IMenuItem retourMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_RETOUR, resourcesManager.retour_region, vbom), 1.2f, 1);

		SOUND_MenuItem = new AnimatedSpriteMenuItem(MENU_SON, resourcesManager.son_region, vbom);
		
		if (Sound_on)
		{
			SOUND_MenuItem.setCurrentTileIndex(0);
		}
		else
		{
			SOUND_MenuItem.setCurrentTileIndex(1);
		}
		
		
		menuChildScene.addMenuItem(retourMenuItem);
		menuChildScene.addMenuItem(SOUND_MenuItem);
		
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		retourMenuItem.setPosition(729/2, 214);
		SOUND_MenuItem.setPosition(729/2, 100);
		
		
		menuChildScene.setOnMenuItemClickListener(this);
		
		setChildScene(menuChildScene);
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
		// TODO Auto-generated method stub
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
				if (Sound_on)
				{
					Sound_on = false;
					SOUND_MenuItem.setCurrentTileIndex(1);
					resourcesManager.music_menu.resume();	
				}
				else
				{
					Sound_on = true;
					SOUND_MenuItem.setCurrentTileIndex(0);
					resourcesManager.music_menu.pause();
				}
				return true;
			default:
				return false;
		}
	}

}

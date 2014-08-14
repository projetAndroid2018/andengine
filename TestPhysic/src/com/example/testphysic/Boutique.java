package com.example.testphysic;

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

public class Boutique extends BaseScene implements IOnMenuItemClickListener
{

	private final int MENU_RETOUR = 0;
	private MenuScene menuChildScene;
	
	
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
		createBackground();
		createMenuChildScene();
		
		
		attachChild(new Text(200, engine.getCamera().getCenterY() + 200,  resourcesManager.font, "COIN :" + "???", vbom));
	}

	private void createBackground()
	{
		attachChild(new Sprite(400, 240, engine.getCamera().getWidth(), engine.getCamera().getHeight(), 
				resourcesManager.background_boutique, vbom)
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
		
		
		final IMenuItem retourMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_RETOUR, resourcesManager.retour_region_boutique, vbom), 1.2f, 1);
		
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
		return SceneType.SCENE_BOUTIQUE;
	}

	@Override
	public void disposeScene() 
	{
		
	}

}

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

	//-------------------------------------------------------------------------
	//HADRIEN HADRIEN HADRIEN//
	//INSTRUCTIONS POUR LA BOUTIQUE
	
	//INCREMENTE de 5 lorsqu'un ***_FIVE_GAME_KEY est acheté
	//INCREMENTE de 1 lorsqu'un ***_ONE_GAME_KEY est acheté
	//METTRE à 1 lorsqu'un ****GLOBAL_KEY est acheté
	//	try {resourcesManager.write(GameScene.TWO_LIVES_GLOBAL_KEY, "1");} catch (IOException e) {e.printStackTrace();}
	//  try {resourcesManager.write(GameScene.TWO_LIVES_FIVE_GAME_KEY, "5");} catch (IOException e) {e.printStackTrace();}
	//	try {resourcesManager.write(GameScene.TWO_LIVES_ONE_GAME_KEY, "1");} catch (IOException e) {e.printStackTrace();}
	//
	//	try {resourcesManager.write(GameScene.ONE_LIFE_GLOBAL_KEY, "1");} catch (IOException e) {e.printStackTrace();}
	//  try {resourcesManager.write(GameScene.ONE_LIFE_FIVE_GAME_KEY, "5");} catch (IOException e) {e.printStackTrace();}
	//	try {resourcesManager.write(GameScene.ONE_LIFE_ONE_GAME_KEY, "1");} catch (IOException e) {e.printStackTrace();}
	//
	//
	//---------------------------------------------------------------------------
	
	
	private final int MENU_RETOUR = 0;
	private final int MENU_VALIDEZ = 1;
	
	
	private MenuScene menuChildScene;
	
	private Sprite coinSprite;
	//private Text cointext;
	//private String coinstring  = "??";
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) 
	{
		switch(pMenuItem.getID())
		{
			case MENU_RETOUR:
				SceneManager.getInstance().createMenuScene();
				return true;
			case MENU_VALIDEZ:
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
		
		attachChild(new Text(250, engine.getCamera().getCenterY() + 200,  resourcesManager.font, "COIN : ???", vbom));
		
		
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
		
		coinSprite = new Sprite(engine.getCamera().getCenterX() - 50,engine.getCamera().getCenterY() - 200, 60,60,resourcesManager.region_coin,vbom);
		
		final IMenuItem retourMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_RETOUR, resourcesManager.retour_region_boutique, vbom), 1.2f, 1);
		final IMenuItem validezMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_VALIDEZ, resourcesManager.validez_region_boutique, vbom), 1.2f, 1);
		
		menuChildScene.addMenuItem(retourMenuItem);
		menuChildScene.addMenuItem(validezMenuItem);
		menuChildScene.attachChild(coinSprite);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		retourMenuItem.setPosition(engine.getCamera().getCenterX() - 200, engine.getCamera().getCenterY() - 200);
		validezMenuItem.setPosition(engine.getCamera().getCenterX() + 200, engine.getCamera().getCenterY() - 200);
		
		coinSprite.setPosition(100, engine.getCamera().getCenterY() + 200);
		
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

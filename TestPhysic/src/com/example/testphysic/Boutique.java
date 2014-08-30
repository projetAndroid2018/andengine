package com.example.testphysic;


import java.io.IOException;
import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
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
import org.andengine.util.adt.color.Color;

import Miscellaneous.Items;

import android.view.MotionEvent;

import com.example.testphysic.SceneManager.SceneType;

public class Boutique extends BaseScene implements IOnMenuItemClickListener, IOnSceneTouchListener
{	
	private float mTouchY = 0, mTouchOffsetY = 0;
	private Text coinsPossessedText;
	private String coinsPossessedString;
	private Sprite backgroundBoutique;
	private final int MENU_RETOUR = 0;
	public static ArrayList<Items> ItemsList = new ArrayList<Items>();

	public Text gotText;
	public String gotString;
	
	private MenuScene menuChildScene;
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) 
	{
		switch(pMenuItem.getID())
		{
			case MENU_RETOUR:
				onBackKeyPressed();
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
		this.setOnSceneTouchListener(this);
		
		camera.setBoundsEnabled(true);
		camera.setBounds(0, -1050, 800, 480);
		
		Text title = new Text(420, 410, resourcesManager.font, "abcdefghijklmnopqrstuvwxyz", vbom);
		title.setText("Bonus Store");
		title.setPosition(420, 410);
		attachChild(title);
		
		ItemsList.add( new Items(resourcesManager.S2G5, resourcesManager.pictureBackgroundRegion, resourcesManager.synopsisBackgroundRegion, 
				GameScene.MULTIPLICATION_TIME_2_SCORE_FIVE_GAME_KEY, 1, "Multiply score per 2, five game", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 
		ItemsList.add( new Items(resourcesManager.S2G1, resourcesManager.pictureBackgroundRegion, resourcesManager.synopsisBackgroundRegion, 
				GameScene.MULTIPLICATION_TIME_2_SCORE_ONE_GAME_KEY, 2, "Multiply score per 2, one game", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 
		ItemsList.add( new Items(resourcesManager.S5G5, resourcesManager.pictureBackgroundRegion, resourcesManager.synopsisBackgroundRegion, 
				GameScene.MULTIPLICATION_TIME_5_SCORE_FIVE_GAME_KEY, 3, "Multiply score per 5, five game", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 
		ItemsList.add( new Items(resourcesManager.S5G1, resourcesManager.pictureBackgroundRegion, resourcesManager.synopsisBackgroundRegion, 
				GameScene.MULTIPLICATION_TIME_5_SCORE_ONE_GAME_KEY, 4, "Multiply score per 5, one game", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 

		ItemsList.add( new Items(resourcesManager.background_boutique, resourcesManager.background_boutique, resourcesManager.background_boutique, 
				"null", 000, "", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 
		
		ItemsList.add( new Items(resourcesManager.C2G5, resourcesManager.pictureBackgroundRegion, resourcesManager.synopsisBackgroundRegion, 
				GameScene.MULTIPLICATION_TIME_2_COINS_FIVE_GAME_KEY, 1, "Multiply coins per 2, five game", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 
		ItemsList.add( new Items(resourcesManager.C2G1, resourcesManager.pictureBackgroundRegion, resourcesManager.synopsisBackgroundRegion, 
				GameScene.MULTIPLICATION_TIME_2_COINS_ONE_GAME_KEY, 2, "Multiply coins per 2, one game", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 
		ItemsList.add( new Items(resourcesManager.C5G5, resourcesManager.pictureBackgroundRegion, resourcesManager.synopsisBackgroundRegion, 
				GameScene.MULTIPLICATION_TIME_5_COINS_FIVE_GAME_KEY, 3, "Multiply coins per 5, five game", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 
		ItemsList.add( new Items(resourcesManager.C5G1, resourcesManager.pictureBackgroundRegion, resourcesManager.synopsisBackgroundRegion, 
				GameScene.MULTIPLICATION_TIME_5_COINS_ONE_GAME_KEY, 4, "Multiply coins per 5, one game", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 
		
		
		ItemsList.add( new Items(resourcesManager.background_boutique, resourcesManager.background_boutique, resourcesManager.background_boutique, 
				"null", 000, "", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 
		
		ItemsList.add( new Items(resourcesManager.oneShot1G, resourcesManager.pictureBackgroundRegion, resourcesManager.synopsisBackgroundRegion, 
				GameScene.FIRE_POWER_ONE_GAME, 3, "one shot, one game", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 
		ItemsList.add( new Items(resourcesManager.oneShot5G, resourcesManager.pictureBackgroundRegion, resourcesManager.synopsisBackgroundRegion, 
				GameScene.FIRE_POWER_FIVE_GAME, 4, "one shot, five game", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 
		
		ItemsList.add( new Items(resourcesManager.background_boutique, resourcesManager.background_boutique, resourcesManager.background_boutique, 
				"null", 000, "", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 
		
		ItemsList.add( new Items(resourcesManager.S2G5, resourcesManager.pictureBackgroundRegion, resourcesManager.synopsisBackgroundRegion, 
				GameScene.ONE_LIFE_ONE_GAME_KEY, 1, "one extra life, one game", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 
		ItemsList.add( new Items(resourcesManager.S2G1, resourcesManager.pictureBackgroundRegion, resourcesManager.synopsisBackgroundRegion, 
				GameScene.ONE_LIFE_FIVE_GAME_KEY, 2, "one extra life, five game", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 
		ItemsList.add( new Items(resourcesManager.S5G5, resourcesManager.pictureBackgroundRegion, resourcesManager.synopsisBackgroundRegion, 
				GameScene.ONE_LIFE_GLOBAL_KEY, 3, "one extra life, forever", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 
		ItemsList.add( new Items(resourcesManager.S5G1, resourcesManager.pictureBackgroundRegion, resourcesManager.synopsisBackgroundRegion, 
				GameScene.TWO_LIVES_ONE_GAME_KEY, 4, "two extra lives, one game", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 
		ItemsList.add( new Items(resourcesManager.S2G5, resourcesManager.pictureBackgroundRegion, resourcesManager.synopsisBackgroundRegion, 
				GameScene.TWO_LIVES_FIVE_GAME_KEY, 1, "two extra lives, five game", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 
		ItemsList.add( new Items(resourcesManager.S2G1, resourcesManager.pictureBackgroundRegion, resourcesManager.synopsisBackgroundRegion, 
				GameScene.TWO_LIVES_GLOBAL_KEY, 2, "two extra lives, forever ", resourcesManager.vbom, resourcesManager.font, resourcesManager)); 

		
		
		
		for(int i = 0; i < ItemsList.size(); i++)
		{
			ItemsList.get(i).setSpritesPosition(70, 325 - 70 * i);
			String coinsPossessed;
			try {coinsPossessed = resourcesManager.read(GameScene.TOTAL_COINS_POSSESSED_KEY);} catch (IOException e) {coinsPossessed = "0";}
			ItemsList.get(i).checkColorPrice(coinsPossessed);
			attachChild(ItemsList.get(i).pictureBackground);
			attachChild(ItemsList.get(i).picture);
			attachChild(ItemsList.get(i).priceText);
			attachChild(ItemsList.get(i).synopsisBackground);
			attachChild(ItemsList.get(i).synopsisText);
			attachChild(ItemsList.get(i).gotText);
			registerTouchArea(ItemsList.get(i).spriteTouch);
		}
		
		

		
		coinsPossessedText = new Text(750, camera.getCenterY() + 240 - 70, resourcesManager.font, "0123456789", vbom);

		try {coinsPossessedString = resourcesManager.read(GameScene.TOTAL_COINS_POSSESSED_KEY);} catch (IOException e) {coinsPossessedString = "0";}

		coinsPossessedText.setText(coinsPossessedString);
		coinsPossessedText.setPosition(750, camera.getCenterY() + 240 - 70);
		coinsPossessedText.setColor(Color.YELLOW);
		attachChild(coinsPossessedText);
		
		
		registerUpdateHandler(new TimerHandler(1f / 10.0f, true, new ITimerCallback()
		{
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
        		try {coinsPossessedString = resourcesManager.read(GameScene.TOTAL_COINS_POSSESSED_KEY);} catch (IOException e) {coinsPossessedString = "0";}
            	coinsPossessedText.setText(coinsPossessedString);
            	
            	for(int i = 0; i < ItemsList.size(); i++)
            	{
            		ItemsList.get(i).setNumberActual();
            	}
            }

		}));

	}

	private void createBackground()
	{
		backgroundBoutique = new Sprite(800 / 2, 240, engine.getCamera().getWidth(), engine.getCamera().getHeight(), 
				resourcesManager.background_boutique, vbom)
		{
    		@Override
            protected void preDraw(GLState pGLState, Camera pCamera) 
    		{
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
		};
		
		attachChild(backgroundBoutique);
	}
	
	private void createMenuChildScene()
	{
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(0, 0);

		final IMenuItem retourMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_RETOUR, resourcesManager.retour_region_boutique, vbom), 1.2f, 1);

		menuChildScene.addMenuItem(retourMenuItem);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		retourMenuItem.setPosition(engine.getCamera().getCenterX(), 80);

		
		menuChildScene.setOnMenuItemClickListener(this);
		
		
		setChildScene(menuChildScene);
	}
	
	@Override
	public void onBackKeyPressed() 
	{
		for(int i = 0; i < ItemsList.size(); i++)
		{
			ItemsList.get(i).picture.detachSelf();
			ItemsList.get(i).pictureBackground.detachSelf();
			ItemsList.get(i).synopsisBackground.detachSelf();
			ItemsList.get(i).priceText.detachSelf();
			ItemsList.get(i).synopsisText.detachSelf();
			ItemsList.get(i).spriteTouch.detachSelf();
			ItemsList.get(i).gotText.detachSelf();
			
			ItemsList.get(i).picture.dispose();
			ItemsList.get(i).pictureBackground.dispose();
			ItemsList.get(i).synopsisBackground.dispose();
			ItemsList.get(i).priceText.dispose();
			ItemsList.get(i).synopsisText.dispose();
			ItemsList.get(i).spriteTouch.dispose();
			ItemsList.get(i).gotText.dispose();
		}
		ItemsList.clear();
		resourcesManager.boutiqueTextureAtlas.unload();
		camera.setChaseEntity(null); 
		camera.setCenter(400, 240);
		SceneManager.getInstance().createMenuScene();
	}

	@Override
	public SceneType getSceneType() 
	{
		return SceneType.SCENE_BOUTIQUE;
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pTouchEvent) 
	{
		if(pTouchEvent.getAction() == MotionEvent.ACTION_DOWN  | pTouchEvent.getAction() == MotionEvent.ACTION_UP)
        {
                //mTouchX = pTouchEvent.getMotionEvent().getX();
                mTouchY = pTouchEvent.getMotionEvent().getY();
                coinsPossessedText.setPosition(750, camera.getCenterY() + 240 - 70);
                backgroundBoutique.setPosition(800 / 2, camera.getCenterY());
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
                
                coinsPossessedText.setPosition(750, camera.getCenterY() + 240 - 70);
                backgroundBoutique.setPosition(800 / 2, camera.getCenterY());
        }
        return true;
	}
	

	@Override
	public void disposeScene() 
	{
		
	}


}

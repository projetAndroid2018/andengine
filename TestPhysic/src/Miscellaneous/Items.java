package Miscellaneous;

import java.io.IOException;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import com.example.testphysic.GameScene;
import com.example.testphysic.ResourcesManager;

public class Items 
{

	public ITextureRegion transparent;
	public Sprite picture;
	public Sprite pictureBackground;
	public Sprite synopsisBackground;
	public Text synopsisText;
	public Text gotText;
	public Text priceText;
	public VertexBufferObjectManager vbom;
	public String synopsis;
	public String price;
	public Rectangle spriteTouch;
	public String key;
	public String gotCurrently;
	public ResourcesManager res;
	
	public Items(ITextureRegion region, ITextureRegion pictureBackgroundRegion, 
			ITextureRegion synopsisBackgroundRegion, final String key,
			final int price, String synopsis, VertexBufferObjectManager aVbom, Font font, final ResourcesManager resourcesManager)
	{
		transparent = region;
		res = resourcesManager;
		//VBOM
		vbom = aVbom;
		
		//pictures
		picture = new Sprite(0, 0, 50, 50, region, vbom);

		pictureBackground = new Sprite (0, 0, 70, 70, pictureBackgroundRegion, vbom);
		synopsisBackground = new Sprite (0, 0, 600, 70, synopsisBackgroundRegion, vbom);
		
		//price and synopsis
		this.price = Integer.toString(price);
		this.synopsis = synopsis;
		this.key = key;
		
		synopsisText = new Text(0, 0, font, 
				"abcdefghijklmnopqrstuvwxyz" 
				, new TextOptions(HorizontalAlign.LEFT), vbom);
		
		priceText = new Text(0, 0, font, "1234567890" 
				, new TextOptions(HorizontalAlign.LEFT), vbom);
		
		gotText = new Text(0, 0,font, "1234567890", 
				new TextOptions(HorizontalAlign.LEFT), vbom);
		
		try {gotCurrently = resourcesManager.read(key);} catch (IOException e) {gotCurrently = "0";};
		
		
		spriteTouch = new Rectangle(0, 0, 50, 50, vbom)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
				if(pSceneTouchEvent.isActionDown())
				{
					String coinsPossessed;
					try {coinsPossessed = resourcesManager.read(GameScene.TOTAL_COINS_POSSESSED_KEY);} 
					catch (IOException e) {coinsPossessed = "0";}
					
					if(Integer.parseInt(coinsPossessed) >= price)
					{
						//ok
						//Déduit le nombre de pièce
						try {resourcesManager.write(GameScene.TOTAL_COINS_POSSESSED_KEY, Integer.toString(Integer.parseInt(coinsPossessed) - price) );				
						} catch (NumberFormatException e) {} catch (IOException e) {}
						
						String actualNumber;
						try {actualNumber = resourcesManager.read(key);} catch (IOException e) {actualNumber = "0";}
						
						if(key == GameScene.ONE_LIFE_FIVE_GAME_KEY || key == GameScene.TWO_LIVES_FIVE_GAME_KEY || 
								key == GameScene.FIRE_POWER_FIVE_GAME || key == GameScene.MULTIPLICATION_TIME_2_COINS_FIVE_GAME_KEY || key == GameScene.MULTIPLICATION_TIME_2_SCORE_FIVE_GAME_KEY
								|| key == GameScene.MULTIPLICATION_TIME_5_COINS_FIVE_GAME_KEY || key == GameScene.MULTIPLICATION_TIME_5_SCORE_FIVE_GAME_KEY)
						{
							try {resourcesManager.write(key, Integer.toString(Integer.parseInt(actualNumber) + 5));} catch (IOException e) {}
						}
						else
						{
							try {resourcesManager.write(key, Integer.toString(Integer.parseInt(actualNumber) + 1));} catch (IOException e) {}
						}
						
						}
					else
					{
						//ERROR
					}
				}
				
				return true;
			}
		};
	}
	


	public void setSpritesPosition(float x, float y)
	{
		//pictures
		picture.setPosition(x, y);
		pictureBackground.setPosition(x, y);
		synopsisBackground.setPosition(x + 370, y);
		spriteTouch.setPosition(x, y);
		
		//Text
		synopsisText.setPosition(x + 370, y);
		synopsisText.setText(synopsis);
		priceText.setPosition(x + 25, y - 25);
		priceText.setText(price);
		gotText.setText(gotCurrently);
		gotText.setPosition(x - 35, y + 25);
		gotText.setColor(Color.BLUE);		
	}
	
	public void checkColorPrice(String coinsPossessed)
	{
		if(transparent != null && transparent == ResourcesManager.getInstance().background_boutique)
		{
			priceText.setColor(Color.TRANSPARENT);
			gotText.setColor(Color.TRANSPARENT);
		}
		else
		{
			if(Integer.parseInt(coinsPossessed) >= Integer.parseInt(price))
				priceText.setColor(Color.GREEN);
			else
				priceText.setColor(Color.RED);
		}
	}
	
	public void setNumberActual()
	{
		String actualNumber;
		try {actualNumber = res.read(key);} catch (IOException e) {actualNumber = "0";}
		
		gotText.setText(actualNumber);
	}
	
	
	
	
}

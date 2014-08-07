package Bonus;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.example.testphysic.ResourcesManager;



public class ScoreReducer extends Sprite
{
	public ScoreReducer(float pX, float pY, float width, float height, VertexBufferObjectManager vbo)
	{
		super(pX, pY, width, height, ResourcesManager.getInstance().destructible_bloc, vbo);
	}
	
}
	
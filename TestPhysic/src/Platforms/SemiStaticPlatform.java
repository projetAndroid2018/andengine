package Platforms;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.example.testphysic.GameScene;
import com.example.testphysic.Player;
import com.example.testphysic.SceneManager;


public class SemiStaticPlatform extends Sprite
{
	
	private Body body;

	public SemiStaticPlatform(float pX, float pY, float width, float height, VertexBufferObjectManager vbo, Camera camera,
			PhysicsWorld physicsWorld, ITextureRegion texture)
	{
		super(pX, pY, width, height, texture, vbo);
		createPhysics(camera, physicsWorld);
	}
	
	private void createPhysics(final Camera camera, final PhysicsWorld physicsWorld)
	{		
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.StaticBody, 
				PhysicsFactory.createFixtureDef(0, 0, 0));
		body.setUserData("platform2");
		body.setFixedRotation(true);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false)
		{
			@Override
	        public void onUpdate(float pSecondsElapsed)
	        {
				super.onUpdate(pSecondsElapsed);
				camera.onUpdate(0.1f);
				checkToUnload(GameScene.player, physicsWorld);
	        }			
		});
	}
	

	private void checkToUnload(Player player, PhysicsWorld physicsWorld) 
	{
		if(getX() + 800 < player.getX())
		{
			unloadBloc(physicsWorld);
		}
	}
	
	private void unloadBloc(PhysicsWorld physicsWorld) 
	{
	    body.setActive(false);
	    SceneManager.getInstance().getCurrentScene().detachChild(this);
	    physicsWorld.unregisterPhysicsConnector(
	            physicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(this));
	    physicsWorld.destroyBody(body);
	}
}
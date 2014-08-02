package Platforms;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.example.testphysic.GameScene;
import com.example.testphysic.ResourcesManager;
import com.example.testphysic.SceneManager;

public class DestructibleBlock extends AnimatedSprite
{
	private Body body;
	private boolean isBroken;
	
	public DestructibleBlock(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld)
	{
		super(pX, pY, 50, 50, ResourcesManager.getInstance().destructible_bloc, vbo);
		isBroken = false;
		createPhysics(camera, physicsWorld);
		
	}
	
	private void createPhysics(final Camera camera, final PhysicsWorld physicsWorld)
	{		
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));
		body.setUserData("destructiblebloc");
		body.setFixedRotation(true);
		MassData mass = new MassData();
		mass.mass = 1000000000;
		body.setMassData(mass);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false)
		{
			@Override
	        public void onUpdate(float pSecondsElapsed)
	        {
				super.onUpdate(pSecondsElapsed);
				camera.onUpdate(0.1f);
				isBroken(physicsWorld);
	        }
			
		});
		
	}
	public void isBroken(PhysicsWorld physicsWorld)
	{
		if(isBroken && body.getUserData().equals("hit"))
		{
				unloadBloc(physicsWorld);
		}
		else
		{
			if(body.getUserData().equals("hit"))
			{
				body.setUserData("destructiblebloc");
				setCurrentTileIndex(1);
				isBroken = true;
			}
		}
	}
	public void unloadBloc(PhysicsWorld physicsWorld) 
	{
		if(GameScene.player.footContacts > 1)
		{
			GameScene.player.footContacts--;
		}
	    body.setActive(false);
	    SceneManager.getInstance().getCurrentScene().detachChild(this);
	    physicsWorld.unregisterPhysicsConnector(
	            physicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(this));
	    physicsWorld.destroyBody(body);
	}
}

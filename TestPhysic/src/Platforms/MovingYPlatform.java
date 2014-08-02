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

public class MovingYPlatform extends Sprite
{

	private Body body;
	private float shiftY;
	private float initialY;

	public MovingYPlatform(float pX, float pY, float shiftY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld, ITextureRegion texture)
	{
		super(pX, pY, texture, vbo);
		createPhysics(camera, physicsWorld);
		initialY = pY;
		this.shiftY = shiftY;
	}
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld)
	{		
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.KinematicBody, PhysicsFactory.createFixtureDef(0, 0, 0));
		body.setUserData("platform5");
		body.setFixedRotation(true);
		body.setLinearVelocity(0, 5);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false)
		{
			@Override
	        public void onUpdate(float pSecondsElapsed)
	        {
				super.onUpdate(pSecondsElapsed);
				camera.onUpdate(0.1f);
				moveY();
	        }
			
		});
	}
	private void moveY()
	{
		if (getY() < initialY - shiftY)
        {
            body.setLinearVelocity(0, 5);
        }
		else if (getY() > initialY + shiftY)
        {
            body.setLinearVelocity(0,-5);
        }
	}
	
}
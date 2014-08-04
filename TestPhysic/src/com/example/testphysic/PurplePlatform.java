package com.example.testphysic;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PurplePlatform extends Sprite
{

	private Body body;
	private float shiftY;
	private float initialY;

	public PurplePlatform(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld)
	{
		super(pX, pY, ResourcesManager.getInstance().platform5_region, vbo);
		createPhysics(camera, physicsWorld);
		initialY = pY;
		shiftY = 100;
	}
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld)
	{		
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.KinematicBody, PhysicsFactory.createFixtureDef(0, 0, 0));
		body.setUserData("platform5");
		body.setFixedRotation(true);
		body.setLinearVelocity(0, -1 * 5);
		
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
		if (getY() <= initialY- shiftY)
        {
            body.setLinearVelocity(0, body.getLinearVelocity().y * -1);
        }
        if (getY() >= initialY + shiftY)
        {
            body.setLinearVelocity(0, body.getLinearVelocity().y * -1);
        }
	}
	
}
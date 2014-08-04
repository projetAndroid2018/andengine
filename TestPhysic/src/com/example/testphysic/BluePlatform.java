package com.example.testphysic;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class BluePlatform extends Sprite
{

	private Body body;
	private float shiftX;
	private float initialX;

	public BluePlatform(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld)
	{
		super(pX, pY, ResourcesManager.getInstance().platform4_region, vbo);
		createPhysics(camera, physicsWorld);
		initialX = pX;
		shiftX = 100;
	}
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld)
	{		
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.KinematicBody, PhysicsFactory.createFixtureDef(0, 0, 0));
		body.setUserData("platform4");
		body.setFixedRotation(true);
		body.setLinearVelocity(-1 * 5, 0);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false)
		{
			@Override
	        public void onUpdate(float pSecondsElapsed)
	        {
				super.onUpdate(pSecondsElapsed);
				camera.onUpdate(0.1f);
				moveX();
	        }
			
		});
	}
	private void moveX()
	{
		if (getX() <= initialX - shiftX)
        {
            body.setLinearVelocity(body.getLinearVelocity().x * -1, 0);
        }
        if (getX() >= initialX + shiftX)
        {
            body.setLinearVelocity(body.getLinearVelocity().x * -1, 0);
        }
	}
	
}
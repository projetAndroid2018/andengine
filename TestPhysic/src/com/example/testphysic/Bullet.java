package com.example.testphysic;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public abstract class Bullet extends Sprite
{
	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------
	public static boolean canFire = true;
	public boolean unload;
	public Body body;

	// ---------------------------------------------
	// CONSTRUCTOR
	// ---------------------------------------------
	
	public Bullet(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld)
	{
		super(pX, pY, ResourcesManager.getInstance().bullet, vbo);
		createPhysics(camera, physicsWorld);
		unload = false;
	}
	
	// ---------------------------------------------
	// CLASS LOGIC
	// ---------------------------------------------
	
	private void createPhysics(final Camera camera, final PhysicsWorld physicsWorld)
	{		
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.KinematicBody, PhysicsFactory.createFixtureDef(0, 0, 0));
		body.setLinearVelocity(new Vector2(12,0));
		body.setUserData("bullet");
		body.setFixedRotation(true);
		
		MassData md = new MassData();
		md.mass = 1;
		body.setMassData(md);
		
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false)
		{
			@Override
	        public void onUpdate(float pSecondsElapsed)
	        {
				super.onUpdate(pSecondsElapsed);
				camera.onUpdate(0.1f);
				checkPositionX();
				checkCollides();
				if(body.getType() == BodyType.StaticBody || unload)
					unloadBullet(physicsWorld);
	        }
		});
	}
	
	public void unloadBullet(PhysicsWorld physicsWorld)
	{
	    body.setActive(false);
	    SceneManager.getInstance().getCurrentScene().detachChild(this);
	    physicsWorld.unregisterPhysicsConnector(
	            physicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(this));
	    physicsWorld.destroyBody(body);

            canFire = true;
	}
	public abstract void checkPositionX();
	public abstract void checkCollides();
	
}


package com.example.testphysic;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.MassData;

public class Trap extends Sprite
{
	public Body body;

	// ---------------------------------------------
	// CONSTRUCTOR
	// ---------------------------------------------
	public Trap(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld)
	{
		super(pX, pY, 120, 46, ResourcesManager.getInstance().trapRegion, vbo);
		createPhysics(camera, physicsWorld);
	}
	
	
	private void createPhysics(final Camera camera, final PhysicsWorld physicsWorld)
	{		
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));
		body.setUserData("trap");
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
				if(body.getType() == BodyType.StaticBody)
				{
					unloadTrap(physicsWorld);
					GameScene.numberTrapsDestroyed++;
				}
	        }
		});
	}
	
	private void unloadTrap(PhysicsWorld physicsWorld)
	{
		body.setActive(false);
		SceneManager.getInstance().getCurrentScene().detachChild(this);
		physicsWorld.unregisterPhysicsConnector(
				physicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(this));
		physicsWorld.destroyBody(body);
	}

}

package com.example.testphysic.enemies;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
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

public abstract class Enemy1 extends AnimatedSprite
{
	private Body body;
	public long[] hitAnimation = {100, 100, 100, 100, 100, 100, 100, 100,
			0, 0, 0, 0, 0, 0, 0, 0};
	public long[] hitRedAnimation = {0, 0, 0, 0, 0, 0, 0, 0, 
			100, 100, 100, 100, 100, 100, 100, 100};
	public long[] currentAnimation;
	public int identifier;
	public int lifeEnemy;
	
	public Enemy1(float pX, float pY, VertexBufferObjectManager vbo, Camera camera,
			PhysicsWorld physicsWorld) 
	{
		super(pX, pY, ResourcesManager.getInstance().ennemi1Region, vbo);
		createPhysics(camera, physicsWorld);
		lifeEnemy = 2;
		currentAnimation = hitAnimation;
		animate(currentAnimation);
	}
	
	private void createPhysics(final Camera camera, final PhysicsWorld physicsWorld)
	{		
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));
		MassData md = new MassData();
		md.mass = 10000000;
		body.setMassData(md);
		body.setUserData("enemy1");
		body.setFixedRotation(true);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false)
		{
			@Override
	        public void onUpdate(float pSecondsElapsed)
	        {
				super.onUpdate(pSecondsElapsed);
				camera.onUpdate(0.1f);
				
				if(body.getType() == BodyType.StaticBody)
				{
					lifeEnemy--;
					currentAnimation = hitRedAnimation;
					animate(currentAnimation);
					body.setType(BodyType.DynamicBody);
					
					
					//RED BLANKING
					SceneManager.getInstance().getCurrentScene().registerUpdateHandler(new TimerHandler(0.8f, 
							true, new ITimerCallback() 
					{
		                @Override
		                public void onTimePassed(final TimerHandler pTimerHandler) 
		                {
		                	currentAnimation = hitAnimation;
		                	animate(currentAnimation);
		                	SceneManager.getInstance().getCurrentScene().unregisterUpdateHandler(pTimerHandler);		                }
		           }));
    				
					if(lifeEnemy <= 0)
    				{
    					unloadEnemy1(physicsWorld);
    					GameScene.numberEnemiesDestroyed++;
    				}
				}
				
				checkForUnloading();
				
	        
	        }});
	}
	
	public void unloadEnemy1(PhysicsWorld physicsWorld) 
	{
		if(GameScene.player.footContacts > 1)
			GameScene.player.footContacts--;
	    body.setActive(false);
	    SceneManager.getInstance().getCurrentScene().detachChild(this);
	    physicsWorld.unregisterPhysicsConnector(
	            physicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(this));
	    physicsWorld.destroyBody(body);
	}
	
	public abstract void checkForUnloading();
	
	
	
	
	

}

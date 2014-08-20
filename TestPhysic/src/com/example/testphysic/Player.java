package com.example.testphysic;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public abstract class Player extends AnimatedSprite
{
	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------
	
	public Body body;
	
	public boolean canRun = false;
	
	public int footContacts = 0;
	
	public boolean canFire = true;
	
	public int life;
	
	public boolean collides;
	
	public float speedX;
	
	private float heightJump;
	
	//Flag for double jump
	private boolean hasjumped;

	// ---------------------------------------------
	// CONSTRUCTOR
	// ---------------------------------------------
	
	public Player(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld)
	{
		super(pX, pY, ResourcesManager.getInstance().player_region, vbo);
		createPhysics(camera, physicsWorld);
		camera.setChaseEntity(this);
		life = 3;
		speedX = 6;
		heightJump = 8;
		hasjumped = false;
	}
	
	// ---------------------------------------------
	// CLASS LOGIC
	// ---------------------------------------------

	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld)
	{		
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));
		MassData md = new MassData();
		md.mass = 0.00000001f;
		body.setMassData(md);
		body.setUserData("player");
		body.setFixedRotation(true);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false)
		{
			@Override
	        public void onUpdate(float pSecondsElapsed)
	        {
				super.onUpdate(pSecondsElapsed);
				camera.onUpdate(0.1f);
				GameScene.numberMeters = (getX() - GameScene.initialPosition) / 10;
				if (getY() <= 0)				
					onDie();
				
				if (canRun)
					body.setLinearVelocity(new Vector2(speedX, body.getLinearVelocity().y)); 
				else
					body.setLinearVelocity(new Vector2(0, body.getLinearVelocity().y));
				
				level();
				checkLife();				
	        }
		});
	}
	
	public void setRunning()
	{
		canRun = true;
		
		final long[] PLAYER_ANIMATE = new long[] { 100, 100, 100 };
		
		animate(PLAYER_ANIMATE, 0, 2, true);
	}

	public void jump()
	{
		if (footContacts < 1) 
			return; 
		
		
		body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, heightJump));
		
		//Check for double jump
		if(!hasjumped)
			hasjumped = true;
		else
		{
			hasjumped = false;
			decreaseFootContacts();
		}
		//If player hasJumped once, is able to jump another time
		if(hasjumped)
			increaseFootContacts();

		GameScene.numberJumps++;
	}
	
	public void increaseFootContacts()
	{
		footContacts++;
	}
	
	public void decreaseFootContacts()
	{	
		footContacts--;
	}
	public void increaseLife()
	{
		if(life < 3 && life > 0)
			life++;
		else
			return;
			
	}
	
	public abstract void onDie();
	public abstract void level();
	public abstract void checkLife();
}
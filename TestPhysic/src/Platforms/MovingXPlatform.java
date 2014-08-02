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

public class MovingXPlatform extends Sprite
{

	private Body body;
	private float shiftX;
	private float initialX;

	public MovingXPlatform(float pX, float pY, float width, float height, float shiftX, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld, ITextureRegion texture)
	{
		super(pX, pY, width, height, texture, vbo);
		createPhysics(camera, physicsWorld);
		initialX = pX;
		this.shiftX = shiftX;
	}
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld)
	{		
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.KinematicBody, PhysicsFactory.createFixtureDef(0, 0, 0));
		body.setUserData("platform4");
		body.setFixedRotation(true);
		body.setLinearVelocity(5, 0);
		
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
		if (getX() < initialX - (getWidth() / 2) - shiftX)
        {
            body.setLinearVelocity(5, 0);
        }
        if (getX() + (getWidth() / 2) > initialX + shiftX)
        {
            body.setLinearVelocity(-5, 0);
        }
	}
	
}
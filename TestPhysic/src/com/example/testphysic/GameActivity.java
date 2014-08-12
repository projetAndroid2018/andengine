package com.example.testphysic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import com.google.android.gms.ads.*;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;

import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.FrameLayout;

public class GameActivity extends BaseGameActivity
{
	private AdView adView;
	//private AdView adView2;
	private BoundCamera camera;
	public InterstitialAd interstitial;
	public static String MY_AD_UNIT_ID_BOTTOM = "ca-app-pub-1773597333087804/7248870773";
	private static String MY_AD_UNIT_ID_INTERT = "ca-app-pub-1773597333087804/5087479977";
	public static final String myHash= "893414CD738FC2D104BAFB5169F67803"; //TABLETTE
	public static final String myHash2 = "361A4F228352D6761A32993A07F59A07"; //TELEPHONE
	public static int timerAd = 3;

	
	
	
	@Override 
	protected void onSetContentView() 
	{
		
        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(this);
        // Insert the Ad Unit ID
        interstitial.setAdUnitId(MY_AD_UNIT_ID_INTERT);
 

 
        // Request for Ads
        AdRequest adRequest1 = new AdRequest.Builder()
 
        // Add a test device to show Test Ads
        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	    .addTestDevice(myHash)
	    .addTestDevice(myHash2)
	    .build();

        // Load ads into Interstitial Ads
        interstitial.loadAd(adRequest1);
		
		//---------------------------------------------------------
		// FIRST AD (BOTTOM)
		//---------------------------------------------------------		
		
        // CREATING the parent FrameLayout //
        final FrameLayout frameLayout = new FrameLayout(this);
 
        // CREATING the layout parameters, fill the screen //
        final FrameLayout.LayoutParams frameLayoutLayoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                             FrameLayout.LayoutParams.MATCH_PARENT);
 
        // CREATING a Smart Banner View //
        this.adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(MY_AD_UNIT_ID_BOTTOM);
 
        // Doing something I'm not 100% sure on, but guessing by the name //
        adView.refreshDrawableState();
        adView.setVisibility(AdView.INVISIBLE);
 
        // ADVIEW layout, show at the bottom of the screen //
        FrameLayout.LayoutParams adViewLayoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                                             FrameLayout.LayoutParams.WRAP_CONTENT,
                                             Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);

        
	    // Créez la demande d'annonce.	    
	       final AdRequest adRequest = new AdRequest.Builder()
	        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		    .addTestDevice(myHash)
		    .addTestDevice(myHash2)
		    .build();


	       
	       
	       adView.loadAd(adRequest);
        
 
        // RENDER the ad on top of the scene //
        this.mRenderSurfaceView = new RenderSurfaceView(this);
        mRenderSurfaceView.setRenderer(mEngine, this);
 
        // SURFACE layout ? //
        final android.widget.FrameLayout.LayoutParams surfaceViewLayoutParams =
                new FrameLayout.LayoutParams(super.createSurfaceViewLayoutParams());
 
        // ADD the surface view and adView to the frame //
        frameLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams);
        frameLayout.addView(adView, adViewLayoutParams);
 
        // SHOW AD //
        adView.setBackgroundColor(android.graphics.Color.TRANSPARENT);
        this.setContentView(frameLayout, frameLayoutLayoutParams);


	  }

	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) 
	{
		return new LimitedFPSEngine(pEngineOptions, 60);
	}
   
    public void displayInterstitial() 
    {
    	
    	this.runOnUiThread(new Runnable() 
        {
                @Override
                public void run() 
                {
                    // If Ads are loaded, show Interstitial else show nothing.
                    if (interstitial.isLoaded()) 
                    {
                        interstitial.show();
                    }
                }
        });
    }
	
	public EngineOptions onCreateEngineOptions()
	{
		camera = new BoundCamera(0, 0,800, 480);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), this.camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.getRenderOptions().getConfigChooserOptions().setRequestedMultiSampling(true);		
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
	    return engineOptions;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{  
	    if (keyCode == KeyEvent.KEYCODE_BACK)
	    {
	    	SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
	    }
	    return false; 
	}

	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException
	{
		ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
		pOnCreateResourcesCallback.onCreateResourcesFinished();
		setAdMobVisibile();
	}

	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException
	{
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
	}

	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException
	{
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
		{
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                SceneManager.getInstance().createMenuScene();
                SceneManager.getInstance().disposeSplashScene();
            }
		}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	@Override
	public void onDestroy()
	{
		if(adView != null)
			adView.destroy();
		super.onDestroy();
		System.exit(0);	
	}
	
	@Override
	public void onPause() 
	{
	    adView.pause();
	    if(ResourcesManager.music_menu != null && ResourcesManager.music_menu.isPlaying())
	    	ResourcesManager.music_menu.pause();
	    if(ResourcesManager.music_game != null && ResourcesManager.music_game.isPlaying())
	    	ResourcesManager.music_game.pause();
	    super.onPause();
	}

	@Override
	public void onResume() 
	{
	    adView.resume();
	    super.onResume();
	    
	    //--------------------------
	    // HADRIEN 
	    //--------------------------
	    
	    //Rajoute le booleen dans le if pour chaque music
	    
	    //if(ResourcesManager.music_menu != null)
	    //	ResourcesManager.music_menu.play();
	    //if(ResourcesManager.music_game != null)
	    //	ResourcesManager.music_game.play();
	}

	
	public void writeStats(String key, String value) throws IOException
	{
		//WRITE
		FileOutputStream fOut = openFileOutput(key, MODE_WORLD_WRITEABLE);
		fOut.write(value.getBytes());
		fOut.close();
	}
	
	public String readStats(String key) throws IOException
	{
		FileInputStream fin = openFileInput(key);
		int c;
		String temp="";
		while( (c = fin.read()) != -1)
		{
		   temp = temp + Character.toString((char)c);
		}
		//string temp contains all the data of the file.
		fin.close();
		return temp;
	}

    public void setAdMobInVisibile() 
    {
        this.runOnUiThread(new Runnable() 
        {
                @Override
                public void run() 
                {
                	adView.setVisibility(AdView.INVISIBLE);
                }
        });
    }

    public void setAdMobVisibile() 
    {
        this.runOnUiThread(new Runnable() 
        {
                @Override
                public void run() 
                {
                	adView.setVisibility(AdView.VISIBLE);
                }
        });
    }



}

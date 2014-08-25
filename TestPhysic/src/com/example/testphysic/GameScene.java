package com.example.testphysic;


import java.io.IOException;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.SAXUtils;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;


import Platforms.DestructibleBlock;
import Platforms.FragilePlatform;
import Platforms.MovingXPlatform;
import Platforms.MovingYPlatform;
import Platforms.SemiStaticPlatform;
import Platforms.StaticPlatform;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.example.testphysic.LevelCompleteWindow.StarsCount;
import com.example.testphysic.SceneManager.SceneType;
import com.example.testphysic.enemies.Enemy1;


public class GameScene extends BaseScene implements IOnSceneTouchListener
{
	
	private HUD gameHUD;
	private Text scoreText;
	private PhysicsWorld physicsWorld;
	private LevelCompleteWindow levelCompleteWindow;
	
	//TAG LOAD LEVEL
	private static final String TAG_ENTITY = "entity";
	private static final String TAG_ENTITY_ATTRIBUTE_X = "x";
	private static final String TAG_ENTITY_ATTRIBUTE_Y = "y";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";
	private static final String TAG_ENTITY_ATTRIBUTE_WIDTH = "width";
	private static final String TAG_ENTITY_ATTRIBUTE_HEIGHT = "height";
	private static final String TAG_ENTITY_ATTRIBUTE_MOVE = "move";
	

	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM1 = "platform1";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM2 = "platform2";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM3 = "platform3";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM4 = "platform4";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM5 = "platform5";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LINE = "line";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_STICK = "stick";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_TRAP = "trap";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_COIN = "coin";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER = "player";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LEVEL_COMPLETE = "levelComplete";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_ENEMY1 = "enemy1";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_DESTRUCTIBLE_BLOC = "destructibleBloc";
	
	//BONUS--MALUS
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SCORE_INCREASER = "scoreplus";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SCORE_REDUCER = "scoreminus";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SPEED_RAISER = "speedplus";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SPEED_REDUCER = "speedminus";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_MYSTERY_BONUS = "mystery";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_HEART = "heart";
	
	//PERTURBATIONS
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_VERTICAL_PERTURBATIONS = "verticalper";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_HORIZONTAL_PERTURBATIONS = "horizontalper";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_DIAGONAL_PERTURBATIONS = "diagonalper";


	
	
	public static Player player;
	
	//HUD
	private TiledSprite heart1;
	private TiledSprite heart2;
	private TiledSprite heart3;
	private TiledSprite heart4;
	private TiledSprite heart5;
	private Sprite pause;
	private Sprite play;
	private Rectangle pauseRe;
	private Rectangle playRe;
	private AnimatedSprite area;
	private static long[] animateArea = {100 , 0};
	private static long[] animateArea2 = {0 ,100};
	private static long[] animate;
	private Rectangle rectangle;
	private Sprite balance;
	private Sprite arrow;
	private Sprite toLeftArrow;
	private Sprite toRightArrow;
	private Rectangle toRightArrowRectangle;
	private Rectangle toLeftArrowRectangle;
	
	//GameOver Text
	private Text gameOverText;
	private boolean gameOverDisplayed = false;

	//In Game
	private boolean firstTouch = false;
	public static int levelToLoad = 0;
	private float angle = 0;
	private float shift;
	private boolean updateBalance;
	private boolean firstBalance = true;
	private static int scorePlus = 0;
	private static int scoreMoins = 0;
	private int perturbationsVerticalTime = 300;
	private int perturbationsHorizontalTime = 300;
	private int perturbationsDiagonaTime = 300;
	private int scoreMultiplier;
	private int coinsMultiplier;
	public static boolean oneShot = false;
	
	
	//-------------------------------------------
	//STATS && SCORE
	//-------------------------------------------
	
	
	//----------------------------------
	//PER GAME 
	
	//Number Scene Loaded (800px by 800px)
	public static int numberScenesLoaded;
	
	//Number Enemy Destroyed
	public static int numberEnemiesDestroyed;
	
	//Number Traps Destroyed
	public static int numberTrapsDestroyed;
	
	//Number Jumps
	public static int numberJumps;
	
	//Number Kilometers
	public static float numberMeters;
	public static float initialPosition;
	
	//Number Coins Collected
	public static int numberCoinsCollected;
	
	//Score
	public static int score;
	
	
	//-------------------------------------------------------------
	//Best
	
	//Number Scene Loaded (800px by 800px)
	public final static String BEST_NUMBER_SCENES_LOADED_KEY = "BNSL";
	
	//Number Enemy Destroyed
	public final static String BEST_NUMBER_ENEMIES_DESTROYED_KEY = "BNED";
	
	//Number Traps Destroyed
	public final static String BEST_NUMBER_TRAPS_DESTROYED_KEY = "BNTD";
	
	//Number Jumps
	public final static String BEST_NUMBER_JUMPS_KEY = "BNJ";
	
	//Number Kilometers
	public final static String BEST_NUMBER_KILOMETERS_KEY = "BNK";
	
	//Number Coins Collected
	public final static String BEST_NUMBER_COINS_COLLECTED_KEY = "BNCC";
	
	//Score
	public final static String BEST_SCORE_KEY = "BSK";
	
	
	//----------------------------------------------------------------
	//GLOBAL
	//Number Game Played
	public final static String GLOBAL_NUMBER_GAMES_PLAYED_KEY = "GNGP";
	
	//Number Scene Loaded (800px by 800px)
	public final static String GLOBAL_NUMBER_SCENES_LOADED_KEY = "GNSL";
		
	//Number Enemy Destroyed
	public final static String GLOBAL_NUMBER_ENEMIES_DESTROYED_KEY = "GNED";
		
	//Number Traps Destroyed
	public final static String GLOBAL_NUMBER_TRAPS_DESTROYED_KEY = "GNTD";
		
	//Number Jumps
	public final static String GLOBAL_NUMBER_JUMPS_KEY = "GNJ";
		
	//Number Kilometers
	public final static String GLOBAL_NUMBER_KILOMETERS_KEY = "GNK";
		
	//Number Coins Collected
	public final static String GLOBAL_NUMBER_COINS_COLLECTED_KEY = "GNCC";
		
	//Score
	public final static String GLOBAL_SCORE_KEY = "GSK";
	
	//--------------------------------------------------------------------
	//BONUS
	//--------------------------------------------------------------------
	
	//Life
	
	//Global
	public final static String ONE_LIFE_GLOBAL_KEY = "OLGK";
	public final static String TWO_LIVES_GLOBAL_KEY = "TLGK";
	//Five game
	public final static String ONE_LIFE_FIVE_GAME_KEY = "OLFGK";
	public final static String TWO_LIVES_FIVE_GAME_KEY = "TLVGK";
	//One game
	public final static String ONE_LIFE_ONE_GAME_KEY = "OLOGK";
	public final static String TWO_LIVES_ONE_GAME_KEY= "TLOGK";
	
	//Multiplication coins
	
	//One game
	public final static String MULTIPLICATION_TIME_2_COINS_ONE_GAME_KEY = "MT2COGK";
	public final static String MULTIPLICATION_TIME_5_COINS_ONE_GAME_KEY = "MT5COGK";
	//Five game
	public final static String MULTIPLICATION_TIME_2_COINS_FIVE_GAME_KEY = "MT2CFGK";
	public final static String MULTIPLICATION_TIME_5_COINS_FIVE_GAME_KEY = "MT5CFGK";
	
	//Multiplication score
	
	//One game
	public final static String MULTIPLICATION_TIME_2_SCORE_ONE_GAME_KEY = "MT2SOGK";
	public final static String MULTIPLICATION_TIME_5_SCORE_ONE_GAME_KEY = "MT5SOGK";
	//Five game
	public final static String MULTIPLICATION_TIME_2_SCORE_FIVE_GAME_KEY = "MT2SFGK";
	public final static String MULTIPLICATION_TIME_5_SCORE_FIVE_GAME_KEY = "MT5SFGK";
	
	//Player's fire power
	public final static String FIRE_POWER_ONE_GAME = "FPOG";
	public final static String FIRE_POWER_FIVE_GAME = "FPFG";
	
	
	
		

	
	@Override
	public void createScene()
	{
		resourcesManager.hideAd();
		InitializeGameStats();
		InitializeBestStats();
		InitializedGlobal();
		InitializedBonus();
		Bullet.canFire = true;
		createBackground();
		resourcesManager.loadHUDGraphics();
		createHUD();
		createPhysics();
		levelToLoad = 1;
		loadLevel(levelToLoad);
		createGameOverText();
		levelCompleteWindow = new LevelCompleteWindow(vbom);
		setOnSceneTouchListener(this);
		oneShot = checkFirePower();
	}


	@Override
	public void onBackKeyPressed()
	{
		disposeScene();
		SceneManager.getInstance().createStatScene();
		resourcesManager.displayAd();
	}
	
	@Override
	public SceneType getSceneType()
	{
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene()
	{
		camera.setHUD(null);
		camera.setChaseEntity(null); 
		camera.setCenter(400, 240);
	}
	
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
	{
		if (pSceneTouchEvent.isActionDown())
		{
			if (!firstTouch)
			{
				player.setRunning();
				firstTouch = true;
			}
			else
			{
				player.jump();
			}
		}
		return false;
	}
	
	private void loadLevel(int levelID)
	{
		final SimpleLevelLoader levelLoader = new SimpleLevelLoader(vbom);
		//final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
		
		//CAMERA BOUNDS
		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(LevelConstants.TAG_LEVEL)
		{
			public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException 
			{
				final int width = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
				final int height = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);
				
				camera.setBounds(0, 0, width, height); // here we set camera bounds
				camera.setBoundsEnabled(true);

				return GameScene.this;
			}
		});
		
		//ENTITY
		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_ENTITY)
		{
			public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException
			{
				final int x = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_X);
				final int y = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_Y);
				final String type = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);
				final int width = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_WIDTH);
				final int height = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_HEIGHT);
				final int move = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_MOVE);
				
				final Sprite levelObject;
				
				if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM1))
				{
					levelObject = new StaticPlatform(x + ((levelToLoad-1) *800), y, width, height, vbom, camera, physicsWorld, resourcesManager.platform1_region);
				} 
				else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM2))
				{
					levelObject = new SemiStaticPlatform(x + ((levelToLoad-1) *800), y, width, height, vbom, camera, physicsWorld, resourcesManager.platform2_region);
				}
				else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM3))
				{
					levelObject = new FragilePlatform(x + ((levelToLoad-1) *800), y, width, height, vbom, camera, physicsWorld, resourcesManager.platform3_region);
				}
				else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM4))
				{
					levelObject = new MovingXPlatform(x + ((levelToLoad-1) *800), y, width, height, move, vbom, camera, physicsWorld, resourcesManager.platform4_region);					
				}
				else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM5))
				{
					levelObject = new MovingYPlatform(x + ((levelToLoad-1) *800), y, width, height, move, vbom, camera, physicsWorld, resourcesManager.platform5_region);					
				}
				else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LINE))
				{
					StaticPlatform line = new StaticPlatform(x + ((levelToLoad-1) *800), y, width, height, vbom, camera, physicsWorld, resourcesManager.lineRegion);
					line.body.setUserData("line");
					levelObject = line;
				}
				else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_STICK))
				{
					levelObject = new StaticPlatform(x + ((levelToLoad-1) *800), y, width, height, vbom, camera, physicsWorld, resourcesManager.stickRegion);					
				}
				else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_COIN))
				{
					levelObject = new Sprite(x + ((levelToLoad-1) *800), y, resourcesManager.coin_region, vbom)
					{
						@Override
						protected void onManagedUpdate(float pSecondsElapsed) 
						{
							super.onManagedUpdate(pSecondsElapsed);

							if (player.collidesWith(this))
							{
								this.setVisible(false);
								this.setIgnoreUpdate(true);
								numberCoinsCollected += coinsMultiplier;
							}
						}
					};
					levelObject.registerEntityModifier(new LoopEntityModifier(new ScaleModifier(1, 1, 1.3f)));
				}	
				else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER))
				{
						player = new Player(x + ((levelToLoad-1) *800), y, vbom, camera, physicsWorld)
						{
							@Override
							public void onDie()
							{
								if (!gameOverDisplayed)
								{
									displayGameOverText();
									resourcesManager.activity.setAdMobVisibile();
									resourcesManager.activity.displayInterstitial();

									player.setVisible(false);
									try { CheckBestStats(); } catch (NumberFormatException e) {} catch (IOException e) {}
									try { UpdateGlobatStats();} catch (IOException e) {}
									
									registerUpdateHandler(new TimerHandler(5, true, new ITimerCallback() 
									{
						                @Override
						                public void onTimePassed(final TimerHandler pTimerHandler) 
						                {
						                	onBackKeyPressed();
						                }
						           }));
									
								}
								
							}

							@Override
							public void level() 
							{
								addToScore(scoreMultiplier);
								if(this.getX() > ((levelToLoad-1)*800))
								{
									levelToLoad++;
									if(levelToLoad % 2 == 0 && player.speedX < 15)
										player.speedX += 0.5f;
									numberScenesLoaded++;
									if(levelToLoad <= 4)
										loadLevel(levelToLoad);
									else
										loadLevel(4);
								}
							}						
							
							@Override
							public void checkLife()
							{
								switch(player.life)
								{
								case 0:
									heart1.setCurrentTileIndex(1);
									heart2.setCurrentTileIndex(1);
									heart3.setCurrentTileIndex(1);
									heart4.setVisible(false);
									heart5.setVisible(false);
									onDie();
									break;
								case 1:
									heart1.setCurrentTileIndex(0);
									heart2.setCurrentTileIndex(1);
									heart3.setCurrentTileIndex(1);
									heart4.setVisible(false);
									heart5.setVisible(false);
									break;
								case 2:
									heart1.setCurrentTileIndex(0);
									heart2.setCurrentTileIndex(0);
									heart3.setCurrentTileIndex(1);
									heart4.setVisible(false);
									heart5.setVisible(false);
									break;
								case 3:
									heart1.setCurrentTileIndex(0);
									heart2.setCurrentTileIndex(0);
									heart3.setCurrentTileIndex(0);
									heart4.setVisible(false);
									heart5.setVisible(false);
									break;
								case 4:
									heart1.setCurrentTileIndex(0);
									heart2.setCurrentTileIndex(0);
									heart3.setCurrentTileIndex(0);
									heart4.setCurrentTileIndex(0);
									heart5.setVisible(false);
									break;
								case 5:
									heart1.setCurrentTileIndex(0);
									heart2.setCurrentTileIndex(0);
									heart3.setCurrentTileIndex(0);
									heart4.setCurrentTileIndex(0);
									heart5.setCurrentTileIndex(0);
									break;
								}
							}
							
							
						};
						initialPosition = x;
						checkNumberLifeCharacter();
						scoreMultiplier = checkMultiplier();
						coinsMultiplier = checkCoinsMultiplier();
						levelObject = player;
				}
				else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_ENEMY1))
				{
						final Enemy1 enemy1 = new Enemy1(x + ((levelToLoad-1) *800), y, vbom, camera, physicsWorld)
						{

							@Override
							public void checkForUnloading() 
							{
								if (player.getX() >= this.getX() + 400)
									unloadEnemy1(physicsWorld);
							}

						};
						levelObject = enemy1;
				}
				else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_LEVEL_COMPLETE))
				{
					levelObject = new Sprite(x + ((levelToLoad-1) *800), y, resourcesManager.complete_stars_region, vbom)
					{
						@Override
						protected void onManagedUpdate(float pSecondsElapsed) 
						{
							super.onManagedUpdate(pSecondsElapsed);

							if (player.collidesWith(this))
							{
								levelCompleteWindow.display(StarsCount.TWO, GameScene.this, camera);
								this.setVisible(false);
								this.setIgnoreUpdate(true);
							}
						}
					};
					levelObject.registerEntityModifier(new LoopEntityModifier(new ScaleModifier(1, 1, 1.3f)));
				}
				else if(type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_TRAP))
				{
					Trap trap = new Trap(x + ((levelToLoad-1) *800), y, vbom, camera, physicsWorld);
					trap.setUserData("bullet");
					levelObject = trap;
				}
				else if(type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_DESTRUCTIBLE_BLOC))
				{
					levelObject = new DestructibleBlock(x + ((levelToLoad-1) *800), y, 50, 50, vbom, camera, physicsWorld);
				}
				else if(type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SPEED_REDUCER))
				{
					levelObject = new Sprite(x + ((levelToLoad-1) *800), y, resourcesManager.blueBallRegion, vbom)
					{
						@Override
						protected void onManagedUpdate(float pSecondsElapsed)
						{
							super.onManagedUpdate(pSecondsElapsed);
							if (player.collidesWith(this))
							{
								this.setVisible(false);
								this.setIgnoreUpdate(true);
								player.speedX += -2;
							}
						};
					};
				}
				else if(type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SPEED_RAISER))
				{
					levelObject = new Sprite(x + ((levelToLoad-1) *800), y, resourcesManager.redBallRegion, vbom)
					{
						@Override
						protected void onManagedUpdate(float pSecondsElapsed)
						{
							super.onManagedUpdate(pSecondsElapsed);
							if (player.collidesWith(this))
							{
								this.setVisible(false);
								this.setIgnoreUpdate(true);
								player.speedX += 2;
							}
						};
					};
				}
				else if(type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SCORE_REDUCER))
				{
					levelObject = new Sprite(x + ((levelToLoad-1) *800), y, resourcesManager.orangeBallRegion, vbom)
					{
						@Override
						protected void onManagedUpdate(float pSecondsElapsed)
						{
							super.onManagedUpdate(pSecondsElapsed);
							if (player.collidesWith(this))
							{
								this.setVisible(false);
								this.setIgnoreUpdate(true);
								scoreMoins++;
							}
						};
					};
				}
				else if(type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SCORE_INCREASER))
				{
					levelObject = new Sprite(x + ((levelToLoad-1) *800), y, resourcesManager.blackBallRegion, vbom)
					{
						@Override
						protected void onManagedUpdate(float pSecondsElapsed)
						{
							super.onManagedUpdate(pSecondsElapsed);
							if (player.collidesWith(this))
							{
								scorePlus++; 
								this.setVisible(false);
								this.setIgnoreUpdate(true);
							}
						};
					};
				}
				else if(type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_VERTICAL_PERTURBATIONS))
				{
					levelObject = new Sprite(x + ((levelToLoad-1) *800), y, resourcesManager.verticalRegionRectangle, vbom)
					{
						@Override
						protected void onManagedUpdate(float pSecondsElapsed)
						{
							super.onManagedUpdate(pSecondsElapsed);
							if (player.collidesWith(this))
							{
								this.setVisible(false);
								this.setIgnoreUpdate(true);
								final Sprite perturbations = new Sprite(camera.getCenterX(), camera.getCenterY(), resourcesManager.verticalRegion, vbom)
								{
									@Override
									protected void onManagedUpdate(float pSecondsElapsed)
									{
										super.onManagedUpdate(pSecondsElapsed);
										setPosition(camera.getCenterX(), camera.getCenterY());
										perturbationsVerticalTime--;
										if(perturbationsVerticalTime <= 0)
										{
											this.setVisible(false);
											this.setIgnoreUpdate(true);
											perturbationsVerticalTime = 300;
										}	
									};
								};
								SceneManager.getInstance().getCurrentScene().attachChild(perturbations);
							}
						};						
					};
				}
				else if(type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_HORIZONTAL_PERTURBATIONS))
				{
					levelObject = new Sprite(x + ((levelToLoad-1) *800), y, resourcesManager.horizontalRegionRectangle, vbom)
					{
						@Override
						protected void onManagedUpdate(float pSecondsElapsed)
						{
							super.onManagedUpdate(pSecondsElapsed);
							if (player.collidesWith(this))
							{
								this.setVisible(false);
								this.setIgnoreUpdate(true);
								final Sprite perturbations = new Sprite(camera.getCenterX(), camera.getCenterY(), resourcesManager.horizontalRegion, vbom)
								{
									@Override
									protected void onManagedUpdate(float pSecondsElapsed)
									{
										super.onManagedUpdate(pSecondsElapsed);
										setPosition(camera.getCenterX(), camera.getCenterY());
										perturbationsHorizontalTime--;
										if(perturbationsHorizontalTime <= 0)
										{
											this.setVisible(false);
											this.setIgnoreUpdate(true);
											perturbationsHorizontalTime = 300;
										}	
									};
								};
								SceneManager.getInstance().getCurrentScene().attachChild(perturbations);
							}
						};						
					};
				}
				else if(type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_DIAGONAL_PERTURBATIONS))
				{
					levelObject = new Sprite(x + ((levelToLoad-1) *800), y, resourcesManager.diagonalRegionRectangle, vbom)
					{
						@Override
						protected void onManagedUpdate(float pSecondsElapsed)
						{
							super.onManagedUpdate(pSecondsElapsed);
							if (player.collidesWith(this))
							{
								this.setVisible(false);
								this.setIgnoreUpdate(true);
								final Sprite perturbations = new Sprite(camera.getCenterX(), camera.getCenterY(), resourcesManager.diagonalRegion, vbom)
								{
									@Override
									protected void onManagedUpdate(float pSecondsElapsed)
									{
										super.onManagedUpdate(pSecondsElapsed);
										setPosition(camera.getCenterX(), camera.getCenterY());
										perturbationsDiagonaTime--;
										if(perturbationsDiagonaTime<= 0)
										{
											this.setVisible(false);
											this.setIgnoreUpdate(true);
											perturbationsDiagonaTime = 300;
										}	
									};
								};
								SceneManager.getInstance().getCurrentScene().attachChild(perturbations);
							}
						};						
					};
				}
				else if(type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_MYSTERY_BONUS))
				{
					levelObject = new Sprite(x + ((levelToLoad-1) *800), y, resourcesManager.mysteryBallRegion, vbom)
					{
						@Override
						protected void onManagedUpdate(float pSecondsElapsed)
						{
							super.onManagedUpdate(pSecondsElapsed);
							if (player.collidesWith(this))
							{
								this.setVisible(false);
								this.setIgnoreUpdate(true);
								if( (int) player.getX() % 2 == 0)
									score += 200;
								else
									score += -100;
							}
						};
					};
				}
				else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_HEART))
				{
					levelObject = new AnimatedSprite(x + ((levelToLoad-1) *800), y, width, height, resourcesManager.heartsRegion, vbom)
					{
						@Override
						protected void onManagedUpdate(float pSecondsElapsed)
						{
							setCurrentTileIndex(0);
							if(this.collidesWith(player))
							{
								setVisible(false);
								player.increaseLife();
								setIgnoreUpdate(true);
							}
						};
					};
				}
				else
				{
					throw new IllegalArgumentException();
				}
				
				levelObject.setCullingEnabled(true);

				return levelObject;
			}

			private void checkNumberLifeCharacter() throws IOException 
			{
				//GLOBAL
				final int ONE_LIFE_GLOBAL = Integer.parseInt(resourcesManager.read(ONE_LIFE_GLOBAL_KEY));
				final int TWO_LIVES_GLOBAL = Integer.parseInt(resourcesManager.read(TWO_LIVES_GLOBAL_KEY));

				//FIVE GAME
				final int ONE_LIFE_FIVE_GAME = Integer.parseInt(resourcesManager.read(ONE_LIFE_FIVE_GAME_KEY));
				final int TWO_LIVES_FIVE_GAME = Integer.parseInt(resourcesManager.read(TWO_LIVES_FIVE_GAME_KEY));
				
				//ONE 
				final int ONE_LIFE_ONE_GAME = Integer.parseInt(resourcesManager.read(ONE_LIFE_ONE_GAME_KEY));
				final int TWO_LIVES_ONE_GAME = Integer.parseInt(resourcesManager.read(TWO_LIVES_ONE_GAME_KEY));
				
				//--------------------------------
				//CHECK FOR TWO BONUS LIVES
				if(TWO_LIVES_GLOBAL <= 0)
				{
					if(TWO_LIVES_FIVE_GAME <= 0)
					{
						if(TWO_LIVES_ONE_GAME <= 0)
						{
							//CHECK FOR ONE BONUS LIFE
							if(ONE_LIFE_GLOBAL <= 0)
							{
								if(ONE_LIFE_FIVE_GAME <= 0)
								{
									if(ONE_LIFE_ONE_GAME <= 0)
									{
										player.life = player.life;
									}
									else
									{
										//player = 4 lives for 1 game
										player.life = 4;
										
										int numberTime = (Integer.parseInt(resourcesManager.read(ONE_LIFE_ONE_GAME_KEY))) - 1;
										resourcesManager.write(ONE_LIFE_ONE_GAME_KEY, Integer.toString(numberTime));
									}
								}
								else
								{
									//player = 4 lives for 5 games
									player.life = 4;
									
									int numberTime = (Integer.parseInt(resourcesManager.read(ONE_LIFE_FIVE_GAME_KEY))) - 1;
									resourcesManager.write(ONE_LIFE_FIVE_GAME_KEY, Integer.toString(numberTime));
								}
							}
							else
							{
								//player = 4 lives at anytime
								player.life = 4;
							}
						}
						else
						{
							//player = 5 lives for 1 game
							player.life = 5;
							
							int numberTime = (Integer.parseInt(resourcesManager.read(TWO_LIVES_ONE_GAME_KEY))) - 1;
							resourcesManager.write(TWO_LIVES_ONE_GAME_KEY, Integer.toString(numberTime));
						}
					}
					else
					{
						//player = 5 lives for 5 games
						player.life = 5;
						
						int numberTime = (Integer.parseInt(resourcesManager.read(TWO_LIVES_FIVE_GAME_KEY))) - 1;
						resourcesManager.write(TWO_LIVES_FIVE_GAME_KEY, Integer.toString(numberTime));
					}
				}
				else
				{
					//player = 5 lives at anytime
					player.life = 5;
				}	
			}
		});
		
		//PATH
		levelLoader.loadLevelFromAsset(activity.getAssets(), "level/" + levelID + ".lvl");
	}
	
	private void createGameOverText()
	{
		gameOverText = new Text(0, 0, resourcesManager.font, "Game Over!", vbom);
	}
	
	private void displayGameOverText()
	{
		camera.setChaseEntity(null);
		gameOverText.setPosition(camera.getCenterX(), camera.getCenterY());
		attachChild(gameOverText);
		player.canRun = false;
		player.body.setLinearVelocity(new Vector2(0,0));

		gameOverDisplayed = true;
	}
	
	private void createHUD()
	{
		gameHUD = new HUD();
		
		scoreText = new Text(20, 420, resourcesManager.font, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		scoreText.setAnchorCenter(0, 0);
		
		 
		heart1 = new TiledSprite(60, 380, 50, 50, ResourcesManager.getInstance().heartsRegion, vbom);
		heart2 = new TiledSprite(120, 380, 50, 50, ResourcesManager.getInstance().heartsRegion, vbom);
		heart3 = new TiledSprite(180, 380, 50, 50, ResourcesManager.getInstance().heartsRegion, vbom);
		heart4 =  new TiledSprite(240, 380, 50, 50, ResourcesManager.getInstance().heartsRegion, vbom);
		heart5 = new TiledSprite(300, 380, 50, 50, ResourcesManager.getInstance().heartsRegion, vbom);
		
		heart1.setCurrentTileIndex(1);
		heart2.setCurrentTileIndex(1);
		heart3.setCurrentTileIndex(1);
		heart4.setCurrentTileIndex(1);
		heart5.setCurrentTileIndex(1);
		
		
		gameHUD.attachChild(heart1);
		gameHUD.attachChild(heart2);
		gameHUD.attachChild(heart3);
		gameHUD.attachChild(heart4);
		gameHUD.attachChild(heart5);
		
		 
		area = new AnimatedSprite(500, 420, 300, 100, resourcesManager.area, vbom);
		
		resourcesManager.loadHUDGraphics();
		
		
		rectangle = new Rectangle(500, 420, 300, 100,vbom)
	{
		@Override
		public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
        {
            if (touchEvent.isActionDown())
            {
            	animate = animateArea2;
            	area.animate(animate,true);
            	
            	if(Bullet.canFire)
            	{
            	final Bullet bullet = new Bullet(player.getX() + 50, player.getY(), vbom, camera, physicsWorld)
            	{
            		@Override
            		public void checkPositionX()
            		{
            			if(player.getX() + 420 <= this.getX())
            			{
            				unload = true;
            			}
            			
            		}
					@Override
					public void checkCollides() 
					{
						if(player.collidesWith(this))
						{
							unload = true;
						}
					}
            	};
            	bullet.setUserData("bullet");
            	
            	SceneManager.getInstance().getCurrentScene().attachChild(bullet);
            	}
            }
            else if(touchEvent.isActionUp())
            {
            	animate = animateArea;
            	area.animate(animate,true);
            }
            return true;
        };
	};
		
	arrow = new Sprite (200 , 190, 50, 100, resourcesManager.arrowRegion, vbom);
	balance = new Sprite(200, 200, resourcesManager.balanceRegion, vbom);
	
	toLeftArrow = new Sprite (500 , 100, 200, 81, resourcesManager.toLeftArrow, vbom);
	toRightArrow = new Sprite (200 , 100, 200, 81, resourcesManager.toRightArrow, vbom);
	
	scoreText.setText("Score: 0");
	gameHUD.registerTouchArea(rectangle);
	gameHUD.attachChild(scoreText);

	animate = animateArea;
	area.animate(animate,true);
	gameHUD.attachChild(area);
		
	//PAUSE BUTTON
	pause = new Sprite(710, 410, 50, 50, resourcesManager.pauseRegion, vbom);
	pauseRe = new Rectangle(710, 410, 50, 50, vbom)
	{
		@Override
		public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
		{
			if(pSceneTouchEvent.isActionDown())
				pauseMenu();
			return true;
		};
	};
	gameHUD.attachChild(pause);
	gameHUD.registerTouchArea(pauseRe);
	
	camera.setHUD(gameHUD);
	}

	private void displayEquilibrium()
	{
		gameHUD.attachChild(arrow);
		gameHUD.attachChild(balance);
		
		toLeftArrowRectangle = new Rectangle(500, 100, 200, 81, vbom)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
				if(pSceneTouchEvent.isActionDown())
					if(levelToLoad <= 20)
						angle += (levelToLoad / 4) * -4;	
					else
						angle += 5 * -4;	
				return true;
			};
		};
		
		
		toRightArrowRectangle = new Rectangle(200, 100, 200, 81, vbom)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
				if(pSceneTouchEvent.isActionDown())
					if(levelToLoad <= 20)
						angle += (levelToLoad / 4) * 4;			
					else
						angle += 5 * 4;	
				return true;
			};
		};
		
		gameHUD.registerTouchArea(toLeftArrowRectangle);
		gameHUD.registerTouchArea(toRightArrowRectangle);
		
		gameHUD.attachChild(toLeftArrow);
		gameHUD.attachChild(toRightArrow);
	    
	    if(firstBalance)
	    registerUpdateHandler(new TimerHandler(1f / 20.0f, true, new ITimerCallback() 
	    {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
            	firstBalance = false;
            	if(updateBalance)
            	{
            		if(angle < 0)
            		{
            			if(levelToLoad <= 20)
            				shift = (levelToLoad / 4) * -.8f;
            			else
            				shift = 5 * -.8f;
            			
            		}
            		else if (angle > 0)
            		{
            			if(levelToLoad <= 20)
            				shift = (levelToLoad / 4)  * 0.8f;
            			else
            				shift = 5 * 0.8f;
            		}
            		else 
            		{
            			if((int) player.getX() % 2 == 0)
            			{
            				if(levelToLoad <= 20)
            					shift = (levelToLoad / 4) * -.8f;
            				else
            					shift = 5 * -.8f;
            			}
            			else
            			{
            				if(levelToLoad <= 20)
            					shift = (levelToLoad / 4)  * 0.8f;
            				else
            					shift = 5  * 0.8f;
            			}
            		}
            		
            		angle += shift;
            		arrow.setRotation(angle);
            		if(angle > 90 || angle < (-90))
            		{
            			unregisterUpdateHandler(pTimerHandler);
            			player.onDie();
            		}
            	}
            }
	    }));
	    
	    
	    
	    
	    
	    
	}
	
	private void removeEquilibrium()
	{
		arrow.detachSelf();
		balance.detachSelf();
		toLeftArrow.detachSelf();
		toRightArrow.detachSelf();
		angle = 0;
		shift = 0;
		gameHUD.unregisterTouchArea(toLeftArrowRectangle);
		gameHUD.unregisterTouchArea(toRightArrowRectangle);
		updateBalance = false;
	}

	private void createBackground()
	{
		ParallaxBackground background = new ParallaxBackground(0, 0, 0);
	    background.attachParallaxEntity(new ParallaxEntity(100, new Sprite(camera.getWidth() / 2, camera.getHeight() / 2, resourcesManager.backgroud, vbom)));
	    setBackground(background);
	}
	
	private void addToScore(int multiplier)
	{		
		score = (int)((numberCoinsCollected * 2) + (numberTrapsDestroyed * 2) + (numberEnemiesDestroyed * 2) + (numberMeters / 10) + (scorePlus * 50) + (scoreMoins * (-25) )) * multiplier;
		scoreText.setText("Score: " + score);
	}
	
	private int checkMultiplier()
	{
		int twoMultiplierOneGame;
		int fiveMultiplierOneGame;
		int twoMultiplierFiveGame;
		int fiveMultiplierFiveGame;
		
		try 
		{
			twoMultiplierOneGame = Integer.parseInt(resourcesManager.read(MULTIPLICATION_TIME_2_SCORE_ONE_GAME_KEY));
			fiveMultiplierOneGame = Integer.parseInt(resourcesManager.read(MULTIPLICATION_TIME_5_SCORE_ONE_GAME_KEY));
			twoMultiplierFiveGame = Integer.parseInt(resourcesManager.read(MULTIPLICATION_TIME_2_SCORE_FIVE_GAME_KEY));
			fiveMultiplierFiveGame = Integer.parseInt(resourcesManager.read(MULTIPLICATION_TIME_5_SCORE_FIVE_GAME_KEY));
			
			if(fiveMultiplierFiveGame > 0)
			{
				int numberTime = (Integer.parseInt(resourcesManager.read(MULTIPLICATION_TIME_5_SCORE_FIVE_GAME_KEY))) - 1;
				resourcesManager.write(MULTIPLICATION_TIME_5_SCORE_FIVE_GAME_KEY, Integer.toString(numberTime));
				
				return 5;
			}
			else if(fiveMultiplierOneGame > 0)
			{
				int numberTime = (Integer.parseInt(resourcesManager.read(MULTIPLICATION_TIME_5_SCORE_ONE_GAME_KEY))) - 1;
				resourcesManager.write(MULTIPLICATION_TIME_5_SCORE_ONE_GAME_KEY, Integer.toString(numberTime));
				
				return 5;
			}
			else if(twoMultiplierFiveGame > 0)
			{
				int numberTime = (Integer.parseInt(resourcesManager.read(MULTIPLICATION_TIME_2_SCORE_FIVE_GAME_KEY))) - 1;
				resourcesManager.write(MULTIPLICATION_TIME_2_SCORE_FIVE_GAME_KEY, Integer.toString(numberTime));
				
				return 2;
			}
			else if(twoMultiplierOneGame > 0)
			{
				int numberTime = (Integer.parseInt(resourcesManager.read(MULTIPLICATION_TIME_2_SCORE_ONE_GAME_KEY))) - 1;
				resourcesManager.write(MULTIPLICATION_TIME_2_SCORE_ONE_GAME_KEY, Integer.toString(numberTime));
				
				return 2;
			}
			else
				return 1;
		} 
		catch (IOException e) 
		{
			return 1;
		}
		
	}
	
	private int checkCoinsMultiplier()
	{
		int twoMultiplierOneGame;
		int fiveMultiplierOneGame;
		int twoMultiplierFiveGame;
		int fiveMultiplierFiveGame;
		
		try 
		{
			twoMultiplierOneGame = Integer.parseInt(resourcesManager.read(MULTIPLICATION_TIME_2_COINS_ONE_GAME_KEY));
			fiveMultiplierOneGame = Integer.parseInt(resourcesManager.read(MULTIPLICATION_TIME_5_COINS_ONE_GAME_KEY));
			twoMultiplierFiveGame = Integer.parseInt(resourcesManager.read(MULTIPLICATION_TIME_2_COINS_FIVE_GAME_KEY));
			fiveMultiplierFiveGame = Integer.parseInt(resourcesManager.read(MULTIPLICATION_TIME_5_COINS_FIVE_GAME_KEY));
			
			if(fiveMultiplierFiveGame > 0)
			{
				int numberTime = (Integer.parseInt(resourcesManager.read(MULTIPLICATION_TIME_5_COINS_FIVE_GAME_KEY))) - 1;
				resourcesManager.write(MULTIPLICATION_TIME_5_COINS_FIVE_GAME_KEY, Integer.toString(numberTime));
				
				return 5;
			}
			else if(fiveMultiplierOneGame > 0)
			{
				int numberTime = (Integer.parseInt(resourcesManager.read(MULTIPLICATION_TIME_5_COINS_ONE_GAME_KEY))) - 1;
				resourcesManager.write(MULTIPLICATION_TIME_5_COINS_ONE_GAME_KEY, Integer.toString(numberTime));
				
				return 5;
			}
			else if(twoMultiplierFiveGame > 0)
			{
				int numberTime = (Integer.parseInt(resourcesManager.read(MULTIPLICATION_TIME_2_COINS_FIVE_GAME_KEY))) - 1;
				resourcesManager.write(MULTIPLICATION_TIME_2_COINS_FIVE_GAME_KEY, Integer.toString(numberTime));
				
				return 2;
			}
			else if(twoMultiplierOneGame > 0)
			{
				int numberTime = (Integer.parseInt(resourcesManager.read(MULTIPLICATION_TIME_2_COINS_ONE_GAME_KEY))) - 1;
				resourcesManager.write(MULTIPLICATION_TIME_2_COINS_ONE_GAME_KEY, Integer.toString(numberTime));
				
				return 2;
			}
			else
				return 1;
			
		} 
		catch (IOException e) 
		{
			return 1;
		}
		
		
	}
	
	private boolean checkFirePower()
	{
		int oneGame;
		int fiveGame;
		
		try 
		{
			oneGame = Integer.parseInt(resourcesManager.read(FIRE_POWER_ONE_GAME));
			fiveGame = Integer.parseInt(resourcesManager.read(FIRE_POWER_FIVE_GAME));
			
			if(fiveGame > 0)
			{
				int numberTime = (Integer.parseInt(resourcesManager.read(FIRE_POWER_FIVE_GAME))) - 1;
				resourcesManager.write(FIRE_POWER_FIVE_GAME, Integer.toString(numberTime));
				
				return true;
			}
			else if(oneGame > 0)
			{
				int numberTime = (Integer.parseInt(resourcesManager.read(FIRE_POWER_ONE_GAME))) - 1;
				resourcesManager.write(FIRE_POWER_ONE_GAME, Integer.toString(numberTime));
				
				return true;
			}
			else
				return false;
		}
		catch (NumberFormatException e) 
		{
			return false;
		} 
		catch (IOException e) 
		{
			return false;
		}		
	}
	
	private void createPhysics()
	{
		physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, -17), false); 
		physicsWorld.setContactListener(contactListener());
		registerUpdateHandler(physicsWorld);
	}
	
	// ---------------------------------------------
	// INTERNAL CLASSES
	// ---------------------------------------------
	
	private ContactListener contactListener()
	{
		ContactListener contactListener = new ContactListener()
		{
			public void beginContact(Contact contact)
			{
				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();
				
				//JUMP
				if (x1.getBody().getUserData() != null && x2.getBody().getUserData() != null)
				{
					if (x2.getBody().getUserData().equals("player"))
					{
							player.increaseFootContacts();
					}
					else if (x1.getBody().getUserData().equals("player"))
					{
							player.increaseFootContacts();
					}
					
					
					//RED PLATFORM FALLING
					if (x1.getBody().getUserData().equals("platform2") && x2.getBody().getUserData().equals("player"))
					{
						engine.registerUpdateHandler(new TimerHandler(0.2f, new ITimerCallback()
						{									
						    public void onTimePassed(final TimerHandler pTimerHandler)
						    {
						    	pTimerHandler.reset();
						    	engine.unregisterUpdateHandler(pTimerHandler);
						    	x1.getBody().setType(BodyType.DynamicBody);
						    }
						}));
					}
					else if (x2.getBody().getUserData().equals("platform2") && x1.getBody().getUserData().equals("player"))
					{
						engine.registerUpdateHandler(new TimerHandler(0.2f, new ITimerCallback()
						{									
						    public void onTimePassed(final TimerHandler pTimerHandler)
						    {
						    	pTimerHandler.reset();
						    	engine.unregisterUpdateHandler(pTimerHandler);
						    	x2.getBody().setType(BodyType.DynamicBody);
						    }
						}));
					}
					
					//YELLOW FALLING
					if (x1.getBody().getUserData().equals("platform3") && x2.getBody().getUserData().equals("player"))
					{
						x1.getBody().setType(BodyType.DynamicBody);
					}
					else if (x2.getBody().getUserData().equals("platform3") && x1.getBody().getUserData().equals("player"))
					{
						x2.getBody().setType(BodyType.DynamicBody);
					}
					
					//------------------------------------------------
					//BULLET COLLISIONS
					//------------------------------------------------
					if(x1.getBody().getUserData().equals("bullet") && x2.getBody().getUserData().equals("plateform1"))
					{
						x1.getBody().setType(BodyType.StaticBody);
					}
					else if(x2.getBody().getUserData().equals("bullet") && x1.getBody().getUserData().equals("platform1"))
					{
						x2.getBody().setType(BodyType.StaticBody);
					} 
					if(x1.getBody().getUserData().equals("bullet") && x2.getBody().getUserData().equals("plateform2"))
					{
						x1.getBody().setType(BodyType.StaticBody);
					}
					else if(x2.getBody().getUserData().equals("bullet") && x1.getBody().getUserData().equals("platform2"))
					{
						x2.getBody().setType(BodyType.StaticBody);
					} 
					if(x1.getBody().getUserData().equals("bullet") && x2.getBody().getUserData().equals("plateform3"))
					{
						x1.getBody().setType(BodyType.StaticBody);
					}
					else if(x2.getBody().getUserData().equals("bullet") && x1.getBody().getUserData().equals("platform3"))
					{
						x2.getBody().setType(BodyType.StaticBody);
					} 
					if(x1.getBody().getUserData().equals("bullet") && x2.getBody().getUserData().equals("enemy1"))
					{
						x1.getBody().setType(BodyType.StaticBody);
						x2.getBody().setType(BodyType.StaticBody);
					}
					else if(x1.getBody().getUserData().equals("enemy1") && x2.getBody().getUserData().equals("bullet"))
					{
						x2.getBody().setType(BodyType.StaticBody);
						x1.getBody().setType(BodyType.StaticBody);
					}
					if(x1.getBody().getUserData().equals("bullet") && x2.getBody().getUserData().equals("destructiblebloc"))
					{
						x1.getBody().setType(BodyType.StaticBody);
						x2.getBody().setUserData("hit");
					}
					else if(x2.getBody().getUserData().equals("bullet") && x1.getBody().getUserData().equals("destructiblebloc"))
					{
						x1.getBody().setUserData("hit");
						x2.getBody().setType(BodyType.StaticBody);
					} 
					
					//---------------------------------
					//TRAP
					//---------------------------------
					
					if (x1.getBody().getUserData().equals("player") && x2.getBody().getUserData().equals("trap"))
					{
						player.life--;
					}
					else if (x1.getBody().getUserData().equals("trap") && x2.getBody().getUserData().equals("player"))
					{
						player.life--;
					}
					
					if (x1.getBody().getUserData().equals("bullet") && x2.getBody().getUserData().equals("trap"))
					{
						x2.getBody().setType(BodyType.StaticBody);
						x1.getBody().setType(BodyType.StaticBody);						
					}
					else if (x1.getBody().getUserData().equals("trap") && x2.getBody().getUserData().equals("bullet"))
					{
						x1.getBody().setType(BodyType.StaticBody);
						x2.getBody().setType(BodyType.StaticBody);
					}
					
					//--------------------------------------
					//Enemy1
					//--------------------------------------
					if (x1.getBody().getUserData().equals("player") && x2.getBody().getUserData().equals("enemy1"))
					{
						player.life--;
						if(x1.getBody().getPosition().y - player.getHeight() < x2.getBody().getPosition().y)
						{
							x1.getBody().setLinearVelocity( new Vector2(0,0));
							x2.getBody().setLinearVelocity( new Vector2(0,0));
						}
					}
					else if (x1.getBody().getUserData().equals("enemy1") && x2.getBody().getUserData().equals("player"))
					{
						player.life--;
						if(x2.getBody().getPosition().y - player.getHeight() < x1.getBody().getPosition().y)
						{
							x2.getBody().setLinearVelocity( new Vector2(0,0));
							x1.getBody().setLinearVelocity( new Vector2(0,0));
						}
					}
					//BALANCE
					if(x1.getBody().getUserData().equals("line") && x2.getBody().getUserData().equals("player"))
					{
						updateBalance = true;
						displayEquilibrium();
					}
					else if(x1.getBody().getUserData().equals("player") && x2.getBody().getUserData().equals("line"))
					{
						updateBalance = true;
						displayEquilibrium();
					}
				}
			}

			public void endContact(Contact contact)
			{
				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();

				if (x1.getBody().getUserData() != null && x2.getBody().getUserData() != null)
				{
					//JUMP
					if (x2.getBody().getUserData().equals("player"))
					{
						player.decreaseFootContacts();
					}
					else if (x1.getBody().getUserData().equals("player"))
					{
						player.decreaseFootContacts();
					}
					
					//COLLIDES PLAYER ENEMY1
					if (x1.getBody().getUserData().equals("player") && x2.getBody().getUserData().equals("enemy1"))
					{
						player.canRun = true;
						x2.getBody().setLinearVelocity( new Vector2(0,0));
						
					}
					else if (x1.getBody().getUserData().equals("enemy1") && x2.getBody().getUserData().equals("player"))
					{
						player.canRun = true;
						x1.getBody().setLinearVelocity( new Vector2(0,0));
					}
					
					//EQUILIBIRUM
					if(x1.getBody().getUserData().equals("line") && x2.getBody().getUserData().equals("player"))
					{
						removeEquilibrium();
						updateBalance = false;
					}
					else if(x1.getBody().getUserData().equals("player") && x2.getBody().getUserData().equals("line"))
					{
						removeEquilibrium();
						updateBalance = false;
					}
					
					
				}
			}

			public void preSolve(Contact contact, Manifold oldManifold)
			{

			}

			public void postSolve(Contact contact, ContactImpulse impulse)
			{

			}
		};
		return contactListener;
	}

	private void InitializeGameStats()
	{
		numberCoinsCollected = 0;
		numberEnemiesDestroyed = 0;
		numberJumps = 0;
		numberMeters = 0;
		numberScenesLoaded = 1;
		numberTrapsDestroyed = 0;
		score = 0;
	}
	private void InitializeBestStats()
	{
		try 
		{
			resourcesManager.read(BEST_NUMBER_COINS_COLLECTED_KEY);
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write(BEST_NUMBER_COINS_COLLECTED_KEY, "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try 
		{
			resourcesManager.read(BEST_NUMBER_ENEMIES_DESTROYED_KEY);
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write(BEST_NUMBER_ENEMIES_DESTROYED_KEY, "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try 
		{
			resourcesManager.read(BEST_NUMBER_JUMPS_KEY);
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write(BEST_NUMBER_JUMPS_KEY, "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try 
		{
			resourcesManager.read(BEST_NUMBER_KILOMETERS_KEY);
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write(BEST_NUMBER_KILOMETERS_KEY, "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try 
		{
			resourcesManager.read(BEST_NUMBER_SCENES_LOADED_KEY);
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write(BEST_NUMBER_SCENES_LOADED_KEY, "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try
		{
			resourcesManager.read(BEST_NUMBER_TRAPS_DESTROYED_KEY);
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write(BEST_NUMBER_TRAPS_DESTROYED_KEY, "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try 
		{
			resourcesManager.read(BEST_SCORE_KEY);
		} 
		catch (IOException e)
		{
			try {resourcesManager.write(BEST_SCORE_KEY, "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
	}
	private void InitializedGlobal()
	{
		try 
		{
			resourcesManager.read(GLOBAL_NUMBER_COINS_COLLECTED_KEY);
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write(GLOBAL_NUMBER_COINS_COLLECTED_KEY, "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try 
		{
			resourcesManager.read(GLOBAL_NUMBER_ENEMIES_DESTROYED_KEY);
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write(GLOBAL_NUMBER_ENEMIES_DESTROYED_KEY, "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try 
		{
			resourcesManager.read(GLOBAL_NUMBER_JUMPS_KEY);
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write(GLOBAL_NUMBER_JUMPS_KEY, "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try 
		{
			resourcesManager.read(GLOBAL_NUMBER_KILOMETERS_KEY);
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write(GLOBAL_NUMBER_KILOMETERS_KEY, "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try 
		{
			resourcesManager.read(GLOBAL_NUMBER_SCENES_LOADED_KEY);
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write(GLOBAL_NUMBER_SCENES_LOADED_KEY, "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try
		{
			resourcesManager.read(GLOBAL_NUMBER_TRAPS_DESTROYED_KEY);
		} 
		catch (IOException e) 
		{
			try {resourcesManager.write(GLOBAL_NUMBER_TRAPS_DESTROYED_KEY, "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try 
		{
			resourcesManager.read(GLOBAL_SCORE_KEY);
		} 
		catch (IOException e)
		{
			try {resourcesManager.write(GLOBAL_SCORE_KEY, "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
		try 
		{
			resourcesManager.read(GLOBAL_NUMBER_GAMES_PLAYED_KEY);
		} 
		catch (IOException e)
		{
			try {resourcesManager.write(GLOBAL_NUMBER_GAMES_PLAYED_KEY, "0");} 
			catch (IOException e1) {e1.printStackTrace();}
		}
	}
	private void InitializedBonus() 
	{
		//LIFE
		
		//GLOBAL
		try {resourcesManager.read(ONE_LIFE_GLOBAL_KEY);} catch (IOException e) {try 
		{resourcesManager.write(ONE_LIFE_GLOBAL_KEY, "0");} catch (IOException e1) {e1.printStackTrace();}}
		try {resourcesManager.read(TWO_LIVES_GLOBAL_KEY);} catch (IOException e) {try 
		{resourcesManager.write(TWO_LIVES_GLOBAL_KEY, "0");} catch (IOException e1) {e1.printStackTrace();}}
		
		//FIVE GAME
		try {resourcesManager.read(ONE_LIFE_FIVE_GAME_KEY);} catch (IOException e) {try 
		{resourcesManager.write(ONE_LIFE_FIVE_GAME_KEY, "0");} catch (IOException e1) {e1.printStackTrace();}}
		try {resourcesManager.read(TWO_LIVES_FIVE_GAME_KEY);} catch (IOException e) {try 
		{resourcesManager.write(TWO_LIVES_FIVE_GAME_KEY, "0");} catch (IOException e1) {e1.printStackTrace();}}
		
		//ONE GAME
		try {resourcesManager.read(ONE_LIFE_ONE_GAME_KEY);} catch (IOException e) {try 
		{resourcesManager.write(ONE_LIFE_ONE_GAME_KEY, "0");} catch (IOException e1) {e1.printStackTrace();}}
		try {resourcesManager.read(TWO_LIVES_ONE_GAME_KEY);} catch (IOException e) {try 
		{resourcesManager.write(TWO_LIVES_ONE_GAME_KEY, "0");} catch (IOException e1) {e1.printStackTrace();}}
		
		
		//COINS
		
		//FIVE GAME
		try {resourcesManager.read(MULTIPLICATION_TIME_2_COINS_FIVE_GAME_KEY);} catch (IOException e) {try 
		{resourcesManager.write(MULTIPLICATION_TIME_2_COINS_FIVE_GAME_KEY, "0");} catch (IOException e1) {e1.printStackTrace();}}
		try {resourcesManager.read(MULTIPLICATION_TIME_5_COINS_FIVE_GAME_KEY);} catch (IOException e) {try 
		{resourcesManager.write(MULTIPLICATION_TIME_5_COINS_FIVE_GAME_KEY, "0");} catch (IOException e1) {e1.printStackTrace();}}
		
		//ONE GAME
		try {resourcesManager.read(MULTIPLICATION_TIME_2_COINS_ONE_GAME_KEY);} catch (IOException e) {try 
		{resourcesManager.write(MULTIPLICATION_TIME_2_COINS_ONE_GAME_KEY, "0");} catch (IOException e1) {e1.printStackTrace();}}
		try {resourcesManager.read(MULTIPLICATION_TIME_5_COINS_ONE_GAME_KEY);} catch (IOException e) {try 
		{resourcesManager.write(MULTIPLICATION_TIME_5_COINS_ONE_GAME_KEY, "0");} catch (IOException e1) {e1.printStackTrace();}}
		
		
		//SCORE
		
		//FIVE GAME
		try {resourcesManager.read(MULTIPLICATION_TIME_2_SCORE_FIVE_GAME_KEY);} catch (IOException e) {try 
		{resourcesManager.write(MULTIPLICATION_TIME_2_SCORE_FIVE_GAME_KEY, "0");} catch (IOException e1) {e1.printStackTrace();}}
		try {resourcesManager.read(MULTIPLICATION_TIME_5_SCORE_FIVE_GAME_KEY);} catch (IOException e) {try 
		{resourcesManager.write(MULTIPLICATION_TIME_5_SCORE_FIVE_GAME_KEY, "0");} catch (IOException e1) {e1.printStackTrace();}}
		
		//ONE GAME
		try {resourcesManager.read(MULTIPLICATION_TIME_2_SCORE_ONE_GAME_KEY);} catch (IOException e) {try 
		{resourcesManager.write(MULTIPLICATION_TIME_2_SCORE_ONE_GAME_KEY, "0");} catch (IOException e1) {e1.printStackTrace();}}
		try {resourcesManager.read(MULTIPLICATION_TIME_5_SCORE_ONE_GAME_KEY);} catch (IOException e) {try 
		{resourcesManager.write(MULTIPLICATION_TIME_5_SCORE_ONE_GAME_KEY, "0");} catch (IOException e1) {e1.printStackTrace();}}
		
		//PLAYER'S POWER FIRE
		
		//ONE GAME
		try {resourcesManager.read(FIRE_POWER_ONE_GAME);} catch (IOException e) {try 
		{resourcesManager.write(FIRE_POWER_ONE_GAME, "0");} catch (IOException e1) {e1.printStackTrace();}}
		try {resourcesManager.read(FIRE_POWER_FIVE_GAME);} catch (IOException e) {try 
		{resourcesManager.write(FIRE_POWER_FIVE_GAME, "0");} catch (IOException e1) {e1.printStackTrace();}}
	}
	private void CheckBestStats() throws NumberFormatException, IOException
	{
		//Coins Collected
		if(numberCoinsCollected > Integer.parseInt(resourcesManager.read(BEST_NUMBER_COINS_COLLECTED_KEY)))
		{
			resourcesManager.write(BEST_NUMBER_COINS_COLLECTED_KEY, Integer.toString(numberCoinsCollected));
		}
		//Enemies Destroyed
		if(numberEnemiesDestroyed > Integer.parseInt(resourcesManager.read(BEST_NUMBER_ENEMIES_DESTROYED_KEY)))
		{
			resourcesManager.write(BEST_NUMBER_ENEMIES_DESTROYED_KEY, Integer.toString(numberEnemiesDestroyed));
		}
		//Number Jumps
		if(numberJumps > Integer.parseInt(resourcesManager.read(BEST_NUMBER_JUMPS_KEY)))
		{
			resourcesManager.write(BEST_NUMBER_JUMPS_KEY, Integer.toString(numberJumps));
		}
		//Number Kilometers
		if(numberMeters > Float.parseFloat(resourcesManager.read(BEST_NUMBER_KILOMETERS_KEY)))
		{
			resourcesManager.write(BEST_NUMBER_KILOMETERS_KEY, Float.toString(numberMeters));
		}
		//Scenes Loaded
		if(numberScenesLoaded > Integer.parseInt(resourcesManager.read(BEST_NUMBER_SCENES_LOADED_KEY)))
		{
			resourcesManager.write(BEST_NUMBER_SCENES_LOADED_KEY, Integer.toString(numberScenesLoaded));
		}
		//Traps destroyed
		if(numberTrapsDestroyed > Integer.parseInt(resourcesManager.read(BEST_NUMBER_TRAPS_DESTROYED_KEY)))
		{
			resourcesManager.write(BEST_NUMBER_TRAPS_DESTROYED_KEY, Integer.toString(numberTrapsDestroyed));
		}
		//Score
		if(score > Integer.parseInt(resourcesManager.read(BEST_SCORE_KEY)))
		{
			resourcesManager.write(BEST_SCORE_KEY, Integer.toString(score));
		}
	}
	private void UpdateGlobatStats() throws IOException
	{
		//COINS
		final int getCoins = Integer.parseInt(resourcesManager.read(GLOBAL_NUMBER_COINS_COLLECTED_KEY));
		resourcesManager.write(GLOBAL_NUMBER_COINS_COLLECTED_KEY, Integer.toString(getCoins + numberCoinsCollected));
		//ENEMIES DESTROYED
		final int getEnemiesDestroyed = Integer.parseInt(resourcesManager.read(GLOBAL_NUMBER_ENEMIES_DESTROYED_KEY));
		resourcesManager.write(GLOBAL_NUMBER_ENEMIES_DESTROYED_KEY, Integer.toString(getEnemiesDestroyed + numberEnemiesDestroyed));
		//GAME PLAYED
		final int getGamePlayed = Integer.parseInt(resourcesManager.read(GLOBAL_NUMBER_GAMES_PLAYED_KEY));
		resourcesManager.write(GLOBAL_NUMBER_GAMES_PLAYED_KEY, Integer.toString(getGamePlayed + 1));
		//JUMPS
		final int getJumps = Integer.parseInt(resourcesManager.read(GLOBAL_NUMBER_JUMPS_KEY));
		resourcesManager.write(GLOBAL_NUMBER_JUMPS_KEY, Integer.toString(getJumps + numberJumps));
		//KILOMETERS
		final float getMeters = Float.parseFloat(resourcesManager.read(GLOBAL_NUMBER_KILOMETERS_KEY));
		resourcesManager.write(GLOBAL_NUMBER_KILOMETERS_KEY, Float.toString(getMeters + numberMeters));
		//SCENES
		final int getScenesLoaded = Integer.parseInt(resourcesManager.read(GLOBAL_NUMBER_SCENES_LOADED_KEY));
		resourcesManager.write(GLOBAL_NUMBER_SCENES_LOADED_KEY, Integer.toString(getScenesLoaded + numberScenesLoaded));
		//TRAPS DESTROYED
		final int getTrapsDestroyed = Integer.parseInt(resourcesManager.read(GLOBAL_NUMBER_TRAPS_DESTROYED_KEY));
		resourcesManager.write(GLOBAL_NUMBER_TRAPS_DESTROYED_KEY, Integer.toString(getTrapsDestroyed + numberTrapsDestroyed));
		//SCORE
		final int getScore = Integer.parseInt(resourcesManager.read(GLOBAL_SCORE_KEY));
		resourcesManager.write(GLOBAL_SCORE_KEY, Integer.toString(getScore + score));
	}

	private void pauseMenu()
	{
		MenuScene pauseScreen = new MenuScene(camera);

		play = new Sprite(360, 240, 50, 50, resourcesManager.playRegion, vbom);
		playRe = new Rectangle(360, 240, 50, 50, vbom)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
				if(pSceneTouchEvent.isActionDown())
					resumeGame();
				return true;
			};
		};
		final Sprite fond = new Sprite(780 / 2, 480 / 2, resourcesManager.fondRegion, vbom);
		
		pauseScreen.registerTouchArea(playRe);
		pauseScreen.attachChild(fond);
		pauseScreen.attachChild(play);
				
		pauseScreen.setBackgroundEnabled(false);
		setChildScene(pauseScreen, false, true, true); 
	}

	private void resumeGame() 
	{
		clearChildScene();
	}
	
}
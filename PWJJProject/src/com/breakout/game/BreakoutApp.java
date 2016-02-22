package com.breakout.game;



import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import com.almasb.fxgl.GameApplication;
import com.almasb.fxgl.GameSettings;
import com.almasb.fxgl.asset.Assets;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityType;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsEntity;
import com.almasb.fxgl.physics.PhysicsManager;
import com.breakout.menu.DecWindow;
import com.breakout.menu.Menu;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;

public class BreakoutApp extends GameApplication 
{
	private Assets assets;
	private PhysicsEntity desk, desk2, ball, ball2, brick;
	private IntegerProperty score = new SimpleIntegerProperty();
	private Boolean flagaBall = true;
	private Boolean flagaBall2 = true;
	private Integer scoreHolder;
	
	private enum Type implements EntityType
	{
		BALL, BRICK, DESK, SCREEN, BORDER;
	}

	@Override
	protected void initSettings(GameSettings settings) 
	{
		settings.setTitle("Block Blaster");
		settings.setVersion("1.0");
		settings.setWidth(640);
		settings.setHeight(960);
		settings.setIntroEnabled(false);
	}
	
	@Override
	protected void initAssets() throws Exception 
	{
		assets = assetManager.cache();
		assets.logCached();
	}
	
	@Override
	protected void initGame() 
	{
		physicsManager.setGravity(0, 0);
		
		initScreenBounds();
		initBall();
		initBall2();
		initDesk();
		initBrick();
		initDesk2();
		
		physicsManager.addCollisionHandler(new CollisionHandler(Type.BALL, Type.BRICK)
		{

			@Override
			public void onCollisionBegin(Entity a, Entity b) 
			{
				removeEntity(b);
				score.set(score.get() + 100);
			}

			@Override
			public void onCollision(Entity a, Entity b){}
	
			@Override
			public void onCollisionEnd(Entity a, Entity b){}
			
		});
		
		physicsManager.addCollisionHandler(new CollisionHandler(Type.DESK, Type.SCREEN)
		{

			@Override
			public void onCollisionBegin(Entity a, Entity b) 
			{
				
			}

			@Override
			public void onCollision(Entity a, Entity b)
			{
				
			}
	
			@Override
			public void onCollisionEnd(Entity a, Entity b){}
			
		});
		
		physicsManager.addCollisionHandler(new CollisionHandler(Type.BALL, Type.BORDER)
		{

			@Override
			public void onCollisionBegin(Entity a, Entity b) 
			{
				removeEntity(a);
				if(flagaBall) flagaBall = false;
				else flagaBall2 = false;
						
			}

			@Override
			public void onCollision(Entity a, Entity b){}
	
			@Override
			public void onCollisionEnd(Entity a, Entity b){}	
		});
	}
	
	private void initScreenBounds()
	{
		PhysicsEntity top = new PhysicsEntity(Type.BORDER);
		top.setPosition(0, 30);
		top.setGraphics(new Rectangle(getWidth(), 10));
		top.setCollidable(true);
		
		PhysicsEntity bottom = new PhysicsEntity(Type.BORDER);
		bottom.setPosition(0, getHeight());
		bottom.setGraphics(new Rectangle(getWidth(), 10));
		bottom.setCollidable(true);
		
		PhysicsEntity left = new PhysicsEntity(Type.SCREEN);
		left.setPosition(-10, 0);
		left.setGraphics(new Rectangle(10, getHeight()));
		left.setCollidable(true);

		PhysicsEntity right = new PhysicsEntity(Type.SCREEN);
		right.setPosition(getWidth(), 0);
		right.setGraphics(new Rectangle(10, getHeight()));

		right.setCollidable(true);

		addEntities(top, bottom, left, right);
	}
	
	private void initBall()
	{
		ball = new PhysicsEntity(Type.BALL);
		ball.setPosition(getWidth()/2 -30/2, getHeight()/2 + 120);
		ball.setGraphics(assets.getTexture("ball.png"));
		ball.setBodyType(BodyType.DYNAMIC);
		ball.setCollidable(true);
		flagaBall = true;
		FixtureDef fd = new FixtureDef();
		fd.restitution = 0.8f;
		fd.shape = new CircleShape();
		fd.shape.setRadius(PhysicsManager.toMeters(15));
		ball.setFixtureDef(fd);
		
		addEntities(ball);
		
		ball.setLinearVelocity(5, -5);
	}
	
	private void initBall2()
	{
		ball2 = new PhysicsEntity(Type.BALL);
		ball2.setPosition(getWidth()/2 -30/2, getHeight()/2 - 200);
		ball2.setGraphics(assets.getTexture("ball2.png"));
		ball2.setBodyType(BodyType.DYNAMIC);
		ball2.setCollidable(true);
		flagaBall2 = true;
		FixtureDef fd = new FixtureDef();
		fd.restitution = 0.8f;
		fd.shape = new CircleShape();
		fd.shape.setRadius(PhysicsManager.toMeters(15));
		ball2.setFixtureDef(fd);
		
		addEntities(ball2);
		
		ball2.setLinearVelocity(-5, 5);
	}
	
	private void initDesk()
	{
		desk = new PhysicsEntity(Type.DESK);
		desk.setPosition(getWidth()/2 - 128/2, getHeight() - 25);
		desk.setGraphics(assets.getTexture("desk.png"));
		desk.setBodyType(BodyType.DYNAMIC);
		
		addEntities(desk);
	}
	
	private void initDesk2()
	{
		desk2 = new PhysicsEntity(Type.DESK);
		desk2.setPosition(getWidth()/2 - 128/2, 40);
		desk2.setGraphics(assets.getTexture("desk2.png"));
		desk2.setBodyType(BodyType.DYNAMIC);

		addEntities(desk2);
	}
	
	private void initBrick()
	{
		for(int i = 0; i < 48; i++)
		{
			brick = new PhysicsEntity(Type.BRICK);
			brick.setPosition((i%16) * 40, ((i/16)+10) * 40);
			brick.setGraphics(assets.getTexture("brick.png"));
			brick.setCollidable(true);
			
			addEntities(brick);
		}
	}
	@Override
	protected void initUI(Pane uiRoot) 
	{
		Text scoreText = new Text();
		scoreText.setTranslateY(20);
		scoreText.setFont(Font.font(18));
		scoreText.setText("Wynik: ");
		scoreText.textProperty().bind(score.asString());
		uiRoot.getChildren().add(scoreText);	
	}

	@Override
	protected void initInput() 
	{
		inputManager.addKeyPressBinding(KeyCode.A, () -> {
			desk.setLinearVelocity(-7, 0);
		});
		
		inputManager.addKeyPressBinding(KeyCode.D, () -> {
			desk.setLinearVelocity(7, 0);
		});
		
		inputManager.addKeyPressBinding(KeyCode.N, () -> {
			desk2.setLinearVelocity(-7, 0);
		});
		
		inputManager.addKeyPressBinding(KeyCode.M, () -> {
			desk2.setLinearVelocity(7, 0);
		});
		
	}
	private void onRestart()
	{
		Platform.runLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					DecWindow dec = new DecWindow();
					dec.start(new Stage());
					
				} 
				catch (Exception e)
				{
					System.err.println(e);
				}
			}
		});

		/*removeEntity(desk);
		removeEntity(desk2);
		removeEntity(brick);
		initDesk();
		initDesk2();
		initBall();
		initBall2();
		initBrick();
		score.set(0);*/

		super.mainStage.hide();

		
	}
	@Override
	protected void onUpdate() 
	{
		desk.setLinearVelocity(0, 0);	
		desk2.setLinearVelocity(0, 0);
		
		Point2D v1 = ball.getLinearVelocity();
		if(Math.abs(v1.getY()) < 5)
		{
			double x = v1.getX();
			double signY = Math.signum(v1.getY());
			ball.setLinearVelocity(x, signY * 5);
		}
		Point2D v2 = ball2.getLinearVelocity();
		if(Math.abs(v2.getY()) < 5)
		{
			double x = v2.getX();
			double signY = Math.signum(v2.getY());
			ball2.setLinearVelocity(x, signY * 5);
		}
		
		if((!flagaBall && !flagaBall2) || score.getValue() == 4800)
		{	
				flagaBall = flagaBall2 = true;
				onRestart();
		}
	}
	
	public void shutdown()
	{
		
	}
	
	
}


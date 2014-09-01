package com.jiavan.rescue;

import java.util.Random;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.jiavan.rescue.userdata.UserDataBorder;
import com.jiavan.rescue.userdata.UserDataBox;
import com.jiavan.rescue.userdata.UserDataBullet;
import com.jiavan.rescue.userdata.UserDataEnemy;
import com.jiavan.rescue.userdata.UserDataMe;
import com.jiavan.rescue.userdata.UserDataRubbish;

public class MySurfaceView extends SurfaceView implements Callback, Runnable{

	private SurfaceHolder sfh;
	private Canvas canvas;
	private Paint paint, paintBlack;
	private Thread threadChangeUI;
	private boolean flag;
	public boolean isSendBullet;
	public int meDirectionStatus;
	public int rockerPosition;
	public float bigRockerX, bigRockerY, bigRockerR;
	public float smallRockerY, smallRockerX, smallRockerR;
	public float enemyPositionY;
	public AABB worldAABB;
	public World world;
	public Vec2 gravity;
	public Body bodyMe, bodyBorder, bodyEnemy;
	public Bitmap bmpMe, bmpEnemy, bmpSocks;
	public int timer;
	public float boxSpeed;
	public float screenW, screenH;
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.GRAY);
		paint.setAlpha(0x90);
		paint.setAntiAlias(true);
		paint.setTextSize(15);
		paintBlack = new Paint();
		paintBlack.setColor(Color.BLACK);
		paintBlack.setAntiAlias(true);
	
		initBox();
	}

	public void initBox(){
		worldAABB = new  AABB();
		worldAABB.lowerBound.set(-100f, -100f);
		worldAABB.upperBound.set(100f, 100f);
		
		gravity = new Vec2(0f, 10f);
		Constant.doSleep = true;
		
		world = new World(worldAABB, gravity, Constant.doSleep);
	}
	
	public void initTheGameData(){
		screenH = this.getHeight();
		screenW = this.getWidth();
		this.smallRockerX = 0.15f*screenW;
		this.smallRockerY = 0.75f*screenH;
		this.bigRockerX = 0.15f*screenW;
		this.bigRockerY = 0.75f*screenH;
		this.smallRockerR = 0.1f*screenH;
		this.bigRockerR = 0.25f*screenH;
		this.boxSpeed = 10f;
		this.enemyPositionY = 125;
		this.timer = 0;
		this.meDirectionStatus = Constant.LEFT;
		this.rockerPosition = Constant.CENTER;
	}
	
	public void createGameLogic(){

		bmpSocks = BitmapFactory.decodeResource(getResources(), R.drawable.scoks);
		bmpMe = BitmapFactory.decodeResource(getResources(), R.drawable.jiavan);
		//bmpEnemy = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
		bodyBorder = BodyFactory.createBorder(world, 0.3f*screenW, 0.83f*screenH, 0.60f*screenW, 0.042f*screenH);
		bodyMe = BodyFactory.createMe(world, 0.44f*screenW, 0.31f*screenH, 0.1f*screenH, 0.1f*screenH, bmpMe);
		bodyEnemy = BodyFactory.createEnemy(world, 0.91f*screenW, 0.21f*screenH, 0.1f*screenH, 0.1f*screenH, bmpEnemy);
	}

	public void bodyPositionLogic(){
		Body body = world.getBodyList();
		for(int i = 1; i < world.getBodyCount(); i++){
			if(body.m_userData instanceof UserDataMe){
				UserDataMe me = (UserDataMe) body.m_userData;
				me.setX(body.getPosition().x*Constant.RATE - 25);
				me.setY(body.getPosition().y*Constant.RATE - 25);
			}else if(body.m_userData instanceof UserDataBox){
				UserDataBox box = (UserDataBox) body.m_userData;
				float angle = (float)(body.getAngle()*180/Math.PI);
				box.setX(body.getPosition().x*Constant.RATE - box.getW()/2);
				box.setY(body.getPosition().y*Constant.RATE - box.getH()/2);
				box.setAngle(angle);
			}else if(body.m_userData instanceof UserDataBullet){
				UserDataBullet bullet = (UserDataBullet) body.m_userData;
				float angle = (float)(body.getAngle()*180/Math.PI);
				bullet.setX(body.getPosition().x*Constant.RATE - bullet.getW()/2);
				bullet.setY(body.getPosition().y*Constant.RATE - bullet.getH()/2);
				bullet.setAngle(angle);
			}else if(body.m_userData instanceof UserDataEnemy){
				UserDataEnemy enemy = (UserDataEnemy) body.m_userData;
				enemy.setX(body.getPosition().x*Constant.RATE - 25);
				enemy.setY(body.getPosition().y*Constant.RATE - 25);
			}else if(body.m_userData instanceof UserDataRubbish){
				UserDataRubbish rubbish = (UserDataRubbish)body.m_userData;
				rubbish.setX(body.getPosition().x*Constant.RATE - 25);
				rubbish.setY(body.getPosition().y*Constant.RATE - 25);
			}
			body = body.m_next;
		}
	}
	
	public void createADropBoxAnd1Bullet(){
		float x = (float)(new Random().nextInt((int)(0.4375f*screenW)) + 0.375f*screenW);
		float y = 10f;
		float angle = 0f;
		if(timer%80 == 0){
			BodyFactory.createABox(world, x, y, 0.145f*screenH, 0.145f*screenH, angle);
		}
		
		if(timer % 160 == 0){
			BodyFactory.createRubbish(world, 100, 50, 50, 50, 0, bmpSocks).setLinearVelocity(new Vec2(10f, 0));
		}
		
		if(isSendBullet){
			if(meDirectionStatus == Constant.RIGHT){
				Body b = BodyFactory.createBullet(world, bodyMe.getPosition().x*Constant.RATE + 0.1f*screenH, bodyMe.getPosition().y*Constant.RATE, 6, 2, 0);
				b.applyForce(new Vec2(500000f, 0f), b.getWorldCenter());
			}else{
				Body b = BodyFactory.createBullet(world, bodyMe.getPosition().x*Constant.RATE - 0.1f*screenH, bodyMe.getPosition().y*Constant.RATE, 6, 2, 0);
				b.applyForce(new Vec2(-500000f, 0f), b.getWorldCenter());
			}
			isSendBullet = false;
		}
		timer++;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
			world.step(Constant.timeStep, Constant.iterations);
			long start = System.currentTimeMillis();
			createADropBoxAnd1Bullet();
			bodyPositionLogic();
			//theEnemyMoveLogic();
			myDraw();
			switch(getRockerPosition(smallRockerX, smallRockerY)){
				case Constant.UP : {
					if(bodyMe.getLinearVelocity().y <= 0 && bodyMe.getLinearVelocity().y >= -11){
						boxSpeed = boxSpeed + 0.1f;
						bodyMe.applyForce(new Vec2(0f, -0.01f), bodyMe.getWorldCenter()); 
						bodyMe.setLinearVelocity(new Vec2(0.0f, -boxSpeed));
					}else{
						boxSpeed = 10f;
						bodyMe.setLinearVelocity(new Vec2(0.0f, boxSpeed));
					}
				}break;
				case Constant.DOWN : {
					bodyMe.applyForce(new Vec2(0f, 5000f), bodyMe.getWorldCenter()); 
				}break;
				case Constant.LEFT : {
					if(bodyMe.getLinearVelocity().y == 0){
						bodyMe.setLinearVelocity(new Vec2(-10f, 0.01f));
						bodyMe.applyForce(new Vec2(-5000f, 0f), bodyMe.getWorldCenter());
					}else{
						bodyMe.setLinearVelocity(new Vec2(-10f, bodyMe.getLinearVelocity().y));
						bodyMe.applyForce(new Vec2(-5000f, 0f), bodyMe.getWorldCenter());
					}
				}break;
				case Constant.RIGHT : {
					if(bodyMe.getLinearVelocity().y == 0){
						bodyMe.setLinearVelocity(new Vec2(10f, 0.01f));
						bodyMe.applyForce(new Vec2(5000f, 0f), bodyMe.getWorldCenter());
					}else{
						bodyMe.setLinearVelocity(new Vec2(10f, bodyMe.getLinearVelocity().y));
						bodyMe.applyForce(new Vec2(5000f, 0f), bodyMe.getWorldCenter());
					}
				}break;
				default : {
					if(bodyMe.getLinearVelocity().x > 0){
						bodyMe.setLinearVelocity(new Vec2(0f, 0f));
					}else if(bodyMe.getLinearVelocity().x < 0){
						bodyMe.setLinearVelocity(new Vec2(0f, 0f));
					}
					if(bodyMe.getLinearVelocity().y < 0){
						bodyMe.setLinearVelocity(new Vec2(0f, 2f));
					}
				} break;
			}
			long end = System.currentTimeMillis();
			Log.i("FPS", GetFPS.FPS(start, end) + "");
			if(end - start < 50){
				try {
					Thread.sleep(50 - (end - start));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void myDraw(){
		try{
			canvas = sfh.lockCanvas();
			if(canvas != null){
				canvas.drawColor(Color.WHITE);
				canvas.drawCircle(bigRockerX, bigRockerY, bigRockerR, paint);
				canvas.drawCircle(smallRockerX, smallRockerY, smallRockerR, paint);
				
				rockerPosition = getRockerPosition(smallRockerX, smallRockerY);
				canvas.drawText("position " + rockerPosition, 30, 30, paint);
				
				Body body = world.getBodyList();
				for(int i = 1; i < world.getBodyCount(); i++){
					if(body.m_userData instanceof UserDataMe){
						UserDataMe me = (UserDataMe) body.m_userData;
						me.drawMe(canvas, paint, meDirectionStatus);
					}else if(body.m_userData instanceof UserDataBox){
						UserDataBox box = (UserDataBox) body.m_userData;
						box.drawBox(canvas, paint);
					}else if(body.m_userData instanceof UserDataBorder){
						UserDataBorder border = (UserDataBorder) body.m_userData;
						border.drawBorder(canvas, paint);
					}else if(body.m_userData instanceof UserDataBullet){
						UserDataBullet bullet = (UserDataBullet) body.m_userData;
						bullet.drawBullet(canvas, paintBlack);
					}else if(body.m_userData instanceof UserDataEnemy){
						UserDataEnemy enemy = (UserDataEnemy) body.m_userData;
						enemy.drawEnemy(canvas, paint);
					}else if(body.m_userData instanceof UserDataRubbish){
						UserDataRubbish rubbish = (UserDataRubbish)body.m_userData;
						rubbish.drawRubbish(canvas, paint);
					}
					body = body.m_next;
				}
			}
		}catch(Exception e){
		}finally{
			sfh.unlockCanvasAndPost(canvas);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//注意此处是屏幕点击的位置和大圆的半径之差
		if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
			if(Math.sqrt(Math.pow((event.getX() - bigRockerX), 2) + Math.pow(event.getY() - bigRockerY, 2)) > bigRockerR){
				smallRockerX = bigRockerX;
				smallRockerY = bigRockerY;
			}else{
				smallRockerX = event.getX();
				smallRockerY = event.getY();
			}
		}else{
			smallRockerX = bigRockerX;
			smallRockerY = bigRockerY;
		}
		
		if(event.getAction() == MotionEvent.ACTION_DOWN && event.getX() > 0.5*screenW){
			isSendBullet = true;
		}
		
		return true;
	}
	
	private int getRockerPosition(float eventX, float eventY){
		float x = eventX - bigRockerX;
		float y = eventY - bigRockerY;
		float z = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		float cosAngle = x / z;
		float rad = (float) Math.acos(cosAngle);
		if (eventY > bigRockerY) {  
            rad = -rad;  
        }
		float angle = (float)(rad*180/Math.PI);
		
		if(angle > 45 && angle < 135){
			return Constant.UP;
		}else if(angle > -45 && angle < 45){
			meDirectionStatus = Constant.RIGHT;
			return Constant.RIGHT;
		}else if(angle > -135 && angle < -45){
			return Constant.DOWN;
		}else if(angle < -135 || angle > 135){
			meDirectionStatus = Constant.LEFT;
			return Constant.LEFT;
		}else{
			return Constant.CENTER;
		}
	}

	public void theEnemyMoveLogic(){
		if((bodyEnemy.getPosition().y*Constant.RATE) <= 100){
			bodyEnemy.setXForm(new Vec2(0.91f*screenW, ++enemyPositionY), 0);
		}else if((bodyEnemy.getPosition().y*Constant.RATE) >= 400){
			bodyEnemy.setXForm(new Vec2(0.91f*screenW, --enemyPositionY), 0);
		}
		//bodyEnemy.applyForce(new Vec2(0f, -115.2f), bodyEnemy.getWorldCenter());
		//Log.i("TAG", bodyEnemy.getMass() + "");
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		initTheGameData();
		
		threadChangeUI = new Thread(this);
		flag = true;
		createGameLogic();
		threadChangeUI.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		flag = false;
	}
}

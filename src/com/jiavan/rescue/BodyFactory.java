package com.jiavan.rescue;

import org.jbox2d.collision.PolygonDef;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import com.jiavan.rescue.userdata.UserDataBorder;
import com.jiavan.rescue.userdata.UserDataBox;
import com.jiavan.rescue.userdata.UserDataBullet;
import com.jiavan.rescue.userdata.UserDataEnemy;
import com.jiavan.rescue.userdata.UserDataMe;
import com.jiavan.rescue.userdata.UserDataRubbish;

import android.graphics.Bitmap;

public class BodyFactory {
	public static Body createMe(World world, float x, float y, float w, float h, Bitmap bmp){
		PolygonDef shape = new PolygonDef();
		shape.density = 1f;
		shape.restitution = 0.5f;
		shape.friction = 0.5f;
		shape.setAsBox(w / 2 / Constant.RATE, h / 2 / Constant.RATE);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set((x + w/2) / Constant.RATE, (y + h/2) / Constant.RATE);
		
		Body body = world.createBody(bodyDef);
		body.m_userData = new UserDataMe(x, h, bmp);
		body.createShape(shape);
		body.setMassFromShapes();
		
		return body;
	}
	
	public static Body createBorder(World world, float x, float y, float w, float h){
		PolygonDef shape = new PolygonDef();
		shape.density = 0f;
		shape.restitution = 0.2f;
		shape.friction = 0.8f;
		shape.setAsBox(w / 2 / Constant.RATE, h / 2 / Constant.RATE);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set((x + w/2) / Constant.RATE, (y + h/2) / Constant.RATE);
		bodyDef.allowSleep = true;
		
		Body body = world.createBody(bodyDef);
		body.m_userData = new UserDataBorder(x, y, w, h);
		body.createShape(shape);
		body.setMassFromShapes();
		
		return body;
	}
	
	public static Body createABox(World world, float x, float y, float w, float h, float angle){
		PolygonDef shape = new PolygonDef();
		shape.density = 1f;
		shape.restitution = 0.2f;
		shape.friction = 0.5f;
		shape.setAsBox(w / 2 / Constant.RATE, h / 2 / Constant.RATE);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set((x + w/2) / Constant.RATE, (y + h/2) / Constant.RATE);
		bodyDef.allowSleep = true;
		bodyDef.isBullet = true;
		
		Body body = world.createBody(bodyDef);
		body.m_userData = new UserDataBox(x, y, w, h, angle);
		body.createShape(shape);
		body.setMassFromShapes();
		
		return body;
	}
	
	public static Body createBullet(World world, float x, float y, float w, float h, float angle){
		PolygonDef shape = new PolygonDef();
		shape.density = 5f;
		shape.restitution = 1f;
		shape.friction = 0f;
		shape.setAsBox(w / 2 / Constant.RATE, h / 2 / Constant.RATE);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set((x + w/2) / Constant.RATE, (y + h/2) / Constant.RATE);
		bodyDef.allowSleep = true;
		
		Body body = world.createBody(bodyDef);
		body.m_userData = new UserDataBullet(x, y, w, h, angle);
		body.createShape(shape);
		body.setMassFromShapes();
		
		return body;
	}
	
	public static Body createEnemy(World world, float x, float y, float w, float h, Bitmap bmp){
		PolygonDef shape = new PolygonDef();
		shape.density = 0.5f;
		shape.restitution = 0.5f;
		shape.friction = 0f;
		shape.setAsBox(w / 2 / Constant.RATE, h / 2 / Constant.RATE);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set((x + w/2) / Constant.RATE, (y + h/2) / Constant.RATE);
		
		Body body = world.createBody(bodyDef);
		body.m_userData = new UserDataEnemy(x, y, bmp);
		body.createShape(shape);
		body.setMassFromShapes();
		
		return body;
	}
	
	public static Body createRubbish(World world, float x, float y, float w, float h, float angle, Bitmap bmp){
		PolygonDef shape = new PolygonDef();
		shape.density = 1f;
		shape.restitution = 0.2f;
		shape.friction = 0.8f;
		shape.setAsBox(w / 2 / Constant.RATE, h / 2 / Constant.RATE);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set((x + w/2) / Constant.RATE, (y + h/2) / Constant.RATE);
		
		Body body = world.createBody(bodyDef);
		body.m_userData = new UserDataRubbish(x, y, angle, bmp);
		body.createShape(shape);
		body.setMassFromShapes();
		
		return body;
	}
}

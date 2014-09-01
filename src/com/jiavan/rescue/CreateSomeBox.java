package com.jiavan.rescue;

import java.util.Random;

import org.jbox2d.dynamics.World;

public class CreateSomeBox {
	private World world;
	private float x, y, angle;
	public CreateSomeBox(World world){
		this.world = world;
	}
	public void startCreate(){
		long time = System.currentTimeMillis();
		x = (float)(new Random().nextInt(350) + 300);
		y = 10f;
		angle = 0f;
		if(time%3000 == 0){
			BodyFactory.createABox(world, x, y, 50, 50, angle);
		}
	}
}

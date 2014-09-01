package com.jiavan.rescue.userdata;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class UserDataEnemy {
	private float x, y;
	private Bitmap bmp;
	
	public UserDataEnemy(float x, float y, Bitmap bmp){
		this.x = x;
		this.y = y;
		this.bmp = bmp;
	}
	
	public void drawEnemy(Canvas canvas, Paint paint){
		canvas.drawBitmap(bmp, x, y, paint);
	}
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Bitmap getBmp() {
		return bmp;
	}

	public void setBmp(Bitmap bmp) {
		this.bmp = bmp;
	}
}
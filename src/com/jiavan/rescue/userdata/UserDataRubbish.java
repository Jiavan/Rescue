package com.jiavan.rescue.userdata;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class UserDataRubbish {
	private float x, y, angle;
	private Bitmap bmp;
	
	public UserDataRubbish(float x, float y, float angle, Bitmap bmp){
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.bmp = bmp;
	}
	
	public void drawRubbish(Canvas canvas, Paint paint){
		canvas.save();
		canvas.rotate(angle);
		canvas.drawBitmap(bmp, x, y, paint);
		canvas.restore();
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
	
	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}
}
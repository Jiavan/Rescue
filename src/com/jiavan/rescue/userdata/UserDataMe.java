package com.jiavan.rescue.userdata;

import com.jiavan.rescue.Constant;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class UserDataMe {
	private float x, y;
	private Bitmap bmp;
	
	public UserDataMe(float x, float y, Bitmap bmp){
		this.x = x;
		this.y = y;
		this.bmp = bmp;
	}
	
	public void drawMe(Canvas canvas, Paint paint, int directions){
		if(directions == Constant.LEFT){
			canvas.drawBitmap(bmp, x, y, paint);
		}else{
			canvas.save();
			canvas.scale(-1, 1, x + bmp.getWidth()/2, y + bmp.getHeight()/2);
			canvas.drawBitmap(bmp, x, y, paint);
			canvas.restore();
		}
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
package com.jiavan.rescue.userdata;

import android.graphics.Canvas;
import android.graphics.Paint;

public class UserDataBorder {
	private float x, y, w, h;
	
	public UserDataBorder(float x, float y, float w, float h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void drawBorder(Canvas canvas, Paint paint){
		canvas.drawRect(x, y, x + w, y + h, paint);
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

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}
}

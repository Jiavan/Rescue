package com.jiavan.rescue.userdata;

import android.graphics.Canvas;
import android.graphics.Paint;

public class UserDataBox {
private float x, y, w, h, angle;
	
	public UserDataBox(float x, float y, float w, float h, float angle){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.angle = angle;
	}
	
	public void drawBox(Canvas canvas, Paint paint){
		canvas.save();
		canvas.rotate(angle, x + w/2, y + h/2);
		canvas.drawRect(x, y, x + w, y + h, paint);
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

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
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

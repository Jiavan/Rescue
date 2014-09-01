package com.jiavan.rescue;

public class GetFPS {
	public static int FPS(long start, long end){
		return (int)(1000/(end - start));
	}
}

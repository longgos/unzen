package com.unzen.common.core.study;

import java.util.Random;

import org.apache.hadoop.mapred.gethistory_jsp;
import org.mockito.internal.matchers.CompareTo;

public class Rectangle extends AabstractTest{
	
	private final int CHANGE1 = 123;

	private String width;
	
	private String height;
	
	public Rectangle(String width, String height){
		this.width = width;
		this.height = height;
	}

	public Rectangle() {
		// TODO Auto-generated constructor stub
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
		
	}
	
	public static void main(String[] args) {
		Integer a = 13;
		//System.out.println(a.toBinaryString(a));
		Boolean b = new Boolean(true);
		
		//System.out.println(b);
		
		for (int i = 0; i < 10; i++) {
			Random r = new Random();
			System.out.println(r.nextBoolean());
		}
	
		new Rectangle().themethod();
		new Rectangle().themethod(1);
		new Rectangle().themethod(1,2);
		new Rectangle().themethod(1,2,3,5,67,65756,7567,5);
	}
	
	
	public void themethod(int...s){
		super.a();
	}
}

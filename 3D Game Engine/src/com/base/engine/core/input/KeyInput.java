package com.base.engine.core.input;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyInput extends GLFWKeyCallback {	
	//private static Singleton instance;
	private static ArrayList<Integer> downKeys = new ArrayList<Integer>();	
	private static ArrayList<Integer> upKeys = new ArrayList<Integer>();	
	
	
	  // The GLFWKeyCallback class is an abstract method that
	  // can't be instantiated by itself and must instead be extended
	  // 
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
	    // TODO Auto-generated method stub
      if(action==GLFW_PRESS) {
      	downKeys.add(key);	
      } else if (action==GLFW_RELEASE){
      	upKeys.add(key);
      }
	}
	
	public static ArrayList<Integer> getUpKeys(){
		return upKeys;
	}
	
	public static ArrayList<Integer> getDownKeys(){
		return downKeys;
	}
	
	public static void clearUpKeys(){
		upKeys.clear();
	}
	public static void clearDownKeys(){
		downKeys.clear();
	}
}

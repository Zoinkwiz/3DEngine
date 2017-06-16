package com.base.engine.core.input;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseButtonInput extends GLFWMouseButtonCallback {
	private static ArrayList<Integer> downMouse = new ArrayList<Integer>();
	private static ArrayList<Integer> upMouse = new ArrayList<Integer>();
	@Override
	public void invoke(long window, int button, int action, int mods) {
		  if(action==GLFW_PRESS) {
	        	downMouse.add(button);
	        } else if (action==GLFW_RELEASE){
	        	upMouse.add(button);
	        }
	}
	
	public static ArrayList<Integer> getUpMouse() {
		return upMouse;
	}
	
	public static ArrayList<Integer> getDownMouse() {
		return downMouse;
	}
	
	public static void clearUpMouse() {
		upMouse.clear();
	}
	public static void clearDownMouse() {
		downMouse.clear();
	}
	
}

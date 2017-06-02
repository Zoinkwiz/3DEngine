package com.base.core;
import java.util.ArrayList;

import com.base.rendering.Window;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
	private static ArrayList<Integer> downMouse = new ArrayList<Integer>();
	private static ArrayList<Integer> upMouse = new ArrayList<Integer>();
	private static ArrayList<Integer> keysUp = new ArrayList<Integer>();
	private static ArrayList<Integer> keysDown = new ArrayList<Integer>();
	private static ArrayList<Integer> keysHeld = new ArrayList<Integer>();
	private static Vector2f currentMousePos = getMousePosition();
	public static void update() {
		keysHeld.remove(KeyInput.getUpKeys()); //Remove keys that have been let go
		keysUp = new ArrayList<Integer>(KeyInput.getUpKeys());
		KeyInput.clearUpKeys();

		keysDown = new ArrayList<Integer>(KeyInput.getDownKeys());
		KeyInput.clearDownKeys();
		
		keysHeld.removeAll(keysUp);
		keysHeld.addAll(keysDown);	
		
		upMouse = new ArrayList<Integer>(MouseButtonInput.getUpMouse());
		MouseButtonInput.clearUpMouse();
		
		downMouse = new ArrayList<Integer>(MouseButtonInput.getDownMouse());
		MouseButtonInput.clearDownMouse();
		
		currentMousePos = getMousePosition();
	}
	
	 
	public static boolean getKeyDown(int keyCode) {
		if(keysDown.contains(keyCode)) {
			return true;
		}
		return false;
	}
	
	public static boolean getKeyHeld(int keyCode) {
		if(keysHeld.contains(keyCode)) {
			return true;
		}
		return false;
	}
	
	public static boolean getKeyUp(int keyCode) {
		if(keysUp.contains(keyCode)) {
			return true;
		}
		return false;
	}
	
	
	
	public static boolean getMouseUp(int keyCode) {
		if(upMouse.contains(keyCode)) {
			return true;
		}
		return false;
	}
	
	public static boolean getMouseDown(int keyCode) {
		if(downMouse.contains(keyCode)) {
			return true;
		}
		return false;
	}
	
	public static Vector2f getMousePosition() {
		return MousePosInput.getMousePosition();
	}
	
	public static Vector2f getCurrentMousePosition() {
		return currentMousePos;
	}
	
	public static void setMousePosition(Vector2f pos) {
		MousePosInput.setMousePosition(pos);
		currentMousePos = pos;
	}
	
	public static void setMouseVisible(boolean visible) {
		if(visible) {
			glfwSetInputMode(Window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		} else {
			glfwSetInputMode(Window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
		}

		//glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}
}

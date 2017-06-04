package com.base.core;
//import static org.lwjgl.glfw.Callbacks.*;
//import static org.lwjgl.glfw.GLFW.*;


//import org.lwjgl.glfw.GLFWVidMode;
//import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.system.MemoryUtil.*;
public abstract class Game {
	private GameObject root;
	
	public void init() {
		
	}
	
	public void input(float delta) {
		getRootObject().input(delta);
	}
	
	public void update(float delta) {
		getRootObject().update(delta);
	}
	
	public GameObject getRootObject() {
		if(root==null) {
			root = new GameObject();
		}
		return root;
	}

}

package com.base.core;

import com.base.rendering.RenderingEngine;

//import static org.lwjgl.glfw.Callbacks.*;
//import static org.lwjgl.glfw.GLFW.*;


//import org.lwjgl.glfw.GLFWVidMode;
//import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.system.MemoryUtil.*;
public abstract class Game {
	private GameObject root = new GameObject();
	
	public void init() {}
	
	public void input(float delta) {
		getRootObject().input(delta);
	}
	
	public void update(float delta) {
		getRootObject().update(delta);
	}
	
	public void render(RenderingEngine renderingEngine) {
		renderingEngine.render(root);
	}
	
	public void addObject(GameObject object) {
		getRootObject().addChild(object);
	}
	
	private GameObject getRootObject() {
		if(root==null) {
			root = new GameObject();
		}
		return root;
	}

}

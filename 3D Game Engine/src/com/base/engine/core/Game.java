package com.base.engine.core;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import com.base.engine.core.input.Input;
import com.base.engine.rendering.RenderingEngine;

public abstract class Game {
	private GameObject root = new GameObject("root");
	
	public void init() {}
	
	public void input(float delta) {
		getRootObject().inputAll(delta);
	}
	
	public void update(float delta) {
		getRootObject().updateAll(delta);
	}
	
	public void render(RenderingEngine renderingEngine) {
		if(!Input.getKeyHeld(GLFW_KEY_SPACE)) {
		renderingEngine.render(root);
		}
	}
	
	public void addToScene(GameObject object) {
		getRootObject().addChild(object);
	}
	
	//TODO: Confirm works, WIP
	public void removeFromScene(GameObject object) {
		getRootObject().removeChild(object);
	}
	
	private GameObject getRootObject() {
		if(root==null) {
			root = new GameObject();
		}
		return root;
	}

	public void setEngine(CoreEngine engine) { getRootObject().setEngine(engine); }
}

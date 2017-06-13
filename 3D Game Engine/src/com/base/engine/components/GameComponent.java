package com.base.engine.components;

import com.base.core.GameObject;
import com.base.core.Transform;
import com.base.rendering.RenderingEngine;
import com.base.rendering.Shader;

public abstract class GameComponent {

	private GameObject parent;
	
	public void input(float delta) {}
	public void update(float delta) {}
	public void render(Shader shader, RenderingEngine renderingEngine) {}
	
	public void setParent(GameObject parent) {
		this.parent = parent;
	}
	
	public Transform getTransform() {
		return parent.getTransform();
	}
	
	public void addToRenderingEngine(RenderingEngine renderingEngine) {}
}

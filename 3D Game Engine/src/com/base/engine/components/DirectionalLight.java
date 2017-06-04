package com.base.engine.components;

import com.base.core.*;
import com.base.rendering.BaseLight;

//Not inheriting due to GLSL being unable to do inheritence
public class DirectionalLight extends GameComponent {
	private BaseLight base;
	private Vector3f direction;
	
	public DirectionalLight(BaseLight base, Vector3f direction) {
		this.base = base;
		this.direction = direction.normalized();
	}
	
	@Override
	public void addToRenderingEngine(RenderingEngine renderingEngine) {
		renderingEngine.addDirectionalLight(this);
	}

	public BaseLight getBase() {
		return base;
	}

	public void setBase(BaseLight base) {
		this.base = base;
	}

	public Vector3f getDirection() {
		return direction;
	}

	public void setDirection(Vector3f direction) {
		this.direction = direction.normalized();
	}
	
}

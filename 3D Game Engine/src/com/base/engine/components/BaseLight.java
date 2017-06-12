package com.base.engine.components;

import com.base.core.*;
import com.base.rendering.*;

public class BaseLight extends GameComponent {
	private Vector3f colour;
	private float intensity;
	private Shader shader;
//	private Transform transform;
	
	
	public BaseLight(Vector3f colour, float intensity) {
		this.colour = colour;
		this.intensity = intensity;
	}
	
	@Override
	public void addToRenderingEngine(RenderingEngine renderingEngine) {
		renderingEngine.addLight(this);
	}
	
	public void setShader(Shader shader) {
		this.shader = shader;
	}
	
	public Shader getShader() {
		return shader;
	}

	public Vector3f getColour() {
		return colour;
	}

	public void setColour(Vector3f colour) {
		this.colour = colour;
	}

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

}

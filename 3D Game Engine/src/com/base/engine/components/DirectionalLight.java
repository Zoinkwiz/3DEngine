package com.base.engine.components;

import com.base.core.*;
import com.base.rendering.ForwardDirectional;

//Not inheriting due to GLSL being unable to do inheritence
public class DirectionalLight extends BaseLight {
//	private BaseLight base;
	private Vector3f direction;
	
	public DirectionalLight(Vector3f colour, float intensity, Vector3f direction) {
		super(colour, intensity);
		this.direction = direction.normalized();
		
		setShader(ForwardDirectional.getInstance());
	}
	

	public Vector3f getDirection() {
		return direction;
	}

	public void setDirection(Vector3f direction) {
		this.direction = direction.normalized();
	}
	
}

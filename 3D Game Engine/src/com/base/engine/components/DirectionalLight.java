package com.base.engine.components;

import com.base.core.*;
import com.base.rendering.ForwardDirectional;

//Not inheriting due to GLSL being unable to do inheritence
public class DirectionalLight extends BaseLight {
	public DirectionalLight(Vector3f colour, float intensity) {
		super(colour, intensity);
		
		setShader(ForwardDirectional.getInstance());
	}
	

	public Vector3f getDirection() {
		return getTransform().getTransformedRot().getForward();
	}
	
}

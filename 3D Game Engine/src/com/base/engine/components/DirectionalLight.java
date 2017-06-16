package com.base.engine.components;

import com.base.engine.core.*;
import com.base.engine.rendering.Shader;

/** Directional Light creator. Extends BaseLight. **/
public class DirectionalLight extends BaseLight {
	public DirectionalLight(Vector3f colour, float intensity) {
		super(colour, intensity);
		setShader(new Shader("forward-directional"));
	}
	

	public Vector3f getDirection() {
		return getTransform().getTransformedRot().getForward();
	}
	
}

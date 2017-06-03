package com.base.rendering;

import com.base.core.Matrix4f;
import com.base.core.Transform;

public class BasicShader extends Shader {
	private static BasicShader instance = null;
	public BasicShader() {
		super();
		
		addVertexShaderFromFile("basicVertex.vs");
		addFragmentShaderFromFile("basicFragment.fs");
		compileShader();
		
		addUniform("transform");
		addUniform("colour");
		
	}
	
	public void updateUniforms(Transform transform,  Material material) {
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture().bind();
		
		setUniform("transform", projectedMatrix);
		setUniform("colour", material.getColour());
	}
	
	public static BasicShader getInstance() {
		if(instance == null) {
			instance = new BasicShader();
		}
		return instance;
	}
}

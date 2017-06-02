package com.base.rendering;

import com.base.core.Matrix4f;

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
	
	public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material) {
		if(material.getTexture()!=null) {
			material.getTexture().bind();
		} else {
			RenderUtil.unbindTextures();
		}
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

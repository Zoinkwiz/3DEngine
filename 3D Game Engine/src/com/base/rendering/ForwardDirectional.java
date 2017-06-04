package com.base.rendering;

import com.base.core.Matrix4f;
import com.base.core.Transform;
import com.base.engine.components.DirectionalLight;

public class ForwardDirectional extends Shader {
	
	private static final ForwardDirectional instance = new ForwardDirectional();
	public static ForwardDirectional getInstance() {return instance;}
	
	public ForwardDirectional() {
		super();
		
		addVertexShaderFromFile("forward-directional.vs");
		addFragmentShaderFromFile("forward-directional.fs");
		
		setAttribLocation("position", 0);
		setAttribLocation("texCoord", 1);
		setAttribLocation("normal", 2);
		
		compileShader();
		
		addUniform("model");
		addUniform("MVP");
		
		addUniform("specularIntensity");
		addUniform("specularPower");
		addUniform("eyePos");
		
		addUniform("directionalLight.base.colour");
		addUniform("directionalLight.base.intensity");
		addUniform("directionalLight.direction");
		
	}
	
	public void updateUniforms(Transform transform,  Material material) {
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture().bind();
		
		setUniform("model", worldMatrix);
		setUniform("MVP", projectedMatrix);
		
		setUniformf("specularIntensity", material.getSpecularIntensity());
		setUniformf("specularPower", material.getspecularPower());
		
		setUniform("eyePos", getRenderingEngine().getMainCamera().getPos());
		setUniform("directionalLight", getRenderingEngine().getDirectionalLight());
	}
	
	
	public void setUniform(String uniformName, BaseLight baseLight) {
		setUniform(uniformName + ".colour", baseLight.getColour());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniform(String uniformName, DirectionalLight directionalLight) {
		setUniform(uniformName + ".base", directionalLight.getBase());
		setUniform(uniformName + ".direction", directionalLight.getDirection());
		
	}	
}

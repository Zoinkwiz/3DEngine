package com.base.engine.components;

import com.base.engine.core.Vector3f;
import com.base.engine.rendering.*;

public class PointLight extends BaseLight {

	private static final int COLOUR_DEPTH = 256;
	
	private Attenuation attenuation;
	private float range;
	
	public PointLight(Vector3f colour, float intensity, Attenuation attenuation) {
		super(colour, intensity);
		this.attenuation = attenuation;
		
		float a = attenuation.getExponent();
		float b = attenuation.getLinear();
		float c = attenuation.getConstant() - COLOUR_DEPTH * getIntensity() * getColour().max();
		//DON'T DO NEGATIVE ATTENUATION
		this.range = (float)(-b + Math.sqrt(b*b - 4*a*c))/(2*a);
		setShader(new Shader("forward-point"));
	}
	
	public Attenuation getAttenuation() {
		return attenuation;
	}
	
	public float getRange() {
		return range;
	}
}

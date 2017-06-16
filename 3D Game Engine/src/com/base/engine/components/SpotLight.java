package com.base.engine.components;

import com.base.engine.core.Vector3f;
import com.base.engine.rendering.*;

/** SpotLight creator, extends PointLight which extends BaseLight. **/
public class SpotLight extends PointLight {
	private float cutoff;
	
	public SpotLight(Vector3f colour, float intensity, Attenuation attenuation, float cutoff) {
		super(colour, intensity, attenuation);
		this.cutoff = cutoff;
		setShader(new Shader("forward-spot"));
	}
	
	public Vector3f getDirection() { return getTransform().getTransformedRot().getForward(); }
	
	public float getCutoff() {
		return cutoff;
	}
	
	public void setCutoff(float cutoff) {
		this.cutoff = cutoff;
}
	
}

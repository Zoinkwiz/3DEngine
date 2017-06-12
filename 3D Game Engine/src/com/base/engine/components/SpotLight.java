package com.base.engine.components;

import com.base.core.Vector3f;
import com.base.rendering.ForwardSpot;

public class SpotLight extends PointLight {
	private Vector3f direction;
	private float cutoff;
	
	public SpotLight(Vector3f colour, float intensity, Vector3f attenuation, float cutoff) {
		super(colour, intensity, attenuation);
//		this.direction = direction.normalized();
		this.cutoff = cutoff;
		
		setShader(ForwardSpot.getInstance());
	}
	
	public Vector3f getDirection() { return getTransform().getRot().getForward(); }
	
	public float getCutoff() {
		return cutoff;
	}
	
	public void setCutoff(float cutoff) {
		this.cutoff = cutoff;
	}
	
}

package com.base.rendering;

import com.base.core.Vector3f;

public class BaseLight {
	private Vector3f colour;
	private float intensity;
	
	public BaseLight(Vector3f colour, float intensity) {
		this.colour = colour;
		this.intensity = intensity;
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

package com.base.rendering;

import com.base.core.Vector3f;

public class Material {

	private Texture texture;
	private Vector3f colour;
	private float specularIntensity;
	private float specularPower; //Focus of spec.
	
	public Material(Texture texture) {
		this(texture, new Vector3f(1,1,1));
	}
	
	public Material(Texture texture, Vector3f colour) {
		this(texture, colour, 2, 32);
	}
	
	public Material(Texture texture, Vector3f colour, float specularIntensity, float specularExponent) {
		this.texture = texture;
		this.colour = colour;
		this.specularIntensity = specularIntensity;
		this.specularPower = specularExponent;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Vector3f getColour() {
		return colour;
	}

	public void setColour(Vector3f colour) {
		this.colour = colour;
	}

	public float getSpecularIntensity() {
		return specularIntensity;
	}

	public void setSpecularIntensity(float specularIntensity) {
		this.specularIntensity = specularIntensity;
	}

	public float getspecularPower() {
		return specularPower;
	}

	public void setspecularPower(float specularExponent) {
		this.specularPower = specularExponent;
	}
}

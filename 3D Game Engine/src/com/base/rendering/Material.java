package com.base.rendering;

import java.util.HashMap;

import com.base.core.Vector3f;

public class Material {

	private HashMap<String, Texture> textureHashMap;
	private HashMap<String, Vector3f> vector3fHashMap;
	private HashMap<String, Float> floatHashMap;


	public Material() {
		textureHashMap = new HashMap<String, Texture>();
		vector3fHashMap = new HashMap<String, Vector3f>();
		floatHashMap = new HashMap<String, Float>();
	}

	public void addTexture(String name, Texture texture) { textureHashMap.put(name, texture); }
	public void addVector3f(String name, Vector3f vec) { vector3fHashMap.put(name, vec); }
	public void addFloat(String name, float f) { floatHashMap.put(name, f); }

	public Texture getTexture(String name) { 
		Texture result =  textureHashMap.get(name); 
		if(result != null) {
			return result;
		}
		
		return new Texture("test.png");
	}
	public Vector3f getVector3f(String name) { 
		Vector3f result = vector3fHashMap.get(name); 
		if(result != null) {
			return result;
		}
		//TODO: Add a debug message saying no specified vector
		return new Vector3f(0,0,0);
	}
	public Float getFloat(String name) {
		Float result = floatHashMap.get(name); 
		if(result != null) {
			return result;
		}
		return 0.0f;
	}
}

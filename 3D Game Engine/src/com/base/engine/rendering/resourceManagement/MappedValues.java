package com.base.engine.rendering.resourceManagement;

import java.util.HashMap;

import com.base.engine.core.Vector3f;

public abstract class MappedValues {
	private HashMap<String, Vector3f> vector3fHashMap;
	private HashMap<String, Float> floatHashMap;
	
	public MappedValues() {
		vector3fHashMap = new HashMap<String, Vector3f>();
		floatHashMap = new HashMap<String, Float>();
	}
	
	public void addVector3f(String name, Vector3f vec) { vector3fHashMap.put(name, vec); }
	public void addFloat(String name, float f) { floatHashMap.put(name, f); }
	
	public Vector3f getVector3f(String name) { 
		Vector3f result = vector3fHashMap.get(name); 
		if(result != null) {
			return result;
		}
		//TODO: Add a debug message saying no specified vector
		//System.out.
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

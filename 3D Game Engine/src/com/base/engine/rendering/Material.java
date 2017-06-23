package com.base.engine.rendering;

import com.base.engine.rendering.resourceManagement.MappedValues;

import java.util.HashMap;

public class Material extends MappedValues {
	private HashMap<String, Texture> textureHashMap;


	public Material() {
		super();
		textureHashMap = new HashMap<String, Texture>();
	}
	
	public Material(Texture diffuse, float specularIntensity, float specularPower, Texture normalMap, Texture dispMap, float dispMapScale, float dispMapOffset) {
		super();
		textureHashMap = new HashMap<String, Texture>();
		addTexture("diffuse", diffuse);
		addFloat("specularIntensity", specularIntensity);
		addFloat("specularPower", specularPower);
		if(normalMap==null) {
			addTexture("normalMap", new Texture("default_normal.jpg"));
		} else {
			addTexture("normalMap", normalMap);
		}
		
		if(dispMap==null) {
			addTexture("dispMap", new Texture("default_disp.png"));
		} else {
			addTexture("dispMap", dispMap);
		}
		
		float baseBias = dispMapScale/2.0f;
		addFloat("dispMapScale", dispMapScale);
		addFloat("dispMapBias", -baseBias + baseBias * dispMapOffset);
	}

	public void addTexture(String name, Texture texture) { textureHashMap.put(name, texture); }

	public Texture getTexture(String name) {
		Texture result = textureHashMap.get(name);
		if(result != null)
			return result;

		return new Texture("test.png");
	}
}

package com.base.core;

import java.util.ArrayList;

public class GameObject {
	private ArrayList<GameObject> children;
	
	public GameObject() {
		children = new ArrayList<GameObject>();
	}
	
	public void addChild(GameObject child) {
		children.add(child);
	}
	
	public void input() {
		for(GameObject child : children) {
			child.input();
		}
	}
	
	public void update() {
		for(GameObject child : children) {
			child.update();
		}
	}
	
	public void render() {
		for(GameObject child : children) {
			child.render();
		}
	}
}

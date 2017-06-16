package com.base.engine.core;

import java.util.ArrayList;

import com.base.engine.components.GameComponent;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

public class GameObject {
	private ArrayList<GameObject> children;
	private ArrayList<GameComponent> components;
	private Transform transform;
	private CoreEngine engine;
	private String name = "Unset";
	
	public GameObject() {
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
		engine = null;
	}
	
	public GameObject(String name) {
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
		this.name = name;
		engine = null;
	}
	
	public void addChild(GameObject child) {
		children.add(child);
		child.setEngine(engine);
		child.getTransform().setParent(transform);
	}
	
	public void removeChild(GameObject child) {
		if(children.contains(child)) {
//			ArrayList<GameObject> listObjs = getAllAttached();
//			for(GameObject obj : listObjs) {
//				System.out.println(obj.getName());
//			}
			System.out.println("The child is: " + child.getName());
			children.remove(child);
			ArrayList<GameObject> listObjs = getAllAttached();
			for(GameObject obj : listObjs) {
				System.out.println(obj.getName());
			}
			System.out.println("Child removed");
		} else if (children != null) {
			for(GameObject kid : children) {
				kid.removeChild(child);
			}
		}
		//child.setEngine(engine);
		//child.getTransform().setParent(transform);
	}
	
	public GameObject addComponent(GameComponent component) {
		components.add(component);
		component.setParent(this);
		
		return this;
	}
	
	public ArrayList<GameComponent> getComponents() {
		return components;
	}
	
	public void inputAll(float delta) {	
		input(delta);
		
		for(GameObject child : children) {
			child.inputAll(delta);
		}
	}
	
	public void input(float delta) {	
		transform.update();
		
		for(GameComponent component : components) {
			component.input(delta);
		}
	}
	
	public void updateAll(float delta) {
		update(delta);
		
		for(GameObject child : children) {
			child.updateAll(delta);
		}
	}
	
	public void update(float delta) {
		for(GameComponent component : components) {
			component.update(delta);
		}
	}
	
	public void renderAll(Shader shader, RenderingEngine renderingEngine) {
		render(shader, renderingEngine);
		
		for(GameObject child : children) {
			child.renderAll(shader, renderingEngine);
		}
	}
	
	public void render(Shader shader, RenderingEngine renderingEngine) {
		for(GameComponent component : components) {
			component.render(shader, renderingEngine);
		}
	}
	
	public ArrayList<GameObject> getAllAttached() {
		ArrayList<GameObject> result = new ArrayList<GameObject>();
		for(GameObject child : children) {
			result.addAll(child.getAllAttached());
		}
		result.add(this);
		return result;
	}
	
	
	public Transform getTransform() {
		return transform;
	}
	
	public void setEngine(CoreEngine engine) {
		if(this.engine != engine) { //If not currently in engine...
			this.engine = engine;
			
			for(GameComponent component : components) {
				component.addToEngine(engine);
			}
			
			for(GameObject child : children) {
				child.setEngine(engine);
			}
		}
	}
	
	public String getName() {
		return name;
	}
}

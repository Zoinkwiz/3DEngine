package com.base.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import java.util.ArrayList;

import com.base.core.GameObject;
import com.base.core.Vector3f;
import com.base.engine.components.*;


public class RenderingEngine {
	
	private Camera mainCamera;
	private Vector3f ambientLight;
	
	
	//More 'Permanent' Structures
	private ArrayList<BaseLight> lights;
	private BaseLight activeLight;
	
	public RenderingEngine() {
		
		lights = new ArrayList<BaseLight>();
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);

		glEnable(GL_DEPTH_CLAMP);
		
		glEnable(GL_TEXTURE_2D);
		
//		mainCamera = new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f);
		
		ambientLight = new Vector3f(0.1f, 0.1f, 0.1f);
	}
	
	public Vector3f getAmbientLight() {
		return ambientLight;
	}
	
	public void render(GameObject object) {
		clearScreen();
		
		lights.clear();
		object.addToRenderingEngine(this);
		
		Shader forwardAmbient = ForwardAmbient.getInstance();
		forwardAmbient.setRenderingEngine(this);
		
		object.render(forwardAmbient);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE); //Add together values
		glDepthMask(false); //Currently checks if pixels is closer than older one. turn off here.
		glDepthFunc(GL_EQUAL); //Only creates pixel if same depth value as current one.
		
		for(BaseLight light : lights) {
			light.getShader().setRenderingEngine(this);
			
			activeLight = light;
			
			object.render(light.getShader());
		}

		
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}
	
	
	private static void clearScreen() {
		//TODO: Stencil Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	
	public static String getOpenGLVersion() {
		return glGetString(GL_VERSION);
	}
	
//	public void addDirectionalLight(DirectionalLight directionalLight) {
//		directionalLights.add(directionalLight);
//	}
//	
//	public void addPointLight(PointLight pointLight) {
//		pointLights.add(pointLight);
//	}
	
	public void addLight(BaseLight light) {
		lights.add(light);
	}
	
	public void addCamera(Camera camera) {
		mainCamera = camera;
	}
	
	public BaseLight getActiveLight() { return activeLight; }

	public Camera getMainCamera() { return mainCamera; }

	public void setMainCamera(Camera mainCamera) { this.mainCamera = mainCamera; }
	
	
}

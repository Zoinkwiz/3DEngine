package com.base.core;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import java.util.ArrayList;

import com.base.engine.components.DirectionalLight;
import com.base.engine.components.PointLight;
import com.base.rendering.*;

public class RenderingEngine {
	private Camera mainCamera;
	private Vector3f ambientLight;
	private DirectionalLight activeDirectionalLight;
	private PointLight activePointLight;
	private SpotLight spotLight;
	
	//"Permanent" Structures
	private ArrayList<DirectionalLight> directionalLights;
	private ArrayList<PointLight> pointLights;	
	public RenderingEngine() {
		
		directionalLights = new ArrayList<DirectionalLight>();
		pointLights = new ArrayList<PointLight>();
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);

		glEnable(GL_DEPTH_CLAMP);
		
		glEnable(GL_TEXTURE_2D);
		
		mainCamera = new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f);
		
		ambientLight = new Vector3f(0.1f, 0.1f, 0.1f);
//		directionalLight = new DirectionalLight(new BaseLight(new Vector3f(0,0,1), 0.4f), new Vector3f(1,1,1));
//		directionalLight2 = new DirectionalLight(new BaseLight(new Vector3f(1,0,0), 0.4f), new Vector3f(-1,1,-1));
//		pointLight = new PointLight(new BaseLight(new Vector3f(0,1,0), 0.6f), new Attenuation(0,0,1), new Vector3f(5,0,5), 100);
//		spotLight = new SpotLight(new PointLight(new BaseLight(new Vector3f(0,1,1), 0.4f), 
//								  new Attenuation(0,0,0.1f), 
//								  new Vector3f(5,-0.5f,5), 100), 
//								  new Vector3f(1,0,0), 0.7f);
	}
	
	public Vector3f getAmbientLight() {
		return ambientLight;
	}
	
	public DirectionalLight getDirectionalLight() {
		return activeDirectionalLight;
	}
	
	public PointLight getPointLight() {
		return activePointLight;
	}
	
	public SpotLight getSpotLight() {
		return spotLight;
	}
	
	public void input(float delta) {
		mainCamera.input(delta);
	}
	
	public void render(GameObject object) {
		clearScreen();
		
		clearLightList();
		object.addToRenderingEngine(this);
		
		Shader forwardAmbient = ForwardAmbient.getInstance();
		Shader forwardDirectional = ForwardDirectional.getInstance();
		Shader forwardPoint = ForwardPoint.getInstance();
//		Shader forwardSpot = ForwardSpot.getInstance();
		forwardAmbient.setRenderingEngine(this);
		forwardDirectional.setRenderingEngine(this);
		forwardPoint.setRenderingEngine(this);
//		forwardSpot.setRenderingEngine(this);
		
		object.render(forwardAmbient);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE); //Add together values
		glDepthMask(false); //Currently checks if pixels is closer than older one. turn off here.
		glDepthFunc(GL_EQUAL); //Only creates pixel if same depth value as current one.
		
		
		for(DirectionalLight light : directionalLights) {
			activeDirectionalLight = light;
			object.render(forwardDirectional);
		}
		
		for(PointLight light : pointLights) {
			activePointLight = light;
			object.render(forwardPoint);
		}

		
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}
	
	private void clearLightList() {
		directionalLights.clear();
		pointLights.clear();
	}
	
	private static void clearScreen() {
		//TODO: Stencil Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	
	public static String getOpenGLVersion() {
		return glGetString(GL_VERSION);
	}
	
	public void addDirectionalLight(DirectionalLight directionalLight) {
		directionalLights.add(directionalLight);
	}
	
	public void addPointLight(PointLight pointLight) {
		pointLights.add(pointLight);
	}

	public Camera getMainCamera() {
		return mainCamera;
	}

	public void setMainCamera(Camera mainCamera) {
		this.mainCamera = mainCamera;
	}
	
	
}

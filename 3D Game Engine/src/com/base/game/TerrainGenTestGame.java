package com.base.game;

import com.base.engine.components.*;
import com.base.engine.core.*;
import com.base.engine.core.input.Input;
import com.base.engine.generation.TerrainGeneration;
import com.base.engine.rendering.*;
import static org.lwjgl.glfw.GLFW.*;

public class TerrainGenTestGame extends Game {
	
	GameObject planeObject;
	GameObject testPlane;
	
	//public final int 
	
	public void init() {
		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;
		
		Material material = new Material();//(new Texture("test.png"), new Vector3f(1,1,1), 1, 8);
		material.addTexture("diffuse", new Texture("test.png"));
		material.addFloat("specularIntensity", 1.0f);
		material.addFloat("specularPower", 8.0f);
		
		Vertex[] vertices = new Vertex[] {  new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f,0.0f)),
											new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth*3), new Vector2f(0.0f,1.0f)),
											new Vertex(new Vector3f(fieldWidth*3, 0.0f, -fieldDepth), new Vector2f(1.0f,0.0f)),
											new Vertex(new Vector3f(fieldWidth*3, 0.0f, fieldDepth*3), new Vector2f(1.0f,1.0f))
		};
		
		int[] indices = new int[] { 0,1,2,
				2,1,3};
		
		Mesh meshTest = new Mesh(vertices, indices, true);
		MeshRenderer meshRendererTest = new MeshRenderer(meshTest, material);
		testPlane = new GameObject("testPlane");
		testPlane.addComponent(meshRendererTest);
		addToScene(testPlane);	
		
		testPlane.getTransform().setPos(0, 40, 0);
		
		GameObject directionalLightObject = new GameObject("DirectionaLight");
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(64.0f/255.0f, 156.0f/255.0f, 255.0f/255.0f), 0.4f);
		directionalLightObject.addComponent(directionalLight);
		directionalLightObject.getTransform().setRot(new Quaternion(new Vector3f(1,0,0),(float)Math.toRadians(-90)));
		directionalLight.getTransform().setRot(new Quaternion(new Vector3f(1,0,0),(float)Math.toRadians(-45)));

		
		Material materialMix = new Material();//(new Texture("test.png"), new Vector3f(1,1,1), 1, 8);
		materialMix.addTexture("diffuse", new Texture("mix.png"));
		materialMix.addFloat("specularIntensity", 1.0f);
		materialMix.addFloat("specularPower", 8.0f);

		Mesh mesh = TerrainGeneration.generatePerlinNoise(-5000, 5000, -5000, 5000, 1000f, 1000, TerrainGeneration.PERLIN);
		MeshRenderer meshRenderer = new MeshRenderer(mesh, materialMix);
		planeObject = new GameObject("Terrain");
		planeObject.addComponent(meshRenderer);
		addToScene(planeObject);		
		
		addToScene(directionalLightObject);
		
		GameObject cameraObject = new GameObject("Camera").addComponent(new FreeLook(0.5f)).addComponent(new FreeMove(10)).addComponent(new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 8000.0f));
		addToScene(cameraObject);
		cameraObject.getTransform().setPos(0, 20, 0);
	}

	int presses = 0;
	@Override
	public void update(float delta) {
		super.update(delta);
		if(Input.getKeyUp(GLFW_KEY_SPACE) && presses==0) {
			presses = 1;
		    removeFromScene(planeObject);
			Mesh meshx = TerrainGeneration.generatePerlinNoise(-5000, 5000, -5000, 5000, 1000f, 1000, TerrainGeneration.BILLOW);
			Material materialx = new Material();//(new Texture("test.png"), new Vector3f(1,1,1), 1, 8);
			materialx.addTexture("diffuse", new Texture("test.png"));
			materialx.addFloat("specularIntensity", 1.0f);
			materialx.addFloat("specularPower", 8.0f);
			
			MeshRenderer meshRenderex = new MeshRenderer(meshx, materialx);
			planeObject = new GameObject();
			planeObject.addComponent(meshRenderex);
			addToScene(planeObject);
			
		}
	}
}
